package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Map;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;

public interface NotDepentSpecialLeaveOfEmployee {
	/**
	 * RequestList501: 社員に依存しない特別休暇情報を取得する
	 * @param param
	 * @return
	 */
	InforSpecialLeaveOfEmployee getNotDepentInfoSpecialLeave(NotDepentSpecialLeaveOfEmployeeInput param);

	/**
	 * đối ứng cps003
	 * RequestList501: 社員に依存しない特別休暇情報を取得する
	 * @param param
	 * @return
	 */
	Map<String, InforSpecialLeaveOfEmployee> getNotDepentInfoSpecialLeave(List<NotDepentSpecialLeaveOfEmployeeInputExtend> param);


	/**
	 * 付与日数情報を取得する
	 * @param param
	 * @return
	 */
	GrantDaysInforByDates getGrantDays(NotDepentSpecialLeaveOfEmployeeInput param, SpecialHoliday speHoliday);

	/**
	 * đối ứng cps003
	 * 付与日数情報を取得する
	 * @param param
	 * @return
	 */
	Map<String,GrantDaysInforByDates> getGrantDays(List<NotDepentSpecialLeaveOfEmployeeInputExtend> param, SpecialHoliday speHoliday);
	/**
	 * 固定の付与日一覧を求める
	 * @param param
	 * @param speHoliday
	 * @return
	 */
	GrantDaysInforByDates getGrantDaysOfFixed(RequestGrantData param, SpecialHoliday speHoliday);

	/**
	 * đối ứng cps003
	 * 固定の付与日一覧を求める
	 * @param param
	 * @param speHoliday
	 * @return
	 */
	Map<String, GrantDaysInforByDates> getGrantDaysOfFixed(List<RequestGrantDataExtend> param, SpecialHoliday speHoliday);

	/**
	 * テーブルに基づいた付与日数一覧を求める
	 * @param param
	 * @param speHoliday
	 * @return
	 */
	GrantDaysInforByDates getGrantDaysOfTable(RequestGrantData param, SpecialHoliday speHoliday);

	/**
	 * đối ứng cps003
	 * テーブルに基づいた付与日数一覧を求める
	 * @param param
	 * @param speHoliday
	 * @return
	 */
	Map<String, GrantDaysInforByDates> getGrantDaysOfTable(List<RequestGrantDataExtend> param, SpecialHoliday speHoliday);

	/**
	 * 期限を取得する
	 * @param param
	 * @return
	 */
	List<SpecialHolidayInfor> getPeriodGrantDate(GrantDaysInforByDates param, SpecialHoliday speHoliday);

	/**
	 * đối ứng cps003
	 * 期限を取得する
	 * @param param
	 * @return
	 */
	Map<String, List<SpecialHolidayInfor>> getPeriodGrantDate(List<GrantDaysInforByDates> param, SpecialHoliday speHoliday);
}
