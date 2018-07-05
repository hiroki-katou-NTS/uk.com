package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;

@AllArgsConstructor
@Getter
public class ExOutSummarySetting {
	private List<StdOutItem> stdOutItemList;
	private List<CtgItemDataCustom> ctgItemDataCustomList;
}
