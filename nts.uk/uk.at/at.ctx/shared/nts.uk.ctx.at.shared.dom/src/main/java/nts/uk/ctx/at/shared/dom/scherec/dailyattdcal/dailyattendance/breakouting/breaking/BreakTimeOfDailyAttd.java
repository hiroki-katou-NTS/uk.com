package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;

/**
 * 日別勤怠の休憩時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.休憩・外出.休憩.日別勤怠の休憩時間帯
 * @author tutk
 *
 */
@Getter
public class BreakTimeOfDailyAttd implements DomainObject {
	
	//時間帯
	private List<BreakTimeSheet> breakTimeSheets;
	
	public BreakTimeOfDailyAttd() {
		super();
		this.breakTimeSheets = new ArrayList<>();
	}
	
	public BreakTimeOfDailyAttd(List<BreakTimeSheet> breakTimeSheets) {
		super();
		this.breakTimeSheets = breakTimeSheets;
	}
	
	/**
	 * 休憩時間帯を全て控除項目の時間帯に変換する(パラメータ固定)
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> changeAllTimeSheetToDeductionItem(){
		return this.breakTimeSheets.stream().map(tc -> tc.toTimeSheetOfDeductionItem()).collect(Collectors.toList());
	}
	
	/**
	 * 休憩時間帯と重複するか
	 * @param target
	 * @return
	 */
	public boolean isDuplicatedWithBreakTime(TimeSpanForCalc target) {
		
		return this.breakTimeSheets.stream()
				.map( sheet -> sheet.convertToTimeSpanForCalc() )
				.anyMatch( timespan -> timespan.checkDuplication(target).isDuplicated() );
	}
	
}
