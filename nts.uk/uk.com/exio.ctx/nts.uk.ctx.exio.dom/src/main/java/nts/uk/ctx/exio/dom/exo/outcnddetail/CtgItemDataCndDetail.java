package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;

@Value
@AllArgsConstructor
public class CtgItemDataCndDetail {
	private List<CtgItemData> dataItemsDetail;
	
	private OutCndDetail dataCndDetail;
}
