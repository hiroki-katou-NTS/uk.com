package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

/**
* 明細書紐付け履歴（会社）
*/
public interface StateCorrelationHisCompanyRepository {

    Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyById(String cid, String hisId);

    Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyById(String cid);

    void add(String cid, YearMonthHistoryItem history);

    void update(String cid, YearMonthHistoryItem history);

    void remove(String cid, String hisId);

}
