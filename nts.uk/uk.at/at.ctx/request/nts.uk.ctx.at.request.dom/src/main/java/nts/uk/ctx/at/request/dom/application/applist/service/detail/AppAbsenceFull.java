package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import lombok.Value;
@Value
public class AppAbsenceFull {
	/** 申請ID */
	private String appID;
	/** 休暇種類 */
	private Integer holidayAppType;
	//日数
	private Integer day;
	/**就業時間帯コード - Name */
	private String workTimeName;
	/**終日半日休暇区分*/
	private Integer allDayHalfDayLeaveAtr;
	/**開始時刻*/
	private String startTime1;
	/**終了時刻*/
	private String endTime1;
	/**開始時刻2*/
	private String startTime2;
	/**終了時刻2*/
	private String endTime2;
	/**続柄コード - Name*/
	private String relationshipCode;
	/**続柄コード - Name*/
	private String relationshipName;
	/**喪主フラグ*/
	private Boolean mournerFlag;
	/**勤務種類名称*/
    private String workTypeName;
}
