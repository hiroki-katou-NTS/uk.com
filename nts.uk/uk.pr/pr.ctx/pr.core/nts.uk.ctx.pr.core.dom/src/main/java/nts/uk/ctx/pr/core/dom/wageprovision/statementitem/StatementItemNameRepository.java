package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

/**
 * 明細書項目名称
 */
public interface StatementItemNameRepository {

	List<StatementItemName> getAllSpecItemName();

	Optional<StatementItemName> getSpecItemNameById(String cid, String salaryItemId);

	void add(StatementItemName domain);

	void update(StatementItemName domain);

	void remove(String cid, String salaryItemId);

}
