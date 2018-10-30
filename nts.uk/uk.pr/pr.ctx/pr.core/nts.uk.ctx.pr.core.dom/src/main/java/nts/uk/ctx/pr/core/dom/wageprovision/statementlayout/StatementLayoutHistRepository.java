package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト履歴
*/
public interface StatementLayoutHistRepository
{

    List<StatementLayoutHist> getAllStatementLayoutHist();

    Optional<StatementLayoutHist> getStatementLayoutHistById(String cid, int specCd, String histId);

    void add(StatementLayoutHist domain);

    void update(StatementLayoutHist domain);

    void remove(String cid, int specCd, String histId);

}
