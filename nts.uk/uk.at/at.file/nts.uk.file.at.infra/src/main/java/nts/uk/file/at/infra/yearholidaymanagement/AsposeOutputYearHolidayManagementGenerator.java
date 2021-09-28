package nts.uk.file.at.infra.yearholidaymanagement;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.StatusOfEmployeeExport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.AnnualHolidayGrantDetailInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualHolidayGrantInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualHolidayGrantInforDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.RqClosureAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.SysAuthWorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceInfoImport;
import nts.uk.file.at.app.export.yearholidaymanagement.AnnualLeaveAcquisitionDate;
import nts.uk.file.at.app.export.yearholidaymanagement.BreakPageType;
import nts.uk.file.at.app.export.yearholidaymanagement.ClosurePrintDto;
import nts.uk.file.at.app.export.yearholidaymanagement.ComparisonConditions;
import nts.uk.file.at.app.export.yearholidaymanagement.EmployeeHolidayInformationExport;
import nts.uk.file.at.app.export.yearholidaymanagement.ExtractionConditionSetting;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementGenerator;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementQuery;
import nts.uk.file.at.app.export.yearholidaymanagement.PeriodToOutput;
import nts.uk.file.at.app.export.yearholidaymanagement.WorkplaceHolidayExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeOutputYearHolidayManagementGenerator extends AsposeCellsReportGenerator
		implements OutputYearHolidayManagementGenerator {
	
	/** The Constant FONT_NAME. */
	private static final String FONT_NAME = "ＭＳ ゴシック";
	/** The Constant COMPANY_ERROR. */
	private static final String COMPANY_ERROR = "Company is not found!!!!";
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/KDR002.xlsx";
	/** The Constant PDF_EXT. */
	private static final String EXCEL_EXT = ".xlsx";
	/** The Constant PDF_EXT. */
	private static final String PDF_EXT = ".pdf";
	/** The Constant EXPORT_EXCEL. */
	private static final int EXPORT_EXCEL = 0;
	/** The Constant EXPORT_PDF. */
	private static final int EXPORT_PDF = 1;
	/** The Constant HEADER_ROW. */
	private static final int HEADER_ROW = 1;
	/** The Constant DES_ROW. */
	private static final int DES_ROW = 0;
	/** The Constant WP_COL. */
	private static final int WP_COL = 0;
	/** The Constant PRINT_DATE_COL. */
	private static final int PRINT_DATE_COL = 9;
	/** The Constant PRINT_EXT_CONDITION_COL. */
	private static final int PRINT_EXT_CONDITION_COL = 15;
	/** The Constant PRINT_EXT_TEXT. */
	private static final int PRINT_EXT_TEXT = 18;
	/** The Constant EMP_CODE_COL. */
	private static final int EMP_CODE_COL = 0;
	/** The Constant EMP_NAME_COL. */
	private static final int EMP_NAME_COL = 1;
	/** The Constant EMP_POS_COL. */
	private static final int EMP_POS_COL = 0;
	/** The Constant GRANT_DATE_COL. */
	private static final int GRANT_DATE_COL = 2;
	/** The Constant GRANT_DEADLINE_COL. */
	private static final int GRANT_DEADLINE_COL = 3;
	/** The Constant GRANT_DAYS_COL. */
	private static final int GRANT_DAYS_COL = 4;
	/** The Constant GRANT_USEDAY_COL. */
	private static final int GRANT_USEDAY_COL = 5;
	/** The Constant GRANT_REMAINDAY_COL. */
	private static final int GRANT_REMAINDAY_COL = 6;
	/** The Constant NEXT_GRANTDATE_COL. */
	private static final int NEXT_GRANTDATE_COL = 22;
	/** The Constant MIN_GRANT_DETAIL_COL. */
	private static final int MIN_GRANT_DETAIL_COL = 7;
	/** The Constant MAX_GRANT_DETAIL_COL. */
	private static final int MAX_GRANT_DETAIL_COL = 21;
	/** The Constant MAX_COL. */
	private static final int MAX_COL = 23;
	/** The Constant MAX_ROW. */
	private static final int MAX_ROW = 34;
	/** The Constant NORMAL_FONT_SIZE. */
	private static final int NORMAL_FONT_SIZE = 9;
	/** The Constant WORKPLACE_TITLE */
	private static final String WORKPLACE_TITLE = "●職場：";
	private static final int DATA_ROW_START = 2;
	
	@Inject
	private CompanyAdapter company;
	@Inject
	private RqClosureAdapter closureAdapter;
	@Inject
	private GetAnnualHolidayGrantInfor getGrantInfo;
	@Inject
	private AnnualHolidayGrantDetailInfor getGrantDetailInfo;
	@Inject
	private EmployeeInformationPub empInfo;
	@Inject
	private ManagedParallelWithContext parallel;
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
	@Inject
	private SysAuthWorkplaceAdapter sysAuthWorkplaceAdapter;

	@Override
	public void generate(FileGeneratorContext generatorContext, OutputYearHolidayManagementQuery query) {
		if (query.getSelectedDateType().equals(PeriodToOutput.AFTER_1_YEAR)) {
			query.setSelectedReferenceType(ReferenceAtr.APP_AND_SCHE.value);
		} else if (query.getSelectedDateType().equals(PeriodToOutput.PAST)) {
			query.setSelectedReferenceType(ReferenceAtr.RECORD.value);
		}
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();

			WorksheetCollection worksheets = workbook.getWorksheets();
			String programName = query.getProgramName();
			// lấy dữ liệu để in
			List<EmployeeHolidayInformationExport> data = getData(query);

			// thực hiện in
			String companyName = company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
					.getCompanyName();
			reportContext.setHeader(0, "&9&\"MS ゴシック\"" + companyName);
			reportContext.setHeader(1, "&16&\"MS ゴシック,Bold\"" + TextResource.localize("KDR002_10"));
			DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm", Locale.JAPAN);
			reportContext.setHeader(2,
					"&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
			String exportTime = query.getExportTime().toString();
			Worksheet normalSheet = worksheets.get(0);
			String normalSheetName = TextResource.localize("KDR002_10");

			printData(normalSheet, programName, companyName, exportTime, data, normalSheetName, query);
			worksheets.setActiveSheetIndex(0);
			reportContext.processDesigner();
			String fileName = programName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN));
			if (query.getMode() == EXPORT_EXCEL) {
				reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));
			} else if(query.getMode() == EXPORT_PDF) {
				reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + PDF_EXT));
			}


		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * xác định base date
	 * 
	 * @param closureData 就業締め日 = 社員範囲選択で選択した締め日
	 * @param selectedDateType 対象期間 = ユーザ固有情報「年休管理表の出力条件.対象期間」
	 * @param printDate 指定年月 = ユーザ固有情報「年休管理表の出力条件.指定年月」
	 * @param period 期間 = ユーザ固有情報「年休管理表の出力条件.期間」
	 * @return baseDate được xác định
	 */
	private GeneralDate dateDetermination(ClosurePrintDto closureData, Integer selectedDateType,
			Integer printDate, DatePeriod period) {

		GeneralDate returnDate = null;
		PresentClosingPeriodImport closure;
		String companyId = AppContexts.user().companyId();
		closure = closureAdapter.getClosureById(companyId, closureData.getClosureId()).get();

		// Input．対象期間をチェックする
		if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.PAST)) {
			// 過去
			// 所属情報取得用の基準日 ← INPUT.指定月 +取得した 「現在締め期間.終了年月日」の日
			// １年以上前（過去）(quá khứ)
			returnDate = GeneralDate.fromString(
					printDate.toString()
							+ StringUtils.leftPad(String.valueOf(closure.getClosureEndDate().day()), 2, '0'),
					"yyyyMMdd");
		}
		if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.AFTER_1_YEAR)) {
			// １年経過時点
			// 所属情報取得用の基準日を返す - Returns the reference date for acquiring affiliation information
			returnDate = period.end().addYears(-1);
		}
		if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.CURRENT)) {
			// 現在
			// 所属情報取得用の基準日 ← 取得した「現在締め期間.終了年月日」
			returnDate = closure.getClosureEndDate();
		}
		
		return returnDate;
	}

	/**
	 * lấy data để chuẩn bị cho việc in dữ liệu
	 * 
	 * @param query
	 * @return data lấy được
	 */

	private List<EmployeeHolidayInformationExport> getData(OutputYearHolidayManagementQuery query) {
		// từ xử lý ユーザ固有情報「年休管理表の出力条件」を更新する trở về trước được thực hiện bên client
		String companyId = AppContexts.user().companyId();
		// アルゴリズム「使用基準日判定処理」を実行する
		GeneralDate baseDate = this.dateDetermination(query.getClosureData(), query.getSelectedDateType().value, query.getPrintDate(), query.getPeriod());
		List<String> empIds = query.getSelectedEmployees().stream().map(x -> {
			return x.getEmployeeId();
		}).collect(Collectors.toList());
		// <<Public>> 社員を並べ替える
		//empIds = empAdaptor.sortEmployee(companyId, empIds, SystemType.EMPLOYMENT.value, 1, 1, GeneralDateTime.now());

		// <<Public>> 社員の情報を取得する
		EmployeeInformationQueryDto param = EmployeeInformationQueryDto.builder().toGetWorkplace(true)
				.toGetPosition(true).toGetEmployment(false).employeeIds(empIds).referenceDate(baseDate).build();

		List<EmployeeHolidayInformationExport> employeeExports = empInfo.find(param).stream()
				.map(x -> EmployeeHolidayInformationExport.fromEmpInfo(x)).collect(Collectors.toList());
		// lại bỏ những data bị lỗi không có WP ID
		employeeExports = employeeExports.stream().filter(x -> x.getWorkplace() != null).collect(Collectors.toList());
		//sắp xếp theo employeeCode
		employeeExports = employeeExports.stream().sorted((a, b) -> a.getEmployeeCode().compareTo(b.getEmployeeCode()))
				.collect(Collectors.toList());
		// 職場IDから階層コードを取得する
		List<String> workplaceIds = employeeExports.stream().map(x -> {
			return x.getWorkplace().getWorkplaceId();
		}).collect(Collectors.toList());
//
//		List<WorkplaceConfigInfo> wpInfos = getHiCDFromWPID(companyId, workplaceIds, baseDate);
//
		
		// [No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInfoImport> wkpInfos = this.sysAuthWorkplaceAdapter
				.getWorkplaceInfoByWkpIds(companyId, workplaceIds, baseDate);
		// set code Hierarchy vào employee
		employeeExports.forEach(emp -> {
			String wpId = emp.getWorkplace().getWorkplaceId();
			String hierarchyCd = wkpInfos.stream().filter(data -> data.getWorkplaceId().equals(wpId))
					.findAny()
					.map(WorkplaceInfoImport::getHierarchyCode)
					.orElse(null);
			emp.getWorkplace().setHierarchyCode(hierarchyCd);
		});
		
		// 職場を職場階層コードの順に並び替える ※帳票出力時は、職場階層コード > 社員コード の順に出力する
		employeeExports.sort(Comparator.comparing(emp -> emp.getWorkplace().getHierarchyCode()));
		// đảo xuống dưới để tiện việc map data
		// 社員分ループ


		List<EmployeeHolidayInformationExport> itemValuesSyncs = Collections
				.synchronizedList(new ArrayList<EmployeeHolidayInformationExport>(employeeExports));

		this.parallel.forEach(itemValuesSyncs, employee -> {
			if (employee != null) {
				// 所属雇用が存在しない社員の場合、次の社員へ
				String empId = employee.getEmployeeId();
				ReferenceAtr refType = EnumAdaptor.valueOf(query.getSelectedReferenceType(), ReferenceAtr.class);
				Optional<AnnualHolidayGrantInfor> holidayInfo = Optional.empty();
				List<AnnualHolidayGrantDetail> holidayDetails = Collections.emptyList();
				
				//対象期間をチェック - (Check khoảng thời gian)
				if (query.getSelectedDateType() == PeriodToOutput.CURRENT) {
					// 社員に対応する処理締めを取得する
					Closure closure = ClosureService.getClosureDataByEmployee(
							ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
							new CacheCarrier(), empId, baseDate);
					// アルゴリズム「年休付与情報を取得」を実行する I - nhận thông tin trợ cấp nghỉ phép hàng năm
					if (closure != null && closure.getClosureMonth() != null) {
						YearMonth yearMonthInput = closure.getClosureMonth().getProcessingYm();
						// RQ550
						GetAnnualHolidayGrantInforDto anualHolidayGrantInfo = this.getGrantInfo.getAnnGrantInfor(companyId,
								empId, refType, yearMonthInput, baseDate, query.getSelectedDateType().value,
								Optional.empty(),
								query.isDoubleTrack(),
								query.isExtCondition(),
								query.getExtractionCondtionSetting().get().getDays(),
								query.getExtractionCondtionSetting().get().getComparisonConditions().value);
						holidayInfo = anualHolidayGrantInfo.getAnnualHolidayGrantInfor();
						
						Optional<GeneralDate> doubleTrackStartDate = holidayInfo
								.map(info -> info.getDoubleTrackStartDate()
									.map(date -> anualHolidayGrantInfo.getAnnualHolidayGrantInfor().get().getDoubleTrackStartDate().get())
									.orElse(null));
						// 抽出対象社員かチェックする
						if(!anualHolidayGrantInfo.isEmployeeExtracted()) {
							return;
						}
						// アルゴリズム「年休明細情報を取得」を実行する II
						// lẩy ra thông tin chi tiết nghỉ hàng năm 
						holidayDetails = getGrantDetailInfo.getAnnHolidayDetail(
								companyId, 
								empId, 
								refType, 
								yearMonthInput,
								baseDate,
								query.getSelectedDateType().value,
								Optional.of(query.getPeriod()),
								doubleTrackStartDate);

					}
				}
				if (query.getSelectedDateType() == PeriodToOutput.PAST) {
					YearMonth printDate = YearMonth.of(query.getPrintDate());
					// アルゴリズム「年休付与情報を取得」を実行する I
					GetAnnualHolidayGrantInforDto anualHolidayGrantInfo = this.getGrantInfo.getAnnGrantInfor(companyId,
							empId, ReferenceAtr.RECORD, printDate, baseDate, 
							query.getSelectedDateType().value,
							Optional.empty(),
							query.isDoubleTrack(),
							query.isExtCondition(),
							query.getExtractionCondtionSetting().get().getDays(),
							query.getExtractionCondtionSetting().get().getComparisonConditions().value);
					holidayInfo = anualHolidayGrantInfo.getAnnualHolidayGrantInfor();
					Optional<GeneralDate> doubleTrackStartDate = holidayInfo
							.map(info -> info.getDoubleTrackStartDate()
								.map(date -> anualHolidayGrantInfo.getAnnualHolidayGrantInfor().get().getDoubleTrackStartDate().get())
								.orElse(null));
					// 抽出対象社員かチェックする
					if(!anualHolidayGrantInfo.isEmployeeExtracted()) {
						return;
					}
					// holidayInfo
					// アルゴリズム「年休明細情報を取得」を実行する II
					holidayDetails = getGrantDetailInfo.getAnnHolidayDetail(
							companyId, 
							empId, 
							ReferenceAtr.RECORD, 
							printDate, 
							baseDate,
							query.getSelectedDateType().value,
							Optional.of(query.getPeriod()),
							doubleTrackStartDate);

				}
				if (query.getSelectedDateType() == PeriodToOutput.AFTER_1_YEAR) { 
					YearMonth printDate = YearMonth.of(query.getPrintDate());
					// アルゴリズム「年休付与情報を取得」を実行する I
					// 抽出対象社員かチェックする
					GetAnnualHolidayGrantInforDto anualHolidayGrantInfo = this.getGrantInfo.getAnnGrantInfor(companyId,
							empId, ReferenceAtr.RECORD, printDate, baseDate,
							query.getSelectedDateType().value,
							Optional.of(query.getPeriod()),
							query.isDoubleTrack(),
							query.isExtCondition(),
							query.getExtractionCondtionSetting().get().getDays(),
							query.getExtractionCondtionSetting().get().getComparisonConditions().value);
					holidayInfo = anualHolidayGrantInfo.getAnnualHolidayGrantInfor();
					Optional<GeneralDate> doubleTrackStartDate = holidayInfo
							.map(info -> info.getDoubleTrackStartDate()
								.map(date -> anualHolidayGrantInfo.getAnnualHolidayGrantInfor().get().getDoubleTrackStartDate().get())
								.orElse(null));
					// 抽出対象社員かチェックする
					if(!anualHolidayGrantInfo.isEmployeeExtracted()) {
						return;
					}
					// アルゴリズム「年休明細情報を取得」を実行する II
					holidayDetails = getGrantDetailInfo.getAnnHolidayDetail(
							companyId, 
							empId, 
							ReferenceAtr.RECORD, 
							printDate, 
							baseDate,
							query.getSelectedDateType().value,
							Optional.of(query.getPeriod()),
							doubleTrackStartDate);
				}
				// ❻入社退職の考慮
				AnnualHolidayGrantInfor annalInforJoinLeaving = holidayInfo.orElse(null);
				// 年休付与情報、年休使用詳細について、入社・退職の考慮を行う
				// Consider joining / leaving the company for annual leave grant information and details of annual leave usage
				AnnualHolidayGrantData holidayGrantData = this.getJoinLeavingForAnnualLeaveGrantInfo(employee.getEmployeeId(), annalInforJoinLeaving, holidayDetails);
				List<AnnualHolidayGrantDetail> holidayDetailsSort   = holidayGrantData.getHolidayDetails();
				if (!holidayGrantData.getHolidayDetails().isEmpty()) {
					holidayDetailsSort = holidayGrantData.getHolidayDetails().stream()
							.sorted((a, b) -> a.getYmd().compareTo(b.getYmd())).collect(Collectors.toList());
				}
				employee.setHolidayInfo(holidayGrantData.getAnnualHolidayGrantInfor());
				employee.setHolidayDetails(holidayDetailsSort);
			}
		});

		employeeExports = itemValuesSyncs.stream().filter(
				data -> data.getHolidayInfo().map(grantInfo -> !grantInfo.getLstGrantInfor().isEmpty()).orElse(false)
						|| !data.getHolidayDetails().isEmpty())
				.collect(Collectors.toList());
		
		// 印刷件数を確認する- Check the number of prints
		if(employeeExports.isEmpty()) {
			//０件
			//エラーメッセージ(Msg_37)を表示する
			throw new BusinessException("Msg_37");
		}
		// [No.560]職場IDから職場の情報をすべて取得する - Get all workplace information from the workplace ID
		Map<WorkplaceHolidayExport, List<EmployeeHolidayInformationExport>> resultmap = employeeExports.stream()
				.collect(Collectors.groupingBy(o -> o.getWorkplace()));

		resultmap = resultmap.entrySet().stream()
				.filter(f -> f.getKey().getHierarchyCode() != null)
				.sorted((o1, o2) -> o1.getKey().getHierarchyCode().compareTo(o2.getKey().getHierarchyCode()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		return resultmap.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
	}

	/**
	 * set dữ liệu vào worksheet
	 * 
	 * @param worksheet
	 * @param programId
	 * @param companyName
	 * @param exportTime
	 * @param data
	 * @param sheetName
	 * @param query
	 */

	private void printData(Worksheet worksheet, String programId, String companyName, String exportTime,
			List<EmployeeHolidayInformationExport> data, String sheetName, OutputYearHolidayManagementQuery query) {
		try {
			AnnualPaidLeaveSetting annualPaidLeaveSetting = this.annualPaidLeaveSettingRepository
					.findByCompanyId(AppContexts.user().companyId());
			boolean isManageTime = annualPaidLeaveSetting.getTimeSetting().getTimeManageType().equals(ManageDistinct.YES);
			worksheet.setName(sheetName);
			Cells cells = worksheet.getCells();
			this.setHeader(cells, query);
			int lastWPRow = 2;
			int currentRow = 2;
			int beginRow = 0;
			boolean isBlueBackground = false;
			DecimalFormat formatter = new DecimalFormat("0.0#");
			for (int i = 0; i < data.size(); i++) {
				if ((currentRow - beginRow) > MAX_ROW + 1) {
					beginRow = currentRow;
				}
				EmployeeHolidayInformationExport emp = data.get(i);
				AnnualHolidayGrantInfor holidayInfo = emp.getHolidayInfo().isPresent() ? emp.getHolidayInfo().get()
						: null;
				List<AnnualHolidayGrantDetail> holidayDetails = emp.getHolidayDetails();
				// tính tổng số dòng để xác định phân trang nếu data quá quy định
				List<Integer> dataLine = this.getTotalLineOfEmp(holidayInfo, holidayDetails, isManageTime);
				String wpName = WORKPLACE_TITLE + emp.getWorkplace().getWorkplaceCode() + "　"
						+ emp.getWorkplace().getWorkplaceName();
				String lastWpName = cells.get(lastWPRow, 0).getStringValue();
				// tổng số dòng = số dòng data + 1 dòng in WorkPlace (nếu có);
				int totalLine = dataLine.stream().reduce(0, Integer::sum) + (!lastWpName.equals(wpName) ? 1 : 0);
				int maxrow = currentRow < 38 ? MAX_ROW + 1 : MAX_ROW - 3;
				if (currentRow - beginRow + totalLine > maxrow) {
					HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
					pageBreaks.add(currentRow);
					beginRow = currentRow;
					lastWPRow = 0;
				}
				// Print WP Name
				lastWpName = cells.get(lastWPRow, 0).getStringValue();
				if (!lastWpName.equals(wpName)) {
					if (query.getPageBreakSelected() == BreakPageType.WORKPLACE.value && currentRow != 2) {

						HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
						pageBreaks.add(currentRow);
						beginRow = currentRow;
					}
					currentRow = this.printWP(cells, currentRow, wpName);
					lastWPRow = currentRow - 1;
				} else {
					if ((currentRow - beginRow) > MAX_ROW + 1) {
						currentRow = this.printWP(cells, currentRow, wpName);
						lastWPRow = currentRow - 1;
					}
				}

				// Print EmployeeInfoImported Region
				// print emp Code
				String empCode = emp.getEmployeeCode();
				cells.get(currentRow, EMP_CODE_COL).setValue(empCode);
				// print emp Name
				String empName = emp.getBusinessName();
				cells.get(currentRow, EMP_NAME_COL).setValue(empName);
				// print emp pos
				String empPos = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";
				cells.get(currentRow + 1, EMP_POS_COL).setValue(empPos);
				Range employeeNameRange = cells.createRange(currentRow + 1, EMP_POS_COL, 1, 2);
				employeeNameRange.merge();
				// Print AnnualHolidayGrantInfor
				int holidayInfoRow = currentRow;
				if (holidayInfo != null) {
					// print AnnualHolidayGrant
					cells.get(currentRow, NEXT_GRANTDATE_COL).setValue(String.valueOf(holidayInfo.getYmd()));
					if (holidayInfo.getLstGrantInfor().size() > 0) {
						for (int j = 0; j < holidayInfo.getLstGrantInfor().size(); j++) {
							AnnualHolidayGrant grantInfo = holidayInfo.getLstGrantInfor().get(j);
							String grantDate = String.valueOf(grantInfo.getYmd());
							cells.get(holidayInfoRow, GRANT_DATE_COL).setValue(grantDate);
							String grantDealine = String.valueOf(grantInfo.getDeadline());
							cells.get(holidayInfoRow, GRANT_DEADLINE_COL).setValue(grantDealine);
							String grantDay = formatter
									.format(StringUtils.isEmpty(grantDate) ? "0.0" : grantInfo.getGrantDays()).toString();
							cells.get(holidayInfoRow, GRANT_DAYS_COL).setValue(grantDay);
							String useDay = formatter
									.format(StringUtils.isEmpty(grantDate) ? "0.0" : grantInfo.getUseDays()).toString();
							cells.get(holidayInfoRow, GRANT_USEDAY_COL).setValue(useDay);
							String remainDays = formatter
									.format(StringUtils.isEmpty(grantDate) ? "0.0" : grantInfo.getRemainDays())
									.toString();
							cells.get(holidayInfoRow, GRANT_REMAINDAY_COL).setValue(remainDays);

							holidayInfoRow++;
						}
					} else {
						cells.get(holidayInfoRow, GRANT_DATE_COL).setValue("");
						cells.get(holidayInfoRow, GRANT_DAYS_COL).setValue("");
						cells.get(holidayInfoRow, GRANT_USEDAY_COL).setValue("0.0");
						cells.get(holidayInfoRow, GRANT_REMAINDAY_COL).setValue("0.0");
					}
				} else {
					cells.get(holidayInfoRow, GRANT_DATE_COL).setValue("");
					cells.get(holidayInfoRow, GRANT_USEDAY_COL).setValue("0.0");
					cells.get(holidayInfoRow, GRANT_REMAINDAY_COL).setValue("0.0");
				}
				// Print HolidayDetails
				// Lọc dữ liệu schedule trong trường hợp chỉ lấy dữ liệu thực hoặc quá khứ
				if (query.getSelectedReferenceType() == 0 || query.getSelectedDateType().equals(PeriodToOutput.PAST)) {
					holidayDetails = holidayDetails.stream()
							.filter(detail -> detail.getReferenceAtr().equals(ReferenceAtr.RECORD))
							.collect(Collectors.toList());
				}
				int holidayDetailRow = currentRow;
				int holidayDetailCol = MIN_GRANT_DETAIL_COL;
				for (int j = 0; j < holidayDetails.size(); j++) {
					if (!holidayDetails.get(j).getUsedNumbers().getUsedDays().isPresent()
							|| holidayDetails.get(j).getUsedNumbers().getUsedDays().get().v().equals(0d)) continue;
					AnnualHolidayGrantDetail detail = holidayDetails.get(j);
					cells.get(holidayDetailRow, holidayDetailCol).setValue(this.genHolidayText(detail, query.getPrintAnnualLeaveDate()));
					if (holidayDetailCol == MAX_GRANT_DETAIL_COL) {
						holidayDetailRow++;
						holidayDetailCol = MIN_GRANT_DETAIL_COL;
					} else {
						holidayDetailCol++;
					}
				}

				currentRow = currentRow + dataLine.get(0);
				isBlueBackground = this.setRowStyle(cells, currentRow, dataLine.get(0), isBlueBackground, isManageTime, false);
				
				// If manageTime in KMF001C = ON
				if (isManageTime) {
					holidayInfoRow = currentRow;
					// Print AnnualHolidayGrantInfor
					if (holidayInfo != null) {
						// print AnnualHolidayGrant
						if (holidayInfo.getLstGrantInfor().size() > 0) {
							for (int j = 0; j < holidayInfo.getLstGrantInfor().size(); j++) {
								AnnualHolidayGrant grantInfo = holidayInfo.getLstGrantInfor().get(j);
								String grantDate = String.valueOf(grantInfo.getYmd());
								cells.get(holidayInfoRow, GRANT_DATE_COL).setValue(grantDate);
								String grantDealine = String.valueOf(grantInfo.getDeadline());
								cells.get(holidayInfoRow, GRANT_DEADLINE_COL).setValue(grantDealine);
								String grantHours = this.generateTimeText(grantInfo.getGrantMinutes());
								cells.get(holidayInfoRow, GRANT_DAYS_COL).setValue(grantHours);
								String useHours = this.generateTimeText(grantInfo.getUsedMinutes());
								cells.get(holidayInfoRow, GRANT_USEDAY_COL).setValue(useHours);
								String remainHours = this.generateTimeText(grantInfo.getRemainMinutes());
								cells.get(holidayInfoRow, GRANT_REMAINDAY_COL).setValue(remainHours);

								holidayInfoRow++;
							}
						} else {
							cells.get(holidayInfoRow, GRANT_DATE_COL).setValue("");
							cells.get(holidayInfoRow, GRANT_DAYS_COL).setValue("");
							cells.get(holidayInfoRow, GRANT_USEDAY_COL).setValue("0:00");
							cells.get(holidayInfoRow, GRANT_REMAINDAY_COL).setValue("0:00");
						}
					} else {
						cells.get(holidayInfoRow, GRANT_DATE_COL).setValue("");
						cells.get(holidayInfoRow, GRANT_USEDAY_COL).setValue("0:00");
						cells.get(holidayInfoRow, GRANT_REMAINDAY_COL).setValue("0:00");
					}
					// Print HolidayDetails

					holidayDetailRow = currentRow;
					holidayDetailCol = MIN_GRANT_DETAIL_COL;
					List<String> dateList = holidayDetails.stream()
							.filter(detail -> detail.getUsedNumbers().getUsedTime().isPresent()
									&& detail.getUsedNumbers().getUsedTime().get().v() > 0)
							.map(detail -> this.genHolidayText(detail, query.getPrintAnnualLeaveDate()))
							.collect(Collectors.toList());
					List<String> hourList = holidayDetails.stream()
							.filter(detail -> detail.getUsedNumbers().getUsedTime().isPresent()
									&& detail.getUsedNumbers().getUsedTime().get().v() > 0)
							.map(this::genTimeText)
							.collect(Collectors.toList());
					
//					if (holidayInfo != null && 
//							holidayInfo.getLstGrantInfor().stream().anyMatch(item -> item.getGrantMinutes().v() > 0)) {
					int newLineCount = ((int) Math.ceil(dateList.size() / 15.0)) * 2;
					if (newLineCount > dataLine.get(1)) {
						dataLine.set(1, newLineCount);
					}
					for (int j = 0; j < dateList.size(); j++) {
						cells.get(holidayDetailRow, holidayDetailCol).setValue(dateList.get(j));
						cells.get(holidayDetailRow + 1, holidayDetailCol).setValue(hourList.get(j));
						if (holidayDetailCol == MAX_GRANT_DETAIL_COL) {
							holidayDetailRow += 2;
							holidayDetailCol = MIN_GRANT_DETAIL_COL;
						} else {
							holidayDetailCol++;
						}
					}
//					}
					currentRow = currentRow + dataLine.get(1);
					isBlueBackground = this.setRowStyle(cells, currentRow, dataLine.get(1), isBlueBackground, isManageTime, true);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * lấy số dòng cần thiết để in ra dữ liệu của employee đó
	 * 
	 * @param holidayInfo
	 * @param holidayDetails
	 * @return số dòng cần để in ([x, y] với x là số dòng in date, y là số dòng in time (optional))
	 */
	private List<Integer> getTotalLineOfEmp(AnnualHolidayGrantInfor holidayInfo, List<AnnualHolidayGrantDetail> holidayDetails,
			boolean isManageTime) {
		// mặc định là 2 dòng với date, 4 dòng với time (nếu có)
		List<Integer> result = isManageTime ? Arrays.asList(2, 4) : Arrays.asList(2);
		// danh sách các detail sử dụng ngày nghỉ năm
		List<AnnualHolidayGrantDetail> useDays = holidayDetails.stream()
				.filter(detail -> detail.getUsedNumbers().getUsedDays().map(AnnualLeaveUsedDayNumber::v).orElse(0d) > 0)
				.collect(Collectors.toList());
		// danh sách các detail sử dụng giờ nghỉ năm
		List<AnnualHolidayGrantDetail> useHours = holidayDetails.stream()
				.filter(detail -> detail.getUsedNumbers().getUsedTime().map(UsedMinutes::valueAsMinutes).orElse(0) > 0)
				.collect(Collectors.toList());
		// dòng của lineOfDetails = tổng data /15 + 1 dòng nếu /15 có dư ra
		int lineOfDayDetails = (int) Math.ceil(useDays.size() / 15.0);
		// nếu trường hợp time thì tính thêm detail time x2
		int lineOfHourDetails = 0;
		if (isManageTime) {
			lineOfHourDetails = ((int) Math.ceil(useHours.size() / 15.0)) * 2;
		}
		int lineOfholidayInfo = holidayInfo != null ? holidayInfo.getLstGrantInfor().size() : 0;
		
		switch (result.size()) {
		// có in thời gian
		case 2:
			// số dòng của data sẽ là của getLstGrantInfor hoặc holidayDetails nếu 1 trong 2 > 2
			if (lineOfholidayInfo > result.get(1) || lineOfHourDetails > result.get(1)) {
				// số dòng của data tùy thuộc getLstGrantInfor (+1 nếu lẻ) hoặc holidayDetails bên nào lớn hơn
				result.set(1, Collections.max(Arrays.asList((int) Math.ceil(lineOfholidayInfo / 2.0) * 2, lineOfHourDetails)));
			}
		// in ngày
		case 1:
			// số dòng của data sẽ là của getLstGrantInfor hoặc holidayDetails nếu 1 trong 2 > 2
			if (lineOfholidayInfo > result.get(0) || lineOfDayDetails > result.get(0)) {
				// số dòng của data tùy thuộc getLstGrantInfor hoặc holidayDetails bên nào lớn hơn
				result.set(0, Collections.max(Arrays.asList(lineOfholidayInfo, lineOfDayDetails)));
			}
			break;
		}
		return result;
	}

	/**
	 * set border , font style , font size và background color cho row
	 * 
	 * @param cells
	 * @param newRow
	 * @param totalLine
	 * @param isBlueBackground
	 * @return trạng thái màu của dòng kế được in ra (xanh hay trắng)
	 */
	private boolean setRowStyle(Cells cells, int newRow, int totalLine, boolean isBlueBackground, 
			boolean isManageTime, boolean isPrintingTime) {
		Style style = new Style();
		// In case of printing date section
		if (!isPrintingTime) {
			for (int i = totalLine; i > 0; i--) {
				for (int j = 0; j < MAX_COL; j++) {
					Cell cell = cells.get(newRow - i, j);
					if (j != NEXT_GRANTDATE_COL && j != EMP_CODE_COL) {
						if (j == EMP_NAME_COL || j == GRANT_REMAINDAY_COL) {
							this.setBorder(cell, BorderType.RIGHT_BORDER, CellBorderType.THIN);
						} else {
							this.setBorder(cell, BorderType.RIGHT_BORDER, CellBorderType.DOTTED);
						}
						if (j > GRANT_REMAINDAY_COL) {
							this.setBorder(cell, BorderType.BOTTOM_BORDER, CellBorderType.DOTTED);
							if (isBlueBackground) {
								this.setBackGround(cell);
							}
						}
						// case last line
						if (i == 1 && j != EMP_NAME_COL) {
							this.setBorder(cell, BorderType.BOTTOM_BORDER, CellBorderType.THIN);
						}
					}
					this.setTextStyle(cell);
				}
				// case last line
				if (i == 1) {
					isBlueBackground = false;
				} else {
					isBlueBackground = !isBlueBackground;
				}
			}
		} else { 
			// In case of printing time section
			for (int i = totalLine; i > 0; i--) {
				for (int j = 0; j < MAX_COL; j++) {
					Cell cell = cells.get(newRow - i, j);
					if (j != NEXT_GRANTDATE_COL && j != EMP_CODE_COL) {
						if (j == EMP_NAME_COL || j == GRANT_REMAINDAY_COL) {
							this.setBorder(cell, BorderType.RIGHT_BORDER, CellBorderType.THIN);
						} else {
							this.setBorder(cell, BorderType.RIGHT_BORDER, CellBorderType.DOTTED);
						}
						if (j > GRANT_REMAINDAY_COL) {
							this.setBorder(cell, BorderType.BOTTOM_BORDER, CellBorderType.DOTTED);
							if (isBlueBackground) {
								this.setBackGround(cell);
							}
						}
					}
					this.setTextStyle(cell);
				}
				if (i == 1) {
					isBlueBackground = false;
				} else if ((totalLine - i) % 2 !=0 && i != totalLine) {
					isBlueBackground = !isBlueBackground;
				}
				
			}
		}
		
		// set border when end employee
		if (!isManageTime || isPrintingTime) {
			for (int i = 0; i < MAX_COL; i++) {
				style.copy(cells.get(newRow - 1, i).getStyle());
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
				cells.get(newRow - 1, i).setStyle(style);
			}
		}
		return isBlueBackground;
	}

	/**
	 * set màu cho background
	 * 
	 * @param cell
	 */
	private void setBackGround(Cell cell) {
		Style style = new Style();
		style.copy(cell.getStyle());
		style.setPattern(BackgroundType.SOLID);
		style.setForegroundColor(Color.fromArgb(211, 235, 247));
		cell.setStyle(style);
	}

	/**
	 * set font style và font size cho cell
	 * 
	 * @param cell
	 */
	private void setTextStyle(Cell cell) {
		Style style = new Style();
		int col = cell.getColumn();
		style.copy(cell.getStyle());
		if (col > EMP_NAME_COL) {
//			if (col == GRANT_DAYS_COL || col == GRANT_USEDAY_COL || col == GRANT_REMAINDAY_COL) {
//				style.setVerticalAlignment(TextAlignmentType.CENTER);
//				style.setHorizontalAlignment(TextAlignmentType.LEFT);
//			} else {
				style.setVerticalAlignment(TextAlignmentType.CENTER);
				style.setHorizontalAlignment(TextAlignmentType.RIGHT);
//			}
		}
		// set chữ vừa 1 cell
		style.setShrinkToFit(true);
		Font font = style.getFont();
		font.setDoubleSize(NORMAL_FONT_SIZE);
		font.setName(FONT_NAME);
		cell.setStyle(style);
	}

	/**
	 * set border cho cell
	 * 
	 * @param cell
	 * @param border
	 * @param type
	 */
	private void setBorder(Cell cell, int border, int type) {
		Style style = new Style();
		style.copy(cell.getStyle());
		style.setBorder(border, type, Color.getBlack());
		cell.setStyle(style);
	}

	/**
	 * trả về text của Ngày nghỉ theo định đạng được quy ước
	 * 
	 * @param detail
	 * @return text của ngày nghỉ
	 */
	private String genHolidayText(AnnualHolidayGrantDetail detail, AnnualLeaveAcquisitionDate dateType) {
		String format = dateType.equals(AnnualLeaveAcquisitionDate.YMD) ? "yy/MM/dd" : "MM/dd";
		String result = detail.getYmd().toString(format);
		if (detail.isFlexFlag()) {
			switch (detail.getAmPmAtr()) {
			// １日分表記の場合
			case ONE_DAY:
				result += TextResource.localize("KDR002_69");
				break;
			// 半日分表記の場合
			default: 
				result += TextResource.localize("KDR002_70");
			}
		} else {
			if (detail.getAmPmAtr() == AmPmAtr.AM) {
				result += "A";
			}
			if (detail.getAmPmAtr() == AmPmAtr.PM) {
				result += "P";
			}
		}
		return this.formatApplicationOrSchedule(result, detail.getReferenceAtr());
	}
	
	private String genTimeText(AnnualHolidayGrantDetail detail) {
		return detail.getUsedNumbers().getUsedTime()
				.map(this::generateTimeText)
				.map(result -> this.formatApplicationOrSchedule(result, detail.getReferenceAtr()))
				.orElse(null);
	}
	
	private String formatApplicationOrSchedule(String input, ReferenceAtr referenceAtr) {
		if (referenceAtr.equals(ReferenceAtr.APP_AND_SCHE)) {
			return "(" + input + ")";
		}
		return input;
	}

	/**
	 * in WorkPlace
	 * 
	 * @param cells
	 * @param currentRow
	 * @param wpName
	 * @return chỉ số dòng để in dữ liệu tiếp theo
	 */
	private int printWP(Cells cells, int currentRow, String wpName) {
		cells.get(currentRow, 0).setValue(wpName);

		Range workPlaceRange = cells.createRange(currentRow, WP_COL, 1, 9);
		
		if (currentRow != DATA_ROW_START) {
			Range workPlaceRangeTemp = cells.createRange(DATA_ROW_START, WP_COL, 1, 9);
			workPlaceRange.copyStyle(workPlaceRangeTemp);
			workPlaceRange.setRowHeight(workPlaceRangeTemp.getRowHeight());
		}
		workPlaceRange.merge();

		this.setWPStyle(currentRow, cells);

		currentRow++;

		return currentRow;
	}

	/**
	 * set style cho WorkPlace
	 * 
	 * @param currentRow
	 * @param cells
	 */
	private void setWPStyle(int currentRow, Cells cells) {
		for (int i = 0; i < MAX_COL; i++) {
			Style style = new Style();
			style.copy(cells.get(currentRow, i).getStyle());
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			style.setForegroundColor(Color.fromArgb(155,194,230));
			style.setPattern(BackgroundType.SOLID);
			Font font = style.getFont();
			font.setDoubleSize(NORMAL_FONT_SIZE);
			font.setName(FONT_NAME );
			cells.get(currentRow, i).setStyle(style);
		}
	}

	/**
	 * set Header cho tài liệu
	 * 
	 * @param cells
	 * @param query
	 */
	private void setHeader(Cells cells, OutputYearHolidayManagementQuery query) {
		// Header Data
		cells.get(DES_ROW, 0).setValue(TextResource.localize("KDR002_11"));
		cells.get(DES_ROW, PRINT_DATE_COL).setValue(this.genDateText(query));
		cells.get(DES_ROW, PRINT_EXT_CONDITION_COL).setValue(this.generateExtractionCondition(query));
		cells.get(DES_ROW, PRINT_EXT_TEXT).setValue(query.isDoubleTrack() ? TextResource.localize("KDR002_68") : "");

		cells.get(HEADER_ROW, 0).setValue(TextResource.localize("KDR002_12"));
		cells.get(HEADER_ROW, 2).setValue(TextResource.localize("KDR002_13"));
		cells.get(HEADER_ROW, 3).setValue(TextResource.localize("KDR002_56"));
		cells.get(HEADER_ROW, 4).setValue(TextResource.localize("KDR002_14"));
		cells.get(HEADER_ROW, 5).setValue(TextResource.localize("KDR002_15"));
		cells.get(HEADER_ROW, 6).setValue(TextResource.localize("KDR002_16"));
		cells.get(HEADER_ROW, 7).setValue(TextResource.localize("KDR002_17"));
		cells.get(HEADER_ROW, 8).setValue(TextResource.localize("KDR002_18"));
		cells.get(HEADER_ROW, 9).setValue(TextResource.localize("KDR002_19"));
		cells.get(HEADER_ROW, 10).setValue(TextResource.localize("KDR002_20"));
		cells.get(HEADER_ROW, 11).setValue(TextResource.localize("KDR002_21"));
		cells.get(HEADER_ROW, 12).setValue(TextResource.localize("KDR002_22"));
		cells.get(HEADER_ROW, 13).setValue(TextResource.localize("KDR002_24"));
		cells.get(HEADER_ROW, 14).setValue(TextResource.localize("KDR002_25"));
		cells.get(HEADER_ROW, 15).setValue(TextResource.localize("KDR002_26"));
		cells.get(HEADER_ROW, 16).setValue(TextResource.localize("KDR002_27"));
		cells.get(HEADER_ROW, 17).setValue(TextResource.localize("KDR002_28"));
		cells.get(HEADER_ROW, 18).setValue(TextResource.localize("KDR002_29"));
		cells.get(HEADER_ROW, 19).setValue(TextResource.localize("KDR002_30"));
		cells.get(HEADER_ROW, 20).setValue(TextResource.localize("KDR002_31"));
		cells.get(HEADER_ROW, 21).setValue(TextResource.localize("KDR002_32"));
		cells.get(HEADER_ROW, 22).setValue(TextResource.localize("KDR002_38"));
	}
	
	private String genDateText(OutputYearHolidayManagementQuery query) {
		String result = "";
		if (EnumAdaptor.valueOf(query.getSelectedDateType().value, PeriodToOutput.class).equals(PeriodToOutput.PAST)) {
			String dateString = query.getPrintDate().toString();
			result = TextResource.localize("KDR002_64") + dateString.substring(0, 4) + '/'
					+ dateString.substring(4, 6);
		}
		if (EnumAdaptor.valueOf(query.getSelectedDateType().value, PeriodToOutput.class).equals(PeriodToOutput.AFTER_1_YEAR)) {
			String datePrint = query.getPeriod().start().toString() + "　" + TextResource.localize("KDR002_65") + "　" + query.getPeriod().end().toString();
			result = TextResource.localize("KDR002_63") + datePrint;
		}
		return result;
	}
	
	private List<WorkplaceConfigInfo> convertData(List<WorkplaceInformation> wp) {
		Map<Pair<String, String>, List<WorkplaceInformation>> map =
				wp.stream().collect(Collectors.groupingBy(p -> Pair.of(p.getCompanyId(), p.getWorkplaceHistoryId())));
		List<WorkplaceConfigInfo> returnList = new ArrayList<WorkplaceConfigInfo>();
		for (Pair<String, String> key : map.keySet()) {
			returnList.add(new WorkplaceConfigInfo(key.getLeft(), key.getRight(), 
					map.get(key).stream().map(x -> WorkplaceHierarchy.newInstance(x.getWorkplaceId(), x.getHierarchyCode().v())).collect(Collectors.toList())));
		}
		return returnList;
	}

	/**
	 * 年休付与情報、年休使用詳細について、入社・退職の考慮を行う
	 * 
	 * @param employeeId
	 * @param annualHolidayGrantInfor - 年休付与情報
	 * @param holidayDetailList - 年休使用詳細
	 * 
	 * 
	 * @return AnnualHolidayGrantData
	 */
	private AnnualHolidayGrantData getJoinLeavingForAnnualLeaveGrantInfo(String employeeId,
			AnnualHolidayGrantInfor annualHolidayGrantInfor, List<AnnualHolidayGrantDetail> holidayDetailList) {
		AnnualHolidayGrantData annualHolidayGrantData = new AnnualHolidayGrantData();
		// 指定された社員の全ての所属会社履歴(YMD)を取得する
		// 社員の指定期間中の所属期間を取得する RQ588
		DatePeriod workPeriod = new DatePeriod(GeneralDate.ymd(1900, 01, 01), GeneralDate.ymd(9999, 12, 31));
		List<StatusOfEmployeeExport> listStatusOfEmployeeExport = this.syCompanyRecordAdapter
				.getListAffComHistByListSidAndPeriod(Arrays.asList(employeeId), workPeriod);
		// 年休付与情報．年休付与(List)分ループ
		if(annualHolidayGrantInfor != null ) {
			// Fix bug ConcurrentModificationException
			annualHolidayGrantInfor.getLstGrantInfor().removeIf(holidayGrant -> {
				// 年休付与(i)．付与日が取得した所属期間(List)のどれかに入っているか？
//				// 年休付与(i)．付与日 đang ở trong phần nào của 所属期間(List) đã cấp
				for (StatusOfEmployeeExport sttEmployee : listStatusOfEmployeeExport) { 
					for (DatePeriod period : sttEmployee.getListPeriod()) {
						if (holidayGrant.getYmd().before(period.start()) || holidayGrant.getYmd().after(period.end())) {
							return true;
						}
					}
				}
				return false;
			});
			
			// 指定する期間(YMD)と所属会社履歴の重複期間(YMD)を取得する
			// 社員の指定期間中の所属期間を取得する RQ588
			GeneralDate overlapStartDate = annualHolidayGrantInfor.getDoubleTrackStartDate().isPresent()
					? annualHolidayGrantInfor.getDoubleTrackStartDate().get() : annualHolidayGrantInfor.getPeriod().start();
			
			DatePeriod overlapPeriod = new DatePeriod(overlapStartDate, annualHolidayGrantInfor.getPeriod().end());
			List<StatusOfEmployeeExport> overlapSttEmp = this.syCompanyRecordAdapter
					.getListAffComHistByListSidAndPeriod(Arrays.asList(employeeId), overlapPeriod);
			// 重複期間が０件
			if(overlapSttEmp.isEmpty()) {
				// 　<OUTPUT>年休付与情報．年休付与  <OUTPUT>年休使用詳細  - を空にする。
				annualHolidayGrantData.setHolidayDetails(new ArrayList<>());
				annualHolidayGrantData.setAnnualHolidayGrantInfor(Optional.empty());
				return annualHolidayGrantData;
			}
			// Fix bug ConcurrentModificationException
			holidayDetailList.removeIf(detail ->  {
				// 年休使用詳細(i)．年月日と取得した重複期間(List)の重なりをチェックする
				for(StatusOfEmployeeExport employeeExport: overlapSttEmp) {
					// どの重複期間とも重なりが無かった
					for(DatePeriod period: employeeExport.getListPeriod()) {
						if(detail.getYmd().before(period.start()) || detail.getYmd().after(period.end())) {
							// <OUTPUT>年休使用詳細(i) を削除(空に)する。
							return true;
						}
					}
				}
				return false;
			});
		
			// 指定する期間(YMD)と所属会社履歴の期間(YMD)が重複する場合、そのオリジナルの所属会社履歴を取得する
			// RequestList211
			List<AffCompanyHistImport> listAffCompanyHistImport = this.syCompanyRecordAdapter
					.getAffCompanyHistByEmployee(Arrays.asList(employeeId), new DatePeriod(overlapStartDate, GeneralDate.today()));
			for (AffCompanyHistImport affCompany : listAffCompanyHistImport) {
				// 年休付与(i)．付与日～期限日と取得した所属期間(List)の重なりをチェックする
				// Fix bug ConcurrentModificationException
				annualHolidayGrantInfor.getLstGrantInfor().removeIf(holidayGrant -> {
					for(AffComHistItemImport affCom : affCompany.getLstAffComHistItem()) {
						// どの重複期間とも重なりが無かった
						if(holidayGrant.getYmd().before(affCom.getDatePeriod().start()) || holidayGrant.getYmd().after(affCom.getDatePeriod().end())) {
							return true;
						}
					}
					return false;
				});
			}	
		}
		annualHolidayGrantData.setHolidayDetails(holidayDetailList);
		annualHolidayGrantData.setAnnualHolidayGrantInfor(Optional.ofNullable(annualHolidayGrantInfor));
		
		return annualHolidayGrantData;
	}
	
	private String generateExtractionCondition(OutputYearHolidayManagementQuery query) {
		String result = "";
		if (query.isExtCondition()) {
			ExtractionConditionSetting extCondition = query.getExtractionCondtionSetting().get();
			result = TextResource.localize("KDR002_66") + extCondition.getDays()
					+ TextResource.localize("KDR002_67") + (EnumAdaptor.valueOf(extCondition.getComparisonConditions().value, ComparisonConditions.class)
							.equals(ComparisonConditions.UNDER) ? TextResource.localize("KDR002_47")
									: TextResource.localize("KDR002_48"));
		}
		return result;
	}
	
	
	private String generateTimeText(TimeDurationPrimitiveValue<? extends TimeDurationPrimitiveValue<?>> time) {
		String hour = String.valueOf(time.hour());
		String min = StringUtil.padLeft(String.valueOf(time.minute()), 2, '0');
		return hour + ":" + min;
	}
}
