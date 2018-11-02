package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 明細書紐付け履歴（職位）
*/
public interface StateCorrelationHisPositionRepository {

    Optional<StateCorrelationHisPosition> getStateCorrelationHisPositionById(String cid, String hisId);

    Optional<StateCorrelationHisPosition> getStateCorrelationHisClassificationByCid(String cId);

    void add(String cid, YearMonthHistoryItem history);

    void update(String cid, YearMonthHistoryItem history);

    void remove(String cid, String hisId);

}
