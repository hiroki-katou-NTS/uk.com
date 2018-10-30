package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト
*/
public interface StatementLayoutRepository
{

    List<StatementLayout> getAllStatementLayout();

    Optional<StatementLayout> getStatementLayoutById(String cid, String specCd);

    void add(StatementLayout domain);

    void update(StatementLayout domain);

    void remove(String cid, String specCd);

}
