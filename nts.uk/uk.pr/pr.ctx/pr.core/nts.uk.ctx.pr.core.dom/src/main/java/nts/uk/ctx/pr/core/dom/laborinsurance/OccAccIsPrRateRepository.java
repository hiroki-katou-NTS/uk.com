package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.List;

import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 労災保険料率
*/
public interface OccAccIsPrRateRepository {

    OccAccIsPrRate getOccAccIsPrRateByHisId(String cId, String hisId);
    
    OccAccIsHis getOccAccIsHisByCid(String cId);
    
    void add(List<OccAccInsurBusiBurdenRatio> domain,String cId, YearMonthHistoryItem yearMonthHistory);

    void update(List<OccAccInsurBusiBurdenRatio> domain,String cId, YearMonthHistoryItem yearMonthHistory);
    
    void update(String cId, YearMonthHistoryItem yearMonthHistory);
    
    void remove(String cId, String hisId);
}
