package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

/**
* 明細書紐付け履歴（個人）
*/
public interface StateCorreHisIndiviRepository {

    Optional<StateCorreHisIndivi> getStateCorrelationHisIndividualById(String empId, String hisId);

    Optional<StateCorreHisIndivi> getStateCorrelationHisIndividualByEmpId(String empId);

    Optional<StateCorreHisIndivi> getStateCorrelationHisIndividualByDate(String empId, GeneralDate date);

    Optional<StateLinkSetIndivi> getStateLinkSettingIndividualById(String empId, String hisId);


    void add(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void update(String empId, YearMonthHistoryItem history);

    void update(String empId, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void remove(String cid, String hisId);

}
