package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;

/**
* 給与個人単価設定
*/
public interface SalaryPerUnitPriceSettingRepository
{

    List<SalaryPerUnitPriceSetting> getAllSalaryPerUnitPriceSetting();

    Optional<SalaryPerUnitPriceSetting> getSalaryPerUnitPriceSettingById(String cid, String code);

    void add(SalaryPerUnitPriceSetting domain);

    void update(SalaryPerUnitPriceSetting domain);

    void remove(String cid, String code);

}
