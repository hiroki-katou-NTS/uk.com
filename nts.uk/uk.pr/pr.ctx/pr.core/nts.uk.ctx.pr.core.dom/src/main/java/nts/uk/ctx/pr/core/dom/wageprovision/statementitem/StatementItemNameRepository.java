package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

/**
 * 明細書項目名称
 */
public interface StatementItemNameRepository {

	List<StatementItemName> getAllStatementItemName();

	Optional<StatementItemName> getStatementItemNameById(String cid, String salaryItemId);
	
	List<StatementItemName> getStatementItemNameByListSalaryItemId(String cid, List<String> salaryItemIds);

	void add(StatementItemName domain);

	void update(StatementItemName domain);
	
	void updateListStatementItemName(List<StatementItemName> domain);

	void remove(String cid, String salaryItemId);

}
