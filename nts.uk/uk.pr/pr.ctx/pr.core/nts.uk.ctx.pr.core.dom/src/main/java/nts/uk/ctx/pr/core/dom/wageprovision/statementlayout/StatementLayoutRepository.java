package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト
*/
public interface StatementLayoutRepository
{
    List<StatementLayout> getStatementCode(String cid, String salaryCd, int startYearMonth);

    List<StatementLayout> getAllStatementLayout();

    Optional<StatementLayout> getStatementLayoutById(String cid, String statementCd);

    void add(StatementLayout domain);

    void update(StatementLayout domain);

    void remove(String cid, String statementCd);

}
