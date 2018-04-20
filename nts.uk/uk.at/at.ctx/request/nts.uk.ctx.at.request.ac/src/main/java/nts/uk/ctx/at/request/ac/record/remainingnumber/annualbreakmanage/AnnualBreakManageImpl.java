package nts.uk.ctx.at.request.ac.record.remainingnumber.annualbreakmanage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManageExport;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.AnnualBreakManagePub;
import nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage.YearlyHolidaysTimeRemainingExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualBreakManageAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualBreakManageImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.YearlyHolidaysTimeRemainingImport;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnnualBreakManageImpl implements AnnualBreakManageAdapter {

	@Inject
	private AnnualBreakManagePub annualBreakManagePub;

	@Override
	public List<AnnualBreakManageImport> getEmployeeId(List<String> employeeId, GeneralDate startDate,
			GeneralDate endDate) {

		List<AnnualBreakManageExport> annualBreakManageExport = annualBreakManagePub.getEmployeeId(employeeId,
				startDate, endDate);
		if (CollectionUtil.isEmpty(annualBreakManageExport)) {
			return Collections.emptyList();
		}
		List<AnnualBreakManageImport> annualBreakManageImport = annualBreakManageExport.stream()
				.map(f -> new AnnualBreakManageImport(f.getEmployeeId())).collect(Collectors.toList());
		return annualBreakManageImport;
	}

	@Override
	public List<YearlyHolidaysTimeRemainingImport> getYearHolidayTimeAnnualRemaining(String employeeId,
			GeneralDate confirmDay) {
		List<YearlyHolidaysTimeRemainingExport> yearlyHolidaysTimeRemainingExport = annualBreakManagePub
				.getYearHolidayTimeAnnualRemaining(employeeId, confirmDay);

		if (CollectionUtil.isEmpty(yearlyHolidaysTimeRemainingExport)) {
			return Collections.emptyList();
		}

		return yearlyHolidaysTimeRemainingExport.stream()
				.map(f -> new YearlyHolidaysTimeRemainingImport(f.getAnnualHolidayGrantDay(), f.getAnnualRemaining(),
						f.getAnnualRemainingGrantTime()))
				.collect(Collectors.toList());
	}

	@Override
	public List<NextAnnualLeaveGrant> calculateNextHolidayGrant(String employeeId, DatePeriod time) {
		return annualBreakManagePub.calculateNextHolidayGrant(employeeId, time);
	}

}
