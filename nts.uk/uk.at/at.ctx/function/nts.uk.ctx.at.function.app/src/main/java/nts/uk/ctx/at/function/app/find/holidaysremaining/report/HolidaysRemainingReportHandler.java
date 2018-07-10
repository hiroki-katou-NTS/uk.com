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
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

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
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ClosureRepository closureRepository;

	@Override
	protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
		HolidaysRemainingReportQuery query = context.getQuery();
		String cId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.fromString(query.getHolidayRemainingOutputCondition().getBaseDate(), "yyyy/MM/dd");
		Optional<HolidaysRemainingManagement> hdManagement = hdFinder
				.findByCode(query.getHolidayRemainingOutputCondition().getOutputItemSettingCode());
		if (hdManagement.isPresent()) {

			LocalDate endDate = (GeneralDate.fromString(query.getHolidayRemainingOutputCondition().getEndMonth(),
					"yyyy/MM/dd")).toLocalDate();
			List<String> employeeIds = query.getLstEmpIds().stream().map(m -> m.getEmployeeId())
					.collect(Collectors.toList());
			employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(cId, employeeIds,
					AppContexts.system().getInstallationType().value, null, null,
					GeneralDateTime.localDateTime(LocalDateTime.of(endDate, LocalTime.of(0, 0))));

			Map<String, String> empNameMap = query.getLstEmpIds().stream()
					.collect(Collectors.toMap(EmployeeQuery::getEmployeeId, EmployeeQuery::getEmployeeName));

			Map<String, HolidaysRemainingEmployee> employees = new HashMap<>();

			List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds,
					GeneralDate.localDate(endDate), true, false, true, true, false, false));
			for (EmployeeInformationImport emp : listEmployeeInformationImport) {
				String wpCode = "";
				String wpName = "";
				String empmentName = "";
				String positionName = "";
				if (emp.getWorkplace() != null) {
					wpCode = emp.getWorkplace().getWorkplaceCode();
					wpName = emp.getWorkplace().getWorkplaceName();
				}
				if (emp.getEmployment() != null) {
					empmentName = emp.getEmployment().getEmploymentName();
				}
				if (emp.getPosition() != null) {
					positionName = emp.getPosition().getPositionName();
				}

				employees.put(emp.getEmployeeId(),
						new HolidaysRemainingEmployee(emp.getEmployeeId(), emp.getEmployeeCode(),
								empNameMap.get(emp.getEmployeeId()), wpCode, wpName, empmentName, positionName,
								this.getCurrentMonth(cId, emp.getEmployeeId(), baseDate)));
			}

			HolidayRemainingDataSource dataSource = new HolidayRemainingDataSource(
					query.getHolidayRemainingOutputCondition().getStartMonth(),
					query.getHolidayRemainingOutputCondition().getEndMonth(),
					query.getHolidayRemainingOutputCondition().getOutputItemSettingCode(),
					query.getHolidayRemainingOutputCondition().getPageBreak(),
					query.getHolidayRemainingOutputCondition().getBaseDate(), hdManagement.get(), employeeIds,
					employees);

			this.reportGenerator.generate(context.getGeneratorContext(), dataSource);
		}
	}

	// 当月を取得
	private Optional<YearMonth> getCurrentMonth(String companyId, String employeeId, GeneralDate systemDate) {
		// ドメインモデル「所属雇用履歴」を取得する
		Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt = shareEmploymentAdapter
				.findEmploymentHistory(companyId, employeeId, systemDate);
		if (bsEmploymentHistOpt.isPresent()) {
			String employmentCode = bsEmploymentHistOpt.get().getEmploymentCode();
			// ドメインモデル「雇用に紐づく就業締め」を取得する
			Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepo.findByEmploymentCD(companyId,
					employmentCode);

			// 雇用に紐づく締めを取得する
			Integer closureId = 1;
			if (closureEmploymentOpt.isPresent()) {
				closureId = closureEmploymentOpt.get().getClosureId();
			}

			// 当月の年月を取得する
			Optional<Closure> closureOpt = closureRepository.findById(companyId, closureId);
			if (closureOpt.isPresent()) {
				return Optional.of(closureOpt.get().getClosureMonth().getProcessingYm());
			}
		}
		return Optional.empty();
	}
}