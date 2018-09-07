package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

@Stateless
public class CompanyMonthlyItemServiceImpl implements CompanyMonthlyItemService {

	@Override
	public List<DailyAttendanceItem> getMonthlyItems(String cid, Optional<String> authorityDailyId,
			List<Integer> attendanceItemIds, List<DailyAttendanceAtr> attribute) {
		// パラメータ「ロールID」をチェックする (Check the parameter "Roll ID")
		if (authorityDailyId.isPresent()) {
			// ドメインモデル「権限別日次項目制御」を取得する

		}
		return Collections.emptyList();
	}

}
