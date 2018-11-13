package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 給与汎用パラメータ年月履歴
*/
public interface SalGenParaYMHistRepository
{

    Optional<SalGenParaYearMonthHistory> getAllSalGenParaYMHist(String cid,String paraNo);

    Optional<SalGenParaYearMonthHistory> getSalGenParaYMHistById(String paraNo, String cid, String hisId);

    void add(YearMonthHistoryItem domain, String cId, String paraNo);

    void update(YearMonthHistoryItem domain, String cId,String paraNo);

    void remove(String paraNo, String cid, String hisId);

}
