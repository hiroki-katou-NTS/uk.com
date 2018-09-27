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
	
	private List<OverTimeFrame> lstFrame;
	/** 就業時間外深夜時間 */
	private String overTimeShiftNight;
	
	/** フレックス超過時間 */
	private String flexExessTime;
	//時間外時間の詳細
	private TimeNo417 timeNo417;
}
