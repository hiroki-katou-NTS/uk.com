package nts.uk.ctx.at.function.infra.generator.annualworkschedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfManagePeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfManagePeriodImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.MonthlyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.MonthlyAttendanceResultImport;
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
import nts.uk.ctx.at.shared.dom.common.Year;
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
	@Inject
	private AgreementTimeOfManagePeriodAdapter agreementTimeAdapter;
	public static final String YM_FORMATER = "uuuu/MM";

	@Override
	public ExportData outputProcess(String cid, String setItemsOutputCd, Year fiscalYear, YearMonth startYm,
			YearMonth endYm, List<Employee> employees, int printFormat) {
		// 帳表出力前チェックをする
		this.checkBeforOutput(startYm, endYm, employees);
		// ユーザ固有情報「年間勤務表（36チェックリスト）」を更新する -> client

		final int numMonth = (int) startYm.until(endYm, ChronoUnit.MONTHS) + 1;
		ExportData exportData = new ExportData();
		LocalDate endYmd = LocalDate.of(startYm.getYear(), endYm.getMonthValue(), 1).plus(1, ChronoUnit.MONTHS).minus(1,
				ChronoUnit.DAYS);
		List<String> employeeIds = employees.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		// init map employees data
		// <<Public>> 社員の情報を取得する
		exportData.setEmployees(this.getEmployeeInfo(employees, employeeIds, endYmd));
		// ドメインモデル「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		SetOutItemsWoSc setOutItemsWoSc = setOutItemsWoScRepository.getSetOutItemsWoScById(cid, setItemsOutputCd).get();
		exportData.setOutNumExceedTime36Agr(setOutItemsWoSc.isOutNumExceedTime36Agr());
		HeaderData header = new HeaderData();
		header.setOutputAgreementTime(setOutItemsWoSc.getDisplayFormat());
		header.setTitle(companyAdapter.getCurrentCompany().map(m -> m.getCompanyName()).orElse(""));
		// A1_2
		header.setReportName("★年間勤務表（1ヶ月）");
		if (OutputAgreementTime.TWO_MONTH.equals(setOutItemsWoSc.getDisplayFormat()))
			header.setReportName("★年間勤務表（2ヶ月）");
		else if (OutputAgreementTime.THREE_MONTH.equals(setOutItemsWoSc.getDisplayFormat()))
			header.setReportName("★年間勤務表（3ヶ月）");
		// B1_1 + B1_2
		String periodStr = startYm.until(endYm, ChronoUnit.MONTHS) == 0
				? startYm.format(DateTimeFormatter.ofPattern(YM_FORMATER))
				: startYm.format(DateTimeFormatter.ofPattern(YM_FORMATER)) + "～"
						+ endYm.format(DateTimeFormatter.ofPattern(YM_FORMATER));
		header.setPeriod(TextResource.localize("KWR008_41") + " " + periodStr);
		List<ItemOutTblBook> listItemOut = setOutItemsWoSc.getListItemOutTblBook().stream()
				.filter(item -> item.isUseClassification()) // ドメインモデル「帳表に出力する項目．使用区分」をチェックする
				.sorted((i1, i2) -> Integer.compare(i1.getSortBy(), i2.getSortBy())).collect(Collectors.toList());
		exportData.setExportItems(listItemOut.stream().map(m -> new ExportItem(m.getCd().v(), m.getHeadingName().v()))
				.collect(Collectors.toList()));
		// 出力項目数による個人情報の出力制限について
		if (listItemOut.size() == 1) {
			header.setEmpInfoLabel(TextResource.localize("KWR008_44"));
		} else if (listItemOut.size() == 2) {
			header.setEmpInfoLabel(TextResource.localize("KWR008_43"));
		} else {
			header.setEmpInfoLabel(TextResource.localize("KWR008_42"));
		}
		YearMonth startYmClone = YearMonth.of(startYm.getYear(), startYm.getMonthValue());
		YearMonth endYmClone = YearMonth.of(endYm.getYear(), endYm.getMonthValue());
		// 期間
		YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(
				nts.arc.time.YearMonth.of(startYm.getYear(), startYm.getMonthValue()),
				nts.arc.time.YearMonth.of(endYm.getYear(), endYm.getMonthValue()));
		// set C2_3, C2_5
		header.setMonthPeriodLabels(
				this.createMonthPeriodLabels(startYmClone, endYm, setOutItemsWoSc.getDisplayFormat()));
		// set C1_2
		header.setMonths(this.createMonthLabels(startYmClone, endYmClone));
		exportData.setHeader(header);
		// 社員を並び替える
		exportData.setEmployeeIds(this.sortEmployees(employeeIds, endYmd));
		// 「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		if (printFormat == 1) {
			// アルゴリズム「年間勤務表の作成」を実行する
			this.createAnnualWorkSchedule(exportData, yearMonthPeriod, exportData.getEmployeeIds(), listItemOut, fiscalYear, startYm,
					numMonth, setOutItemsWoSc.getDisplayFormat());
		} else if (printFormat == 0) {
			// this.createOptionalItems(yearMonthPeriod, employeeId,
			// listItemOut, startYmClone, numMonth);
		}
		// 出力するデータ件数をチェックする
		if (exportData.getEmployees() == null || exportData.getEmployees().isEmpty()) {
			throw new BusinessException("Msg_885");
		}
		return exportData;
	}

	/*
	 * 帳表出力前チェックをする
	 */
	private void checkBeforOutput(YearMonth startYm, YearMonth endYm, List<Employee> employees) {
		// 対象期間をチェックする
		if (startYm.until(endYm, ChronoUnit.MONTHS) + 1 > 12)
			throw new BusinessException("Msg_883");
		// 出力対象の社員をチェックする
		if (employees == null || employees.isEmpty())
			throw new BusinessException("Msg_884");
	}

	/**
	 * 社員を並び替える
	 * 
	 * @param employeeIds
	 *            List＜社員ID＞
	 * @param endYmd
	 *            対象期間の終了日
	 * @return
	 */
	private List<String> sortEmployees(List<String> employeeIds, LocalDate endYmd) {
		return employeeAdapter.sortEmployee(AppContexts.user().companyId(), employeeIds, 1, null, null,
				GeneralDateTime.localDateTime(LocalDateTime.of(endYmd, LocalTime.of(0, 0))));
	}

	/**
	 * 社員の情報を取得する
	 * 
	 * @param employees
	 *            data Employees from client
	 * @param employeeIds
	 *            対象社員ID（List）
	 * @param endYmd
	 *            対象期間の終了日
	 * @return
	 */
	private Map<String, EmployeeData> getEmployeeInfo(List<Employee> employees, List<String> employeeIds,
			LocalDate endYmd) {
		// init Employee name map
		Map<String, String> empNameMap = employees.stream()
				.collect(Collectors.toMap(Employee::getEmployeeId, Employee::getName));
		Map<String, EmployeeData> employeesData = new HashMap<>();
		employeeInformationAdapter.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds,
				GeneralDate.localDate(endYmd), true, false, true, true, false, false)).forEach(emp -> {
					employeesData.put(emp.getEmployeeId(),
							EmployeeData.builder()
									.employeeInfo(new EmployeeInfo(
											emp.getWorkplace() == null ? "" : emp.getWorkplace().getWorkplaceCode(),
											emp.getWorkplace() == null ? "マスタ未登録"
													: TextResource.localize("KWR008_50") + " "
															+ emp.getWorkplace().getWorkplaceName(),
											emp.getEmployeeCode(), empNameMap.get(emp.getEmployeeId()),
											emp.getEmployment() == null ? "" : emp.getEmployment().getEmploymentName(),
											emp.getPosition() == null ? "" : emp.getPosition().getPositionName()))
									.annualWorkSchedule(new HashMap<>()).build());
				});
		return employeesData;
	}

	/**
	 * 年間勤務表の作成
	 */
	private void createAnnualWorkSchedule(ExportData exportData, YearMonthPeriod yearMonthPeriod,
			List<String> employeeIds, List<ItemOutTblBook> listItemOut, Year fiscalYear, YearMonth startYm,
			int numMonth, OutputAgreementTime displayFormat) {
		Optional<ItemOutTblBook> outputAgreementTime36 = listItemOut.stream().filter(m -> m.isItem36AgreementTime())
				.findFirst();
		employeeIds.forEach(empId -> {
			EmployeeData empData = exportData.getEmployees().get(empId);
			Map<String, AnnualWorkScheduleData> annualWorkScheduleData = new HashMap<>();
			// 36協定時間を出力するかのチェックをする
			if (outputAgreementTime36.isPresent()) {
				// アルゴリズム「36協定時間の作成」を実行する
				annualWorkScheduleData.putAll(this.create36AgreementTime(yearMonthPeriod, empId,
						outputAgreementTime36.get(), fiscalYear, startYm, numMonth, displayFormat));
			}
			// ドメインモデル「３６協定運用設定」を取得する
			// TODO

			// 「36協定運用設定」．起算月から年度の期間を求める
			// TODO

			// アルゴリズム「任意項目の作成」を実行する
			annualWorkScheduleData.putAll(this.createOptionalItems(yearMonthPeriod, empId,
					listItemOut.stream().filter(item -> !item.isItem36AgreementTime()).collect(Collectors.toList()),
					startYm, numMonth));

			empData.setAnnualWorkSchedule(annualWorkScheduleData);
		});
	}

	/**
	 * 36協定時間の作成
	 * 
	 * @param yearMonthPeriod
	 *            対象期間
	 * @param employeeId
	 *            社員ID
	 * @param outNumExceedTime36Agr
	 *            超過月数出力するか
	 * @param displayFormat
	 *            表示形式
	 */
	private Map<String, AnnualWorkScheduleData> create36AgreementTime(YearMonthPeriod yearMonthPeriod,
			String employeeId, ItemOutTblBook outputAgreementTime36, Year fiscalYear, YearMonth startYm, int numMonth,
			OutputAgreementTime displayFormat) {
		// RequestList421
		// 36協定時間を取得する
		List<AgreementTimeOfManagePeriodImport> listAgreementTime = agreementTimeAdapter.findByYear(employeeId,
				fiscalYear);
		if (listAgreementTime.isEmpty())
			return new HashMap<>();

		// パラメータ「超過月数を出力する」をチェックする
		if (numMonth > 0) {
			// 年間超過回数の取得
			// TODO request list
		}

		// パラメータ「表示形式」をチェックする
		if (displayFormat.equals(OutputAgreementTime.TWO_MONTH)
				|| displayFormat.equals(OutputAgreementTime.THREE_MONTH)) {
			// アルゴリズム「2・3ヶ月の36協定時間の作成」を実行する
			// TODO
		}

		Map<String, AnnualWorkScheduleData> data = new HashMap<>();
		// アルゴリズム「月平均の算出」を実行する
		data.put(outputAgreementTime36.getCd().v(), AnnualWorkScheduleData
				.fromAgreementTimeList(outputAgreementTime36, listAgreementTime, startYm, numMonth).calc());
		return data;
	}

	/**
	 * 任意項目の作成
	 * 
	 * @param yearMonthPeriod
	 *            対象期間
	 * @param employeeId
	 *            社員ID
	 * @param listItemOut
	 *            「36協定時間」以外の出力対象の項目設定（List）
	 */
	private Map<String, AnnualWorkScheduleData> createOptionalItems(YearMonthPeriod yearMonthPeriod, String employeeId,
			List<ItemOutTblBook> listItemOut, YearMonth startYm, int numMonth) {
		Map<String, AnnualWorkScheduleData> data = new HashMap<>();
		listItemOut.forEach(itemOut -> {
			// アルゴリズム「出力項目の値の算出」を実行する
			data.put(itemOut.getCd().v(),
					this.createOptionalItem(yearMonthPeriod, employeeId, itemOut, startYm, numMonth));
		});
		return data;
	}

	/**
	 * 出力項目の値の算出
	 * 
	 * @param yearMonthPeriod
	 *            対象期間
	 * @param employeeId
	 *            社員ID
	 * @param itemOutTblBook
	 * @return
	 */
	private AnnualWorkScheduleData createOptionalItem(YearMonthPeriod yearMonthPeriod, String employeeId,
			ItemOutTblBook itemOutTblBook, YearMonth startYm, int numMonth) {
		// アルゴリズム「対象期間の月次データの取得」を実行する
		List<MonthlyAttendanceResultImport> monthlyAttendanceResult = monthlyAttendanceItemAdapter
				.getMonthlyValueOf(employeeId, yearMonthPeriod, itemOutTblBook.getListOperationSetting().stream()
						.map(os -> os.getAttendanceItemId()).collect(Collectors.toList()));
		// アルゴリズム「月平均の算出」を実行する
		return AnnualWorkScheduleData
				.fromMonthlyAttendanceList(itemOutTblBook, monthlyAttendanceResult, startYm, numMonth).calc();
	}

	/**
	 * C1_2
	 * 
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
	 * 
	 * @param startYm
	 * @param endYm
	 * @param outputAgreementTime
	 * @return
	 */
	private List<String> createMonthPeriodLabels(YearMonth startYm, YearMonth endYm,
			OutputAgreementTime outputAgreementTime) {
		List<String> monthPeriodLabels = null;
		int standardMonth = 4; // 36協定の起算月⇒4月の場合
		YearMonth stardardYm = YearMonth.of(startYm.getYear(), standardMonth);
		if (stardardYm.isAfter(startYm))
			stardardYm = stardardYm.minusYears(1);

		int distances = 0;
		if (OutputAgreementTime.TWO_MONTH.equals(outputAgreementTime))
			distances = 2;
		else if (OutputAgreementTime.THREE_MONTH.equals(outputAgreementTime))
			distances = 3;

		if (distances != 0) {
			monthPeriodLabels = new ArrayList<>();
			stardardYm = stardardYm.plusMonths(stardardYm.until(startYm, ChronoUnit.MONTHS) / distances * distances);
			int groupStartM, groupEndM;
			do {
				groupStartM = (stardardYm.isBefore(startYm) ? startYm : stardardYm).getMonthValue();
				stardardYm = stardardYm.plusMonths(distances - 1);
				groupEndM = (stardardYm.isAfter(endYm) ? endYm : stardardYm).getMonthValue();
				if (groupStartM == groupEndM)
					monthPeriodLabels.add(String.valueOf(groupStartM));
				else
					monthPeriodLabels.add(groupStartM + "～" + groupEndM);
				stardardYm = stardardYm.plusMonths(1);
			} while (!stardardYm.isAfter(endYm));
		}
		return monthPeriodLabels;
	}
}
