package nts.uk.ctx.at.shared.dom.worktimeset_old;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class Timezone.
 */
//時間帯(使用区分付き)
@Getter
public class Timezone extends DomainObject implements HasTimeSpanForCalc<Timezone>{

	/** The use atr. */
	//使用区分
	private UseSetting useAtr;
	
	/** The work no. */
	//勤務NO
	private int workNo;
	
	/** The start. */
	//開始
	private TimeWithDayAttr start;
	
	/** The end. */
	//終了
	private TimeWithDayAttr end;

	/**
	 * Instantiates a new timezone.
	 *
	 * @param useAtr the use atr
	 * @param workNo the work no
	 * @param start the start
	 * @param end the end
	 */
	public Timezone(UseSetting useAtr, int workNo, TimeWithDayAttr start, TimeWithDayAttr end) {
		super();
		this.useAtr = useAtr;
		this.workNo = workNo;
		this.start = start;
		this.end = end;
	}
	
	public void updateStartTime(TimeWithDayAttr start) {
		this.start = start;
	}
	
	/**
	 * Update end time.
	 *
	 * @param end the end
	 */
	public void updateEndTime(TimeWithDayAttr end) {
		this.end = end;
	}
	
	@Override
	public TimeSpanForCalc getTimeSpan() {
		return new TimeSpanForCalc(start, end);
	}

	@Override
	public Timezone newSpanWith(TimeWithDayAttr start, TimeWithDayAttr end) {
		return new Timezone(this.useAtr, this.workNo, start, end);
	}
}
