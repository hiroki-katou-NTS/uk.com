package nts.uk.ctx.exio.app.find.exo.exoutsummarysetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.CtgItemDataCustom;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.ExOutSummarySetting;

@Getter
public class ExOutSummarySettingDto {

	private List<StdOutItemCustom> ctdOutItemCustomList;
	private List<CtgItemDataCustom> ctgItemDataCustomList;
	
	public ExOutSummarySettingDto(ExOutSummarySetting exOutSummarySetting) {
		this.ctgItemDataCustomList = exOutSummarySetting.getCtgItemDataCustomList();
		this.ctdOutItemCustomList = exOutSummarySetting.getStdOutItemList().stream()
				.map(outItem -> new StdOutItemCustom(outItem)).collect(Collectors.toList());
	}
	
}
