package nts.uk.file.at.infra.worktime;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.task.AsyncTask;
import nts.uk.file.at.app.export.worktime.WorkTimeReportDatasource;
import nts.uk.file.at.app.export.worktime.WorkTimeReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeWorkTimeReportGenerator extends AsposeCellsReportGenerator implements WorkTimeReportGenerator {

	private static final String TEMPLATE_FILE = "report/KMK003.xlsx";
	private static final String SHEET_WORK_TIME_NORMAL = "勤務形態_通常";
	private static final String SHEET_WORK_TIME_FLOW = "勤務形態_流動";
	private static final String SHEET_WORK_TIME_FLEX = "勤務形態_フレックス";
	private static final String REPORT_NAME = "KMK003就業時間帯の登録";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	private static final int WORK_TIME_NORMAL_START_INDEX = 10;
	private static final int WORK_TIME_FLOW_START_INDEX = 10;
	private static final int WORK_TIME_FLEX_START_INDEX = 10;
	private static final int WORK_TIME_NORMAL_NUM_ROW = 10;
	private static final int WORK_TIME_FLOW_NUM_ROW = 10;
	private static final int WORK_TIME_FLEX_NUM_ROW = 10;

	@Override
	public void generate(FileGeneratorContext generatorContext, WorkTimeReportDatasource dataSource) {

		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();

			ExecutorService executorService = Executors.newFixedThreadPool(3);
			CountDownLatch countDownLatch = new CountDownLatch(3);
			AsyncTask taskNormal = AsyncTask.builder().withContexts().keepsTrack(true)
					.threadName(this.getClass().getName()).build(() -> {
						try {
							printWorkTimeNormal(worksheets.get(SHEET_WORK_TIME_NORMAL), dataSource);
						} catch (Exception e) {
							e.printStackTrace();
						}
						countDownLatch.countDown();
					});
			executorService.submit(taskNormal);

			AsyncTask taskFlow = AsyncTask.builder().withContexts().keepsTrack(true)
					.threadName(this.getClass().getName()).build(() -> {
						try {
							printWorkTimeFlow(worksheets.get(SHEET_WORK_TIME_FLOW), dataSource);
						} catch (Exception e) {
							e.printStackTrace();
						}
						countDownLatch.countDown();
					});
			executorService.submit(taskFlow);

			AsyncTask taskFlex = AsyncTask.builder().withContexts().keepsTrack(true)
					.threadName(this.getClass().getName()).build(() -> {
						try {
							printWorkTimeFlex(worksheets.get(SHEET_WORK_TIME_FLEX), dataSource);
						} catch (Exception e) {
							e.printStackTrace();
						}
						countDownLatch.countDown();
					});
			executorService.submit(taskFlex);

			try {
				countDownLatch.await();
				reportContext.processDesigner();
				reportContext.saveAsExcel(
						this.createNewFile(generatorContext, this.getReportName(REPORT_NAME + REPORT_FILE_EXTENSION)));
			} catch (InterruptedException ie) {
				throw new RuntimeException(ie);
			} finally {
				// Force shut down executor services.
				executorService.shutdown();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void printWorkTimeNormal(Worksheet worksheet, WorkTimeReportDatasource dataSource) throws Exception {
		List<Object[]> data = dataSource.getWorkTimeNormal();
		worksheet.setName(TextResource.localize("KMK003_309"));
		Cells cells = worksheet.getCells();
		printCommonHeader(cells, dataSource.getCompanyName(), REPORT_NAME, dataSource.getExportTime(),
				TextResource.localize("KMK003_309"));
		for (int i = 0; i < data.size(); i++) {
			Object[] dataRow = data.get(i);
			if (i % WORK_TIME_NORMAL_NUM_ROW == 0 && i + WORK_TIME_NORMAL_NUM_ROW < data.size()) {
				cells.copyRows(cells, WORK_TIME_NORMAL_START_INDEX + i,
						WORK_TIME_NORMAL_START_INDEX + i + WORK_TIME_NORMAL_NUM_ROW, WORK_TIME_NORMAL_NUM_ROW);
			}
			for (int j = 0; j < dataRow.length; j++) {
				cells.get(WORK_TIME_NORMAL_START_INDEX + i, j).setValue(Objects.toString(dataRow[j], ""));
			}
		}
	}

	private void printWorkTimeFlow(Worksheet worksheet, WorkTimeReportDatasource dataSource) throws Exception {
		List<Object[]> data = dataSource.getWorkTimeFlow();
		worksheet.setName(TextResource.localize("KMK003_552"));
		Cells cells = worksheet.getCells();
		printCommonHeader(cells, dataSource.getCompanyName(), REPORT_NAME, dataSource.getExportTime(),
				TextResource.localize("KMK003_552"));
		for (int i = 0; i < data.size(); i++) {
			Object[] dataRow = data.get(i);
			if (i % WORK_TIME_FLOW_NUM_ROW == 0 && i + WORK_TIME_FLOW_NUM_ROW < data.size()) {
				cells.copyRows(cells, WORK_TIME_FLOW_START_INDEX + i,
						WORK_TIME_FLOW_START_INDEX + i + WORK_TIME_FLOW_NUM_ROW, WORK_TIME_FLOW_NUM_ROW);
			}
			for (int j = 0; j < dataRow.length; j++) {
				cells.get(WORK_TIME_FLOW_START_INDEX + i, j).setValue(Objects.toString(dataRow[j], ""));
			}
		}
	}

	private void printWorkTimeFlex(Worksheet worksheet, WorkTimeReportDatasource dataSource) throws Exception {
		List<Object[]> data = dataSource.getWorkTimeFlex();
		worksheet.setName(TextResource.localize("KMK003_761"));
		Cells cells = worksheet.getCells();
		printCommonHeader(cells, dataSource.getCompanyName(), REPORT_NAME, dataSource.getExportTime(),
				TextResource.localize("KMK003_761"));
		for (int i = 0; i < data.size(); i++) {
			Object[] dataRow = data.get(i);
			if (i % WORK_TIME_FLEX_NUM_ROW == 0 && i + WORK_TIME_FLEX_NUM_ROW < data.size()) {
				cells.copyRows(cells, WORK_TIME_FLEX_START_INDEX + i,
						WORK_TIME_FLEX_START_INDEX + i + WORK_TIME_FLEX_NUM_ROW, WORK_TIME_FLEX_NUM_ROW);
			}
			for (int j = 0; j < dataRow.length; j++) {
				cells.get(WORK_TIME_FLEX_START_INDEX + i, j).setValue(Objects.toString(dataRow[j], ""));
			}
		}
	}

	private void printCommonHeader(Cells cells, String companyName, String reportName, String reportTime,
			String sheetName) {
		cells.get(0, 1).setValue(companyName);
		cells.get(1, 1).setValue(reportName);
		cells.get(2, 1).setValue(reportTime);
		cells.get(3, 1).setValue(sheetName);
	}

}
