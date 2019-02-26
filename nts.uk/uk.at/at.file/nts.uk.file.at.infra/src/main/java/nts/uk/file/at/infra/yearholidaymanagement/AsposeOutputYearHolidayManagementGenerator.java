package nts.uk.file.at.infra.yearholidaymanagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.AnnualHolidayGrantDetailInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualHolidayGrantInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.RqClosureAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.assist.dom.mastercopy.SystemType;
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
	private static final int WORK_TIME_NORMAL_START_INDEX = 10;
	private static final int WORK_TIME_NORMAL_NUM_ROW = 10;
	private static final int HEADER_ROW = 1;
	private static final int DES_ROW = 0;

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
			//lấy dữ liệu để in
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


			printData(normalSheet, programName, companyName, exportTime, data, normalSheetName,
					WORK_TIME_NORMAL_START_INDEX, WORK_TIME_NORMAL_NUM_ROW);
			worksheets.setActiveSheetIndex(0);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					programName + "_" + query.getExportTime().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

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
			returnDate = GeneralDate
					.fromString(printDate.toString() + String.valueOf(closure.getClosureEndDate().day()), "yyyyMMdd");
		}
		if (EnumAdaptor.valueOf(selectedDateType, PeriodToOutput.class).equals(PeriodToOutput.CURRENT)) {
			// 現在
			// 所属情報取得用の基準日 ← 取得した「現在締め期間.終了年月日」
			returnDate = closure.getClosureEndDate();
		}
		return returnDate;
	}

	private List<EmployeeHolidayInformationExport> getData(OutputYearHolidayManagementQuery query) {
		
		//từ xử lý ユーザ固有情報「年休管理表の出力条件」を更新する trở về trước được thực hiện dưới client
		Integer selectedDateType = query.getSelectedDateType();
		String companyId = AppContexts.user().companyId();
		// アルゴリズム「使用基準日判定処理」を実行する
		GeneralDate baseDate = dateDetermination(query.getClosureData(), selectedDateType, query.getPrintDate());
		List<String> empIds = query.getSelectedEmployees().stream().map(x -> {
			return x.getEmployeeId();
		}).collect(Collectors.toList());
		// <<Public>> 社員を並べ替える
		//empIds = empAdaptor.sortEmployee(companyId, empIds, SystemType.EMPLOYMENT.value, null, null, null);
		
		//<<Public>> 社員の情報を取得する
		EmployeeInformationQueryDto param = EmployeeInformationQueryDto.builder()
																.toGetWorkplace(true)
																.toGetPosition(true)
																.toGetEmployment(false).employeeIds(empIds)
																.referenceDate(baseDate)
																.build();
		
		List<EmployeeHolidayInformationExport> employeeExports = empInfo.find(param).stream().map(x-> EmployeeHolidayInformationExport.fromEmpInfo(x)).collect(Collectors.toList());
		
		//đảo xuống dưới để tiện việc map data
		// 社員分ループ
		employeeExports.forEach(emp -> {
			String empId = emp.getEmployeeId();
			ReferenceAtr refType = EnumAdaptor.valueOf(query.getSelectedReferenceType(), ReferenceAtr.class);
			Optional<AnnualHolidayGrantInfor> holidayInfo = null;
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
				HolidayDetails = getGrantDetailInfo.getAnnHolidayDetail(companyId, empId, refType, printDate,
						baseDate);

			}
			emp.setHolidayInfo(holidayInfo);
			emp.setHolidayDetails(HolidayDetails);
		});
		return employeeExports;
	}

	private void printData(Worksheet worksheet, String programId, String companyName, String exportTime,
			List<EmployeeHolidayInformationExport> data, String sheetName, int startIndex, int numRow) {
		try {
			worksheet.setName(sheetName);
			Cells cells = worksheet.getCells();
			setHeader(cells);
			// Main Data
//			for (int i = 0; i < data.size(); i++) {
//				Object[] dataRow = data.get(i);
//				if (i % numRow == 0 && i + numRow < data.size()) {
//					cells.copyRows(cells, startIndex + i, startIndex + i + numRow, numRow);
//				}
//				for (int j = 0; j < dataRow.length; j++) {
//					cells.get(startIndex + i, j).setValue(Objects.toString(dataRow[j], ""));
//				}
//			}
//			cells.deleteRows(startIndex + data.size(), numRow);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

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
