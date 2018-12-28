package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset;

import java.util.Optional;
import java.util.List;

/**
* itemrangeset
*/
public interface StatementItemRangeSettingRepository
{

    List<StatementItemRangeSetting> getAllStatementItemRangeSetting();

    Optional<StatementItemRangeSetting> getStatementItemRangeSettingById(String histId);

    void add(StatementItemRangeSetting domain, int categoryAtr, int lineNumber, int itemPosition);

    void update(StatementItemRangeSetting domain, int categoryAtr, int lineNumber, int itemPosition);

    void remove(String histId, int categoryAtr, int lineNumber, int itemPosition);

}
