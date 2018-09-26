package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exo.category.ExOutCtgDto;
import nts.uk.ctx.exio.app.find.exo.menu.RoleAuthorityDto;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;

@Stateless
public class CtgItemDataFinder {
	@Inject
	private AcquisitionExternalOutputCategory acquisitionCategory;

	@Inject
	private AcquisitionExternalOutputCategory mAcquisitionExOutCtgItem;

	public List<CtgItemDataDto> getAllCategoryItem(Integer categoryId, Integer dataType) {
		return acquisitionCategory.getExternalOutputCategoryItem(categoryId, null).stream()
				.filter(c -> c.getDataType().value == dataType).map(item -> {
					return new CtgItemDataDto(item.getItemNo().v(), item.getItemName());
				}).collect(Collectors.toList());
	}

	public List<CtgItemData> getAllCtgItemData(int categoryId, int ctgItemNo) {
		return mAcquisitionExOutCtgItem.getExternalOutputCategoryItem(categoryId, ctgItemNo);
	}

	public List<ExOutCtgDto> getExternalOutputCategoryList(RoleAuthorityDto param) {
		if(param == null){
			return new ArrayList<ExOutCtgDto>();
		}
		List<ExOutCtgDto> lstCategory = acquisitionCategory.getExternalOutputCategoryList(param.getEmpRole()).stream()
				.map(item -> ExOutCtgDto.fromDomain(item)).collect(Collectors.toList());
		return lstCategory;
	}
}
