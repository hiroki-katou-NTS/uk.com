package nts.uk.ctx.at.function.infra.generator.annualworkschedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.AttendanceItemValueImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.MonthlyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.MonthlyAttendanceResultImport;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeInfo;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.HeaderData;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaAnnualWorkScheduleRepository implements AnnualWorkScheduleRepository {

	@Inject
	private SetOutItemsWoScRepository setOutItemsWoScRepository;
	@Inject
	private MonthlyAttendanceItemAdapter monthlyAttendanceItemAdapter;
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;
	@Inject
	private CompanyAdapter companyAdapter;
	@Inject
	private RegulationInfoEmployeeAdapter employeeAdapter;
	/**
	 * 36協定時間
	 */
	private final String CD_36_AGREEMENT_TIME = "01";
	private YearMonth startYmFinal;
	private YearMonth endYmFinal;
	private int numMonth;
	/**
	 * TODO linh.nd
	 */
	@Override
	public ExportData getData(String cid, String setItemsOutputCd, String startYearMonth,
			String endYearMonth, List<Employee> employees) {
		this.startYmFinal = YearMonth.parse(startYearMonth, DateTimeFormatter.ofPattern("uuuu/MM"));
		this.endYmFinal = YearMonth.parse(endYearMonth, DateTimeFormatter.ofPattern("uuuu/MM"));
		this.numMonth = (int) this.startYmFinal.until(this.endYmFinal, ChronoUnit.MONTHS) + 1;
		ExportData data = new ExportData();
		LocalDate endYmd = LocalDate.of(this.endYmFinal.getYear(), this.endYmFinal.getMonthValue(), 1)
				.plus(1, ChronoUnit.MONTHS)
				.minus(1, ChronoUnit.DAYS);
		//sort by employee code
		List<String> employeeIds = employees.stream()
				.map(m -> m.getEmployeeId()).collect(Collectors.toList());
		data.setEmployeeIds(employeeAdapter.sortEmployee(AppContexts.user().companyId(),
								employeeIds,
								1,
								null,
								null,
								GeneralDateTime.localDateTime(LocalDateTime.of(endYmd, LocalTime.of(0, 0)))));

		data.setEmployees(new HashMap<>());
		Map<String, String> empNameMap = employees.stream()
				.collect(Collectors.toMap(Employee::getEmployeeId, Employee::getName));
		employeeInformationAdapter.getEmployeeInfo(
				new EmployeeInformationQueryDtoImport(data.getEmployeeIds(),
					GeneralDate.localDate(endYmd), true, false, true, true, false, false)).forEach(emp -> {
						data.getEmployees().put(emp.getEmployeeId(),
								EmployeeData.builder()
								.employeeInfo(new EmployeeInfo(emp.getWorkplace() == null? "" : emp.getWorkplace().getWorkplaceCode(),
																emp.getWorkplace() == null? "マスタ未登録" : TextResource.localize("KWR008_50") 
																		+ " " + emp.getWorkplace().getWorkplaceName(),
																emp.getEmployeeCode(),
																empNameMap.get(emp.getEmployeeId()),
																emp.getEmployment() == null? "" : emp.getEmployment().getEmploymentName(),
																emp.getPosition() == null? "" : emp.getPosition().getPositionName()))
								.annualWorkSchedule(new HashMap<>()).build());
					});

		//ドメインモデル「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		SetOutItemsWoSc setOutItemsWoSc = setOutItemsWoScRepository.getSetOutItemsWoScById(cid, setItemsOutputCd).get();
		data.setOutNumExceedTime36Agr(setOutItemsWoSc.isOutNumExceedTime36Agr());
		HeaderData header = new HeaderData();
		header.setOutputAgreementTime(setOutItemsWoSc.getDisplayFormat());
		header.setTitle(companyAdapter.getCurrentCompany().map(m -> m.getCompanyName()).orElse(""));
		//A1_2
		header.setReportName("★年間勤務表（1ヶ月）");
		if (OutputAgreementTime.TWO_MONTH.equals(setOutItemsWoSc.getDisplayFormat())) header.setReportName("★年間勤務表（2ヶ月）");
		else if (OutputAgreementTime.THREE_MONTH.equals(setOutItemsWoSc.getDisplayFormat())) header.setReportName("★年間勤務表（3ヶ月）");;
		//B1_1 + B1_2
		String periodStr = startYmFinal.until(endYmFinal, ChronoUnit.MONTHS) == 0? startYearMonth: startYearMonth + "～" + endYearMonth;
		header.setPeriod(TextResource.localize("KWR008_41") + " " + periodStr);
		//TODO
		List<ItemOutTblBook> listItemOut = setOutItemsWoSc.getListItemOutTblBook().stream()
				.filter(item -> item.isUseClassification())
				.sorted((i1, i2) -> Integer.compare(i1.getSortBy(), i2.getSortBy()))
				.collect(Collectors.toList());
		data.setExportItems(listItemOut.stream().map(m -> new ExportItem(m.getCd().v(), m.getHeadingName().v())).collect(Collectors.toList()));
		//出力項目数による個人情報の出力制限について
		if (listItemOut.size() == 1) {
			header.setEmpInfoLabel(TextResource.localize("KWR008_44"));
		} else if (listItemOut.size() == 2) {
			header.setEmpInfoLabel(TextResource.localize("KWR008_43"));
		} else {
			header.setEmpInfoLabel(TextResource.localize("KWR008_42"));
		}
		YearMonth startYm = YearMonth.of(this.startYmFinal.getYear(), this.startYmFinal.getMonthValue());
		YearMonth endYm = YearMonth.of(this.endYmFinal.getYear(), this.endYmFinal.getMonthValue());

		YearMonthPeriod range = new YearMonthPeriod(nts.arc.time.YearMonth.of(startYm.getYear(), startYm.getMonthValue()),
													nts.arc.time.YearMonth.of(endYm.getYear(), endYm.getMonthValue()));
		//set C2_3, C2_5
		header.setMonthPeriodLabels(this.createMonthPeriodLabels(startYm, endYm, setOutItemsWoSc.getDisplayFormat()));
		//set C1_2
		header.setMonths(this.createMonthLabels(startYm, endYm));

		data.setHeader(header);

		//アルゴリズム「年間勤務表の作成」を実行する
		Optional<ItemOutTblBook> outputAgreementTime36 = listItemOut.stream()
				.filter(m -> CD_36_AGREEMENT_TIME.equals(m.getCd().v())).findFirst();
		//TODO 36協定時間を出力するかのチェックをする
		if (outputAgreementTime36.isPresent()) {
			List<MonthlyAttendanceResultImport> monthlyAttendanceResult
				= monthlyAttendanceItemAdapter
					.getMonthlyValueOf(employees.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList()),
										range, Arrays.asList(202)); //attendance id item 36協定時間 is 202
	
			this.buildAnnualWorkScheduleData(data, outputAgreementTime36.get(), monthlyAttendanceResult);
		}

		listItemOut.stream().filter(item -> !CD_36_AGREEMENT_TIME.equals(item.getCd().v()))
			.forEach(item -> {
				List<MonthlyAttendanceResultImport> monthlyAttendanceResult
					= monthlyAttendanceItemAdapter.getMonthlyValueOf(employees.stream()
							.map(emp -> emp.getEmployeeId())
							.collect(Collectors.toList()),
									range,
									item.getListOperationSetting()
									.stream().map(os -> os.getAttendanceItemId()).collect(Collectors.toList()));

				this.buildAnnualWorkScheduleData(data, item, monthlyAttendanceResult);
			});

		//出力するデータ件数をチェックする
		if (data.getEmployees() == null || data.getEmployees().isEmpty()) {
			throw new BusinessException("Msg_885");
		}
		return data;
	}

	private void buildAnnualWorkScheduleData(ExportData data, ItemOutTblBook itemOut,
			List<MonthlyAttendanceResultImport> monthlyAttendanceResult) {
		final Map<Integer, Integer> operationMap = itemOut.getListOperationSetting().stream()
				.collect(Collectors.toMap(CalcFormulaItem::getAttendanceItemId, CalcFormulaItem::getOperation));

		monthlyAttendanceResult.forEach(m -> {
			String empId = m.getEmployeeId();
			EmployeeData empData = data.getEmployees().get(empId);
			AnnualWorkScheduleData annualWorkScheduleData = empData.getAnnualWorkSchedule().get(itemOut.getCd().v());
			if (annualWorkScheduleData == null) {
				annualWorkScheduleData = new AnnualWorkScheduleData();
				annualWorkScheduleData.setNumMonth(this.numMonth);
				empData.getAnnualWorkSchedule().put(itemOut.getCd().v(), annualWorkScheduleData);
			}

			annualWorkScheduleData.setHeadingName(itemOut.getHeadingName().v());
			annualWorkScheduleData.setValOutFormat(itemOut.getValOutFormat());
			annualWorkScheduleData.setMonthlyData(this.sumAttendanceData(operationMap, m.getAttendanceItems()),
					this.startYmFinal, YearMonth.of(m.getYearMonth().year(), m.getYearMonth().month()));
		});
	}

	private BigDecimal sumAttendanceData(Map<Integer, Integer> operationMap, List<AttendanceItemValueImport> attendanceItemsValue) {
		BigDecimal sum = BigDecimal.valueOf(0);
		for (AttendanceItemValueImport attendanceItem : attendanceItemsValue) {
			//0: integer, 2 decimal
			if (attendanceItem.isNumber()) {
				// 0: subtract, 1: plus
				if (operationMap.get(attendanceItem.getItemId()) == 0) {
					sum = sum.subtract(new BigDecimal(attendanceItem.getValue()));
				} else {
					sum = sum.add(new BigDecimal(attendanceItem.getValue()));
				}
			}
		}
		return sum;
	}

	/**
	 * C1_2
	 * @param startYm
	 * @param endYm
	 * @return
	 */
	private List<String> createMonthLabels(YearMonth startYm, YearMonth endYm) {
		List<String> months = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "");
		int i = 0;
		while (!startYm.isAfter(endYm)) {
			months.set(i, String.valueOf(startYm.getMonthValue()));
			startYm = startYm.plusMonths(1);
			i++;
		}
		return months;
	}

	/**
	 * Create C2_3, C2_5
	 * @param startYm
	 * @param endYm
	 * @param outputAgreementTime
	 * @return
	 */
	private List<String> createMonthPeriodLabels(YearMonth startYm, YearMonth endYm, OutputAgreementTime outputAgreementTime) {
		List<String> monthPeriodLabels = null;
		int standardMonth = 4; //36協定の起算月⇒4月の場合
		YearMonth stardardYm = YearMonth.of(startYm.getYear(), standardMonth);
		if (stardardYm.isAfter(startYm)) stardardYm = stardardYm.minusYears(1);

		int distances = 0;
		if (OutputAgreementTime.TWO_MONTH.equals(outputAgreementTime)) distances = 2;
		else if (OutputAgreementTime.THREE_MONTH.equals(outputAgreementTime)) distances = 3;

		if (distances != 0) {
			monthPeriodLabels = new ArrayList<>();
			stardardYm = stardardYm.plusMonths(stardardYm.until(startYm, ChronoUnit.MONTHS)/distances * distances);
			int groupStartM, groupEndM;
			do {
				groupStartM = (stardardYm.isBefore(startYm)? startYm : stardardYm).getMonthValue();
				stardardYm = stardardYm.plusMonths(distances - 1);
				groupEndM = (stardardYm.isAfter(endYm)? endYm : stardardYm).getMonthValue();
				if (groupStartM == groupEndM) monthPeriodLabels.add(String.valueOf(groupStartM));
				else monthPeriodLabels.add(groupStartM + "～" + groupEndM);
				stardardYm = stardardYm.plusMonths(1);
			} while (!stardardYm.isAfter(endYm));
		}
		return monthPeriodLabels;
	}

}
