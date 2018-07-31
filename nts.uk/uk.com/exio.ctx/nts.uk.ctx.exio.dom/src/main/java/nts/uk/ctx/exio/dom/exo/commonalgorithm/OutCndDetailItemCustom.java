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
}
