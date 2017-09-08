package nts.uk.ctx.at.function.dom.attendancetype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
@AllArgsConstructor
public class AttendanceType {
	/**会社ID*/
	private String companyId;
	/**勤怠項目ID*/
	private int attendanceItemId;
	/**勤怠項目を利用する画面	 */
	private ScreenUseAtr screenUseAtr;
	/**勤怠項目の種類*/
	private AttendanceItemType attendanceItemType;
	
	public static AttendanceType createSimpleFromJavaType(String companyId,
						int attendanceItemId,
						int screenUseAtr,
						int attendanceItemType){
		return new AttendanceType(companyId,
				attendanceItemId,
				EnumAdaptor.valueOf(screenUseAtr, ScreenUseAtr.class),
				EnumAdaptor.valueOf(attendanceItemType,AttendanceItemType.class));
	}
}
