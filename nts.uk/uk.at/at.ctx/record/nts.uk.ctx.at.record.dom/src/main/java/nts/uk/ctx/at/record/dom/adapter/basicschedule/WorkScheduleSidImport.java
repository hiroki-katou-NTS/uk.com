package nts.uk.ctx.at.record.dom.adapter.basicschedule;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class WorkScheduleSidImport {
	
	public String sId;

	public GeneralDate date;
	
	/** The schedule cnt. */
	// 予定勤務回数
	private int scheduleCnt;
	
	/** The schedule start clock. */
	// 予定開始時刻
	private int scheduleStartClock;

	/** The schedule end clock. */
	// 予定終了時刻
	private int scheduleEndClock;
	
	/** The bounce atr. */
	// 直行直帰区分
	private int bounceAtr;

	public WorkScheduleSidImport(String sId, GeneralDate date, int scheduleCnt, int scheduleStartClock,
			int scheduleEndClock, int bounceAtr) {
		super();
		this.sId = sId;
		this.date = date;
		this.scheduleCnt = scheduleCnt;
		this.scheduleStartClock = scheduleStartClock;
		this.scheduleEndClock = scheduleEndClock;
		this.bounceAtr = bounceAtr;
	}

}
