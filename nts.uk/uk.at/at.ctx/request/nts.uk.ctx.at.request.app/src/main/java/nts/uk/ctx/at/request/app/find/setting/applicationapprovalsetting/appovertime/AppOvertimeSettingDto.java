package nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppOvertimeSettingDto {
    public String cid;
    
    public int flexExcessUseSetAtr;
    
    public int preTypeSiftReflectFlg;
    
    public int preOvertimeReflectFlg;
    
    public int postTypesiftReflectFlg;
    
    public int postBreakReflectFlg;
    
    public int postWorktimeReflectFlg;
    
    public int calendarDispAtr;
    
    public int earlyOverTimeUseAtr;
    
    public int instructExcessOtAtr;
    
    public int priorityStampSetAtr;
    
    public int unitAssignmentOvertime;
    
    public int normalOvertimeUseAtr;
    
    public int attendanceId;
    
    public int useOt;
    
    public static AppOvertimeSettingDto convertToDto(AppOvertimeSetting domain){
    	return new AppOvertimeSettingDto(domain.getCompanyID(), domain.getFlexJExcessUseSetAtr().value, 
    			domain.getPreTypeSiftReflectFlg().value, domain.getPreOvertimeReflectFlg().value, 
    			domain.getPostTypeSiftReflectFlg().value, domain.getPostBreakReflectFlg().value, 
    			domain.getPostWorktimeReflectFlg().value, domain.getCalendarDispAtr().value, 
    			domain.getEarlyOvertimeUseAtr().value, domain.getInstructExcessOTAtr().value, 
    			domain.getPriorityStampSetAtr().value, domain.getUnitAssignmentOvertime().value, 
    			domain.getNormalOvertimeUseAtr().value, domain.getOtHour().getAttendanceId().value, 
    			domain.getOtHour().getUseOt().value);
    }
}
