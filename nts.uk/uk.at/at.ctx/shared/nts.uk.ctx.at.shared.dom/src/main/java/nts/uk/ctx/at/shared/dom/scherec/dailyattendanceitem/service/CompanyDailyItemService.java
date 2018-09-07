package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

public interface CompanyDailyItemService {

	/**
	 * 会社の日次項目を取得する
	 * 
	 * @param cid
	 *            会社ID
	 * @param roleId
	 *            ロールID
	 * @param attendanceItemIds
	 *            List＜勤怠項目ID＞
	 * @param attribute
	 *            List＜日次勤怠項目の属性＞
	 * @return
	 */
	List<DailyAttendanceItem> getDailyItems(String cid, Optional<String> roleId, List<Integer> attendanceItemIds,
			List<DailyAttendanceAtr> attribute);

}
