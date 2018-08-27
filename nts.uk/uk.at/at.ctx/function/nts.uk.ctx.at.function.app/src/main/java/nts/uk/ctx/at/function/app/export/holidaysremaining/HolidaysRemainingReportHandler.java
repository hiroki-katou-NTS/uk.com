package nts.uk.ctx.at.function.app.export.holidaysremaining;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
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
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	@Inject
	private ClosureRepository closureRepository;

	@Override
	protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
		val query = context.getQuery();
		val hdRemainCond = query.getHolidayRemainingOutputCondition();
		val cId = AppContexts.user().companyId();
		val baseDate = GeneralDate.fromString(hdRemainCond.getBaseDate(), "yyyy/MM/dd");
		// ドメインモデル「休暇残数管理表の出力項目設定」を取得する
		val hdManagement = hdFinder.findByCode(hdRemainCond.getOutputItemSettingCode());
		if (!hdManagement.isPresent()) {
			return;
		}
		int closureId = hdRemainCond.getClosureId();
		// ※該当の締めIDが「0：全締め」のときは、「1締め（締めID＝1）」とする
		if (closureId == 0) {
			closureId = 1;
		}
		// アルゴリズム「指定した年月の期間をすべて取得する」を実行する
		Optional<Closure> closureOpt = closureRepository.findById(cId, closureId);
		if (!closureOpt.isPresent()) {
			return;
		}
		// アルゴリズム「指定した年月の期間をすべて取得する」を実行する
		String endDate = hdRemainCond.getEndMonth();
		List<DatePeriod> periodByYearMonths = closureOpt.get()
				.getPeriodByYearMonth(GeneralDate.fromString(endDate, "yyyy/MM/dd").yearMonth());
		// 処理基準日を決定する
		Optional<DatePeriod> criteriaDatePeriodOpt = periodByYearMonths.stream().max(Comparator.comparing(DatePeriod::end));
		if (!criteriaDatePeriodOpt.isPresent()) {
			return;
		}
		GeneralDate criteriaDate = criteriaDatePeriodOpt.get().end();
		// 画面項目「A2_2：社員リスト」で選択されている社員の社員ID
		List<String> employeeIds = query.getLstEmpIds().stream().map(EmployeeQuery::getEmployeeId)
				.collect(Collectors.toList());
		// <<Public>> 社員を並べ替える
		employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(cId, employeeIds,
				AppContexts.system().getInstallationType().value, null, null,
				GeneralDateTime.legacyDateTime(criteriaDate.date()));

		Map<String, EmployeeQuery> empMap = query.getLstEmpIds().stream()
				.collect(Collectors.toMap(EmployeeQuery::getEmployeeId, Function.identity()));

		Map<String, HolidaysRemainingEmployee> employees = new HashMap<>();

		// <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
				.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, criteriaDate, 
						true, false, true, true, false, false));
		
		// 出力するデータ件数をチェックする
		if (listEmployeeInformationImport.isEmpty()) {
			throw new BusinessException("Msg_885");
		}
		
		boolean isSameCurrentMonth = true;
		boolean isFirstEmployee = true;
		Optional<YearMonth> currentMonthOfFirstEmp = Optional.empty();
		for (EmployeeInformationImport emp : listEmployeeInformationImport) {
			String wpCode = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceCode() : "";
			String wpName = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceName()
					: TextResource.localize("KDR001_55");
			String empmentName = emp.getEmployment() != null ? emp.getEmployment().getEmploymentName() : "";
			String positionName = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";

			// List内の締め情報．当月をチェックする
			Optional<YearMonth> currentMonth = hdRemainManageFinder.getCurrentMonth(cId, emp.getEmployeeId(), baseDate);
			if (isFirstEmployee) {
				isFirstEmployee = false;
				currentMonthOfFirstEmp = currentMonth;
			} else {
				if (isSameCurrentMonth && !currentMonth.equals(currentMonthOfFirstEmp)) {
					isSameCurrentMonth = false;
				}
			}

			employees.put(emp.getEmployeeId(), new HolidaysRemainingEmployee(emp.getEmployeeId(), emp.getEmployeeCode(),
					empMap.get(emp.getEmployeeId()).getEmployeeName(), empMap.get(emp.getEmployeeId()).getWorkplaceId(),
					wpCode, wpName, empmentName, positionName, currentMonth));
		}

		HolidayRemainingDataSource dataSource = new HolidayRemainingDataSource(hdRemainCond.getStartMonth(),
				hdRemainCond.getEndMonth(), hdRemainCond.getOutputItemSettingCode(), hdRemainCond.getPageBreak(),
				hdRemainCond.getBaseDate(), hdManagement.get(), isSameCurrentMonth, employeeIds, employees);

		this.reportGenerator.generate(context.getGeneratorContext(), dataSource);

	}
	
	private void getHolidayRemainingInfor() {
				
	}
}