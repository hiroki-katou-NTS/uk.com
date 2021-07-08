package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;

public interface ImportableItemsRepository {
	
	Optional<ImportableItem> get(ImportingGroupId groupId, int itemNo);
	
	public List<ImportableItem> get(ImportingGroupId categoryId);
}
