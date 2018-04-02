package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
@Getter
@AllArgsConstructor
public class AppOverTimeInfoFull {

	private String appID;
	/**
	 * 勤務時間From1
	 */
	private String workClockFrom1;
	/**
	 * 勤務時間To1
	 */
	private String workClockTo1;
	/**
	 * 勤務時間From2
	 */
	private String workClockFrom2;
	/**
	 * 勤務時間To2
	 */
	private String workClockTo2;
	/**残業時間合計 - wait loivt*/
	private int total;
	
	private List<OverTimeFrame> lstFrame;
	/**
	 * 就業時間外深夜時間
	 */
	private int overTimeShiftNight;
	
	/**
	 * フレックス超過時間
	 */
	private int flexExessTime;
}
