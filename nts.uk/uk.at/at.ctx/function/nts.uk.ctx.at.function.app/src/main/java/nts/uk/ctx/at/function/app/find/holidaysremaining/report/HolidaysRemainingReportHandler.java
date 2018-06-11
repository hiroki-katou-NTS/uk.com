package nts.uk.ctx.at.function.app.find.holidaysremaining.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageFinder;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HolidaysRemainingReportHandler extends ExportService<HolidaysRemainingReportQuery> {

	@Inject
	private HolidaysRemainingReportGenerator reportGenerator;
	@Inject
	private HdRemainManageFinder hdFinder;
	@Inject
	private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdapter;

	@Override
	protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
		// todo get data here
		HolidaysRemainingReportQuery query = context.getQuery();
		String cId = AppContexts.user().companyId();
		Optional<HolidaysRemainingManagement> hdManagement = hdFinder
				.findByCode(query.getHolidayRemainingOutputCondition().getOutputItemSettingCode());
		if (hdManagement.isPresent()) {

			LocalDate endDate = (GeneralDate.fromString(query.getHolidayRemainingOutputCondition().getEndMonth(), "yyyy/MM/dd")).toLocalDate();
			List<String> employeeIds = query.getLstEmpIds().stream()
					.map(m -> m.getEmployeeId()).collect(Collectors.toList());
			employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(cId, employeeIds, AppContexts.system().getInstallationType().value, null, null, GeneralDateTime.localDateTime(LocalDateTime.of(endDate,LocalTime.of(0, 0))));
			
			Map<String, HolidaysRemainingEmployee> employees = new HashMap<>();
			query.getLstEmpIds().forEach(emp -> {
				employees.put(emp.getEmployeeId(), new HolidaysRemainingEmployee(
						emp.getEmployeeCode(), emp.getEmployeeId(), emp.getEmployeeName(), 
						emp.getWorkplaceCode(), emp.getWorkplaceId(), emp.getWorkplaceName()));
			});
			
			HolidayRemainingDataSource dataSource = new HolidayRemainingDataSource(
                    query.getHolidayRemainingOutputCondition().getStartMonth(),
                    query.getHolidayRemainingOutputCondition().getEndMonth(),
                    query.getHolidayRemainingOutputCondition().getOutputItemSettingCode(),
                    query.getHolidayRemainingOutputCondition().getPageBreak(),
                    query.getHolidayRemainingOutputCondition().getBaseDate(),
                    hdManagement.get(),
                    employeeIds, employees);

			this.reportGenerator.generate(context.getGeneratorContext(), dataSource);
		}
	}
}