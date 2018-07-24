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
    
    /**
	 * 勤務種類、就業時間帯、勤務時間
	 */
    public int preTypeSiftReflectFlg;
    /**
	 * 残業時間
	 */
    public int preOvertimeReflectFlg;
    /**
	 * 勤務種類、就業時間帯
	 */
    public int postTypesiftReflectFlg;
    /**
	 * 勤務時間
	 */
    public int postWorktimeReflectFlg;
    /**
	 * 休憩時間
	 */
    public int postBreakReflectFlg;
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
    /**
	 * 休憩区分
	 */
    public int restAtr;
    
    public static AppOvertimeSettingDto convertToDto(AppOvertimeSetting domain){
    	return new AppOvertimeSettingDto(domain.getCompanyID(), domain.getWorkTypeChangeFlag().value, 
										domain.getFlexJExcessUseSetAtr().value, domain.getPriorityStampSetAtr().value,
										domain.getPreTypeSiftReflectFlg().value, domain.getPreOvertimeReflectFlg().value,
										domain.getPostTypeSiftReflectFlg().value, domain.getPostWorktimeReflectFlg().value,
										domain.getPostBreakReflectFlg().value, domain.getRestAtr().value);
    }
}
