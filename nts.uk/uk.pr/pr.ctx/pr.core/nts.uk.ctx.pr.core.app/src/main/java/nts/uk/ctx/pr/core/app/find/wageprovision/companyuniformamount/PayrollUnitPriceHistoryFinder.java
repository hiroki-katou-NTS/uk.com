package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;


import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
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
        List<PayrollUnitPriceHistoryDto> result = new ArrayList<PayrollUnitPriceHistoryDto>();
        if (mPayrollUnitPriceHistory.isPresent() && mPayrollUnitPriceHistory.get().getHistory() != null) {
            result = PayrollUnitPriceHistoryDto.fromDomain(mPayrollUnitPriceHistory.get());
        }
        return result;
    }

    public List<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHistoryByCidCode(String cid, String code){

        Optional<PayrollUnitPriceHistory> mPayrollUnitPriceHistory =  finder.getPayrollUnitPriceHistoryByCidCode(cid,code);
        List<PayrollUnitPriceHistoryDto> result = new ArrayList<PayrollUnitPriceHistoryDto>();
        if (mPayrollUnitPriceHistory.isPresent() && mPayrollUnitPriceHistory.get().getHistory() != null) {
            result = PayrollUnitPriceHistoryDto.fromDomain(mPayrollUnitPriceHistory.get());
        }
        return result;
    }

    public Optional<PayrollUnitPriceHistoryDto> getPayrollUnitPriceHistoryById(String cid, String code, String hisId){
        Optional<PayrollUnitPriceHistory> payrollUnitPriceHistory = finder.getPayrollUnitPriceHistoryById(cid,code,hisId);

        if(payrollUnitPriceHistory.isPresent()){
            return Optional.ofNullable(new PayrollUnitPriceHistoryDto(payrollUnitPriceHistory.get().getCId(),
                    payrollUnitPriceHistory.get().getHistory().get(0).identifier(),
                    payrollUnitPriceHistory.get().getCode().v(),
                    payrollUnitPriceHistory.get().getHistory().get(0).start().v(),
                    payrollUnitPriceHistory.get().getHistory().get(0).end().v()
            ));
        }
        return Optional.empty();
    }

}
