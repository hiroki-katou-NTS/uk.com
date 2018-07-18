package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutCtgItem;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;

@Stateless
public class CtgItemDataFinder {
	@Inject
	private AcquisitionExternalOutputCategory acquisitionCategory;
	
	@Inject
	private AcquisitionExOutCtgItem mAcquisitionExOutCtgItem;

	public List<CtgItemDataDto> getAllCategoryItem(Integer categoryId) {
		return acquisitionCategory.getExternalOutputCategoryItem(categoryId, null).stream().map(item -> {
			return new CtgItemDataDto(item.getItemNo().v(), item.getItemName());
		}).collect(Collectors.toList());
	}
	public List<CtgItemData> getAllCtgItemData(int categoryId,int ctgItemNo) {
		return mAcquisitionExOutCtgItem.getListExOutCtgItemData(categoryId,ctgItemNo);
	}
}
