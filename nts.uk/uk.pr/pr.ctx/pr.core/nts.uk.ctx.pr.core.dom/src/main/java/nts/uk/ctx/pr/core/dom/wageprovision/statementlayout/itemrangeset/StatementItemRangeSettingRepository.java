package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset;

import java.util.Optional;
import java.util.List;

/**
* itemrangeset
*/
public interface StatementItemRangeSettingRepository
{

    List<StatementItemRangeSetting> getAllStatementItemRangeSetting();

    void add(StatementItemRangeSetting domain);

    void update(StatementItemRangeSetting domain);

}
