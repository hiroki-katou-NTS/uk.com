package nts.uk.ctx.at.request.dom.application.applist.service.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;

/**
 * refactor 5
 * 勤怠項目名称
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AttendanceNameItem {
	
	/**
	 * 勤怠NO
	 */
	private int attendanceNo;
	
	/**
	 * 勤怠名称
	 */
	private String attendanceName;
	
	/**
	 * 勤怠種類
	 */
	private AttendanceType_Update attendanceType;
}
