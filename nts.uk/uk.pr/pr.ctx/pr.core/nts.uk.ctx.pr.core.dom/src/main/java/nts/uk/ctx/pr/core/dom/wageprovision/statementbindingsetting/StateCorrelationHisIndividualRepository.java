package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

/**
* 明細書紐付け履歴（個人）
*/
public interface StateCorrelationHisIndividualRepository {

    Optional<StateCorrelationHisIndividual> getStateCorrelationHisIndividualById(String empId, String hisId);

    Optional<StateCorrelationHisIndividual> getStateCorrelationHisIndividualByEmpId(String empId);

    Optional<StateCorrelationHisIndividual> getStateCorrelationHisIndividualByDate(String empId,GeneralDate date);

    Optional<StateLinkSettingIndividual> getStateLinkSettingIndividualById(String empId, String hisId);


    void add(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void update(String empId, YearMonthHistoryItem history);

    void update(String empId, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void remove(String cid, String hisId);

}
