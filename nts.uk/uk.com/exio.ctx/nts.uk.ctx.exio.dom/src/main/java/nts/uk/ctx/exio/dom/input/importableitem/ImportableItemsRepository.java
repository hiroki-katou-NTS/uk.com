package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

public interface ImportableItemsRepository {
	
	Optional<ImportableItem> get(ImportingDomainId domainId, int itemNo);
	
	public List<ImportableItem> get(ImportingDomainId categoryId);
}
