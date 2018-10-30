package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;

/**
* 給与個人単価名称
*/
public interface SalaryPerUnitPriceRepository
{

    List<SalaryPerUnitPrice> getAllSalaryPerUnitPrice();

    Optional<SalaryPerUnitPrice> getSalaryPerUnitPriceById(String cid, String code);

    void add(SalaryPerUnitPrice domain);

    void update(SalaryPerUnitPrice domain);

    void remove(String cid, String code);

}
