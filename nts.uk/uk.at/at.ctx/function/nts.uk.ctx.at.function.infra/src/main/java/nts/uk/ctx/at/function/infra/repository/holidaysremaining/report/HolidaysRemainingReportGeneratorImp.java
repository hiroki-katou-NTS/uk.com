package nts.uk.ctx.at.function.infra.repository.holidaysremaining.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class HolidaysRemainingReportGeneratorImp extends AsposeCellsReportGenerator
		implements HolidaysRemainingReportGenerator {

	private static final String TEMPLATE_FILE = "report/休暇残数管理票_テンプレート.xlsx";
	private static final String REPORT_FILE_NAME = "休暇残数管理票.xlsx";
	private final int numberRowOfPage = 36;

	@Override
	public void generate(FileGeneratorContext generatorContext) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);

			printTemplate(worksheet);

			printPerson(worksheet);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printTemplate(Worksheet worksheet) {
		// todo
		LocalDateTime start = LocalDateTime.of(2018, 1, 01, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(2019, 1, 01, 0, 0, 0);
		LocalDateTime current = LocalDateTime.of(2018, 04, 01, 0, 0, 0);

		int after = totalMonths(start, current);
		int before = totalMonths(current, end);

		Cells cells = worksheet.getCells();

		int firstRow = 4;
		for (int i = 0; i <= after + before; i++) {
			cells.get(firstRow, 10 + i).setValue(String.valueOf(start.plusMonths(i).getMonthValue()) + "月");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");

		cells.get(1, 0).setValue(TextResource.localize("KDR001_1") + 
				start.format(formatter) + "　～　" + end.format(formatter));
		cells.get(2, 0).setValue(TextResource.localize("KDR001_2"));
	}

	private void printPerson(Worksheet worksheet) throws Exception {
		int firstRow = 0;
		int numOfRowEachPerson = worksheet.getCells().getMaxRow();
		int numOfPageEachPerson = (numOfRowEachPerson - 1) / numberRowOfPage + 1;
		for (int i = 0; i < 10; i++) { // todo foreach person
			firstRow = this.printEachPerson(worksheet, firstRow, numOfPageEachPerson);
		}
	}

	private int printEachPerson(Worksheet worksheet, int firstRow, int numOfPageEachPerson) throws Exception {
		Cells cells = worksheet.getCells();
		cells.copyRows(cells, firstRow, firstRow + numOfPageEachPerson * numberRowOfPage,
				numOfPageEachPerson * numberRowOfPage);
		return firstRow + numOfPageEachPerson * numberRowOfPage;
	}

	private int totalMonths(LocalDateTime start, LocalDateTime end) {
		return (end.getYear() - start.getYear()) * 12 + (end.getMonthValue() - start.getMonthValue());
	}
}
