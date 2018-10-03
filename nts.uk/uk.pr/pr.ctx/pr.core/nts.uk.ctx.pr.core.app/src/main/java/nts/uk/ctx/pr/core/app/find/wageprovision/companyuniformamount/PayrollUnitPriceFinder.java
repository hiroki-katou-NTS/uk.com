package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceRepository;

@Stateless
/**
* 給与会社単価: Finder
*/
public class PayrollUnitPriceFinder
{

    @Inject
    private PayrollUnitPriceRepository finder;

    public List<PayrollUnitPriceDto> getAllpayrollUnitPrice(){
        return finder.getAllPayrollUnitPrice().stream().map(item -> PayrollUnitPriceDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
