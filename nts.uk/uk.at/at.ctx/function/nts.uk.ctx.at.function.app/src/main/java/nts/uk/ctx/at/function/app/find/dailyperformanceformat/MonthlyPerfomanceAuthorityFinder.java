package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class MonthlyPerfomanceAuthorityFinder {

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	public List<AttItemName> getListAttendanceItemName(List<Integer> monthlyAttendanceItemIds) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		return companyMonthlyItemService.getMonthlyItems(companyId, Optional.empty(), monthlyAttendanceItemIds, null);
	}
}
