package nts.uk.file.at.infra.yearholidaymanagement;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.AnnualHolidayGrantDetailInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualHolidayGrantInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrant;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.RqClosureAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.assist.dom.mastercopy.SystemType;
import nts.uk.file.at.app.export.yearholidaymanagement.BreakPageType;
import nts.uk.file.at.app.export.yearholidaymanagement.ClosurePrintDto;
import nts.uk.file.at.app.export.yearholidaymanagement.EmployeeHolidayInformationExport;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementGenerator;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementQuery;
import nts.uk.file.at.app.export.yearholidaymanagement.PeriodToOutput;
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

	private static final String COMPANY_ERROR = "Company is not found!!!!";

	private static final String TEMPLATE_FILE = "report/KDR002.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	private static final int HEADER_ROW = 1;
	private static final int DES_ROW = 0;
	private static final int EMP_CODE_COL = 0;
	private static final int EMP_NAME_COL = 1;
	private static final int EMP_POS_COL = 0;
	private static final int GRANT_DATE_COL = 2;
	private static final int GRANT_DAYS_COL = 3;
	private static final int GRANT_USEDAY_COL = 4;
	private static final int GRANT_REMAINDAY_COL = 5;
	private static final int NEXT_GRANTDATE_COL = 21;
	private static final int MIN_GRANT_DETAIL_COL = 6;
	private static final int MAX_GRANT_DETAIL_COL = 20;
	private static final int MAX_COL = 22;
	private static final int MAX_ROW = 38;
	private static final int NORMAL_FONT_SIZE = 9;
	@Inject
	private CompanyAdapter company;
	@Inject
	private RqClosureAdapter closureAdapter;
	@Inject
	private ClosureService closureService;
	@Inject
	private GetAnnualHolidayGrantInfor getGrantInfo;
	@Inject
	private AnnualHolidayGrantDetailInfor getGrantDetailInfo;
	@Inject
	private RegulationInfoEmployeeAdapter empAdaptor;
	@Inject
	private EmployeeInformationPub empInfo;

	@Override
	public void generate(FileGeneratorContext generatorContext, OutputYearHolidayManagementQuery query) {
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
			reportContext.setHeader(1, "&16&\"MS ゴシック\"" + TextResource.localize("KDR002_10"));
			DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
			reportContext.setHeader(2,
					"&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
			String exportTime = query.getExportTime().toString();
			Worksheet normalSheet = worksheets.get(0);
			String normalSheetName = TextResource.localize("KDR002_10");

			printData(normalSheet, programName, companyName, exportTime, data, normalSheetName, query);
			worksheets.setActiveSheetIndex(0);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					programName + "_" + query.getExportTime().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * xác định base date
	 * 
	 * @param closureData
	 * @param selectedDateType
	 * @param printDate
	 * @return baseDate được xác định
	 */
	private GeneralDate dateDetermination(List<ClosurePrintDto> closureData, Integer selectedDateType,
			Integer printDate) {

		GeneralDate returnDate = null;
		PresentClosingPeriodImport closure;
		String companyId = AppContexts.user().companyId();
		if (closureData.size() == 1) {
			// 全締め以外 closure ID = 1
			// 処理年月と締め期間を取得する
			closure = closureAdapter.getClosureById(companyId, 1).get();
		} else {
			// 全締めの場合
			// 処理年月と締め期間を取得する
			closure = closureAdapter.getClosureById(companyId, closureData.get(0).getClosureId()).get();
		}

		// 参照先区分をチェックする
		if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.PAST)) {
			// 過去
			// 所属情報取得用の基準日 ← INPUT.指定月 +取得した 「現在締め期間.終了年月日」の日

			returnDate = GeneralDate.fromString(
					printDate.toString()
							+ StringUtils.leftPad(String.valueOf(closure.getClosureEndDate().day()), 2, '0'),
					"yyyyMMdd");
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

		// từ xử lý ユーザ固有情報「年休管理表の出力条件」を更新する trở về trước được thực hiện dưới
		// client
		Integer selectedDateType = query.getSelectedDateType();
		String companyId = AppContexts.user().companyId();
		// アルゴリズム「使用基準日判定処理」を実行する
		GeneralDate baseDate = dateDetermination(query.getClosureData(), selectedDateType, query.getPrintDate());
		List<String> empIds = query.getSelectedEmployees().stream().map(x -> {
			return x.getEmployeeId();
		}).collect(Collectors.toList());
		// <<Public>> 社員を並べ替える
		empIds = empAdaptor.sortEmployee(companyId, empIds, SystemType.EMPLOYMENT.value, 1, 1, GeneralDateTime.now());

		// <<Public>> 社員の情報を取得する
		EmployeeInformationQueryDto param = EmployeeInformationQueryDto.builder().toGetWorkplace(true)
				.toGetPosition(true).toGetEmployment(false).employeeIds(empIds).referenceDate(baseDate).build();

		List<EmployeeHolidayInformationExport> employeeExports = empInfo.find(param).stream()
				.map(x -> EmployeeHolidayInformationExport.fromEmpInfo(x)).collect(Collectors.toList());

		// đảo xuống dưới để tiện việc map data
		// 社員分ループ

		for (int i = 0; i < employeeExports.size(); i++) {
			EmployeeHolidayInformationExport emp = employeeExports.get(i);
			String empId = emp.getEmployeeId();
			ReferenceAtr refType = EnumAdaptor.valueOf(query.getSelectedReferenceType(), ReferenceAtr.class);
			Optional<AnnualHolidayGrantInfor> holidayInfo = Optional.empty();
			List<AnnualHolidayGrantDetail> HolidayDetails = Collections.emptyList();
			if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.CURRENT)) {
				// 社員に対応する処理締めを取得する
				Closure closure = closureService.getClosureDataByEmployee(empId, baseDate);
				// アルゴリズム「年休付与情報を取得」を実行する
				// RQ550
				holidayInfo = this.getGrantInfo.getAnnGrantInfor(companyId, empId, refType,
						closure.getClosureMonth().getProcessingYm(), baseDate);
				// アルゴリズム「年休明細情報を取得」を実行する
				HolidayDetails = getGrantDetailInfo.getAnnHolidayDetail(companyId, empId, refType,
						closure.getClosureMonth().getProcessingYm(), baseDate);
			}
			if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.PAST)) {
				YearMonth printDate = YearMonth.of(query.getPrintDate());
				// アルゴリズム「年休付与情報を取得」を実行する
				holidayInfo = this.getGrantInfo.getAnnGrantInfor(companyId, empId, refType, printDate, baseDate);
				// アルゴリズム「年休明細情報を取得」を実行する
				HolidayDetails = getGrantDetailInfo.getAnnHolidayDetail(companyId, empId, refType, printDate, baseDate);

			}
			emp.setHolidayInfo(holidayInfo);
			emp.setHolidayDetails(HolidayDetails);
		}

		employeeExports = employeeExports.stream().filter(x -> x.getWorkplace() != null).collect(Collectors.toList());
		Map<String, List<EmployeeHolidayInformationExport>> resultmap = employeeExports.stream()
				.collect(Collectors.groupingBy(o -> o.getWorkplace().getWorkplaceCode()));

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
			worksheet.setName(sheetName);
			Cells cells = worksheet.getCells();
			setHeader(cells);
			int lastWPRow = 2;
			int currentRow = 2;
			int beginRow = 0;
			boolean isBlueBackground = false;
			DecimalFormat formatter = new DecimalFormat("0.##");
			for (int i = 0; i < data.size(); i++) {
				if ((currentRow - beginRow) > MAX_ROW + 1) {
					beginRow = currentRow;
				}
				EmployeeHolidayInformationExport emp = data.get(i);
				AnnualHolidayGrantInfor holidayInfo = emp.getHolidayInfo().isPresent() ? emp.getHolidayInfo().get()
						: null;
				List<AnnualHolidayGrantDetail> holidayDetails = emp.getHolidayDetails();
				// fix code
				if (holidayInfo == null) {
					holidayInfo = createSampleHInfo(query.getPrintDate().toString());
				}

				// fix code
				if (CollectionUtil.isEmpty(holidayDetails)) {
					holidayDetails = createHSampleDetails(query.getPrintDate().toString());
				}
				// tính tổng số dòng để xác định phân trang nếu data quá quy
				// định
				int dataLine = getTotalLineOfEmp(holidayInfo, holidayDetails);
				String wpName = "●職場：" + emp.getWorkplace().getWorkplaceCode() + " "
						+ emp.getWorkplace().getWorkplaceName();
				String lastWpName = cells.get(lastWPRow, 0).getStringValue();
				// tổng số dòng = số dòng data + 1 dòng in WorkPlace (nếu có);
				int totalLine = dataLine + (!lastWpName.equals(wpName) ? 1 : 0);
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
					currentRow = printWP(cells, currentRow, wpName);
					lastWPRow = currentRow - 1;
				} else {
					if ((currentRow - beginRow) > MAX_ROW + 1) {
						currentRow = printWP(cells, currentRow, wpName);
						lastWPRow = currentRow - 1;
					}
				}

				// Print EmployeeInfo Region
				// print emp Code
				String empCode = emp.getEmployeeCode();
				cells.get(currentRow, EMP_CODE_COL).setValue(empCode);
				// print emp Name
				String empName = emp.getBusinessName();
				cells.get(currentRow, EMP_NAME_COL).setValue(empName);
				// print emp pos
				String empPos = emp.getPosition().getPositionName();
				cells.get(currentRow + 1, EMP_POS_COL).setValue(empPos);
				// Print AnnualHolidayGrantInfor
				if (holidayInfo != null) {
					// print AnnualHolidayGrant
					cells.get(currentRow, NEXT_GRANTDATE_COL).setValue(String.valueOf(holidayInfo.getYmd()));
					int holidayInfoRow = currentRow;
					for (int j = 0; j < holidayInfo.getLstGrantInfor().size(); j++) {
						AnnualHolidayGrant grantInfo = holidayInfo.getLstGrantInfor().get(j);
						cells.get(holidayInfoRow, GRANT_DATE_COL).setValue(String.valueOf(grantInfo.getYmd()));

						cells.get(holidayInfoRow, GRANT_DAYS_COL)
								.setValue(formatter.format(grantInfo.getGrantDays()).toString());

						cells.get(holidayInfoRow, GRANT_USEDAY_COL)
								.setValue(formatter.format(grantInfo.getUseDays()).toString());

						cells.get(holidayInfoRow, GRANT_REMAINDAY_COL)
								.setValue(formatter.format(grantInfo.getRemainDays()).toString());

						holidayInfoRow++;
					}
				}
				// Print HolidayDetails

				int holidayDetailRow = currentRow;
				int holidayDetailCol = MIN_GRANT_DETAIL_COL;
				for (int j = 0; j < holidayDetails.size(); j++) {
					AnnualHolidayGrantDetail detail = holidayDetails.get(j);

					cells.get(holidayDetailRow, holidayDetailCol).setValue(genHolidayText(detail));
					if (holidayDetailCol == MAX_GRANT_DETAIL_COL) {
						holidayDetailRow++;
						holidayDetailCol = 6;
					} else {
						holidayDetailCol++;
					}
				}

				currentRow = currentRow + dataLine;
				isBlueBackground = setRowStyle(cells, currentRow, dataLine, isBlueBackground);
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
	 * @return số dòng cần để in
	 */
	private int getTotalLineOfEmp(AnnualHolidayGrantInfor holidayInfo, List<AnnualHolidayGrantDetail> holidayDetails) {
		// mặc định là 2 dòng
		int result = 2;
		// dòng của lineOfDetails = tổng data /15 + 1 dòng nếu /15 có dư ra
		int lineOfDetails = holidayDetails.size() / 15 + (holidayDetails.size() % 15 > 0 ? 1 : 0);

		int LineOfholidayInfo = holidayInfo != null ? holidayInfo.getLstGrantInfor().size() : 0;
		// số dòng của data sẽ là của getLstGrantInfor hoặc holidayDetails nếu 1
		// trong 2 > 2
		if (LineOfholidayInfo > 2 || lineOfDetails > 2) {
			// số dòng của data tùy thuộc getLstGrantInfor hoặc holidayDetails
			// bên nào lớn hơn
			if (LineOfholidayInfo > lineOfDetails) {
				result = LineOfholidayInfo;
			} else {
				result = lineOfDetails;
			}
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
	 * @return trạng thái màu của dòng kế được được in ra (xanh hay trắng)
	 */
	private boolean setRowStyle(Cells cells, int newRow, int totalLine, boolean isBlueBackground) {
		Style style = new Style();

		for (int i = totalLine; i > 0; i--) {
			for (int j = 0; j < MAX_COL; j++) {
				Cell cell = cells.get(newRow - i, j);
				if (j != NEXT_GRANTDATE_COL && j != EMP_CODE_COL) {
					if (j == EMP_NAME_COL || j == GRANT_REMAINDAY_COL) {
						setBorder(cell, BorderType.RIGHT_BORDER, CellBorderType.THIN);
					} else {
						setBorder(cell, BorderType.RIGHT_BORDER, CellBorderType.DOTTED);
					}
					if (j > GRANT_REMAINDAY_COL) {
						setBorder(cell, BorderType.BOTTOM_BORDER, CellBorderType.DOTTED);
						if (isBlueBackground) {
							setBackGround(cell);
						}
					}
				}
				setTextStyle(cell);
			}
			isBlueBackground = !isBlueBackground;
		}
		// set border when end employee
		for (int i = 0; i < MAX_COL; i++) {
			style.copy(cells.get(newRow - 1, i).getStyle());
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			cells.get(newRow - 1, i).setStyle(style);
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
			style.setVerticalAlignment(TextAlignmentType.CENTER);
			style.setHorizontalAlignment(TextAlignmentType.RIGHT);
		}
		Font font = style.getFont();
		font.setDoubleSize(NORMAL_FONT_SIZE);
		font.setName("ＭＳ ゴシック");
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
	private String genHolidayText(AnnualHolidayGrantDetail detail) {
		String result = detail.getYmd().toString("MM/dd");

		if (!(detail.getUseDays() % 1 == 0)) {
			result = "▲" + result;
		}
		if (detail.getReferenceAtr().equals(ReferenceAtr.APP_AND_SCHE)) {
			result = "(" + result + ")";
		}

		return result;
	}

	private List<AnnualHolidayGrantDetail> createHSampleDetails(String printDate) {

		Random rand = new Random();

		List<AnnualHolidayGrantDetail> result = new ArrayList<AnnualHolidayGrantDetail>();
		Integer row = rand.nextInt(7);

		Integer yearNumber = Integer.valueOf(printDate.substring(0, 4));
		List<Integer> years = new ArrayList<Integer>();
		for (int i = 0; i < row; i++) {
			years.add(yearNumber - i);
		}

		years.forEach(x -> {
			Integer record = rand.nextInt(20);

			for (int i = 0; i < record; i++) {
				Integer month = rand.nextInt((12 - 1) + 1) + 1;
				GeneralDate ymd = GeneralDate.ymd(x, month, rand.nextInt((28 - 1) + 1) + 1);

				double useDays = Double.valueOf(rand.nextInt(2) / 2.0);
				result.add(new AnnualHolidayGrantDetail("", ymd, useDays,
						EnumAdaptor.valueOf(rand.nextInt(2), ReferenceAtr.class)));
			}

		});
		return result;
	}

	private AnnualHolidayGrantInfor createSampleHInfo(String printDate) {
		AnnualHolidayGrantInfor result = new AnnualHolidayGrantInfor();

		List<AnnualHolidayGrant> lstGrantInfor = new ArrayList<AnnualHolidayGrant>();
		Random rand = new Random();

		Integer record = rand.nextInt(6);
		GeneralDate year = GeneralDate.ymd(Integer.valueOf(printDate.substring(0, 4)),
				Integer.valueOf(printDate.substring(4, 6)), rand.nextInt((28 - 1) + 1) + 1).addYears(0 - record);

		Integer month = rand.nextInt((12 - 1) + 1) + 1;
		Integer day = rand.nextInt((28 - 1) + 1) + 1;
		Integer grantDays = rand.nextInt(20);
		if (month > Integer.valueOf(printDate.substring(4, 6))) {
			year.addYears(-1);
		}

		for (int i = 0; i < record; i++) {
			double useDays = grantDays != 0 ? rand.nextInt(grantDays * 2) / 2.0 : 0;
			lstGrantInfor.add(new AnnualHolidayGrant(
					GeneralDate.fromString(String.valueOf(year.year()) + StringUtils.leftPad(month.toString(), 2, '0')
							+ StringUtils.leftPad(day.toString(), 2, '0'), "yyyyMMdd"),
					grantDays, useDays, grantDays - useDays));

			year = year.addYears(1);
		}
		result.setYmd(year.addYears(1));
		result.setLstGrantInfor(lstGrantInfor);
		return result;
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

		setWPStyle(currentRow, cells);

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
			Font font = style.getFont();
			font.setDoubleSize(NORMAL_FONT_SIZE);
			font.setName("ＭＳ ゴシック");
			cells.get(currentRow, i).setStyle(style);
		}
	}

	/**
	 * set Header cho tài liệu
	 * 
	 * @param cells
	 */
	private void setHeader(Cells cells) {
		// Header Data
		cells.get(DES_ROW, 0).setValue(TextResource.localize("KDR002_11"));
		cells.get(HEADER_ROW, 0).setValue(TextResource.localize("KDR002_12"));
		cells.get(HEADER_ROW, 2).setValue(TextResource.localize("KDR002_13"));
		cells.get(HEADER_ROW, 3).setValue(TextResource.localize("KDR002_14"));
		cells.get(HEADER_ROW, 4).setValue(TextResource.localize("KDR002_15"));
		cells.get(HEADER_ROW, 5).setValue(TextResource.localize("KDR002_16"));
		cells.get(HEADER_ROW, 6).setValue(TextResource.localize("KDR002_17"));
		cells.get(HEADER_ROW, 7).setValue(TextResource.localize("KDR002_18"));
		cells.get(HEADER_ROW, 8).setValue(TextResource.localize("KDR002_19"));
		cells.get(HEADER_ROW, 9).setValue(TextResource.localize("KDR002_20"));
		cells.get(HEADER_ROW, 10).setValue(TextResource.localize("KDR002_21"));
		cells.get(HEADER_ROW, 11).setValue(TextResource.localize("KDR002_22"));
		cells.get(HEADER_ROW, 12).setValue(TextResource.localize("KDR002_24"));
		cells.get(HEADER_ROW, 13).setValue(TextResource.localize("KDR002_25"));
		cells.get(HEADER_ROW, 14).setValue(TextResource.localize("KDR002_26"));
		cells.get(HEADER_ROW, 15).setValue(TextResource.localize("KDR002_27"));
		cells.get(HEADER_ROW, 16).setValue(TextResource.localize("KDR002_28"));
		cells.get(HEADER_ROW, 17).setValue(TextResource.localize("KDR002_29"));
		cells.get(HEADER_ROW, 18).setValue(TextResource.localize("KDR002_30"));
		cells.get(HEADER_ROW, 19).setValue(TextResource.localize("KDR002_31"));
		cells.get(HEADER_ROW, 20).setValue(TextResource.localize("KDR002_32"));

		cells.get(HEADER_ROW, 21).setValue(TextResource.localize("KDR002_38"));
	}

}
