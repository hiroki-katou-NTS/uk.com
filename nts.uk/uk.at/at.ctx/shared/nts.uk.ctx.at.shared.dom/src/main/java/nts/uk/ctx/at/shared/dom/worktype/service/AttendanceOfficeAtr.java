package nts.uk.ctx.at.shared.dom.worktype.service;

import lombok.AllArgsConstructor;
/**
 * ・出勤退勤区分(出勤か、退勤か)
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum AttendanceOfficeAtr {
	/**出勤*/
	ATTENDANCE(0, "出勤"),
	/**	退勤*/
	OFFICEWORK(1, "退勤");
	
	public final Integer value;
	
	public final String name;
}
