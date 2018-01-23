package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;


public class GetDataDaily implements Callable<List<DailyModifyResult>> {

	private DailyModifyQueryProcessor dailyModifyQueryProcessor;

	private List<String> sids;

	private DateRange dateRange;

	private List<Integer> itemIds;

	public GetDataDaily(List<String> sids, DateRange dateRange, List<Integer> itemIds, DailyModifyQueryProcessor dailyModifyQueryProcessor) {
		this.sids = sids;
		this.dateRange = dateRange;
		this.itemIds = itemIds;
		this.dailyModifyQueryProcessor = dailyModifyQueryProcessor;
	}

	@Override
	public List<DailyModifyResult> call() throws Exception {
		List<DailyModifyResult> results = new ArrayList<>();
		for (int i = 0; i < sids.size(); i++) {
			for (int j = 0; j < dateRange.toListDate().size(); j++) {
				DailyModifyResult result = dailyModifyQueryProcessor
						.initScreen(new DailyModifyQuery(sids.get(i), dateRange.toListDate().get(j), null), itemIds);
				if (result != null)
					results.add(result);
			}
		}
		return results;
	}

}
