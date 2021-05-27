package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.ArrayList;

import nts.uk.ctx.exio.dom.input.validation.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.ImportableItems;
import nts.uk.ctx.exio.dom.input.validation.ImportableItemsRepository;

public class JpaImportableItemsRepository implements ImportableItemsRepository{

	@Override
	public ImportableItems get(String categoryId) {
		return new ImportableItems(new ArrayList<ImportableItem>());
	}

}
