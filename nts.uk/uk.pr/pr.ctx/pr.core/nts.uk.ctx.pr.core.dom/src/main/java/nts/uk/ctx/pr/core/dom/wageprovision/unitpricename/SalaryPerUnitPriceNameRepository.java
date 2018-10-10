package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;

/**
* 給与個人単価名称
*/
public interface SalaryPerUnitPriceNameRepository
{

    List<SalaryPerUnitPriceName> getAllSalaryPerUnitPriceName();

    Optional<SalaryPerUnitPriceName> getSalaryPerUnitPriceNameById(String cid, String code);

    void add(SalaryPerUnitPriceName domain);

    void update(SalaryPerUnitPriceName domain);

    void remove(String cid, String code);

}
