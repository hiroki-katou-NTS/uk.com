package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class Timezone.
 */
//時間帯(使用区分付き)
@Builder
@Getter
public class Timezone extends DomainObject {

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
}
