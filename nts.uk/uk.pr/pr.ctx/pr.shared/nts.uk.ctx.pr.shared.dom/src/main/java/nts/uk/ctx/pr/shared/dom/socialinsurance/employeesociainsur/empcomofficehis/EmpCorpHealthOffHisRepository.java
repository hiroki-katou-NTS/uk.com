package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

import java.util.List;
import java.util.Optional;

/**
* 社員社保事業所所属履歴
*/
public interface EmpCorpHealthOffHisRepository
{

    List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis();

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId);

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId);

    Optional<EmpCorpHealthOffHis> getBySidDesc(String employeeId);

    Optional<EmpCorpHealthOffHis> getBySidAsc(String employeeId);

    Optional<String> getSocialInsuranceOfficeCd(String cid, String employeeId, GeneralDate baseDate);

    Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(List<String> employeeIds, GeneralDate startDate);

    Optional<EmpCorpHealthOffHis> getBySidAndBaseDate(String sid, GeneralDate baseDate);

    List<EmpCorpHealthOffHis> getByCidAndSids(List<String> sids);

    void add(EmpCorpHealthOffHis domain, DateHistoryItem itemAdded, AffOfficeInformation itemInfo);

    void update(DateHistoryItem domain);

    void update(DateHistoryItem historyItem, AffOfficeInformation info);

    void delete(String hisId, String sid);

    void addAll(List<EmpCorpHealthOffParam> domains);

    void updateAll(List<DateHistoryItem> items);


}
