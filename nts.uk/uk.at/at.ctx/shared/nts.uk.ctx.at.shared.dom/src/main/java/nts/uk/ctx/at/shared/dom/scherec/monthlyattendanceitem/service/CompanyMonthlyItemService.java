package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;

public interface CompanyMonthlyItemService {

	/**
	 * 会社の月次項目を取得する
	 * 
	 * @param cid
	 *            会社ID
	 * @param roleId
	 *            ロールID
	 * @param attendanceItemIds
	 *            List＜勤怠項目ID＞
	 * @param attribute
	 *            List＜月次勤怠項目の属性＞
	 * @return
	 */
	List<AttItemNameImport> getMonthlyItems(String cid, Optional<String> authorityId, List<Integer> attendanceItemIds,
			List<MonthlyAttendanceItemAtr> itemAtrs);

}
