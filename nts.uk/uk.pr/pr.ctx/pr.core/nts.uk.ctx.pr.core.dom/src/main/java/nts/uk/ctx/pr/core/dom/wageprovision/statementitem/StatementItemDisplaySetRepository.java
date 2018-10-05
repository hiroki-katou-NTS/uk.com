package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

/**
 * 明細書項目の表示設定
 */
public interface StatementItemDisplaySetRepository {

	List<StatementItemDisplaySet> getAllSpecItemDispSet();

	Optional<StatementItemDisplaySet> getSpecItemDispSetById(String cid, String salaryItemId);

	void add(StatementItemDisplaySet domain);

	void update(StatementItemDisplaySet domain);

	void remove(String cid, String salaryItemId);

}