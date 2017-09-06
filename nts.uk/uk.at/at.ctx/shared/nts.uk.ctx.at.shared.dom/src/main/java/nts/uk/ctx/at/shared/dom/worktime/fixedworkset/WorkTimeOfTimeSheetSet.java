package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間の時間帯設定
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkTimeOfTimeSheetSet implements HasTimeSpanForCalc<WorkTimeOfTimeSheetSet> {
	
	/** 就業時間枠NO */
	private final int frameNo;
	
	/** 時間帯 */
	private final TimeSpanWithRounding timeSpan;
	
	
	/**
	 * 開始と終了だけ変更した新しいインスタンスを作る
	 * @param start 開始時刻
	 * @param end 終了時刻
	 * @return 新しいインスタンス
	 */
	@Override
	public WorkTimeOfTimeSheetSet newSpanWith(TimeWithDayAttr start, TimeWithDayAttr end) {
		return new WorkTimeOfTimeSheetSet(this.frameNo, new TimeSpanWithRounding(start, end, this.timeSpan.getRounding()));
	}
}
