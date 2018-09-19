package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

public interface SpecialLeaveManagementNotMinusService {
	/**
	 * マイナスなしを含めた期間内の特別休暇残を集計する
	 * @param param //○期間内の特別休暇残を集計するの結果
	 * @return
	 */
	InPeriodOfSpecialLeave complileInPeriodOfSpecialLeaveNotMinus(ComplileInPeriodOfSpecialLeaveParam param);
	/**
	 * マイナスなしの残数・使用数を計算	
	 * @param remainDaysResult
	 * @return
	 */
	RemainDaysOfSpecialHoliday remainDaysNotMinus(RemainDaysOfSpecialHoliday remainDaysResult);
}
