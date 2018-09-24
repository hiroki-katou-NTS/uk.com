package nts.uk.ctx.at.shared.app.find.scherec.attitem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttItemFinder {

	@Inject
	private CompanyDailyItemService companyDailyItemService;

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	public List<AttItemNameImport> getDailyAttItemByIdAndAtr(AttItemParam param) {
		Optional<String> authorityId = Optional.empty();
		List<Integer> attendanceItemIds = Collections.emptyList();
		List<DailyAttendanceAtr> itemAtrs = Collections.emptyList();
		if (param != null) {
			authorityId = param.getRoleId() == null ? Optional.empty() : Optional.ofNullable(param.getRoleId());
			attendanceItemIds = param.getAttendanceItemIds() == null ? Collections.emptyList()
					: param.getAttendanceItemIds();
			itemAtrs = param.getItemAtrs() == null ? Collections.emptyList()
					: param.getItemAtrs().stream().map(x -> EnumAdaptor.valueOf(x, DailyAttendanceAtr.class))
							.collect(Collectors.toList());
		}
		return this.companyDailyItemService.getDailyItems(AppContexts.user().companyId(), authorityId,
				attendanceItemIds, itemAtrs);
	}

	public List<AttItemNameImport> getMonthlyAttItemByIdAndAtr(AttItemParam param) {
		Optional<String> authorityId = Optional.empty();
		List<Integer> attendanceItemIds = Collections.emptyList();
		List<MonthlyAttendanceItemAtr> itemAtrs = Collections.emptyList();
		if (param != null) {
			authorityId = param.getRoleId() == null ? Optional.empty() : Optional.ofNullable(param.getRoleId());
			attendanceItemIds = param.getAttendanceItemIds() == null ? Collections.emptyList()
					: param.getAttendanceItemIds();
			itemAtrs = param.getItemAtrs() == null ? Collections.emptyList()
					: param.getItemAtrs().stream().map(x -> EnumAdaptor.valueOf(x, MonthlyAttendanceItemAtr.class))
							.collect(Collectors.toList());
		}
		return this.companyMonthlyItemService.getMonthlyItems(AppContexts.user().companyId(), authorityId,
				attendanceItemIds, itemAtrs);
	}
}