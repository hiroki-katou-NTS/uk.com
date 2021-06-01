package nts.uk.ctx.exio.dom.input.validation;

import java.util.List;

public interface ImportableItemsRepository {
	public List<ImportableItem> get(String companyId, int categoryId);
}
