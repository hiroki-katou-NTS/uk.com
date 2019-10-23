package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.BreakReflect;
import nts.uk.ctx.at.request.dom.application.Changeable;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
/**
 * 残業申請設定
 * @author loivt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class AppOvertimeSetting extends AggregateRoot{
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * フレックス超過利用設定
	 */
	private FlexExcessUseSetAtr flexJExcessUseSetAtr;
	
	/**
	 * 勤務種類、就業時間帯、勤務時間
	 */
	private UseAtr preTypeSiftReflectFlg;
	/**
	 * 残業時間
	 */
	private UseAtr preOvertimeReflectFlg;
	/**
	 * 勤務種類、就業時間帯
	 */
	private UseAtr postTypeSiftReflectFlg;
	/**
	 * 休憩時間
	 */
	private UseAtr postBreakReflectFlg;
	/**
	 * 勤務時間
	 */
	private UseAtr postWorktimeReflectFlg;
	
	/**
	 * カレンダー表示区分
	 */
	private UseAtr calendarDispAtr;
	/**
	 * 早出残業使用区分
	 */
	private UseAtr earlyOvertimeUseAtr;
	/**
	 * 残業指示超過区分
	 */
	private UseAtr instructExcessOTAtr;
	
	/**
	 * 実績超過打刻優先設定  
	 */
	private PriorityStampSetAtr priorityStampSetAtr;
	/**
	 * 残業時間指定単位
	 */
	private UnitAssignmentOvertime unitAssignmentOvertime;
	/**
	 * 通常残業使用区分
	 */
	private UseAtr normalOvertimeUseAtr;
	/**
	 * 残業時間単位制御区分
	 */
	private OtHourUnitControl otHour;
	/**
	 * 休憩区分
	 */
	private BreakReflect restAtr;
	/**
	 * 勤種変更可否フラグ
	 */
	private Changeable workTypeChangeFlag;
	
	public static AppOvertimeSetting createFromJavaType(String companyId, int flexJExcessUseSetAtr, 
			int preTypeSiftReflectFlg, int preOvertimeReflectFlg, int postTypeSiftReflectFlg, 
			int postBreakReflectFlg, int postWorktimeReflectFlg, int calendarDispAtr, 
			int earlyOvertimeUseAtr, int instructExcessOTAtr, int priorityStampSetAtr, 
			int unitAssignmentOvertime, int normalOvertimeUseAtr, int attendanceId, int useOt, 
			int restAtr, int workTypeChangeFlag){
		return new  AppOvertimeSetting(companyId, EnumAdaptor.valueOf(flexJExcessUseSetAtr, FlexExcessUseSetAtr.class), 
				EnumAdaptor.valueOf(preTypeSiftReflectFlg, UseAtr.class), 
				EnumAdaptor.valueOf(preOvertimeReflectFlg, UseAtr.class), 
				EnumAdaptor.valueOf(postTypeSiftReflectFlg, UseAtr.class), 
				EnumAdaptor.valueOf(postBreakReflectFlg, UseAtr.class), 
				EnumAdaptor.valueOf(postWorktimeReflectFlg, UseAtr.class), 
				EnumAdaptor.valueOf(calendarDispAtr, UseAtr.class), 
				EnumAdaptor.valueOf(earlyOvertimeUseAtr, UseAtr.class), 
				EnumAdaptor.valueOf(instructExcessOTAtr, UseAtr.class), 
				EnumAdaptor.valueOf(priorityStampSetAtr, PriorityStampSetAtr.class), 
				EnumAdaptor.valueOf(unitAssignmentOvertime, UnitAssignmentOvertime.class), 
				EnumAdaptor.valueOf(normalOvertimeUseAtr, UseAtr.class), 
				new OtHourUnitControl(EnumAdaptor.valueOf(attendanceId, AttendanceType.class), 
						EnumAdaptor.valueOf(useOt, UseOtWk.class)), 
				EnumAdaptor.valueOf(restAtr, BreakReflect.class),
				EnumAdaptor.valueOf(workTypeChangeFlag, Changeable.class));
	}
}
