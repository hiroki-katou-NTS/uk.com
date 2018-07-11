package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;

@Stateless
public class CtgItemDataFinder {
	@Inject
	private AcquisitionExternalOutputCategory acquisitionCategory;

	public List<CtgItemDataDto> getAllCategoryItem(String categoryId) {
		return acquisitionCategory.getExternalOutputCategoryItem(categoryId, null).stream().map(item -> {
			return new CtgItemDataDto(item.getItemNo().v(), item.getItemName());
		}).collect(Collectors.toList());
	}
}
