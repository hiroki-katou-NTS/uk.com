package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OtHourUnitControl;;
/**
 * 残業時間単位制御区分
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtHourUnitControlDto extends DomainObject{
	// 勤怠項目ID
	public int attendanceId;
	// 残業枠利用
	public int useOt;
	
	public static OtHourUnitControlDto convertToDto(OtHourUnitControl domain){
    	return new OtHourUnitControlDto(
    			domain.getAttendanceId().value,
    			domain.getUseOt().value);
    }

}
