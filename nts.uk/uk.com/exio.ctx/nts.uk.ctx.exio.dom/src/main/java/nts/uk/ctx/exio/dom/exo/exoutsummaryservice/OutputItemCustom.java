package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;

@Setter
@Getter
@NoArgsConstructor
public class OutputItemCustom {
	private StandardOutputItem standardOutputItem;
	private DataFormatSetting dataFormatSetting;
	private List<CtgItemData> ctgItemDataList;
	
	public OutputItemCustom(StandardOutputItem standardOutputItem, DataFormatSetting dataFormatSetting,
			List<CtgItemData> ctgItemDataList) {
		super();
		this.standardOutputItem = standardOutputItem;
		this.dataFormatSetting = dataFormatSetting;
		this.ctgItemDataList = ctgItemDataList;
	}
}
