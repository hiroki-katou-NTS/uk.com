package nts.uk.ctx.exio.infra.repository.input.validation;

import nts.uk.ctx.exio.dom.input.validation.CheckMethod;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.ImportableItemsRepository;

public class JpaImportableItemsRepository implements ImportableItemsRepository{

	@Override
	public ImportableItem get(String categoryId) {
		return new ImportableItem(
				999,
				999,
				"DUMMY",
				CheckMethod.NO_CHECK);
	}

}
