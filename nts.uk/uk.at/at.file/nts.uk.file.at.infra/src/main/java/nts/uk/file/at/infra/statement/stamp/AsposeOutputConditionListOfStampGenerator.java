package nts.uk.file.at.infra.statement.stamp;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalPageBreakCollection;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.export.WorkingFile;
import nts.arc.time.GeneralDateTime;
import nts.uk.file.at.app.export.statement.stamp.EmployeeInfor;
import nts.uk.file.at.app.export.statement.stamp.OutputConditionListOfStampGenerator;
import nts.uk.file.at.app.export.statement.stamp.OutputConditionListOfStampQuery;
import nts.uk.file.at.app.export.statement.stamp.StampGeneratorExportDto;
import nts.uk.file.at.app.export.statement.stamp.StampList;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class AsposeOutputConditionListOfStampGenerator extends AsposeCellsReportGenerator
		implements OutputConditionListOfStampGenerator {

	private static final String TEMPLATE_FILE = "report/KDP011-打刻一覧表_帳票レイアウト（社員別）.xlsx";

	private static final int START_ROW = 4;

	@Override
	public StampGeneratorExportDto generate(FileGeneratorContext fileGeneratorContext,
			OutputConditionListOfStampQuery query, boolean isCardNo) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			createHeader(reportContext, query);
			val cell = reportContext.getWorkbook().getWorksheets().get(0).getCells();
			reportContext.getWorkbook().getWorksheets().get(0).setName(TextResource.localize("KDP011_1"));
			PrintStyle printStyle = new PrintStyle();
			// 年月日
			printStyle.setDateStyle(cell.get(4, 0).getStyle());
			printStyle.getDateStyle().setTextWrapped(false);
			// 時刻
			printStyle.setStampStyle(cell.get(4, 1).getStyle());
			printStyle.getStampStyle().setTextWrapped(false);
			// 出退勤区分
			printStyle.setClaStyle(cell.get(4, 2).getStyle());
			printStyle.getClaStyle().setTextWrapped(false);
			// 打刻手段
			printStyle.setMeanStyle(cell.get(4, 3).getStyle());
			printStyle.getMeanStyle().setTextWrapped(false);
			// 認証方法
			printStyle.setMethodStyle(cell.get(4, 4).getStyle());
			printStyle.getMethodStyle().setTextWrapped(false);
			// 設置場所
			printStyle.setInsStyle(cell.get(4, 5).getStyle());
			printStyle.getInsStyle().setTextWrapped(false);
			// 位置情報
			printStyle.setLocStyle(cell.get(4, 6).getStyle());
			printStyle.getLocStyle().setTextWrapped(false);
			// 応援カード
			printStyle.setCardStyle(cell.get(4, 7).getStyle());
			printStyle.getCardStyle().setTextWrapped(false);
			// 就業時間帯
			printStyle.setWHourStyle(cell.get(4, 8).getStyle());
			printStyle.getWHourStyle().setTextWrapped(false);
			// 残業時間
			printStyle.setOverStyle(cell.get(4, 9).getStyle());
			printStyle.getOverStyle().setTextWrapped(false);
			// 深夜時間
			printStyle.setNightStyle(cell.get(4, 10).getStyle());
			printStyle.getNightStyle().setTextWrapped(false);
			int row = START_ROW;
			for (int i = 0; i < query.getEmployeeList().size(); i++) {
				if (isCardNo) {
					row = generateDataCard(row, reportContext, query.getEmployeeList().get(i), printStyle);
				} else {
					row = generateDataEmp(row, reportContext, query.getEmployeeList().get(i), printStyle);
				}
			}
			reportContext.getWorkbook().getWorksheets().get(0).setName(TextResource.localize("KDP011_1"));
			reportContext.processDesigner();
			// save as Excel file
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime = dateNow.toString("yyyyMMddHHmmss");
			String fileName = TextResource.localize("KDP011_1") + "_" + dateTime + ".xlsx";
			OutputStream outputStream = this.createNewFile(fileGeneratorContext, fileName);
			reportContext.saveAsExcel(outputStream);
			WorkingFile workingFile = fileGeneratorContext.getWorkingFiles().get(0);
			StampGeneratorExportDto stampGeneratorExportDto = new StampGeneratorExportDto(
					workingFile.getTempFile().getPath(), fileName);
			return stampGeneratorExportDto;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void createHeader(AsposeCellsReportContext reportContext, OutputConditionListOfStampQuery query) {
		// A1_1-会社名
		reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(0,
				"&9&\"ＭＳ ゴシック\" " + query.getHeader().getCompanyName());
		// A1_2 タイトル
		reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(1,
				"&16&\"ＭＳ ゴシック,Bold\" " + TextResource.localize("KDP011_1"));
		val cell = reportContext.getWorkbook().getWorksheets().get(0).getCells();
		/* B1_1, B1_2 */
		cell.get(0, 0).setStyle(cell.get(0, 0).getStyle());
		cell.get(0, 0).setValue(TextResource.localize("KDP011_20") + " " + query.getHeader().getDatePeriodHead());

		cell.get(1, 0).setValue(TextResource.localize("KDP011_21"));
		cell.get(1, 1).setValue(TextResource.localize("KDP011_22"));
		cell.get(1, 2).setValue(TextResource.localize("KDP011_23"));
		cell.get(1, 3).setValue(TextResource.localize("KDP011_24"));
		cell.get(1, 4).setValue(TextResource.localize("KDP011_25"));
		cell.get(1, 5).setValue(TextResource.localize("KDP011_26"));
		cell.get(1, 6).setValue(TextResource.localize("KDP011_27"));
		cell.get(1, 7).setValue(TextResource.localize("KDP011_28"));
		cell.get(1, 8).setValue(TextResource.localize("KDP011_29"));
		cell.get(1, 9).setValue(TextResource.localize("KDP011_30"));
		cell.get(1, 10).setValue(TextResource.localize("KDP011_31"));
	}

	@SneakyThrows
	private int generateDataCard(int startRow, AsposeCellsReportContext reportContext, EmployeeInfor employeeInfor,
			PrintStyle printStyle) {
		val cell = reportContext.getWorkbook().getWorksheets().get(0).getCells();
		int totalPage = employeeInfor.getStampList().size() / 32
				+ (employeeInfor.getStampList().size() % 32 == 0 ? 0 : 1);
		int start = 0;
		List<StampList> stampList;
		int rows = startRow;
		for (int i = 1; i <= totalPage; i++) {
			if (i == totalPage) {
				stampList = employeeInfor.getStampList().subList(start, employeeInfor.getStampList().size());
			} else {
				stampList = employeeInfor.getStampList().subList(start, 32 * i);
				start = 32 * i;
			}
			if (rows > 4) {
				cell.copyRow(cell, 0, rows);
				cell.copyRow(cell, 1, rows + 1);
				cell.copyRow(cell, 2, rows + 2);
				cell.copyRow(cell, 3, rows + 3);
				// 職場名
				cell.get(rows + 2, 0).setValue(employeeInfor.getWorkplace());
				// 社員名
				cell.get(rows + 3, 0).setValue(employeeInfor.getEmployee());
				// カードNo.
				cell.get(rows + 3, 3).setValue(employeeInfor.getCardNo());
				rows += 4;
			} else {
				// 職場名
				cell.get(2, 0).setValue(employeeInfor.getWorkplace());
				// 社員名
				cell.get(3, 0).setValue(employeeInfor.getEmployee());
				// カードNo.
				cell.get(3, 3).setValue(employeeInfor.getCardNo());
			}

			rows = printData(rows, cell, printStyle, stampList);

			if (i == totalPage && stampList.size() < 32) {
				rows = rows + 32 - stampList.size();
			}
			reportContext.getWorkbook().getWorksheets().get(0).getHorizontalPageBreaks().add(rows);
		}
		return rows;
	}

	private int generateDataEmp(int startRow, AsposeCellsReportContext reportContext, EmployeeInfor employeeInfor,
			PrintStyle printStyle) {
		val cell = reportContext.getWorkbook().getWorksheets().get(0).getCells();
		Map<String, List<StampList>> cardGroup = employeeInfor.getStampList().stream()
				.collect(Collectors.groupingBy(StampList::getCardNo));
		Map<Integer, List<StampList>> pages = createPages(cardGroup);
		
		AtomicInteger rows = new AtomicInteger(startRow);
		
		pages.forEach((k, v) -> {
			try {
				if (rows.get() > 4) {
					cell.copyRow(cell, 0, rows.get());
					cell.copyRow(cell, 1, rows.get() + 1);
					cell.copyRow(cell, 2, rows.get() + 2);
					cell.copyRow(cell, 3, rows.get() + 3);
					// 職場名
					cell.get(rows.get() + 2, 0).setValue(employeeInfor.getWorkplace());
					// 社員名
					cell.get(rows.get() + 3, 0).setValue(employeeInfor.getEmployee());
					// カードNo.
					cell.get(rows.get() + 3, 3).setValue(v.get(0).getCardNo());
					rows.set(rows.get() + 4);
				} else {
					// 職場名
					cell.get(2, 0).setValue(employeeInfor.getWorkplace());
					// 社員名
					cell.get(3, 0).setValue(employeeInfor.getEmployee());
					// カードNo.
					cell.get(3, 3).setValue(v.get(0).getCardNo());
				}

				rows.set(printData(rows.get(), cell, printStyle, v));
				if (v.size() < 32) {
					rows.set(rows.get() + (32 - v.size()));
				}
				reportContext.getWorkbook().getWorksheets().get(0).getHorizontalPageBreaks().add(rows.get());
			} catch (Exception ex) {
			}
		});
		return rows.get();
	}

	// print data for each rows
	private int printData(int rows, Cells cell, PrintStyle printStyle, List<StampList> stampList) {
		for (int j = 0; j < stampList.size(); j++) {
			val dateStyle = printStyle.getDateStyle();

			val stampStyle = printStyle.getStampStyle();
			val claStyle = printStyle.getClaStyle();
			val meanStyle = printStyle.getMeanStyle();
			val methodStyle = printStyle.getMethodStyle();
			val insStyle = printStyle.getInsStyle();
			val locStyle = printStyle.getLocStyle();
			val cardStyle = printStyle.getCardStyle();
			val wHourStyle = printStyle.getWHourStyle();
			val overStyle = printStyle.getOverStyle();
			val nightStyle = printStyle.getNightStyle();
			// Tạo màu background
			Color bgColor = j % 2 != 0 ? Color.fromArgb(221, 235, 247) : Color.getWhite();
			dateStyle.setForegroundColor(bgColor);
			dateStyle.setPattern(BackgroundType.SOLID);
			stampStyle.setForegroundColor(bgColor);
			stampStyle.setPattern(BackgroundType.SOLID);
			claStyle.setForegroundColor(bgColor);
			claStyle.setPattern(BackgroundType.SOLID);
			meanStyle.setForegroundColor(bgColor);
			meanStyle.setPattern(BackgroundType.SOLID);
			methodStyle.setForegroundColor(bgColor);
			methodStyle.setPattern(BackgroundType.SOLID);
			insStyle.setForegroundColor(bgColor);
			insStyle.setPattern(BackgroundType.SOLID);
			locStyle.setForegroundColor(bgColor);
			locStyle.setPattern(BackgroundType.SOLID);
			cardStyle.setForegroundColor(bgColor);
			cardStyle.setPattern(BackgroundType.SOLID);
			wHourStyle.setForegroundColor(bgColor);
			wHourStyle.setPattern(BackgroundType.SOLID);
			overStyle.setForegroundColor(bgColor);
			overStyle.setPattern(BackgroundType.SOLID);
			nightStyle.setForegroundColor(bgColor);
			nightStyle.setPattern(BackgroundType.SOLID);

			// 年月日
			cell.get(rows, 0).setStyle(dateStyle);
			cell.get(rows, 0).setValue(stampList.get(j).getDate());
			// 時刻
			cell.get(rows, 1).setStyle(stampStyle);
			cell.get(rows, 1).setValue(stampList.get(j).getTime());
			// 出退勤区分
			cell.get(rows, 2).setStyle(claStyle);
			cell.get(rows, 2).setValue(stampList.get(j).getClassification());
			// 打刻手段
			cell.get(rows, 3).setStyle(meanStyle);
			cell.get(rows, 3).setValue(stampList.get(j).getMean());
			// 認証方法
			cell.get(rows, 4).setStyle(methodStyle);
			cell.get(rows, 4).setValue(stampList.get(j).getMethod());
			// 設置場所
			cell.get(rows, 5).setStyle(insStyle);
			cell.get(rows, 5).setValue(stampList.get(j).getInsLocation());
			// 位置情報
			cell.get(rows, 6).setStyle(locStyle);
			cell.get(rows, 6).setValue(stampList.get(j).getLocationInfor());
			// 応援カード
			cell.get(rows, 7).setStyle(cardStyle);
			cell.get(rows, 7).setValue(stampList.get(j).getSupportCard());
			// 就業時間帯
			cell.get(rows, 8).setStyle(wHourStyle);
			cell.get(rows, 8).setValue(stampList.get(j).getWorkingHour());
			// 残業時間
			cell.get(rows, 9).setStyle(overStyle);
			cell.get(rows, 9).setValue(stampList.get(j).getOvertimeHour());
			// 深夜時間
			cell.get(rows, 10).setStyle(nightStyle);
			cell.get(rows, 10).setValue(stampList.get(j).getNightTime());
			rows += 1;
		}
		return rows;
	}

	// create page to print in case: employee
	private Map<Integer, List<StampList>> createPages(Map<String, List<StampList>> cardGroup) {
		AtomicInteger pages = new AtomicInteger(1);
		Map<Integer, List<StampList>> employeeList = new HashMap<>();
		cardGroup.forEach((k, v) -> {
			if (v.size() > 32) {
				int start = 0;
				int totalPage = v.size() / 32 + (v.size() % 32 == 0 ? 0 : 1);
				List<StampList> stampList;
				for (int i = 1; i <= totalPage; i++) {
					if (i == totalPage) {
						stampList = v.subList(start, v.size());
					} else {
						stampList = v.subList(start, 32 * i);
						start = 32 * i;
					}
					employeeList.put(pages.get(), stampList);
					pages.getAndIncrement();
				}
			} else {
				employeeList.put(pages.get(), v);
				pages.getAndIncrement();
			}
		});
		return employeeList;
	}

}
