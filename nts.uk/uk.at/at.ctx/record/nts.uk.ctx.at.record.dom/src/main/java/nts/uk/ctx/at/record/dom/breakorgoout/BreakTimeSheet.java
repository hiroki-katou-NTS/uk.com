package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BreakClassification;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionClassification;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 休憩時間帯
 *
 */
@Getter
@AllArgsConstructor
public class BreakTimeSheet extends DomainObject {
	
	//休憩枠NO
	private BreakFrameNo breakFrameNo;
	
	//開始 - 勤怠打刻(実打刻付き)
	private TimeWithDayAttr startTime;
	
	//終了 - 勤怠打刻(実打刻付き)
	private TimeWithDayAttr endTime;
	
	/** 休憩時間: 勤怠時間 */
	private AttendanceTime breakTime;
	
	/**
	 * 指定された時間帯に重複する休憩時間帯の重複時間（分）を返す
	 * @param baseTimeSheet
	 * @return　重複する時間（分）　　重複していない場合は0を返す
	 */
	public int calculateMinutesDuplicatedWith(TimeSpanForCalc baseTimeSheet) {
		return baseTimeSheet.getDuplicatedWith(baseTimeSheet)
				.map(ts -> ts.lengthAsMinutes())
				.orElse(0);
	}
	
	/**
	 * 自分自身を控除項目の時間帯に変換する
	 * @return 控除項目の時間帯
	 */
	public TimeSheetOfDeductionItem toTimeSheetOfDeductionItem() {
		return TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new TimeZoneRounding(this.startTime, this.endTime, null),
																			  new TimeSpanForCalc(this.startTime, this.endTime),
																			  Collections.emptyList(),
																			  Collections.emptyList(),
																			  Collections.emptyList(),
																			  Collections.emptyList(),
																			  Optional.empty(),
																			  Finally.empty(),
																			  Finally.of(BreakClassification.BREAK),
																			  Optional.empty(),
																			  DeductionClassification.BREAK
																			  );
	}
}
