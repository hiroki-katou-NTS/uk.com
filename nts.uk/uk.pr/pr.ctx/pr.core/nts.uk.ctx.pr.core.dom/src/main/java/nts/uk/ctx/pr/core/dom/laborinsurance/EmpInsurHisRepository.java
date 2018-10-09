package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.Optional;

import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 労災保険履歴
*/
public interface EmpInsurHisRepository
{


    Optional<EmpInsurHis> getEmpInsurHisByCid(String cid);

    Optional<EmpInsurHis> getEmpInsurHisById(String cid, String hisId);

    void add(YearMonthHistoryItem domain, String cId);

    void update(YearMonthHistoryItem domain, String cId);

    void remove(String cid, String hisId);

}
