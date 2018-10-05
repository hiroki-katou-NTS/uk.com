package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQueryProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyMultiQuery;
import nts.uk.shr.com.time.calendar.date.ClosureDate;


public class GetDataMonthly {

	private MonthlyModifyQueryProcessor monthlyModifyQueryProcessor;

	private List<String> sids;
	
	private List<Integer> itemIds;

	private ClosureId closureId;
	
	private ClosureDate closureDate;
	
	private YearMonth yearMonth;
	
	public GetDataMonthly(List<String> sids, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, List<Integer> itemIds, MonthlyModifyQueryProcessor monthlyModifyQueryProcessor) {
		this.sids = sids;
		this.itemIds = itemIds;
		this.closureId = closureId;
		this.yearMonth = yearMonth;
		this.closureDate = closureDate;
		this.monthlyModifyQueryProcessor = monthlyModifyQueryProcessor;
	}

	public List<MonthlyModifyResult> call() {
		if(sids.isEmpty()|| itemIds.isEmpty()) return new ArrayList<>();
		List<MonthlyModifyResult> results  = monthlyModifyQueryProcessor.initScreen(new MonthlyMultiQuery(sids), itemIds, yearMonth, closureId, closureDate);
		return results;
	}

}
