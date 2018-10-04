package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceRepository;


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

    public Optional<PayrollUnitPriceDto> getPayrollUnitPriceById(String cid, String code){
        return finder.getPayrollUnitPriceById(cid,code).map(item ->PayrollUnitPriceDto.fromDomain(item));
    }


}
