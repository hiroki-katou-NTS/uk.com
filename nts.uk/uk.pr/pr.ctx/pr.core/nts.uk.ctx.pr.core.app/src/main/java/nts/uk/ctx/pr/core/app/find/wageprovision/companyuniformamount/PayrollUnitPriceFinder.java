package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 給与会社単価: Finder
*/
@Stateless
public class PayrollUnitPriceFinder
{

    @Inject
    private PayrollUnitPriceRepository finder;

    public List<PayrollUnitPriceDto> getAllPayrollUnitPriceByCID(String cid){
        return finder.getAllPayrollUnitPriceByCID(cid).stream().map(item -> PayrollUnitPriceDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public Optional<PayrollUnitPriceDto> getPayrollUnitPriceById(String code, String cid){
        Optional<PayrollUnitPrice> payrollUnitPrice = finder.getPayrollUnitPriceById(code,cid);
        if(payrollUnitPrice.isPresent()){
            return payrollUnitPrice.map(item -> PayrollUnitPriceDto.fromDomain(item));
        }
        return Optional.empty();
    }


}
