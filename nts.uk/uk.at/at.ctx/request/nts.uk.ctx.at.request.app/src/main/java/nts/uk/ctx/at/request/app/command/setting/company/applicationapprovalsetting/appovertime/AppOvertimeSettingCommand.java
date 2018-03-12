package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppOvertimeSettingCommand {
    private int flexExcessUseSetAtr;
    
    private int preTypeSiftReflectFlg;
    
    private int preOvertimeReflectFlg;
    
    private int postTypesiftReflectFlg;
    
    private int postBreakReflectFlg;
    
    private int postWorktimeReflectFlg;
    
    private int calendarDispAtr;
    
    private int earlyOverTimeUseAtr;
    
    private int instructExcessOtAtr;
    
    private int priorityStampSetAtr;
    
    private int unitAssignmentOvertime;
    
    private int normalOvertimeUseAtr;
    
    private int attendanceId;
    
    private int useOt;
}
