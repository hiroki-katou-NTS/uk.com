package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailymodify.query.DailyMultiQuery;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


public class GetDataDaily {

	private DailyModifyQueryProcessor dailyModifyQueryProcessor;

	private List<String> sids;

	private DateRange dateRange;

	private List<Integer> itemIds;
	
	private Map<String, List<GeneralDate>> mapDate;

	public GetDataDaily(List<String> sids, DateRange dateRange, List<Integer> itemIds, DailyModifyQueryProcessor dailyModifyQueryProcessor) {
		this.sids = sids;
		this.dateRange = dateRange;
		this.itemIds = itemIds;
		this.dailyModifyQueryProcessor = dailyModifyQueryProcessor;
	}
	
	public GetDataDaily(Map<String, List<GeneralDate>> mapDate, DailyModifyQueryProcessor dailyModifyQueryProcessor){
		this.mapDate = mapDate;
		this.dailyModifyQueryProcessor = dailyModifyQueryProcessor;
	}

	public List<DailyModifyResult> call() {
		if(sids.isEmpty()|| itemIds.isEmpty()) return new ArrayList<>();
		List<DailyModifyResult> results  = dailyModifyQueryProcessor.initScreen(new DailyMultiQuery(sids, new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate())), itemIds);
		return results;
	}

	public Pair<List<DailyModifyResult>, List<DailyRecordDto>> getAllData() {
		if(sids.isEmpty()|| itemIds.isEmpty()) return Pair.of(Collections.emptyList(), Collections.emptyList());
		return dailyModifyQueryProcessor.initScreen(new DailyMultiQuery(sids, new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate())));
	}
	
	public Pair<List<DailyModifyResult>, List<DailyRecordDto>> getDataRow(){
		return dailyModifyQueryProcessor.getRow(mapDate);
	}
}
