package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceItem {
	/*会社ID*/
	private String companyId;
	/*乖離時間*/
	private int divTimeId;
	/*勤怠項目*/
	private int attendanceId;
	
	public static AttendanceItem createSimpleFromJavaType(
			String companyId,
			int divTimeId,
			int attendanceId)
	{
		return new AttendanceItem(companyId, divTimeId, attendanceId);
	}
}
