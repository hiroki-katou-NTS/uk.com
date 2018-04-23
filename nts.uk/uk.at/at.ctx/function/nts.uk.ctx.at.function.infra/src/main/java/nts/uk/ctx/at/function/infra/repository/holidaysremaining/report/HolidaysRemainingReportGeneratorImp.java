package nts.uk.ctx.at.function.infra.repository.holidaysremaining.report;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class HolidaysRemainingReportGeneratorImp extends AsposeCellsReportGenerator
		implements HolidaysRemainingReportGenerator {

	private static final String TEMPLATE_FILE = "report/休暇残数管理票_テンプレート.xlsx";
	private static final String REPORT_FILE_NAME = "休暇残数管理票.xlsx";
	private final int numberRowOfPage = 36;
	private final int minRowDetails = 4;
	private HolidayRemainingDataSource dataSource;

	@Override
	public void generate(FileGeneratorContext generatorContext, HolidayRemainingDataSource dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			this.dataSource = dataSource;
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);

			printTemplate(worksheet);

			printPerson(worksheet);

			removeTemplate(worksheet);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printTemplate(Worksheet worksheet) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
		YearMonth start = YearMonth.parse(dataSource.getStartMonth(), formatter);
		YearMonth end = YearMonth.parse(dataSource.getEndMonth(), formatter);
		YearMonth currentMonth = YearMonth.now();

		Cells cells = worksheet.getCells();

		// B1_1, B1_2
		cells.get(1, 0)
				.setValue(TextResource.localize("KDR001_2") + start.format(formatter) + "　～　" + end.format(formatter));
		// B1_3
		cells.get(2, 0).setValue(TextResource.localize("KDR001_3"));
		// C1_1
		cells.get(3, 2).setValue(TextResource.localize("KDR001_4"));
		// C1_2
		cells.get(3, 4).setValue(TextResource.localize("KDR001_5"));
		// C1_3
		cells.get(3, 9).setValue(TextResource.localize("KDR001_6"));
		// C1_4
		cells.get(4, 4).setValue(TextResource.localize("KDR001_7"));
		// C1_5
		cells.get(4, 5).setValue(TextResource.localize("KDR001_8"));
		// C1_6
		cells.get(4, 6).setValue(TextResource.localize("KDR001_9"));
		// C1_7
		cells.get(4, 7).setValue(TextResource.localize("KDR001_10"));
		// C1_8
		cells.get(4, 8).setValue(TextResource.localize("KDR001_11"));
		if (end.compareTo(currentMonth) > 0 && currentMonth.compareTo(start) > 0) {
			Cell cell1 = cells.get(4, 21);
			Cell cell2 = cells.get(4, 10 + totalMonths(start, currentMonth));
			cell2.copy(cell1);
			worksheet.getShapes().removeAt(0);
		}
		// C1_9
		for (int i = 0; i <= totalMonths(start, end); i++) {
			cells.get(4, 10 + i).setValue(String.valueOf(start.plusMonths(i).getMonthValue()) + "月");
		}

	}

	private void printPerson(Worksheet worksheet) throws Exception {
		int firstRow = numberRowOfPage;

		for (HolidaysRemainingEmployee employee : dataSource.getListEmployee()) {
			firstRow = this.printEachPerson(worksheet, firstRow, employee);
		}
	}

	private int printEachPerson(Worksheet worksheet, int firstRow, HolidaysRemainingEmployee employee)
			throws Exception {
		Cells cells = worksheet.getCells();
		// D index
		// print Header
		cells.copyRows(cells, 0, firstRow, 6);
		firstRow += 6;
		int totalRowDetails = 0;
		int rowIndexD = firstRow + 5;

		// 年休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
			cells.copyRows(cells, 6, firstRow, 2);
			firstRow += 2;
			totalRowDetails += 2;
		}
		// 積立年休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getYearlyReserved().isYearlyReserved()) {
			cells.copyRows(cells, 8, firstRow, 2);
			firstRow += 2;
			totalRowDetails += 2;
		}
		// 代休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSubstituteHoliday()
				.isOutputItemSubstitute()) {
			cells.copyRows(cells, 10, firstRow, 4);
			firstRow += 4;
			totalRowDetails += 4;
		}
		// 振休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isPauseItem()) {
			cells.copyRows(cells, 14, firstRow, 4);
			firstRow += 4;
			totalRowDetails += 4;
		}
		// 特別休暇
		List<Integer> specialHoliday = dataSource.getHolidaysRemainingManagement().getListItemsOutput()
				.getSpecialHoliday();
		for (int i = 0; i < specialHoliday.size(); i++) {
			cells.copyRows(cells, 18, firstRow, 2);
			firstRow += 2;
			totalRowDetails += 2;
		}
		// 子の看護休暇
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
				.isChildNursingLeave()) {
			cells.copyRows(cells, 20, firstRow, 2);
			firstRow += 2;
			totalRowDetails += 2;
		}
		// 介護休暇
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getNursingcareLeave().isNursingLeave()) {
			cells.copyRows(cells, 22, firstRow, 2);
			firstRow += 2;
			totalRowDetails += 2;
		}

		if (totalRowDetails < minRowDetails) {
			cells.copyRows(cells, 25, firstRow, minRowDetails - totalRowDetails);
			firstRow += (minRowDetails - totalRowDetails);
		}
		// print D
		// D1_1, D1_2
		cells.get(rowIndexD, 0).setValue(
				TextResource.localize("KDR001_12") + employee.getWorkplaceCode() + "　" + employee.getWorkplaceName());
		// D2_1
		cells.get(rowIndexD + 1, 0).setValue(employee.getEmployeeCode());
		// D2_2
		cells.get(rowIndexD + 1, 1).setValue(employee.getEmployeeName());
		// D2_3 Todo rql No.369
		

		cells.copyRows(cells, 24, firstRow, 1);

		if (firstRow % numberRowOfPage != 0) {
			firstRow += (numberRowOfPage - firstRow % numberRowOfPage);
		}

		return firstRow;
	}

	private void removeTemplate(Worksheet worksheet) {
		Cells cells = worksheet.getCells();
		cells.deleteRows(0, numberRowOfPage);
	}

	private int totalMonths(YearMonth start, YearMonth end) {
		return (end.getYear() - start.getYear()) * 12 + (end.getMonthValue() - start.getMonthValue());
	}
}
