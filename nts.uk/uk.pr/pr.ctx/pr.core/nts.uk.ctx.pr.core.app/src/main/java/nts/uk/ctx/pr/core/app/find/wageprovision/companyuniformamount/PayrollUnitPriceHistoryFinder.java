package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;


import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 給与会社単価履歴: Finder
*/
@Stateless
public class PayrollUnitPriceHistoryFinder {

    @Inject
    private PayrollUnitPriceHistoryRepository finder;

    @Inject
    private PayrollUnitPriceRepository payrollUnitPriceRepository;

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
            if(payrollUnitPriceHistory.get().getHistory().size() > 0) {
                return Optional.ofNullable(new PayrollUnitPriceHistoryDto(payrollUnitPriceHistory.get().getCId(),
                        payrollUnitPriceHistory.get().getHistory().get(0).identifier(),
                        payrollUnitPriceHistory.get().getCode().v(),
                        payrollUnitPriceHistory.get().getHistory().get(0).start().v(),
                        payrollUnitPriceHistory.get().getHistory().get(0).end().v()
                ));
            }
        }
        return Optional.empty();
    }

    public List<PayrollUnitPriceHistoryListDto> getAllHistoryById(String cid){
        List<PayrollUnitPriceHistoryListDto> list = new ArrayList<PayrollUnitPriceHistoryListDto>();
        List<PayrollUnitPrice> listPayrollUnitPrice = payrollUnitPriceRepository.getAllPayrollUnitPriceByCID(cid);
        listPayrollUnitPrice.forEach(payrollUnitPrice ->{
            Optional<PayrollUnitPriceHistory> oPayrollUnitPriceHistory = finder.getPayrollUnitPriceHistoryByCidCode(cid,payrollUnitPrice.getCode().v());
            if(oPayrollUnitPriceHistory.isPresent()){
                PayrollUnitPriceHistory payrollUnitPriceHistory = oPayrollUnitPriceHistory.get();
                list.add(new PayrollUnitPriceHistoryListDto(payrollUnitPrice.getCode().v(),payrollUnitPriceHistory.getHistory().get(0).identifier(),payrollUnitPrice.getName().v(),PayrollUnitPriceHistoryDto.fromDomain(payrollUnitPriceHistory)));
            }
        });
        return list;
    }


}
