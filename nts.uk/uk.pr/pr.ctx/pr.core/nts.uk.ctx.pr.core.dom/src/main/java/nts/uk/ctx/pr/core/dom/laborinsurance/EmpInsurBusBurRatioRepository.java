package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.List;

import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 雇用保険料率
*/
public interface EmpInsurBusBurRatioRepository {

	EmpInsurHis getEmpInsurHisByCid(String cid);
	
	List<EmpInsurBusBurRatio> getEmpInsurPreRateById(String cid, String historyId);

	void add(EmpInsurPreRate domain, String cId, YearMonthHistoryItem item);

    void update(EmpInsurPreRate domain, String cId, YearMonthHistoryItem item);
    
    void update(YearMonthHistoryItem item , String cId);

    void remove(String cId, String historyId);
   

}
