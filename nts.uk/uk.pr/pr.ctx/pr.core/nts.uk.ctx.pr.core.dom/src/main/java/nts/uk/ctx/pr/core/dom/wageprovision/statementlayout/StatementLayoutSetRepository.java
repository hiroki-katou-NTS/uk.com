package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
 * 明細書レイアウト設定
 */
public interface StatementLayoutSetRepository {

    List<StatementLayoutSet> getAllStatementLayoutSet();

    Optional<StatementLayoutSet> getStatementLayoutSetById(String cid, String code, String histId);

    void add(String statementCd, StatementLayoutSet domain);

    void update(StatementLayoutSet domain);

    void remove(String histId);

}
