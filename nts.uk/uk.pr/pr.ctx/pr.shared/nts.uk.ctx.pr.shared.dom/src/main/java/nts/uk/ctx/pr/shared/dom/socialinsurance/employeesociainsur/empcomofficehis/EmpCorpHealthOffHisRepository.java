package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

/**
* 社員社保事業所所属履歴
*/
public interface EmpCorpHealthOffHisRepository
{

    List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis();
    
    Map<String, DateHistoryItem>  getAllEmpCorpHealthOffHisBySidsAndBaseDate(String cid, List<String> sids, GeneralDate standardDate);

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId);

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId);

    Optional<EmpCorpHealthOffHis> getBySidDesc(String employeeId);

    Optional<EmpCorpHealthOffHis> getBySidAsc(String employeeId);

    Optional<String> getSocialInsuranceOfficeCd(String cid, String employeeId, GeneralDate baseDate);

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(List<String> employeeIds, GeneralDate startDate);

    Optional<EmpCorpHealthOffHis> getBySidAndBaseDate(String sid, GeneralDate baseDate);

    List<EmpCorpHealthOffHis> getByCidAndSids(List<String> sids);
    
    List<EmpCorpHealthOffHis> getByCidAndSidsDesc(String cid, List<String> sids);

    void add(EmpCorpHealthOffHis domain, DateHistoryItem itemAdded, AffOfficeInformation itemInfo);
    
    void update(DateHistoryItem domain);

    void update(DateHistoryItem historyItem, AffOfficeInformation info);

    void delete(String hisId, String sid);

    void addAll(List<EmpCorpHealthOffParam> domains);
    
    void updateAllCps003(List<EmpCorpHealthOffParam> domains);

    void updateAll(List<DateHistoryItem> items);


}
