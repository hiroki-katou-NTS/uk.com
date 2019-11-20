package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;

@Data
@AllArgsConstructor
public class AppOvertimeSettingDto {
	private String companyID;
	/**
	 * フレックス超過利用設定
	 */
	private int flexJExcessUseSetAtr;

	/**
	 * 勤務種類、就業時間帯、勤務時間
	 */
	private int preTypeSiftReflectFlg;
	/**
	 * 残業時間
	 */
	private int preOvertimeReflectFlg;
	/**
	 * 勤務種類、就業時間帯
	 */
	private int postTypeSiftReflectFlg;
	/**
	 * 休憩時間
	 */
	private int postBreakReflectFlg;
	/**
	 * 勤務時間
	 */
	private int postWorktimeReflectFlg;

	/**
	 * カレンダー表示区分
	 */
	private int calendarDispAtr;
	/**
	 * 早出残業使用区分
	 */
	private int earlyOvertimeint;
	/**
	 * 残業指示超過区分
	 */
	private int instructExcessOTAtr;

	/**
	 * 実績超過打刻優先設定
	 */
	private int priorityStampSetAtr;
	/**
	 * 残業時間指定単位
	 */
	private int unitAssignmentOvertime;
	/**
	 * 通常残業使用区分
	 */
	private int normalOvertimeint;
	/**
	 * 残業時間単位制御区分
	 */
	private OtHourUnitControlDto otHour;
	/**
	 * 休憩区分
	 */
	private int restAtr;
	/**
	 * 勤種変更可否フラグ
	 */
	private int workTypeChangeFlag;

	public static AppOvertimeSettingDto convertToDto(AppOvertimeSetting domain) {
		return new AppOvertimeSettingDto(domain.getCompanyID(), domain.getFlexJExcessUseSetAtr().value,
				domain.getPreTypeSiftReflectFlg().value, domain.getPreOvertimeReflectFlg().value,
				domain.getPostTypeSiftReflectFlg().value, domain.getPostBreakReflectFlg().value,
				domain.getPostWorktimeReflectFlg().value, domain.getCalendarDispAtr().value,
				domain.getEarlyOvertimeUseAtr().value, domain.getInstructExcessOTAtr().value,
				domain.getPriorityStampSetAtr().value, domain.getUnitAssignmentOvertime().value,
				domain.getNormalOvertimeUseAtr().value, OtHourUnitControlDto.convertToDto(domain.getOtHour()),
				domain.getRestAtr().value, domain.getWorkTypeChangeFlag().value);
	}
}
