package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;

public interface NotDepentSpecialLeaveOfEmployee {
	/**
	 * RequestList501: 社員に依存しない特別休暇情報を取得する
	 * @param param
	 * @return
	 */
	InforSpecialLeaveOfEmployee getNotDepentInfoSpecialLeave(NotDepentSpecialLeaveOfEmployeeInput param);
	/**
	 * 付与日数情報を取得する
	 * @param param
	 * @return
	 */
	GrantDaysInforByDates getGrantDays(NotDepentSpecialLeaveOfEmployeeInput param, SpecialHoliday speHoliday);
	/**
	 * 固定の付与日一覧を求める
	 * @param param
	 * @param speHoliday
	 * @return
	 */
	GrantDaysInforByDates getGrantDaysOfFixed(RequestGrantData param, SpecialHoliday speHoliday);
	/**
	 * テーブルに基づいた付与日数一覧を求める
	 * @param param
	 * @param speHoliday
	 * @return
	 */
	GrantDaysInforByDates getGrantDaysOfTable(RequestGrantData param, SpecialHoliday speHoliday);
	/**
	 * 期限を取得する
	 * @param param
	 * @return
	 */
	List<SpecialHolidayInfor> getPeriodGrantDate(GrantDaysInforByDates param, SpecialHoliday speHoliday);
}
