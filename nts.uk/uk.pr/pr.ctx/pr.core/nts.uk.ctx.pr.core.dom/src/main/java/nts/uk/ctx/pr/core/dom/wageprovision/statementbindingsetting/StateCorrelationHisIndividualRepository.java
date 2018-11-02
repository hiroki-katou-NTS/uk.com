package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;

import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 明細書紐付け履歴（個人）
*/
public interface StateCorrelationHisIndividualRepository {

    Optional<StateCorrelationHisIndividual> getStateCorrelationHisIndividualById(String empId, String hisId);

    void add(String empId, YearMonthHistoryItem history);

    void update(String empId, YearMonthHistoryItem history);

    void remove(String empId, String hisId);

}
