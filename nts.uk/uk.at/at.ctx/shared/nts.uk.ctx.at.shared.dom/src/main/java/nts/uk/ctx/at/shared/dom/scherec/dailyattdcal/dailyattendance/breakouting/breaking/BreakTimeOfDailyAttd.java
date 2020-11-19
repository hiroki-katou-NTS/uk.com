package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別勤怠の休憩時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.休憩・外出.休憩.日別勤怠の休憩時間帯
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class BreakTimeOfDailyAttd implements DomainObject {
	//休憩種類
	private BreakType breakType;
	//時間帯
	private List<BreakTimeSheet> breakTimeSheets;
	public BreakTimeOfDailyAttd(BreakType breakType, List<BreakTimeSheet> breakTimeSheets) {
		super();
		this.breakType = breakType;
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
	 * 休憩枠NOで休憩時刻を取る
	 * @param breakFrameNo 休憩枠NO
	 * @param startTime 開始時刻か
	 * @return
	 */
	public Optional<TimeWithDayAttr> getBreakTimeWithNo(BreakFrameNo breakFrameNo, boolean startTime) {
		
		Optional<BreakTimeSheet> breakTimeSheet = this.breakTimeSheets.stream()
															.filter( sheet -> sheet.getBreakFrameNo().equals(breakFrameNo))
															.findFirst();
		if ( !breakTimeSheet.isPresent() ) {
			return Optional.empty();
		}
		
		TimeWithDayAttr timeWithDay = startTime ? breakTimeSheet.get().getStartTime() : breakTimeSheet.get().getEndTime();
		return Optional.of(timeWithDay);
	}
	
}
