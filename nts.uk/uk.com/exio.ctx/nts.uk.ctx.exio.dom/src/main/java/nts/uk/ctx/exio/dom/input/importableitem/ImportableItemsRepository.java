package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.List;

import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

public interface ImportableItemsRepository {
	public List<ImportableItem> get(ImportingGroupId categoryId);
}
