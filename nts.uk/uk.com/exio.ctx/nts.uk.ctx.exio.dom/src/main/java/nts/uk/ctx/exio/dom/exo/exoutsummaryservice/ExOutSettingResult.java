package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutput;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;

@Getter
@Setter
public class ExOutSettingResult {
	
	private StdOutputCondSet stdOutputCondSet;
	private List<OutCndDetailItem> outCndDetailItem;
	private Optional<ExOutCtg> exOutCtg;
	private Optional<ExCndOutput> exCndOutput;
	private List<OutputItemCustom> outputItemCustomList;
	private List<CtgItemData> ctgItemDataList;
	
	public ExOutSettingResult(StdOutputCondSet stdOutputCondSet, List<OutCndDetailItem> outCndDetailItem,
			Optional<ExOutCtg> exOutCtg, Optional<ExCndOutput> exCndOutput,
			List<OutputItemCustom> outputItemCustomList, List<CtgItemData> ctgItemDataList) {
		super();
		this.stdOutputCondSet = stdOutputCondSet;
		this.outCndDetailItem = outCndDetailItem;
		this.exOutCtg = exOutCtg;
		this.exCndOutput = exCndOutput;
		this.outputItemCustomList = outputItemCustomList;
		this.ctgItemDataList = ctgItemDataList;
	}
	
}
