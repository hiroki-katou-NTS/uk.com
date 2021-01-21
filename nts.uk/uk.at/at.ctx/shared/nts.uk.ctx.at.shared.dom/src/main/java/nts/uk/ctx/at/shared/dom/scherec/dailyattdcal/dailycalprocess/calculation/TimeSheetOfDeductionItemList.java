package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除項目の時間帯(List)
 *
 */
@AllArgsConstructor
public class TimeSheetOfDeductionItemList {

	/** 控除項目の時間帯(List) */
	private List<TimeSheetOfDeductionItem> items = new ArrayList<>();
	
	/**
	 * 渡された計算範囲と重複する控除項目の時間帯(List)を求める
	 * @param calcRange 計算範囲
	 * @return 重複している控除項目の時間帯
	 */
	public List<TimeSheetOfDeductionItem> getOverlappingTimeSheets(TimeSpanForDailyCalc calcRange){
		
		//重複している控除項目の時間帯
		List<TimeSheetOfDeductionItem> overlappingDeductionTimeSheets = new ArrayList<>();
		
		for(TimeSheetOfDeductionItem item : this.items) {
			//重複している時間帯
			Optional<TimeSpanForDailyCalc> overlappingTime = Optional.empty();
			overlappingTime = item.getTimeSheet().getDuplicatedWith(calcRange);
			if(!overlappingTime.isPresent()) continue;
			
			//重複している控除項目の時間帯に追加
			overlappingDeductionTimeSheets.add(
					item.replaceTimeSpan(Optional.of(new TimeSpanForDailyCalc(
							overlappingTime.get().getStart(),
							overlappingTime.get().getEnd()))));
		}
		return overlappingDeductionTimeSheets;
	}
	
	/**
	 * 控除時間帯分、終了時刻をズラす
	 * @param TimeSpan 時間帯
	 * @return ズラした後の終了時刻
	 */
	public TimeWithDayAttr forwardByDeductionTime(TimeSpanForDailyCalc TimeSpan) {
		//重複している時間帯
		List<TimeSpanForDailyCalc> overlapptingTimes = this.items.stream()
				.filter(item -> item.getTimeSheet().getDuplicatedWith(TimeSpan).isPresent())
				.map(item -> item.getTimeSheet().getDuplicatedWith(TimeSpan).get())
				.collect(Collectors.toList());
		//控除時間分、終了時刻を遅くする
		return TimeSpan.getEnd().forwardByMinutes(overlapptingTimes.stream()
				.mapToInt(time -> time.lengthAsMinutes())
				.sum());
	}
}
