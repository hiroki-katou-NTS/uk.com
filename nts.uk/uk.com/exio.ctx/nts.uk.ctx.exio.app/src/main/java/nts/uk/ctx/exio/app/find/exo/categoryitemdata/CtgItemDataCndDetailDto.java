package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
@Value
@Getter
@Setter
@AllArgsConstructor
public class CtgItemDataCndDetailDto {
	/**
	 * Dto screen D
	 */
	private List<CtgItemData> dataItemsDetail;

	private List<String> dataTableName;
	
	private List<OutCndDetailItem> dataCndItemsDetail;
	
	
}
