package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class AppOverTimeInfoFull {

	/**
	 * 勤務時間From1
	 */
	private int workClockFrom1;
	/**
	 * 勤務時間To1
	 */
	private int workClockTo1;
	/**
	 * 勤務時間From2
	 */
	private int workClockFrom2;
	/**
	 * 勤務時間To2
	 */
	private int workClockTo2;
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
