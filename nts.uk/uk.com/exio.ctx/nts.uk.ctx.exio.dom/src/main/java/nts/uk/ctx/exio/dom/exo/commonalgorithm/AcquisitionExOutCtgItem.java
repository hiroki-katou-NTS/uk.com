package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
@Stateless
public class AcquisitionExOutCtgItem {
	@Inject
	private CtgItemDataRepository mCtgItemDataRepository;
	
	@Inject
	private StdOutputCondSetService mStdOutputCondSetService;
	
	
	/**
	 * 外部出力カテゴリ取得項目
	*/
	public List<CtgItemData> getListExOutCtgItemData(int categoryId, int itemNo ) {
		 int displayClassfication = 1;		
		return mStdOutputCondSetService.filterCtgItemByDataType(mCtgItemDataRepository.getByIdAndDisplayClass(categoryId,Optional.ofNullable(itemNo) ,displayClassfication));
				
				
				
	}
}
