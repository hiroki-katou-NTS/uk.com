package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
@Value
@Getter
@Setter
@AllArgsConstructor
public class CtgItemDataCndDetailDto {
	/**
	 * 
	 */
	private List<CtgItemData> dataItemsDetail;

	/**
	 * 項目名
	 */
	private List<String> dataTableName;
}
