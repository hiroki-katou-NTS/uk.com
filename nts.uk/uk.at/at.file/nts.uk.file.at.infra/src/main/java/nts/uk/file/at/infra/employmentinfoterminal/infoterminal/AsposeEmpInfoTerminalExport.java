package nts.uk.file.at.infra.employmentinfoterminal.infoterminal;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExport;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportDataSource;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * 
 * @author huylq
 *
 */
@Stateless
public class AsposeEmpInfoTerminalExport extends AsposeCellsReportGenerator implements EmpInfoTerminalExport {

	private static final String TEMPLATE_FILE = "report/KNR001.xlsx";
	private static final String PGID = "KNR001";
	private static final String PG = "就業情報端末の登録";
	private static final String SHEET_NAME = "マスタリスト";
	private static final String SHEET_NAME_TWO = "マスタリスト2";
	private static final String COMPANY_ERROR = "Company is not found!!!!";

	private final int ROW_COMPANY = 0;
	private final int ROW_TYPE = 1;
	private final int ROW_DATE_TIME = 2;
	private final int ROW_SHEET_NAME = 3;
	private final int COLUMN_DATA = 1;
	private final int COLUMN_MAC_ADDRESS = 3;
	private final int PADDING_ROWS = 10;

	@Inject
	private CompanyAdapter companyAdapter;

	@Override
	public void export(FileGeneratorContext generatorContext, List<EmpInfoTerminalExportDataSource> dataSource,
			DeclareSet declareSet, List<OvertimeWorkFrame> overtimeWorkFrames, List<WorkdayoffFrame> workdayoffFrames) {

		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			// Sheet 1
			Worksheet worksheet = reportContext.getWorkbook().getWorksheets().get(0);
			this.setHeaderAndHeaderColumn(worksheet, reportContext);

			// Sheet 2
			Worksheet worksheetTwo = reportContext.getWorkbook().getWorksheets().get(1);
			// Set header column
			this.setHeaderAndHeaderColumnSheetTwo(worksheetTwo, reportContext);

			// set data source named "item"
			reportContext.setDataSource("item", dataSource);
			DeclareSetExportDto exportDto = this.convertDtoToExportExcel(declareSet, overtimeWorkFrames,
					workdayoffFrames);
			reportContext.setDataSource("declareSet", Arrays.asList(exportDto));
			// process data binginds in template
			reportContext.processDesigner();

			// merge if isEmpty(ipAddress) == true
			mergeMacAndIp(worksheet, dataSource);
			// delete empty row if no data
			deleteTemplateRow(worksheet, dataSource);

			// save as Excel file
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime = dateNow.toString("yyyyMMddHHmmss");
			String fileName = PGID + PG + "_" + dateTime + ".xlsx";
			OutputStream outputStream = this.createNewFile(generatorContext, fileName);
			reportContext.saveAsExcel(outputStream);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setHeaderAndHeaderColumn(Worksheet worksheet, AsposeCellsReportContext reportContext) {
		String companyCode = companyAdapter.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
				.getCompanyCode();
		String companyName = companyAdapter.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
				.getCompanyName();

		worksheet.setName(SHEET_NAME);

		Cells cells = worksheet.getCells();
		cells.get(ROW_COMPANY, COLUMN_DATA).setValue(companyCode + " " + companyName);
		cells.get(ROW_TYPE, COLUMN_DATA).setValue(PGID + PG);
		cells.get(ROW_DATE_TIME, COLUMN_DATA)
				.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		cells.get(ROW_SHEET_NAME, COLUMN_DATA).setValue(SHEET_NAME);
	}

	private void setHeaderAndHeaderColumnSheetTwo(Worksheet worksheetTwo, AsposeCellsReportContext reportContext) {
		String companyCode = companyAdapter.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
				.getCompanyCode();
		String companyName = companyAdapter.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
				.getCompanyName();
		worksheetTwo.setName(SHEET_NAME_TWO);
		Cells cells = worksheetTwo.getCells();
		cells.get(ROW_COMPANY, COLUMN_DATA).setValue(companyCode + " " + companyName);
		cells.get(ROW_TYPE, COLUMN_DATA).setValue(PGID + PG);
		cells.get(ROW_DATE_TIME, COLUMN_DATA)
				.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		cells.get(ROW_SHEET_NAME, COLUMN_DATA).setValue(SHEET_NAME_TWO);
	}

	private void mergeMacAndIp(Worksheet worksheet, List<EmpInfoTerminalExportDataSource> dataSource) {
		Cells cells = worksheet.getCells();
		for (int i = 0; i < dataSource.size(); i++) {
			EmpInfoTerminalExportDataSource data = dataSource.get(i);
			if (data.getIpAddress() == null || data.getIpAddress().isEmpty()) {
				cells.merge(i + PADDING_ROWS, COLUMN_MAC_ADDRESS, 1, 2);
			}
		}
	}

	private void deleteTemplateRow(Worksheet worksheet, List<EmpInfoTerminalExportDataSource> dataSource) {
		Cells cells = worksheet.getCells();
		if (dataSource.size() <= 0) {
			cells.deleteRow(PADDING_ROWS);
		}
	}

	/**
	 * Convert DeclareSet to DeclareSet Export
	 * 
	 * @param overtimeWorkFrame
	 * @return DeclareSetDto
	 */
	private DeclareSetExportDto convertDtoToExportExcel(DeclareSet declareSet,
			List<OvertimeWorkFrame> overtimeWorkFrames, List<WorkdayoffFrame> workdayoffFrames) {
		if (declareSet != null) {// We need this because orElse passes null
			// convert DeclareSet to dto
			DeclareSetExportDto exportDto = new DeclareSetExportDto();
			exportDto.setCompanyId(declareSet.getCompanyId());
			exportDto.setFrameSet(declareSet.getFrameSet().value == 1 ? "残業・休出枠を指定する" : "就業時間帯の枠設定に従う");
			exportDto.setMidnightAutoCalc(declareSet.getMidnightAutoCalc().value == 1 ? "する" : "しない");
			exportDto.setUsageAtr(declareSet.getUsageAtr().value == 1 ? "する" : "しない");

			// earlyOvertime
			OvertimeWorkFrame earlyOvertime = null;
			if (declareSet.getOvertimeFrame() == null
					|| !declareSet.getOvertimeFrame().getEarlyOvertime().isPresent()) {
				exportDto.setEarlyOvertime("");
			} else if (declareSet.getOvertimeFrame().getEarlyOvertime().get().v() == 0) {
				exportDto.setEarlyOvertime("なし");
			} else {
				earlyOvertime = overtimeWorkFrames.stream().filter(e -> declareSet.getOvertimeFrame().getEarlyOvertime()
						.get().v() == e.getOvertimeWorkFrNo().v().intValue()).findAny().orElse(null);
				exportDto.setEarlyOvertime(earlyOvertime == null ? "" : earlyOvertime.getOvertimeWorkFrName().v());
			}

			// earlyOvertimeMn
			OvertimeWorkFrame earlyOvertimeMn = null;
			if (declareSet.getOvertimeFrame() == null
					|| !declareSet.getOvertimeFrame().getEarlyOvertimeMn().isPresent()) {
				exportDto.setEarlyOvertimeMn("");
			} else if (declareSet.getOvertimeFrame().getEarlyOvertimeMn().get().v() == 0) {
				exportDto.setEarlyOvertimeMn("なし");
			} else {
				earlyOvertimeMn = overtimeWorkFrames.stream().filter(e -> declareSet.getOvertimeFrame()
						.getEarlyOvertimeMn().get().v() == e.getOvertimeWorkFrNo().v().intValue()).findAny()
						.orElse(null);
				exportDto
						.setEarlyOvertimeMn(earlyOvertimeMn == null ? "" : earlyOvertimeMn.getOvertimeWorkFrName().v());
			}

			// overtime
			OvertimeWorkFrame overtime = null;
			if (declareSet.getOvertimeFrame() == null || !declareSet.getOvertimeFrame().getOvertime().isPresent()) {
				exportDto.setOvertime("");
			} else if (declareSet.getOvertimeFrame().getOvertime().get().v() == 0) {
				exportDto.setOvertime("なし");
			} else {
				overtime = overtimeWorkFrames.stream().filter(e -> declareSet.getOvertimeFrame().getOvertime().get()
						.v() == e.getOvertimeWorkFrNo().v().intValue()).findAny().orElse(null);
				exportDto.setOvertime(overtime == null ? "" : overtime.getOvertimeWorkFrName().v());
			}

			// overtimeMn
			OvertimeWorkFrame overtimeMn = null;
			if (declareSet.getOvertimeFrame() == null || !declareSet.getOvertimeFrame().getOvertimeMn().isPresent()) {
				exportDto.setOvertimeMn("");
			} else if (declareSet.getOvertimeFrame().getOvertimeMn().get().v() == 0) {
				exportDto.setOvertimeMn("なし");
			} else {
				overtimeMn = overtimeWorkFrames.stream().filter(e -> declareSet.getOvertimeFrame().getOvertimeMn().get()
						.v() == e.getOvertimeWorkFrNo().v().intValue()).findAny().orElse(null);
				exportDto.setOvertimeMn(overtimeMn == null ? "" : overtimeMn.getOvertimeWorkFrName().v());
			}

			// statutory
			WorkdayoffFrame statutory = null;
			if (declareSet.getHolidayWorkFrame() == null
					|| !declareSet.getHolidayWorkFrame().getHolidayWork().isPresent()) {
				exportDto.setStatutory("");
			} else if (declareSet.getHolidayWorkFrame().getHolidayWork().get().getStatutory().v() == 0) {
				exportDto.setStatutory("なし");
			} else {
				statutory = workdayoffFrames.stream().filter(e -> declareSet.getHolidayWorkFrame().getHolidayWork()
						.get().getStatutory().v() == e.getWorkdayoffFrNo().v().intValue()).findAny().orElse(null);
				exportDto.setStatutory(statutory == null ? "" : statutory.getWorkdayoffFrName().v());
			}

			// notStatutory
			WorkdayoffFrame notStatutory = null;
			if (declareSet.getHolidayWorkFrame() == null
					|| !declareSet.getHolidayWorkFrame().getHolidayWork().isPresent()) {
				exportDto.setNotStatutory("");
			} else if (declareSet.getHolidayWorkFrame().getHolidayWork().get().getNotStatutory().v() == 0) {
				exportDto.setNotStatutory("なし");
			} else {
				notStatutory = workdayoffFrames.stream().filter(e -> declareSet.getHolidayWorkFrame().getHolidayWork()
						.get().getNotStatutory().v() == e.getWorkdayoffFrNo().v().intValue()).findAny().orElse(null);
				exportDto.setNotStatutory(notStatutory == null ? "" : notStatutory.getWorkdayoffFrName().v());
			}

			// notStatHoliday
			WorkdayoffFrame notStatHoliday = null;
			if (declareSet.getHolidayWorkFrame() == null
					|| !declareSet.getHolidayWorkFrame().getHolidayWork().isPresent()) {
				exportDto.setNotStatHoliday("");
			} else if (declareSet.getHolidayWorkFrame().getHolidayWork().get().getNotStatHoliday().v() == 0) {
				exportDto.setNotStatHoliday("なし");
			} else {
				notStatHoliday = workdayoffFrames
						.stream().filter(e -> declareSet.getHolidayWorkFrame().getHolidayWork().get()
								.getNotStatHoliday().v() == e.getWorkdayoffFrNo().v().intValue())
						.findAny().orElse(null);
				exportDto.setNotStatHoliday(notStatHoliday == null ? "" : notStatHoliday.getWorkdayoffFrName().v());
			}

			// statutoryMn
			WorkdayoffFrame statutoryMn = null;
			if (declareSet.getHolidayWorkFrame() == null
					|| !declareSet.getHolidayWorkFrame().getHolidayWorkMn().isPresent()) {
				exportDto.setStatutoryMn("");
			} else if (declareSet.getHolidayWorkFrame().getHolidayWorkMn().get().getStatutory().v() == 0) {
				exportDto.setStatutoryMn("なし");
			} else {
				statutoryMn = workdayoffFrames.stream().filter(e -> declareSet.getHolidayWorkFrame().getHolidayWorkMn()
						.get().getStatutory().v() == e.getWorkdayoffFrNo().v().intValue()).findAny().orElse(null);
				exportDto.setStatutoryMn(statutoryMn == null ? "" : statutoryMn.getWorkdayoffFrName().v());
			}

			// notStatutoryMn
			WorkdayoffFrame notStatutoryMn = null;
			if (declareSet.getHolidayWorkFrame() == null
					|| !declareSet.getHolidayWorkFrame().getHolidayWorkMn().isPresent()) {
				exportDto.setNotStatutoryMn("");
			} else if (declareSet.getHolidayWorkFrame().getHolidayWorkMn().get().getNotStatutory().v() == 0) {
				exportDto.setNotStatutoryMn("なし");
			} else {
				notStatutoryMn = workdayoffFrames.stream().filter(e -> declareSet.getHolidayWorkFrame()
						.getHolidayWorkMn().get().getNotStatutory().v() == e.getWorkdayoffFrNo().v().intValue())
						.findAny().orElse(null);
				exportDto.setNotStatutoryMn(notStatutoryMn.getWorkdayoffFrName().v());
			}

			// notStatHolidayMn
			WorkdayoffFrame notStatHolidayMn = null;
			if (declareSet.getHolidayWorkFrame() == null
					|| !declareSet.getHolidayWorkFrame().getHolidayWorkMn().isPresent()) {
				exportDto.setNotStatHolidayMn("");
			} else if (declareSet.getHolidayWorkFrame().getHolidayWorkMn().get().getNotStatHoliday().v() == 0) {
				exportDto.setNotStatHolidayMn("なし");
			} else {
				notStatHolidayMn = workdayoffFrames
						.stream().filter(e -> declareSet.getHolidayWorkFrame().getHolidayWorkMn().get()
								.getNotStatHoliday().v() == e.getWorkdayoffFrNo().v().intValue())
						.findAny().orElse(null);
				exportDto.setNotStatHolidayMn(
						notStatHolidayMn == null ? "" : notStatHolidayMn.getWorkdayoffFrName().v());
			}
			return exportDto;
		} else {
			return null;
		}
	}
}
