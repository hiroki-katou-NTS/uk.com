package nts.uk.ctx.exio.dom.exo.categoryitemdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;

@Value
@AllArgsConstructor
public class CtgItemDataCndDetail {
	private List<CtgItemData> dataItemsDetail;
	
	private List<OutCndDetailItem> dataCndItemsDetail;
}
