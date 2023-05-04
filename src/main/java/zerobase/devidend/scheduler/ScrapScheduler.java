package zerobase.devidend.scheduler;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.devidend.model.Company;
import zerobase.devidend.model.ScrapedResult;
import zerobase.devidend.persist.CompanyRepository;
import zerobase.devidend.persist.DividendRepository;
import zerobase.devidend.persist.entity.CompanyEntity;
import zerobase.devidend.persist.entity.DividendEntity;
import zerobase.devidend.scraper.Scraper;

@Slf4j
@Component
@AllArgsConstructor
public class ScrapScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper yahooFinanceScraper;

    @Scheduled(cron= "${scheduler.scrap.yahoo}")
    public void yahoofinanceScheduling() {
        // 저장된 회사 목록을 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();
        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company: companies) {
            log.info("scraping scheduler is started ->" + company.getName());
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
                Company.builder()
                    .name(company.getName())
                    .ticker(company.getTicker())
                    .build());
            // 데이터 베이스에 없는 경우, 값 저장
            scrapedResult.getDividends().stream()
                .map(e -> new DividendEntity(company.getId(), e))
                .forEach(e -> {
                    boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                    if (!exists) {
                        this.dividendRepository.save(e);
                    }
                });
            // 연속적으로 스크래핑 대상 사이트에 서버에 요청을 올리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // InterruptedException : 인터럽트를 받는 스레드가 blocking 될 수 있는 메소드를 실행할 때 발생
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
