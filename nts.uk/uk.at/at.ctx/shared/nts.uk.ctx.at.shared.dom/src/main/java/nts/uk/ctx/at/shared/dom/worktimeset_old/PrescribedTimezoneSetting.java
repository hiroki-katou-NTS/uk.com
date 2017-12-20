package nts.uk.ctx.at.shared.dom.worktimeset_old;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

//所定時間帯設定
@Getter
public class PrescribedTimezoneSetting extends DomainObject{

	/** The morning end time. */
	//午前終了時刻
	private TimeWithDayAttr morningEndTime;
	
	/** The afternoon start time. */
	//午後開始時刻
	private TimeWithDayAttr afternoonStartTime;
	
	/** The timezone. */
	//時間帯
	private List<Timezone> timezone;

	/**
	 * Instantiates a new prescribed timezone setting.
	 *
	 * @param morningEndTime the morning end time
	 * @param afternoonStartTime the afternoon start time
	 * @param timezone the timezone
	 */
	public PrescribedTimezoneSetting(TimeWithDayAttr morningEndTime, TimeWithDayAttr afternoonStartTime,
			List<Timezone> timezone) {
		super();
		this.morningEndTime = morningEndTime;
		this.afternoonStartTime = afternoonStartTime;
		this.timezone = timezone;
	}
}
