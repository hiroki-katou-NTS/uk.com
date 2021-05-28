package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.ArrayList;

import nts.uk.ctx.exio.dom.input.validation.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.ImportableItemsRepository;

public class JpaImportableItemsRepository implements ImportableItemsRepository{

	@Override
	public ImportableItem get(String categoryId) {
		return new ImportableItem(new ArrayList<ImportableItem>());
	}

}
