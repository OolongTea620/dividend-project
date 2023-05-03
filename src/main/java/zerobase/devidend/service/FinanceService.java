package zerobase.devidend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.devidend.model.Company;
import zerobase.devidend.model.Dividend;
import zerobase.devidend.model.ScrapedResult;
import zerobase.devidend.persist.CompanyRepository;
import zerobase.devidend.persist.DividendRepository;
import zerobase.devidend.persist.entity.CompanyEntity;
import zerobase.devidend.persist.entity.DividendEntity;

@Service
@AllArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public ScrapedResult getDividendByCompanyName(String companyName) {
        //1. 회사명 기준으로 회사 정보를 조회

        CompanyEntity company = this.companyRepository.findByName(companyName)
                                    .orElseThrow(()-> new RuntimeException("존재하지 않는 회사명 입니다"));

        //2. 조회된 회사 Id로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        //3. 결과 조합 후 반납
        List<Dividend> dividends = dividendEntities.stream()
            .map(e -> Dividend.builder()
                .date(e.getDate())
                .dividend(e.getDividend())
                .build())
            .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                                        .ticker(company.getTicker())
                                        .name(company.getName())
                                        .build(),
                                dividends);
    }
}
