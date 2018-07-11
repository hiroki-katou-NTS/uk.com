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
    /**
	 * 勤種変更可否フラグ
	 */
    public int workTypeChangeFlag;
    /**
	 * フレックス超過利用設定
	 */
    public int flexJExcessUseSetAtr;
    /**
     * 実績超過打刻優先設定
     */
    public int priorityStampSetAtr;
    
    
//    public int preTypeSiftReflectFlg;
//    
//    public int preOvertimeReflectFlg;
//    
//    public int postTypesiftReflectFlg;
//    
//    public int postBreakReflectFlg;
//    
//    public int postWorktimeReflectFlg;
//    
//    public int calendarDispAtr;
//    
//    public int earlyOverTimeUseAtr;
//    
//    public int instructExcessOtAtr;
//    
//    
//    
//    public int unitAssignmentOvertime;
//    
//    public int normalOvertimeUseAtr;
//    
//    public int attendanceId;
//    
//    public int useOt;
//    
//    public int restAtr;
    
    public static AppOvertimeSettingDto convertToDto(AppOvertimeSetting domain){
    	return new AppOvertimeSettingDto(domain.getCompanyID(), domain.getWorkTypeChangeFlag().value, 
										domain.getFlexJExcessUseSetAtr().value, domain.getPriorityStampSetAtr().value);
    }
}
