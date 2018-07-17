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
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;

@Getter
@Setter
public class ExOutSettingResult {
	
	private StdOutputCondSet stdOutputCondSet;
	private OutCndDetailItem outCndDetailItem;
	private Optional<ExOutCtg> exOutCtg;
	private Optional<ExCndOutput> exCndOutput;
	private List<OutputItemCustom> outputItemCustomList;
	private List<CtgItemData> ctgItemDataList;
	private String condSql;
	
	public ExOutSettingResult(StdOutputCondSet stdOutputCondSet, OutCndDetailItem outCndDetailItem,
			Optional<ExOutCtg> exOutCtg, Optional<ExCndOutput> exCndOutput,
			List<OutputItemCustom> outputItemCustomList, List<CtgItemData> ctgItemDataList, String condSql) {
		super();
		this.stdOutputCondSet = stdOutputCondSet;
		this.outCndDetailItem = outCndDetailItem;
		this.exOutCtg = exOutCtg;
		this.exCndOutput = exCndOutput;
		this.outputItemCustomList = outputItemCustomList;
		this.ctgItemDataList = ctgItemDataList;
		this.condSql = condSql;
	}
	
}
