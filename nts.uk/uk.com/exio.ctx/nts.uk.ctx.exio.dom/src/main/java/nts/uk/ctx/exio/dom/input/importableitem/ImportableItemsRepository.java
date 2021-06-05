package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.List;

public interface ImportableItemsRepository {
	public List<ImportableItem> get(String companyId, int categoryId);
}
