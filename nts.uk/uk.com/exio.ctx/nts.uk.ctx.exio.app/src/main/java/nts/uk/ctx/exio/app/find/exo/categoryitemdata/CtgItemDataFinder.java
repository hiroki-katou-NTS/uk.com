package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

	public List<CtgItemDataDto> getAllCategoryItem(Integer categoryId, Integer dataType) {
		return acquisitionCategory.getExternalOutputCategoryItem(categoryId, null).stream()
				.filter(c -> c.getDataType().value == dataType).map(item -> {
					return new CtgItemDataDto(item.getItemNo().v(), item.getItemName());
				}).collect(Collectors.toList());
	}
	public List<CtgItemData> getAllCtgItemData(int categoryId,int ctgItemNo) {
		return mAcquisitionExOutCtgItem.getListExOutCtgItemData(categoryId,ctgItemNo);
	}
	public CtgItemDataCndDetailDto getDataItemDetail(int categoryId,int ctgItemNo) {
		CtgItemDataCndDetail data = mStdOutputCondSetService.outputExCndList(categoryId, ctgItemNo);
		return new CtgItemDataCndDetailDto(data.getDataItemsDetail(), data.getDataTableName(), data.getDataCndItemsDetail());
	}
}
