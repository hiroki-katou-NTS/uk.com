package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;


import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InforSpecialLeaveOfEmployeeSevice {
	/**
	 * RequestList373  社員の特別休暇情報を取得する
	 * @param cid
	 * @param sid
	 * @param specialLeaveCode
	 * @param complileDate
	 * @return
	 */
	InforSpecialLeaveOfEmployee getInforSpecialLeaveOfEmployee(String cid, String sid, int specialLeaveCode, DatePeriod complileDate);
	/**
	 * 付与日数情報を取得する
	 * @param employeeId
	 * @param period
	 * @return
	 */
	GrantDaysInforByDates getGrantDays(String cid, String employeeId, DatePeriod period, SpecialHoliday speHoliday, SpecialLeaveBasicInfo leaveBasicInfo);
	/**
	 * 固定の付与日一覧を求める
	 * @param sid
	 * @param period
	 * @param grantDate
	 * @return
	 */
	GrantDaysInforByDates askGrantDays(String cid, String sid, DatePeriod period, GeneralDate grantDate, SpecialHoliday speHoliday,SpecialLeaveBasicInfo leaveBasicInfo);
	/**
	 * 利用条件をチェックする
	 * @param sid
	 * @param period
	 * @param genderRest: 特別休暇利用条件.性別条件
	 * @return
	 */
	ErrorFlg checkUse(String cid, String sid, GeneralDate baseDate, SpecialHoliday speHoliday);
	/**
	 * テーブルに基づいた付与日数一覧を求める
	 * @param cid
	 * @param sid
	 * @param period
	 * @param granDate
	 * @param basicInfor
	 * @return
	 */
	GrantDaysInforByDates askGrantdaysFromtable(String cid, String sid, DatePeriod period, GeneralDate granDate, 
			SpecialLeaveBasicInfo basicInfor, SpecialHoliday speHoliday);
	/**
	 * 期限を取得する
	 */
	List<SpecialHolidayInfor> getDeadlineInfo(GrantDaysInforByDates grantDaysInfor, SpecialHoliday timeSpecifyMethod);
}
