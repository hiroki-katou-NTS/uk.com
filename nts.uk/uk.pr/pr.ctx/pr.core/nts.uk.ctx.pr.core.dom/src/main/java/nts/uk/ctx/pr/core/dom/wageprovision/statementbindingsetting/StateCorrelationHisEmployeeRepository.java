package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

/**
* 明細書紐付け履歴（雇用）
*/
public interface StateCorrelationHisEmployeeRepository {

    Optional<StateCorrelationHisEmployee> getStateCorrelationHisEmployeeById(String cid, String hisId);

    void add(String cid, YearMonthHistoryItem history);

    void update(String cid, YearMonthHistoryItem history);

    void remove(String cid, String hisId);

}
