package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;


import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSettingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.text.html.Option;

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

    public Optional<PayrollUnitPriceSettingDto> getpayrollUnitPriceSettingById(String hisId){
        Optional<PayrollUnitPriceSetting> payrollUnitPriceSetting = finder.getpayrollUnitPriceSettingById(hisId);

        if(payrollUnitPriceSetting.isPresent()){
            return payrollUnitPriceSetting.map(item -> PayrollUnitPriceSettingDto.fromDomain(item));
        }
        return Optional.empty();
    }

}
