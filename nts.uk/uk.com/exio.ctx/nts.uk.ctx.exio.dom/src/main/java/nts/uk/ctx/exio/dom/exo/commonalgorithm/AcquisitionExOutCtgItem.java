package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;

public class AcquisitionExOutCtgItem {
	@Inject
	private CtgItemDataRepository mCtgItemDataRepository;
	
	
	/**
	 * 外部出力カテゴリ取得項目
	*/
	public List<CtgItemData> getListExOutCtgItemData(int categoryId, int itemNo ) {
		 int displayClassfication = 1;		
		return mCtgItemDataRepository.getByIdAndDisplayClass(categoryId,Optional.ofNullable(itemNo) ,displayClassfication);
	}
}
