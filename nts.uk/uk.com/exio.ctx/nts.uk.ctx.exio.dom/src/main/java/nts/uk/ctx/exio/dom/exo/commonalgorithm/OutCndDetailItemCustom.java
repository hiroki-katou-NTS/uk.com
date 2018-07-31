package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;

@Getter
@Setter
public class OutCndDetailItemCustom extends OutCndDetailItem {
	private String joinedSearchCodeList;
	private Optional<CtgItemData> ctgItemData;
	
	public OutCndDetailItemCustom(OutCndDetailItem outCndDetailItem) {
		super(outCndDetailItem.getConditionSettingCd(), outCndDetailItem.getCategoryId(),
				outCndDetailItem.getCategoryItemNo(), outCndDetailItem.getSeriNum(), outCndDetailItem.getCid(),
				outCndDetailItem.getUserId(), outCndDetailItem.getConditionSymbol(), outCndDetailItem.getSearchNum(),
				outCndDetailItem.getSearchNumEndVal(), outCndDetailItem.getSearchNumStartVal(),
				outCndDetailItem.getSearchChar(), outCndDetailItem.getSearchCharEndVal(),
				outCndDetailItem.getSearchCharStartVal(), outCndDetailItem.getSearchDate(),
				outCndDetailItem.getSearchDateEnd(), outCndDetailItem.getSearchDateStart(),
				outCndDetailItem.getSearchClock(), outCndDetailItem.getSearchClockEndVal(),
				outCndDetailItem.getSearchClockStartVal(), outCndDetailItem.getSearchTime(),
				outCndDetailItem.getSearchTimeEndVal(), outCndDetailItem.getSearchTimeStartVal(),
				outCndDetailItem.getListSearchCodeList());
	}
}
