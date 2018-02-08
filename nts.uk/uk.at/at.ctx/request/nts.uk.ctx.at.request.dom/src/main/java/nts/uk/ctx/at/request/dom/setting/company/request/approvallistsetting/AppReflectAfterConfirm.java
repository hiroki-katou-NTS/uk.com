package nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * データが確立が確定されている場合の承認済申請の反映
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppReflectAfterConfirm {
	
	/**
	 * スケジュールが確定されている場合
	 */
	private ReflectAtr scheduleConfirmedAtr;
	
	/**
	 * 実績が確定されている場合
	 */
	private ReflectAtr achievementConfirmedAtr;
	
	public static AppReflectAfterConfirm toDomain(Integer scheduleConfirmedAtr, Integer achievementConfirmedAtr){
		return new AppReflectAfterConfirm(
				EnumAdaptor.valueOf(scheduleConfirmedAtr, ReflectAtr.class), 
				EnumAdaptor.valueOf(achievementConfirmedAtr, ReflectAtr.class));
	}
}
