package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import lombok.Value;

/**
 * 振休振出申請
 * @author hoatt
 *
 */
@Value
public class AppCompltLeaveFull {

	/**申請ID*/
	private String appID;
	//0 - abs
	//1 - rec
	private int type;
	/**勤務種類Name*/
	private String workTypeName;
	/**勤務時間1.開始時刻*/
	private String startTime;
	/**勤務時間1.終了時刻*/
	private String endTime;
}
