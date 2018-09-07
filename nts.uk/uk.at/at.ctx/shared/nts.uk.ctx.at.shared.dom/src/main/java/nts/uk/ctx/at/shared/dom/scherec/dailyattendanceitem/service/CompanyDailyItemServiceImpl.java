package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

@Stateless
public class CompanyDailyItemServiceImpl implements CompanyDailyItemService {

	@Override
	public List<DailyAttendanceItem> getDailyItems(String cid, Optional<String> authorityDailyId,
			List<Integer> attendanceItemIds, List<DailyAttendanceAtr> attribute) {
		// パラメータ「ロールID」をチェックする (Check the parameter "Roll ID")
		if (authorityDailyId.isPresent()) {

		}
		return Collections.emptyList();
	}

}
