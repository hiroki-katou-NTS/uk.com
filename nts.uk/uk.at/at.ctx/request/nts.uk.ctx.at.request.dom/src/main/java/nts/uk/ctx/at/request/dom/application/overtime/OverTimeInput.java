package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.OvertimeAppPrimitiveTime;

/**
 * @author loivt
 * 残業申請時間設定
 */
@Data
@AllArgsConstructor
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
	 * 勤怠項目ID
	 */
	private AttendanceID attendanceID;
	/**
	 * 勤怠種類
	 */
	private int attendanceNo;
	/**
	 * 勤怠種類名称
	 */
	private String attandanceName;
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
	 * 事前申請
	 */
	private OvertimeAppPrimitiveTime preApplicationTime;
	/**
	 * 指示時間
	 */
	private OvertimeAppPrimitiveTime indicatedTime;
	/**
	 * 計算時間
	 */
	private OvertimeAppPrimitiveTime calculationTime;

}
