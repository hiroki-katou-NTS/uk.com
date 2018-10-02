package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSettingRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与会社単価設定: Finder
*/
public class PayrollUnitPriceSettingFinder
{

    @Inject
    private PayrollUnitPriceSettingRepository finder;

    public List<PayrollUnitPriceSettingDto> getAllpayrollUnitPriceSetting(){
        return finder.getAllpayrollUnitPriceSetting().stream().map(item -> PayrollUnitPriceSettingDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
