package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
* 給与個人単価設定: Finder
*/
public class SalaryPerUnitPriceSettingFinder
{

    @Inject
    private SalaryPerUnitPriceSettingRepository finder;

    public List<SalaryPerUnitPriceSettingDto> getAllSalaryPerUnitPriceSetting(){
        return finder.getAllSalaryPerUnitPriceSetting().stream().map(item -> new SalaryPerUnitPriceSettingDto(item))
                .collect(Collectors.toList());
    }

    public Optional<SalaryPerUnitPriceSettingDto> getSalaryPerUnitPriceSettingById(String cid, String code){
        return finder.getSalaryPerUnitPriceSettingById(cid, code).map(item -> new SalaryPerUnitPriceSettingDto(item));
    }

}
