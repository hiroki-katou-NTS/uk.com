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
	private AttendanceID attendanceID;
	/**
	 * 勤怠項目NO
	 */
	private int attendanceNo;
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
	
	public static OverTimeInput createSimpleFromJavaType(String companyID, String appID, int attendanceID, int attendanceNo, int startTime, int endTime, int applicationTime){
		return new OverTimeInput(companyID,
				appID,
				EnumAdaptor.valueOf(attendanceID, AttendanceID.class),
				attendanceNo,
				new OvertimeAppPrimitiveTime(startTime),
				new OvertimeAppPrimitiveTime(endTime),
				new OvertimeAppPrimitiveTime(applicationTime));
	}

}
