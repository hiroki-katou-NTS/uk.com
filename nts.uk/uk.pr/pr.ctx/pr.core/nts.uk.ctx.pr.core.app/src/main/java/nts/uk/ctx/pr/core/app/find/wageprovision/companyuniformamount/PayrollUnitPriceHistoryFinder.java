package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;


import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 給与会社単価履歴: Finder
*/
@Stateless
public class PayrollUnitPriceHistoryFinder {

    @Inject
    private PayrollUnitPriceHistoryRepository finder;


    public List<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHis(String hisId,String code){
        String cId = AppContexts.user().companyId();
        Optional<PayrollUnitPriceHistory> mPayrollUnitPriceHistory =  finder.getPayrollUnitPriceHistoryById(cId,code,hisId);
        List<PayrollUnitPriceHistoryDto> resulf = new ArrayList<PayrollUnitPriceHistoryDto>();
        if (mPayrollUnitPriceHistory.isPresent() && mPayrollUnitPriceHistory.get().getHistory() != null) {
            resulf = PayrollUnitPriceHistoryDto.fromDomain(mPayrollUnitPriceHistory.get());
        }
        return resulf;


    }

    public List<PayrollUnitPriceHistoryDto> getAllPayrollUnitPriceHistoryByCidAndCode(String cid, String code){
//        return finder.getPayrollUnitPriceHistoryByCidCode(cid,code).stream().map(item -> PayrollUnitPriceHistoryDto.fromDomain(item))
//                .collect(Collectors.toList());
        return null;
    }

}
