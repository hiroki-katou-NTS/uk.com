package nts.uk.ctx.at.function.app.export.annualworkschedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.RegularSortingTypeImport;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.SortingConditionOrderImport;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.ActualMultipleMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.PositionImport;
import nts.uk.ctx.at.function.dom.adapter.jobtitle.JobTitleAdapter;
import nts.uk.ctx.at.function.dom.adapter.jobtitle.JobTitleImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxAverageTimeImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreMaxAverageTimeMultiImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeYearImport;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.GetAgreementPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalculationFormulaOfItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemsOutputToBookTable;
import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.TotalAverageDisplay;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleGenerator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeInfo;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExcludeEmp;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.HeaderData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.PrintFormat;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.export.WorkRecordExport;
import nts.uk.ctx.at.record.dom.workrecord.export.dto.EmpAffInfoExport;
import nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate.MonthlyAttItemCanAggregateRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class AnnualWorkScheduleExportService extends ExportService<AnnualWorkScheduleExportQuery> {
	@Inject
	private AnnualWorkScheduleGenerator generator;
	@Inject
	private AgreementOperationSettingAdapter agreementOperationSettingAdapter;
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;
	@Inject
	private CompanyAdapter companyAdapter;
	@Inject
	private RegulationInfoEmployeeAdapter employeeAdapter;
	@Inject
	private AgreementTimeByPeriodAdapter agreementTimeByPeriodAdapter;
	@Inject
	private JobTitleAdapter jobTitleAdapter;
	@Inject
	private WorkRecordExport workRecordExport;
	@Inject
	private RecordDomRequireService requireService;
	@Inject
	private SetOutputItemOfAnnualWorkSchRepository setOutputItemOfAnnualWorkSchRepo;
	@Inject
	private MonthlyAttItemCanAggregateRepository monthlyAttItemCanAggregateRepo;
	@Inject
	private ActualMultipleMonthAdapter actualMultipleMonthAdapter;
	@Inject
	private GetAgreementPeriodAdapter getAgreementPeriodAdapter;

	public static final String YM_FORMATER = "uuuu/MM";

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	protected void handle(ExportServiceContext<AnnualWorkScheduleExportQuery> context) {
		String                        companyId   = AppContexts.user().companyId();
		AnnualWorkScheduleExportQuery query       = context.getQuery();
		PrintFormat                   printFormat = EnumAdaptor.valueOf(query.getPrintFormat(), PrintFormat.class);

		//・基準月（画面AのA11_2）
		Integer        baseMonth  = query.getCurentMonth();
		ExcludeEmp     excludeEmp = EnumAdaptor.valueOf(query.getExcludeEmp(), ExcludeEmp.class);
		List<Employee> employees  = query.getEmployees().stream()
                .distinct()
				.map(m -> new Employee(m.getEmployeeId(), m.getCode(), m.getName(), m.getWorkplaceName()))
				.collect(Collectors.toList());

		Year      fiscalYear = null;
		YearMonth startYm    = null;
		YearMonth endYm      = null;
		Integer   monthLimit;
		GeneralDate baseDate = GeneralDate.fromString(query.getBaseDate(), "yyyy/MM/dd");

		//=============================================================================================================
		//2022.02.24 稲熊 削除 START
		// ドメインモデル「３６協定運用設定」を取得する
		//  Optional<AgreementOperationSettingImport> agreementSetObj = agreementOperationSettingAdapter.findForAlarm(companyId);
		//2022.02.24 稲熊 削除 END
		//=============================================================================================================

		//36協定チェックリスト　開始年月～終了年月
		if (PrintFormat.AGREEMENT_36.equals(printFormat)) {
			//=============================================================================================================
			//  2022.02.24 稲熊 変更 START
			//fiscalYear = new Year(Integer.parseInt(query.getFiscalYear()));
			//startYm    = this.getStartYearMonth(agreementSetObj, fiscalYear);
			//endYm      = startYm.plusMonths(11);
			//=============================================================================================================
			//
			//  画面入力年度(A9_2)
			fiscalYear = new Year(Integer.parseInt(query.getFiscalYear()));
			//
			//年度を指定して36協定期間(年月日 From-To)を取得 - get RequestList554
			// ドメインモデル「３６協定運用設定」を取得
			Optional<DatePeriod> period = this.getAgreementPeriodAdapter.byYear(companyId, fiscalYear);
			DatePeriod datePeriod = period.get();
			startYm  = YearMonth.of(datePeriod.start().yearMonth().year(), datePeriod.start().yearMonth().month());
			endYm    = YearMonth.of(datePeriod.end().yearMonth().year(), datePeriod.end().yearMonth().month());
			//  2022.02.24 稲熊 変更 END
			//=============================================================================================================
		} else {
		//勤怠チェックリスト　　開始年月～終了年月
			startYm = YearMonth.parse(query.getStartYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));
			endYm   = YearMonth.parse(query.getEndYearMonth(),   DateTimeFormatter.ofPattern("yyyyMM"));
		}

//=============================================================================================================
//  2022.02.24 稲熊 削除 START
//	// get ３６協定超過上限回数
//	if (agreementSetObj.isPresent()) {
//		monthLimit = agreementSetObj.get().getNumberTimesOverLimitType().value;
//	} else {
//		monthLimit = TimeOverLimitTypeImport.ZERO_TIMES.value;
//	}
//  2022.02.24 稲熊 削除 END
//=============================================================================================================

		ExportData data = this.outputProcess(companyId
										   , query.getSetItemsOutputLayoutId()
										   , fiscalYear
										   , startYm
										   , endYm
										   , employees
										   , printFormat
										   , query.getBreakPage()
										   , excludeEmp
//未使用								   , monthLimit					2022.02.24 稲熊 削除
										   , baseMonth
										   , baseDate);

		//エラー
		val dataSetter             = context.getDataSetter();
		List<String> employeeError = data.getEmployeeError();
		if (!employeeError.isEmpty()) {
			dataSetter.setData("messageId", "Msg_1344");
			dataSetter.setData("totalEmpErr", employeeError.size());
			for (int i = 0; i < employeeError.size(); i++) {
				dataSetter.setData("empErr" + i, employeeError.get(i));
			}
		}
		// invoke generator
		this.generator.generate(context.getGeneratorContext(), data);
	}


//=============================================================================================================
//  2022.02.24 稲熊 削除 START
//	/**
//	 * Get startYm, endYm
//	 * @param startYm output
//	 * @param endYm output
//	 */
//	private YearMonth getStartYearMonth(Optional<AgreementOperationSettingImport> agreementSetObj, Year fiscalYear) {
//		String month = "01";
//		// 「36協定運用設定」．起算月から年度の期間を求める
//		if (agreementSetObj.isPresent()) {
//			switch (agreementSetObj.get().getStartingMonth()) {
//			case JANUARY:
//				month = "01";
//				break;
//			case FEBRUARY:
//				month = "02";
//				break;
//			case MARCH:
//				month = "03";
//				break;
//			case APRIL:
//				month = "04";
//				break;
//			case MAY:
//				month = "05";
//				break;
//			case JUNE:
//				month = "06";
//				break;
//			case JULY:
//				month = "07";
//				break;
//			case AUGUST:
//				month = "08";
//				break;
//			case SEPTEMBER:
//				month = "09";
//				break;
//			case OCTOBER:
//				month = "10";
//				break;
//			case NOVEMBER:
//				month = "11";
//				break;
//			case DECEMBER:
//				month = "12";
//				break;
//			}
//		}
//		StringBuilder ym = new StringBuilder();
//		ym.append(fiscalYear.toString());
//		ym.append('/');
//		ym.append(month);
//		return YearMonth.parse(ym.toString(), DateTimeFormatter.ofPattern("uuuu/MM"));
//	}
//  2022.02.24 稲熊 削除 END
//=============================================================================================================

	/**
	 * Create data export*/
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private ExportData outputProcess(
			String         cid, 
			String         layoutId, 
			Year           fiscalYear, 
			YearMonth      startYm, 
			YearMonth      endYm, 
			List<Employee> employees, 
			PrintFormat    printFormat, 
			int            breakPage, 
			ExcludeEmp     excludeEmp, 
//未使用	Integer        monthLimit,				2022.02.24 稲熊 削除
			Integer        baseMonth, 
			GeneralDate    baseDate) {

		ExportData exportData = new ExportData();
		YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(
			nts.arc.time.YearMonth.of(startYm.getYear(), startYm.getMonthValue()),
			nts.arc.time.YearMonth.of(endYm.getYear(),   endYm.getMonthValue())
		);

		// ドメインモデル「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		Optional<SettingOutputItemOfAnnualWorkSchedule> domain = this.setOutputItemOfAnnualWorkSchRepo.findByLayoutId(layoutId);
		
		// if can not find domain by condition
		if (!domain.isPresent()) {
			throw new BusinessException("");
		}

		SettingOutputItemOfAnnualWorkSchedule setOutItemsWoSc = domain.get();

		// 帳表出力前チェックをする
		this.checkBeforOutput(startYm, endYm, employees, setOutItemsWoSc, printFormat);

		// ユーザ固有情報「年間勤務表（36チェックリスト）」を更新する -> client
		exportData.setPageBreak(EnumAdaptor.valueOf(breakPage, PageBreakIndicator.class));
		LocalDate endYmd = LocalDate.of(endYm.getYear(), endYm.getMonthValue(), 1).plus(1, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS);
		List<String> employeeIds = employees.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());

		// init map employees data
		// <<Public>> 社員の情報を取得する
		exportData.setEmployees(this.getEmployeeInfo(employees, employeeIds, endYmd));
		HeaderData header = new HeaderData();
		header.setPrintFormat(printFormat);
		header.setMonthsInTotalDisplay(setOutItemsWoSc.isMultiMonthDisplay() ? setOutItemsWoSc.getMonthsInTotalDisplay() : Optional.empty());
		header.setTitle(companyAdapter.getCurrentCompany().map(m -> m.getCompanyName()).orElse(""));

		// B1_1 + B1_2
		String periodStr = startYm.until(endYm, ChronoUnit.MONTHS) == 0
						 ? startYm.format(DateTimeFormatter.ofPattern(YM_FORMATER))
						 : startYm.format(DateTimeFormatter.ofPattern(YM_FORMATER)) + "　"
						 	+ TextResource.localize("KWR008_75") + "　"
						 	+ endYm.format(DateTimeFormatter.ofPattern(YM_FORMATER));
		header.setPeriod(TextResource.localize("KWR008_41") + periodStr);

		List<ItemsOutputToBookTable> listItemOut = setOutItemsWoSc.getListItemsOutput().stream().filter(item -> item.isUseClass()) // ドメインモデル「帳表に出力する項目．使用区分」をチェックする
				.sorted((i1, i2) -> Integer.compare(i1.getSortBy(), i2.getSortBy())).collect(Collectors.toList());

		// A1_2
		header.setReportName(setOutItemsWoSc.getName().v());

		if (PrintFormat.AGREEMENT_36.equals(printFormat)) {
//			if (!setOutItemsWoSc.isMultiMonthDisplay()) {
//				listItemOut = listItemOut.stream().filter(x -> x.getSortBy() != 2).collect(Collectors.toList());
//			}
			exportData.setOutNumExceedTime36Agr(setOutItemsWoSc.isOutNumExceedTime36Agr());
		} else {
			listItemOut = listItemOut.stream().filter(x -> x.getSortBy() > 2).collect(Collectors.toList());
			exportData.setOutNumExceedTime36Agr(false);
		}

		exportData.setExportItems(
				listItemOut.stream()
						   .map(m -> new ExportItem(m.getItemOutCd().v(), m.getHeadingName().v()))
						   .collect(Collectors.toList())
		);

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

		// set C1_2
		header.setMonths(this.createMonthLabels(startYmClone, endYmClone));
		exportData.setHeader(header);

		// 「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		if (PrintFormat.AGREEMENT_36.equals(printFormat)) {
			// 36協定対象外者のチェック
			employeeIds = this.checkExcludeEmp36Agreement(excludeEmp, employeeIds, endYmd);

			// アルゴリズム「年間勤務表の作成」(36協定チェックリスト)を実行する
			this.createAnnualWorkSchedule36Agreement(
					  cid
					, exportData
//未使用			, yearMonthPeriod				2022.02.24 稲熊 削除
					, employeeIds
					, listItemOut
					, fiscalYear
					, setOutItemsWoSc
					, baseDate
					, baseMonth
					, startYm
			);
		} else {
			// 年間勤務表(勤怠チェックリスト)を作成
			this.createAnnualWorkScheduleAttendance(exportData, yearMonthPeriod, employeeIds, listItemOut, baseDate, startYm);
		}
		// 社員を並び替える
		this.sortEmployees(exportData, endYmd);
		// 出力するデータ件数をチェックする
		if (!exportData.hasDataItemOutput()) {
			throw new BusinessException("Msg_885");
		}
		exportData.setReportName(setOutItemsWoSc.getName().v());
		return exportData;
	}

	/**
	 * 年間勤務表の作成  (36協定チェックリスト)
	 *
	 * @param cid             the cid
	 * @param exportData      the export data
	 * @param yearMonthPeriod the year month period
	 * @param employeeIds:    対象社員ID（List）
	 * @param listItemOut     the list item out
	 * @param fiscalYear:     年度
	 * @param startYm         the start ym
	 * @param isOutNumExceed  the is out num exceed
	 * @param periodAtr       the period atr
	 * @param monthLimit      the month limit
	 * @param baseMonth       the base month
	 * @param setOutItemsWoSc the set out items wo sc
	 * @param baseDate        the base date
	 */
	private void createAnnualWorkSchedule36Agreement(String                                cid
												   , ExportData                            exportData
//未使用										   , YearMonthPeriod                       yearMonthPeriod				2022.02.24 稲熊 削除
												   , List<String>                          employeeIds
												   , List<ItemsOutputToBookTable>          listItemOut
												   , Year                                  fiscalYear
												   , SettingOutputItemOfAnnualWorkSchedule setOutItemsWoSc
												   , GeneralDate                           baseDate
												   , Integer                               baseMonth
												   , YearMonth                             startYm) {

		List<ItemsOutputToBookTable> outputAgreementTime36 = listItemOut.stream().filter(m -> m.getSortBy() <= 2).collect(Collectors.toList());

		// ドメインモデル「集計可能な月次の勤怠項目」を取得する
		List<Integer> atdCanbeAggregate = this.monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(cid)
																			 .stream()
																			 .map(t -> t.v().intValue())
																			 .collect(Collectors.toList());
		//年度を指定して36協定期間を取得 - get RequestList554
		Optional<DatePeriod> period = this.getAgreementPeriodAdapter.byYear(
				cid,
				fiscalYear
		);

		if (!period.isPresent()) {
			throw new BusinessException("Msg_1513");
		}
		DatePeriod datePeriod = period.get(); 
		YearMonthPeriod yearMonthPeriodRQL554 = new YearMonthPeriod(datePeriod.start().yearMonth(),datePeriod.end().yearMonth());

		// 社員の指定期間中の所属期間を取得する（年月）(Có được thời gian liên kết của nhân viên trong thời gian được chỉ định)
		// RequestList 589
        EmpAffInfoExport empAffInfoExport = this.workRecordExport.getAffiliationPeriod(
        		employeeIds, 							// ﾊﾟﾗﾒｰﾀ.社員ID（List）
        		yearMonthPeriodRQL554,				  	// (RQ554 Output)期間
        		baseDate  	  							// KWR008画面設定．社員範囲選択の基準日
		);

        // (RQ589から求めた期間情報（List）．年月期間のみ
        List<YearMonthPeriod> lstYearMonthPeriods = empAffInfoExport.getAffiliationStatus().stream()
        		.flatMap(t -> t.getPeriodInformation().stream().map(u -> u.getYearMonthPeriod()))
        		.collect(Collectors.toList());

		for (String empId : employeeIds) {
			EmployeeData empData = exportData.getEmployees().get(empId);
			Map<String, AnnualWorkScheduleData> annualWorkScheduleData = new HashMap<>();

			// アルゴリズム「36協定明細項目の作成」
			annualWorkScheduleData.putAll(this.create36AgreementTime(
					exportData
					, outputAgreementTime36
					, exportData.getHeader() == null
							? new ArrayList<>()
							: (exportData.getHeader().getMonthPeriodLabels() == null ? new ArrayList<>() : exportData.getHeader().getMonthPeriodLabels())
					, lstYearMonthPeriods
					, empId
					, baseDate
					, fiscalYear
					, yearMonthPeriodRQL554.end().lastGeneralDate()
					, setOutItemsWoSc
					, yearMonthPeriodRQL554
					, baseMonth
					, startYm
				)
			);
			empData.setAnnualWorkSchedule(annualWorkScheduleData);
				

			// アルゴリズム「任意項目の作成」を実行する
			this.createOptionalItems(exportData
				   				   , empId
								   , listItemOut.stream().filter(item -> item.getSortBy() > 2).collect(Collectors.toList())
								   , baseDate
								   , lstYearMonthPeriods
								   , atdCanbeAggregate
								   , startYm);

			
		}
		// 対象の社員IDをエラーリストに格納する
		exportData.storeEmployeeError();
	}

	/**
	 * 36協定明細項目の作成 .
	 *
	 * @param lstItemsOut				出力する項目の設定（List）
	 * @param header the header
	 * @param yearMonthPeriodRQ589 	 	年月期間（List）
	 * @param employeeId 		    	社員ID
	 * @param baseDate 			    	基準日(社員範囲選択の基準日)
	 * @param non36Agreement 	   		年度(36協定年度)
	 * @return the map
	 */
	private Map<String, AnnualWorkScheduleData> create36AgreementTime(ExportData                            exportData
																	, List<ItemsOutputToBookTable>          lstItemsOut
																	, List<String>                          header
																	, List<YearMonthPeriod>                 yearMonthPeriodRQ589
																	, String                                employeeId
																	, GeneralDate                           baseDate
																	, Year                                  non36Agreement
														  		 	, GeneralDate                           endDate
														  		 	, SettingOutputItemOfAnnualWorkSchedule setting
														  		 	, YearMonthPeriod                       yearMonthPeriodRQL554
														  		 	, Integer                               baseMonth
																    , YearMonth                             startYm) {
		Map<String, AnnualWorkScheduleData> data = new HashMap<>();

        val require = requireService.createRequire();
        List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriods = new ArrayList<>();
        // 年月期間（List）のループ(i)
		for (YearMonthPeriod yearMonthPeriod : yearMonthPeriodRQ589) {
			// RequestList612
			// 【36協定時間】と【36協定上限時間】12カ月分を取得する
			agreementTimeOfManagePeriods.addAll(GetAgreementTimeOfMngPeriod.get(
					require,
					Arrays.asList(employeeId),
					yearMonthPeriod
			));
		}

		// 36協定複数月項目の作成 start
		// 複数月表示をチェック
		AgreementExcessInfo agreementExcessInfo = new AgreementExcessInfo();

		// 超過月数を出力するか
		if (setting.isOutNumExceedTime36Agr()) {
			// [RQ.555] 年間超過回数と残数の取得
			agreementExcessInfo = GetExcessTimesYear.getWithRemainTimes(require, employeeId, non36Agreement, baseDate);
		}
		List<AgreMaxAverageTimeImport> listAgreMaxAverageTime = new ArrayList<>();
		// 複数月表示をチェック
		if (setting.isMultiMonthDisplay()) {
			// 暦上の年月変換処理
			nts.arc.time.YearMonth specifiedYearMonth = this.converYearMonth(yearMonthPeriodRQL554, baseMonth);

			// 36協定上限時間の複数月平均を取得する
			List<AgreMaxAverageTimeImport> listAgreMaxAverageTimeImport = this.createMonthlyAverage(
					AppContexts.user().companyId(),
					employeeId,
					endDate,
					specifiedYearMonth
			).stream().sorted((a, b) -> a.getPeriod().start().compareTo(b.getPeriod().start())).collect(Collectors.toList());

			// for header
			List<String> periodLable = new ArrayList<>();
			for (AgreMaxAverageTimeImport agreMaxAverage: listAgreMaxAverageTimeImport) {
				listAgreMaxAverageTime.add(agreMaxAverage);
				periodLable.add(agreMaxAverage.getPeriod().start().month() + "～" +  agreMaxAverage.getPeriod().end().month());
			}
			
			if (setting.isMultiMonthDisplay()
			 && setting.getTotalAverageDisplay().isPresent()
			 && setting.getTotalAverageDisplay().get() == TotalAverageDisplay.AVERAGE) {
				HeaderData headerData = exportData.getHeader();
				headerData.setMonthPeriodLabels(periodLable);
				headerData.setMaximumAgreementTime(true);
				exportData.setHeader(headerData);
			}	
		}
		// 36協定複数月項目の作成 end


		// 出力するデータがある(có data xuất ra)
		if (!agreementTimeOfManagePeriods.isEmpty()) {
			// RequestList549
			// 【36協定時間】と【36協定上限時間】の年間合計を取得する
			Optional<AgreementTimeYearImport> agreementTimeYearImport = this.agreementTimeByPeriodAdapter.timeYear(
					employeeId,
					baseDate,
					non36Agreement
			);

			// 画面の出力項目一覧の並び順に従う(1～2)
			for (ItemsOutputToBookTable itemOut : lstItemsOut) {
				// 並び順をチェック
				if (itemOut.getSortBy() == 1 && itemOut.isUseClass()) {
					// アルゴリズム「月平均の算出」を実行する
					data.put(itemOut.getItemOutCd().v(),
							AnnualWorkScheduleData.fromAgreementTimeList(
									itemOut
									, agreementTimeYearImport.isPresent() ? agreementTimeYearImport.get().getLimitTime() : null
									, agreementTimeOfManagePeriods
									, new ArrayList<>()
									, startYm
									, agreementExcessInfo.getExcessTimes()
									, agreementExcessInfo.getRemainTimes()
									, header
									, false
									, agreementTimeYearImport.get().getStatus()
							).calc(true)
					);
				}

				if (itemOut.getSortBy() == 2 && itemOut.isUseClass()) {
					
					// アルゴリズム「月平均の算出」を実行する
					data.put(itemOut.getItemOutCd().v(),
							AnnualWorkScheduleData.fromAgreementTimeList(
									itemOut
									, agreementTimeYearImport.isPresent() ? agreementTimeYearImport.get().getRecordTime() : null
									, agreementTimeOfManagePeriods
									, listAgreMaxAverageTime
									, startYm
									, 0
									, 0
									, exportData.getHeader().getMonthPeriodLabels() != null ?
											exportData.getHeader().getMonthPeriodLabels() : Collections.emptyList()
									, true
									, agreementTimeYearImport.get().getStatus()
							).calc(true)
					);
				}
			}
		}

		return data;
	}

	/**
	 * 36協定上限時間の複数月平均を取得する
	 */
	private List<AgreMaxAverageTimeImport> createMonthlyAverage(String companyId, String employeeId, GeneralDate criteria, nts.arc.time.YearMonth yearMonth) {
		//get requestList547
		Optional<AgreMaxAverageTimeMultiImport> agreMaxAverageTimeMulti = this.agreementTimeByPeriodAdapter.maxAverageTimeMulti(
			companyId,
			employeeId,
			criteria,
			yearMonth
		);

		if (agreMaxAverageTimeMulti.isPresent()) {
			return agreMaxAverageTimeMulti.get().getAverageTimes();
		}
		return new ArrayList<>();
	}

	/**
	 * 任意項目の作成.
	 *
	 * @param exportData the export data
	 * @param yearMonthPeriod 対象期間
	 * @param employeeIds the employee ids
	 * @param listItemOut 「36協定時間」以外の出力対象の項目設定（List）
	 * @param startYm the start ym
	 * @param baseDate t
	 * @param yearMonthPeriods the year month periods
	 * @param atdIdCanBeAggregate the atd id can be aggregate
	 */
	private void createOptionalItems(ExportData						 exportData
								   , String							 employeeId
								   , List<ItemsOutputToBookTable>	 listItemOut
								   , GeneralDate					 baseDate
								   , List<YearMonthPeriod>			 yearMonthPeriods
								   , List<Integer>					 atdIdCanBeAggregate
								   , YearMonth						 startYm) {
		
		// 画面の出力項目一覧の並び順に従う
		for (ItemsOutputToBookTable itemsOutputToBookTable : listItemOut) {

			// ドメインモデル「年間勤務表の月次表示項目．使用区分」をチェックする
			// 使用しない
			if (!itemsOutputToBookTable.isUseClass()) {
				continue;
			}

			// case 年間勤務表の月次表示項目．使用区分 = true
			for (YearMonthPeriod yearMonthPeriod : yearMonthPeriods) {
				
				// アルゴリズム「出力項目の値の算出」を実行する
				exportData.getEmployees().get(employeeId).getAnnualWorkSchedule().put(
					itemsOutputToBookTable.getItemOutCd().v(),
					this.createOptionalItem(
						employeeId
						, itemsOutputToBookTable
						, yearMonthPeriod
						, atdIdCanBeAggregate
						, startYm
					)
				);
			}
			
		}
	}

	/**
	 * 帳表出力前チェックをする
	 */
	private void checkBeforOutput(YearMonth startYm
								, YearMonth endYm
								, List<Employee> employees
								, SettingOutputItemOfAnnualWorkSchedule setOutItemsWoSc
								, PrintFormat printFormat) {
		// 対象期間をチェックする
		if (startYm.until(endYm, ChronoUnit.MONTHS) + 1 > 12)
			throw new BusinessException("Msg_883");
		// 出力対象の社員をチェックする
		if (employees == null || employees.isEmpty())
			throw new BusinessException("Msg_884");
		// 出力項目をチェックする
		List<ItemsOutputToBookTable> listItemsOutputToBookTable = setOutItemsWoSc.getListItemsOutput().stream()
																		 .filter(x -> x.isUseClass())
																		 .collect(Collectors.toList());
		if (PrintFormat.ATTENDANCE.equals(printFormat)) {
			// 印刷形式="勤怠チェックリスト"の場合
			listItemsOutputToBookTable = listItemsOutputToBookTable.stream().filter(x -> x.getSortBy() != 1 && x.getSortBy() != 2).collect(Collectors.toList());
		}

		if (listItemsOutputToBookTable.size() == 0) {
			throw new BusinessException("Msg_880");
		}
	}

	/**
	 * 社員の情報を取得する 
	 * @param employees data Employees from client
	 * @param employeeIds 対象社員ID（List）
	 * @param endYmd 対象期間の終了日
	 * @return
	 */
	private Map<String, EmployeeData> getEmployeeInfo(List<Employee> employees, List<String> employeeIds, LocalDate endYmd) {
		
		Map<String, String> empNameMap = new HashMap<>();
		for (Employee employee : employees) {
			empNameMap.put(employee.getEmployeeId(), employee.getName());
		}
		//employees.stream().collect(Collectors.toMap(Employee::getEmployeeId, Employee::getName));
		Map<String, EmployeeData> employeesData = new HashMap<>();
		employeeInformationAdapter.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds,
				GeneralDate.localDate(endYmd), true, false, true, true, false, false)).forEach(emp -> {
					employeesData.put(emp.getEmployeeId(), EmployeeData.builder()
							.employeeInfo(new EmployeeInfo(
									emp.getWorkplace() == null ? "" : emp.getWorkplace().getWorkplaceCode(),
									emp.getWorkplace() == null ? "マスタ未登録" : emp.getWorkplace().getWorkplaceName(),
									emp.getEmployeeCode(),
									empNameMap.get(emp.getEmployeeId()),
									emp.getEmployment() == null ? "" : emp.getEmployment().getEmploymentCode(),
									emp.getEmployment() == null ? "" : emp.getEmployment().getEmploymentName(),
									emp.getPosition() == null ? "" : emp.getPosition().getPositionCode(),
									emp.getPosition() == null ? "" : emp.getPosition().getPositionName()))
							.annualWorkSchedule(new HashMap<>()).build());
				});
		return employeesData;
	}

	/**
	 * 36協定対象外者のチェック
	 */
	private List<String> checkExcludeEmp36Agreement(ExcludeEmp excludeEmp, List<String> employeeIds, LocalDate endYmd) {
		List<String> empId = new ArrayList<>();
		GeneralDate endDate = GeneralDate.localDate(endYmd);
		// 年間勤務表（36チェックリスト）の出力条件.印字区分をチェック
		if (ExcludeEmp.NOT_PRINT.equals(excludeEmp)) {
			// <<Public>> 社員の情報を取得する
			List<EmployeeInformationImport> empInfoList = this.employeeInformationAdapter.getEmployeeInfo(
					new EmployeeInformationQueryDtoImport(employeeIds
														, endDate
														, false
														, false
														, true
														, false
														, false
														, false)
			);

			for (EmployeeInformationImport emp : empInfoList) {
				// 取得した社員の「職位情報.管理職とする」をチェック
				if (!this.checkIsManager(emp, endDate)) {
					// パラメータ.対象社員ID（List）へ追加する
					empId.add(emp.getEmployeeId());
				}
			}
		} else {
			// 選択している社員すべて パラメータ.対象社員ID(List) へ追加する
			empId = employeeIds;
		}
		// パラメータ.対象社員ID（List）の件数をチェックする
		if (empId.isEmpty()) {
			// エラーメッセージ(#Msg_1397#)を表示
			throw new BusinessException("Msg_1397");
		}
		return empId;
	}

	private boolean checkIsManager(EmployeeInformationImport emp, GeneralDate endDate) {
		PositionImport postion = emp.getPosition();
		if (postion == null) {
			return false;
		}
		// 職位IDから職位を取得する
		Optional<JobTitleImport> jobTitleOtp = jobTitleAdapter.findByJobId(AppContexts.user().companyId(), emp.getPosition().getPositionId(), endDate);
		if (!jobTitleOtp.isPresent()) {
			return false;
		}
		return jobTitleOtp.get().isManager();
	}

	/**
	 * 出力項目の値の算出.
	 *
	 * @param employeeId 社員ID(1人)
	 * @param itemOut 出力項目設定(勤怠項目、オペレーション)(List)
	 * @param period 対象期間(年月期間)
	 * @param lstAtdCanBeAggregate 集計可能な勤怠項目
	 * @return the map
	 */
	private AnnualWorkScheduleData createOptionalItem(String				 employeeId
												    , ItemsOutputToBookTable itemOut
												    , YearMonthPeriod		 period
												    , List<Integer>			 lstAtdCanBeAggregate
												    , YearMonth				 startYm) {

		// [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
		Map<String, List<MonthlyRecordValueImport>> monthlyValue = this.actualMultipleMonthAdapter.getActualMultipleMonth(
				Arrays.asList(employeeId),
				period,
				itemOut.getListOperationSetting().stream().map(CalculationFormulaOfItem::getAttendanceItemId).collect(Collectors.toList())
		);

		// すべての勤怠項目の取得データをチェックする
		if (monthlyValue.get(employeeId) != null && monthlyValue.get(employeeId).size() > 0) {
			
			// 集計可能な勤怠項目IDかどうかチェックをする
			boolean calculable = itemOut.getListOperationSetting().stream()
					.allMatch(data -> lstAtdCanBeAggregate.contains(data.getAttendanceItemId()));
			// アルゴリズム「月平均の算出」を実行する
			return AnnualWorkScheduleData.fromMonthlyAttendanceList(
				itemOut,
				monthlyValue.get(employeeId),
				startYm).calc(calculable);
		}

		return new AnnualWorkScheduleData();

	}

	/**
	 * 年間勤務表(勤怠チェックリスト)を作成.
	 *
	 * @param exportData the export data
	 * @param yearMonthPeriod 対象期間(年度から求めた期間)(A3_2)
	 * @param employeeIds List<社員ID>
	 * @param listItemOut 「36協定時間」以外の出力対象の項目設定（List）
	 * @param baseDate 社員範囲選択の基準日
	 */
	private void createAnnualWorkScheduleAttendance(ExportData					 exportData
												  , YearMonthPeriod				 yearMonthPeriod
												  , List<String>				 employeeIds
												  , List<ItemsOutputToBookTable> listItemOut
												  , GeneralDate					 baseDate
												  , YearMonth					 startYm) {
		
		// ドメインモデル「集計可能な月次の勤怠項目」を取得する
		List<Integer> atdCanbeAggregate = this.monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(AppContexts.user().companyId())
																			 .stream()
																			 .map(t -> t.v().intValue())
																			 .collect(Collectors.toList());
		
		// 社員の指定期間中の所属期間を取得する（年月）
		// RequestList 589
        EmpAffInfoExport empAffInfoExport = this.workRecordExport.getAffiliationPeriod(
        		employeeIds, 							// ﾊﾟﾗﾒｰﾀ.List<社員ID>
        		yearMonthPeriod,				  		// ﾊﾟﾗﾒｰﾀ.対象期間(年月期間)(A3_2)
        		baseDate  	  							// ﾊﾟﾗﾒｰﾀ.社員範囲選択の基準日
		);
        
        // (RQ589から求めた期間情報（List）．年月期間のみ
        List<YearMonthPeriod> lstYearMonthPeriods = empAffInfoExport.getAffiliationStatus().stream()
        		.flatMap(t -> t.getPeriodInformation().stream().map(u -> u.getYearMonthPeriod()))
        		.collect(Collectors.toList());

        // List<社員ID>分ループ
        for (String sid : employeeIds) {

        	// アルゴリズム「任意項目の作成」を実行する
			this.createOptionalItems(exportData
				   				   , sid
								   , listItemOut.stream().filter(item -> item.getSortBy() > 2).collect(Collectors.toList())
								   , baseDate
								   , lstYearMonthPeriods
								   , atdCanbeAggregate
								   , startYm);
    		
		}
		
		// 対象の社員IDをエラーリストに出力する
		exportData.storeEmployeeError();
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
	 * 社員を並び替える(任意)
	 * @param employeeIds  List＜社員ID＞
	 * @param endYmd  対象期間の終了日
	 * @return
	 */
	private List<String> sortEmployees(List<String> employeeIds, LocalDate endYmd) {
		List<SortingConditionOrderImport> ordersImport = new ArrayList<>();
		ordersImport.add(new SortingConditionOrderImport(1, RegularSortingTypeImport.WORKPLACE));
		List<String> listEmp = employeeAdapter.sortEmployee(AppContexts.user().companyId(), employeeIds, ordersImport, GeneralDateTime.localDateTime(LocalDateTime.of(endYmd, LocalTime.of(0, 0))));
		return listEmp;
	}

	private void sortEmployees(ExportData exportData, LocalDate endYmd) {
		List<String> listEmpSorted = new ArrayList<>();
		List<String> listEmpIdOrgin = exportData.getEmployees().entrySet().stream().map(x -> x.getKey()).collect(Collectors.toList());
		List<String> listEmp = this.sortEmployees(listEmpIdOrgin, endYmd);
		listEmpSorted.addAll(listEmp);
		listEmpSorted.removeAll(exportData.getEmployeeIdsError());
		exportData.setEmployeeIds(listEmpSorted);
	}
	
	/**
	 * アルゴリズム「暦上の年月変換処理」を実行
	 *
	 */
	private nts.arc.time.YearMonth converYearMonth(YearMonthPeriod yearMonthPeriodRQL554, int baseMonth){
		if(baseMonth > yearMonthPeriodRQL554.end().month()) {
			return nts.arc.time.YearMonth.of(yearMonthPeriodRQL554.start().year(), baseMonth);
		}else {
			return nts.arc.time.YearMonth.of(yearMonthPeriodRQL554.end().year(), baseMonth);
		}
	}
}
