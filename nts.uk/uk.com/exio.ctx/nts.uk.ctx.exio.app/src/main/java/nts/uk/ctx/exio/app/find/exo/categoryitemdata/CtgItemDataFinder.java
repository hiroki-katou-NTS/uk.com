package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exo.category.ExOutCtgDto;
import nts.uk.ctx.exio.app.find.exo.menu.RoleAuthorityDto;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataCndDetail;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExOutCtgItem;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;

@Stateless
public class CtgItemDataFinder {
	@Inject
	private AcquisitionExternalOutputCategory acquisitionCategory;
	
	@Inject
	private AcquisitionExOutCtgItem mAcquisitionExOutCtgItem;
	
	@Inject
	private StdOutputCondSetService mStdOutputCondSetService;

	public List<CtgItemDataDto> getAllCategoryItem(Integer categoryId) {
		return acquisitionCategory.getExternalOutputCategoryItem(categoryId, null).stream().map(item -> {
			return new CtgItemDataDto(item.getItemNo().v(), item.getItemName());
		}).collect(Collectors.toList());
	}
	
	public List<CtgItemData> getAllCtgItemData(int categoryId,int ctgItemNo) {
		return mAcquisitionExOutCtgItem.getListExOutCtgItemData(categoryId,ctgItemNo);
	}
	
	public CtgItemDataCndDetailDto getDataItemDetail(String condSetCd, int categoryId) {
		CtgItemDataCndDetail domain = mStdOutputCondSetService.outputExCndList(condSetCd, categoryId);
		return CtgItemDataCndDetailDto.fromDomain(domain);
	}
	
	public List<ExOutCtgDto> getExternalOutputCategoryList(RoleAuthorityDto param) {
		return acquisitionCategory.getExternalOutputCategoryList(param.getEmpRole()).stream()
				.map(item -> ExOutCtgDto.fromDomain(item)).collect(Collectors.toList());
	}
}

