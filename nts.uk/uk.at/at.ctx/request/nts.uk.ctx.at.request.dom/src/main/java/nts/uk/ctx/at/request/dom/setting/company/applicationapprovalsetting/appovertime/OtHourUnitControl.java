package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;;
/**
 * 残業時間単位制御区分
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtHourUnitControl extends DomainObject{
	// 勤怠項目ID
	private AttendanceType attendanceId;
	// 残業枠利用
	private UseOtWk useOt;
	public static OtHourUnitControl createFromJavaType(int attendanceId, int useOt){
		return new OtHourUnitControl(EnumAdaptor.valueOf(attendanceId, AttendanceType.class),
				EnumAdaptor.valueOf(useOt, UseOtWk.class));
	}
}
