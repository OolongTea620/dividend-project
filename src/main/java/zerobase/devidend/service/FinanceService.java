package zerobase.devidend.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zerobase.devidend.model.Company;
import zerobase.devidend.model.Dividend;
import zerobase.devidend.model.ScrapedResult;
import zerobase.devidend.persist.CompanyRepository;
import zerobase.devidend.persist.DividendRepository;
import zerobase.devidend.persist.entity.CompanyEntity;
import zerobase.devidend.persist.entity.DividendEntity;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    // 요청이 자주 발생되는 가?
    // 자주 변경되는 데이터 인가?
    // -> 캐싱에 적합한 상황이다.
    @Cacheable(key="#Companyname" , value = "finance")
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.info("search company -> " + companyName);
        //1. 회사명 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                                    .orElseThrow(()-> new RuntimeException("존재하지 않는 회사명 입니다"));
        //2. 조회된 회사 Id로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        //3. 결과 조합 후 반납
        List<Dividend> dividends =
                dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(), e.getDividend()))
                .collect(Collectors.toList());

        return new ScrapedResult(new Company(company.getTicker(), company.getName()) ,
                                dividends);
    }
}
