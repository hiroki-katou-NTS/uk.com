package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.Optional;
import java.util.List;

/**
 * 明細書項目
 */
public interface StatementItemRepository {

	List<StatementItem> getAllStatementItem();
	
	List<StatementItem> getAllItemByCid(String cid);
	
	List<StatementItem> getByCategory(String cid, int categoryAtr);
	
	List<StatementItem> getByCategoryAndCode(String cid, int categoryAtr, String itemNameCd);

	Optional<StatementItem> getStatementItemById(String cid, int categoryAtr, String itemNameCd);

	List<StatementItemCustom> getItemCustomByCategoryAndDeprecated(String cid, int categoryAtr, boolean isIncludeDeprecated);

	List<StatementItemCustom> getItemCustomByDeprecated(String cid, boolean isIncludeDeprecated);

	List<StatementItemCustom> getItemCustomByCtgAndExcludeCodes(String cid,  int categoryAtr, List<String> itemNameCdExs);

	void add(StatementItem domain);

	void update(StatementItem domain);

	void remove(String cid, int categoryAtr, String itemNameCd);

}
