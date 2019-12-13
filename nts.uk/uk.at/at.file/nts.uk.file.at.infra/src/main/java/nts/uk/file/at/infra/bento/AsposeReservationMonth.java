package nts.uk.file.at.infra.bento;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.cells.CellArea;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.period.DatePeriod;
import nts.uk.file.at.app.export.bento.ReservationBentoLedger;
import nts.uk.file.at.app.export.bento.ReservationEmpLedger;
import nts.uk.file.at.app.export.bento.ReservationMonthDataSource;
import nts.uk.file.at.app.export.bento.ReservationMonthGenerator;
import nts.uk.file.at.app.export.bento.ReservationWkpLedger;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsposeReservationMonth extends AsposeCellsReportGenerator implements ReservationMonthGenerator {
	
	private static final String TEMPLATE_FILE = "report/帳票イメージ-KMR005_月間予約台帳.xlsx";

	private static final String REPORT_FILE_NAME = "帳票イメージ-KMR005_月間予約台帳.xlsx";
	
	private static final Integer EMPLOYEE_COLUMN = 1;
	
	private static final Integer NAME_COLUMN = 2;
	
	private static final Integer START_DATA_COLUMN = 3;
	
	private static final Integer QUANTITY_COLUMN = 34;
	
	private static final Integer AMOUNT1_COLUMN = 35;
	
	private static final Integer AMOUNT2_COLUMN = 36;
	
	@Override
	public void generate(FileGeneratorContext generatorContext, ReservationMonthDataSource dataSource) {
		try {
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			printPage(worksheet, dataSource);
			printContent(worksheet, dataSource);
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * PRINT PAGE HEADER
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPage(Worksheet worksheet, ReservationMonthDataSource dataSource) {
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setHeader(0, dataSource.getCompanyName());
		pageSetup.setHeader(1, dataSource.getTitle());
	}
	
	private void printContent(Worksheet worksheet, ReservationMonthDataSource dataSource) throws Exception {
		Cells cells = worksheet.getCells();
		int maxRow = cells.getMaxRow();
		int maxColumn = cells.getMaxColumn();
		cells.clearContents(CellArea.createCellArea(3, 1, maxRow, maxColumn));
		GeneralDate startDate = dataSource.getPeriod().start();
		GeneralDate endDate = dataSource.getPeriod().end();
		SimpleDateFormat jp = new SimpleDateFormat("yyyy年MM月dd日(E)",Locale.JAPAN);
		cells.get(0, 1).setValue(I18NText.getText("KMR005_8") + jp.format(startDate.date()) + "〜" + jp.format(endDate.date()));
		cells.get(1, 1).setValue(I18NText.getText("Com_Person"));
		cells.get(1, 2).setValue(I18NText.getText("KMR005_9"));
		printDayOfWeekHeader(worksheet, dataSource);
		cells.get(1, 34).setValue(I18NText.getText("KMR005_10"));
		cells.get(1, 35).setValue(I18NText.getText("KMR005_11"));
		cells.get(1, 36).setValue(I18NText.getText("KMR005_12"));
		Integer dataNumberRow = dataSource.getWkpLedgerLst().stream().map(x -> 
			x.getEmpLedgerLst().stream().map(y -> 
				y.getBentoLedgerLst().size() + 1
			).collect(Collectors.summingInt(Integer::intValue))
		).map(x -> x + 1).collect(Collectors.summingInt(Integer::intValue));	
		if(dataNumberRow==0) {
			return;
		}
		cells.insertRows(7, dataNumberRow);
		cells.clearFormats(7, 1, dataNumberRow, maxColumn);
		printFormat(cells, dataSource);
		printContent(cells, dataSource);
		cells.deleteBlankRows();
	}
	
	private void printDayOfWeekHeader(Worksheet worksheet, ReservationMonthDataSource dataSource) {
		Cells cells = worksheet.getCells();
		GeneralDate startDate = dataSource.getPeriod().start();
		GeneralDate endDate = dataSource.getPeriod().end();
		for(int i = 0; i <= endDate.compareTo(startDate); i++) {
			GeneralDate loopDate = startDate.addDays(i);
			cells.get(1, i + 3).setValue(loopDate.day());
			DayOfWeek dayOfWeek = DayOfWeek.of(loopDate.dayOfWeek());
			switch (dayOfWeek) {
			case MONDAY:
				cells.get(2, i + 3).setValue("月");
				break;
			case TUESDAY:
				cells.get(2, i + 3).setValue("火");
				break;
			case WEDNESDAY:
				cells.get(2, i + 3).setValue("水");
				break;
			case THURSDAY:
				cells.get(2, i + 3).setValue("木");
				break;
			case FRIDAY:
				cells.get(2, i + 3).setValue("金");
				break;
			case SATURDAY:
				cells.get(2, i + 3).setValue("土");
				break;
			case SUNDAY:
				cells.get(2, i + 3).setValue("日");
				break;
			default:
				break;
			}
		}
	}
	
	private void printFormat(Cells cells, ReservationMonthDataSource dataSource) throws Exception {
		int startDataRow = 6;
		for(ReservationWkpLedger wkpLedger : dataSource.getWkpLedgerLst()) {
			startDataRow = printWkpFormat(cells, wkpLedger, dataSource.getPeriod(), startDataRow);
		}
	}
	
	private Integer printWkpFormat(Cells cells, ReservationWkpLedger wkpLedger, DatePeriod period, Integer dataRow) throws Exception {
		int startDataRow = dataRow + 1;
		cells.copyRow(cells, 3, startDataRow);
		for(ReservationEmpLedger empLedger : wkpLedger.getEmpLedgerLst()) {
			startDataRow = printEmpFormat(cells, empLedger, period, startDataRow);
		}
		return startDataRow;
	}
	
	private Integer printEmpFormat(Cells cells, ReservationEmpLedger empLedger, DatePeriod period, Integer dataRow) throws Exception {
		int startDataRow = dataRow + 1;
		for(ReservationBentoLedger bentoLedger : empLedger.getBentoLedgerLst()) {
			if(bentoLedger.getBentoNumber().intValue()%2==0) {
				cells.copyRow(cells, 5, startDataRow);
			} else {
				cells.copyRow(cells, 4, startDataRow);
			}
			startDataRow += 1;
		}
		cells.copyRow(cells, 6, startDataRow);
		cells.merge(dataRow + 1, 1, 41, 1);
		return startDataRow;
	}
	
	private void printContent(Cells cells, ReservationMonthDataSource dataSource) throws Exception {
		int startDataRow = 6;
		for(ReservationWkpLedger wkpLedger : dataSource.getWkpLedgerLst()) {
			startDataRow = printWorkPlace(cells, wkpLedger, dataSource.getPeriod(), startDataRow);
		}
	}
	
	private Integer printWorkPlace(Cells cells, ReservationWkpLedger wkpLedger, DatePeriod period, Integer dataRow) throws Exception {
		int startDataRow = dataRow + 1;
		cells.get(startDataRow, EMPLOYEE_COLUMN).setValue(wkpLedger.getWkpName());
		for(ReservationEmpLedger empLedger : wkpLedger.getEmpLedgerLst()) {
			startDataRow = printEmployee(cells, empLedger, period, startDataRow);
		}
		return startDataRow;
	}
	
	private Integer printEmployee(Cells cells, ReservationEmpLedger empLedger, DatePeriod period, Integer dataRow) throws Exception {
		int startDataRow = dataRow + 1;
		cells.get(startDataRow, EMPLOYEE_COLUMN).setValue(empLedger.getEmpName());
		for(ReservationBentoLedger bentoLedger : empLedger.getBentoLedgerLst()) {
			printBento(cells, bentoLedger, period, startDataRow);
			startDataRow += 1;
		}
		cells.get(startDataRow, QUANTITY_COLUMN).setValue(empLedger.getTotalBentoQuantity());
		cells.get(startDataRow, AMOUNT1_COLUMN).setValue(empLedger.getTotalBentoAmount1());
		cells.get(startDataRow, AMOUNT2_COLUMN).setValue(empLedger.getTotalBentoAmount2());
		return startDataRow;
	}
	
	private void printBento(Cells cells, ReservationBentoLedger bentoLedger, DatePeriod period, Integer dataRow) {
		cells.get(dataRow, NAME_COLUMN).setValue(bentoLedger.getBentoName());
		bentoLedger.getQuantityDateMap().entrySet().stream().forEach(x -> {
			GeneralDate loopDate = x.getKey();
			Integer quantity = x.getValue();
			int periodRange = loopDate.compareTo(period.start());
			cells.get(dataRow, START_DATA_COLUMN + periodRange).setValue(quantity);
		});
		cells.get(dataRow, QUANTITY_COLUMN).setValue(bentoLedger.getTotalQuantity());
		cells.get(dataRow, AMOUNT1_COLUMN).setValue(bentoLedger.getTotalAmount1());
		cells.get(dataRow, AMOUNT2_COLUMN).setValue(bentoLedger.getTotalAmount2());
	}
}
