package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;

import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 明細書紐付け履歴（給与分類）
*/
public interface StateCorrelationHisSalaryRepository {

    Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryById(String cid, String hisId);

    Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryByCid(String cid);

    void add(String cid, YearMonthHistoryItem history);

    void update(String cid, YearMonthHistoryItem history);

    void remove(String cid, String hisId);

}
