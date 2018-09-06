package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.Optional;
import java.util.List;

/**
 * 明細書項目
 */
public interface BillingItemRepository {

	List<BillingItem> getAllBillingItem();

	Optional<BillingItem> getBillingItemById(String cid, int categoryAtr, int itemNameCd, String salaryItemId);

	void add(BillingItem domain);

	void update(BillingItem domain);

	void remove(String cid, int categoryAtr, int itemNameCd, String salaryItemId);

}
