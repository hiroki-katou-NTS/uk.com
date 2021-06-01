package nts.uk.ctx.exio.infra.repository.input.validation;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.revise.dataformat.ItemType;
import nts.uk.ctx.exio.dom.input.validation.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.ImportableItemsRepository;

public class JpaImportableItemsRepository implements ImportableItemsRepository{

	@Override
	public ImportableItem get(String categoryId) {
		return new ImportableItem(
				999,
				999,
				ItemType.CHARACTER,
				Optional.empty());
	}

}
