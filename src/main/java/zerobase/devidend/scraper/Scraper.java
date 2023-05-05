package zerobase.devidend.scraper;

import zerobase.devidend.model.Company;
import zerobase.devidend.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
