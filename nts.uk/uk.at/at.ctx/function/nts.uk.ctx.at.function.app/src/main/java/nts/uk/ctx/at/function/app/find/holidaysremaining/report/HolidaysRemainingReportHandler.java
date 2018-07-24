package nts.uk.ctx.at.function.app.find.holidaysremaining.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageFinder;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class HolidaysRemainingReportHandler extends ExportService<HolidaysRemainingReportQuery> {

	@Inject
	private HolidaysRemainingReportGenerator reportGenerator;
	@Inject
	private HdRemainManageFinder hdFinder;
	@Inject
	private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdapter;
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;
	@Inject
	private HdRemainManageFinder hdRemainManageFinder;

	@Override
	protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
		HolidaysRemainingReportQuery query = context.getQuery();
		String cId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.fromString(query.getHolidayRemainingOutputCondition().getBaseDate(),
				"yyyy/MM/dd");
		Optional<HolidaysRemainingManagement> hdManagement = hdFinder
				.findByCode(query.getHolidayRemainingOutputCondition().getOutputItemSettingCode());
		if (hdManagement.isPresent()) {
			String endDate = query.getHolidayRemainingOutputCondition().getEndMonth();

			List<String> employeeIds = query.getLstEmpIds().stream().map(EmployeeQuery::getEmployeeId)
					.collect(Collectors.toList());
			employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(cId, employeeIds,
					AppContexts.system().getInstallationType().value, null, null,
					GeneralDateTime.fromString(endDate + " 00:00", "yyyy/MM/dd HH:mm"));

			Map<String, EmployeeQuery> empMap = query.getLstEmpIds().stream()
					.collect(Collectors.toMap(EmployeeQuery::getEmployeeId, Function.identity()));

			Map<String, HolidaysRemainingEmployee> employees = new HashMap<>();

			List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
					.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds,
							GeneralDate.fromString(endDate, "yyyy/MM/dd"), true, false, true, true, false, false));
			boolean isSameCurrentMonth = true;
			boolean isFirstEmployee = true;
			Optional<YearMonth> currentMonthOfFirstEmp = Optional.empty();
			for (EmployeeInformationImport emp : listEmployeeInformationImport) {
				String wpCode = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceCode() : "";
				String wpName = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceName() : TextResource.localize("KDR001_55");
				String empmentName = emp.getEmployment() != null ? emp.getEmployment().getEmploymentName() : "";
				String positionName = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";

				Optional<YearMonth> currentMonth = hdRemainManageFinder.getCurrentMonth(cId, emp.getEmployeeId(), baseDate);
				if (isFirstEmployee) {
					isFirstEmployee = false;
					currentMonthOfFirstEmp = currentMonth;
				} else {
					if (isSameCurrentMonth && !currentMonth.equals(currentMonthOfFirstEmp)) {
						isSameCurrentMonth = false;
					}
				}

				employees.put(emp.getEmployeeId(),
						new HolidaysRemainingEmployee(emp.getEmployeeId(), emp.getEmployeeCode(),
								empMap.get(emp.getEmployeeId()).getEmployeeName(),
								empMap.get(emp.getEmployeeId()).getWorkplaceId(), wpCode, wpName, empmentName,
								positionName, currentMonth));
			}

			if (employees.isEmpty()) {
				throw new BusinessException("Msg_885");
			}

			HolidayRemainingDataSource dataSource = new HolidayRemainingDataSource(
					query.getHolidayRemainingOutputCondition().getStartMonth(),
					query.getHolidayRemainingOutputCondition().getEndMonth(),
					query.getHolidayRemainingOutputCondition().getOutputItemSettingCode(),
					query.getHolidayRemainingOutputCondition().getPageBreak(),
					query.getHolidayRemainingOutputCondition().getBaseDate(), hdManagement.get(), isSameCurrentMonth,
					employeeIds, employees);

			this.reportGenerator.generate(context.getGeneratorContext(), dataSource);
		}
	}
}