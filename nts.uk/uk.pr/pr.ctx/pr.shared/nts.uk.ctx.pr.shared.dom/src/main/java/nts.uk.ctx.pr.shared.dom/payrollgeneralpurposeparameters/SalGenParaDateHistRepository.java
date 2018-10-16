package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistory;
import nts.uk.shr.com.history.DateHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 給与汎用パラメータ年月日履歴
*/
public interface SalGenParaDateHistRepository
{

    Optional<SalGenParaDateHistory> getAllSalGenParaDateHist(String cid,String paraNo);

    Optional<SalGenParaDateHistory> getSalGenParaDateHistById(String paraNo, String cid, String hisId);

    void add(DateHistoryItem domain, String paraNo, String cId);

    void update(DateHistoryItem domain,String paraNo,String cId);

    void remove(String paraNo, String cid, String hisId);

}
