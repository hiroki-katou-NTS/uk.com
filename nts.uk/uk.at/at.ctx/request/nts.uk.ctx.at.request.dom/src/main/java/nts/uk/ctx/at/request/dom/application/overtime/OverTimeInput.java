package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.OvertimeAppPrimitiveTime;

/**
 * @author loivt
 * 残業申請時間設定
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeInput{
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤怠種類
	 */
	private AttendanceType_Update attendanceType;
	/**
	 * 勤怠項目NO
	 */
	private Integer frameNo;
	/**
	 * 開始時間
	 */
	private OvertimeAppPrimitiveTime startTime;
	/**
	 * 完了時間
	 */
	private OvertimeAppPrimitiveTime endTime;
	/**
	 * 申請時間
	 */
	private OvertimeAppPrimitiveTime applicationTime;
	
	/**
	 * timeItemTypeAtr
	 */
	private TimeItemTypeAtr timeItemTypeAtr;
	
	public OverTimeInput(String companyID, String appID, int attendanceID, Integer frameNo, Integer startTime, Integer endTime, Integer applicationTime, int timeItemTypeAtr) {
		this.companyID = companyID;
		this.appID = appID;
		this.attendanceType = EnumAdaptor.valueOf(attendanceID, AttendanceType_Update.class);
		this.frameNo = frameNo;
		this.startTime = startTime == null? null : new OvertimeAppPrimitiveTime(startTime);
		this.endTime = endTime == null ? null : new OvertimeAppPrimitiveTime(endTime);
		this.applicationTime = applicationTime == null ? null : new OvertimeAppPrimitiveTime(applicationTime);
		this.timeItemTypeAtr = EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class);
	}
	
	public static OverTimeInput createSimpleFromJavaType(String companyID, String appID, int attendanceID, Integer frameNo, Integer startTime, Integer endTime, Integer applicationTime, int timeItemTypeAtr){
		return new OverTimeInput(companyID,
				appID,
				EnumAdaptor.valueOf(attendanceID, AttendanceType_Update.class),
				frameNo,
				startTime == null? null : new OvertimeAppPrimitiveTime(startTime),
				endTime == null ? null : new OvertimeAppPrimitiveTime(endTime),
				applicationTime == null ? null : new OvertimeAppPrimitiveTime(applicationTime),
				EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class));
	}

	public Integer getApplicationTimeValue() {
		return applicationTime != null ? applicationTime.v() : null;
	}
	

}
