package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;


import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 給与会社単価履歴: Finder
*/
@Stateless
public class PayrollUnitPriceHistoryFinder
{

    @Inject
    private PayrollUnitPriceHistoryRepository finder;

    public List<PayrollUnitPriceHistoryDto> getAllPayrollUnitPriceHistory(){
        return finder.getAllPayrollUnitPriceHistory().stream().map(item -> PayrollUnitPriceHistoryDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    public PayrollUnitPriceHistoryDto getPayrollUnitPriceHis(String hisId){
        String cId = AppContexts.user().companyId();
        return PayrollUnitPriceHistoryDto.fromDomain(finder.getPayrollUnitPriceHistoryById(cId,hisId).get());
    }

}
