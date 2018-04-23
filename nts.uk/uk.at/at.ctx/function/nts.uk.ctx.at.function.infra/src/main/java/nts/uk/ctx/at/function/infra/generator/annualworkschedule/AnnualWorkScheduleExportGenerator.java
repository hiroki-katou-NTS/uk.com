package nts.uk.ctx.at.function.infra.generator.annualworkschedule;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleGenerator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportDataSource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AnnualWorkScheduleExportGenerator extends AsposeCellsReportGenerator implements AnnualWorkScheduleGenerator {

	private static final String TEMPLATE_FILE = "report/年間勤務表（1ヶ月）.xlsx";

	private static final String REPORT_FILE_NAME = "年間勤務表.xlsx";

	private static final int MAX_EXPORT_ITEM = 10;
	private static final int ROW_PER_PAGE = 27;

	@Override
	public void generate(FileGeneratorContext fileContext, ExportDataSource dataSource) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook wb = reportContext.getWorkbook();
			WorksheetCollection wsc = wb.getWorksheets();
			Worksheet ws = wsc.get(0);
			Range empRange = wsc.getRangeByName("employeeRange");
			int rowPerEmp = dataSource.getExportItems().size();
			int empPerPage = ROW_PER_PAGE/rowPerEmp;
			//delete superfluous rows
			ws.getCells().deleteRows(empRange.getFirstRow() + empRange.getRowCount() //last row
									- (MAX_EXPORT_ITEM - rowPerEmp),
									MAX_EXPORT_ITEM - rowPerEmp);
			//refresh range after delete superfluous rows
			empRange = wsc.getRangeByName("employeeRange");
			Range workplaceRange = wsc.getRangeByName("workplaceRange");
			//set border bottom after delete superfluous rows
			empRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());

			HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();
			List<EmployeeData> emps = dataSource.getData().getEmployees();
			//set first employee
			RangeCustom newRange = new RangeCustom(empRange, 0);
			String workplace = emps.get(0).getEmployeeInfo().get(0);
			newRange.firstCell().putValue(workplace);
			int offset = 0, countEmpPerPage = 1;
			for (EmployeeData emp : emps) {
				if (!workplace.equals(emp.getEmployeeInfo().get(0)) ||
					countEmpPerPage == empPerPage) {
					workplace = emp.getEmployeeInfo().get(0);
					newRange = copyRangeDown(workplaceRange, offset);
					pageBreaks.add(newRange.range.getFirstRow());
					countEmpPerPage = 1; //reset count
				} else {
					newRange.firstCell().putValue(workplace);
					newRange = copyRangeDown(empRange, offset);
					countEmpPerPage ++;
				}
				offset = newRange.offset;
				newRange.firstCell().putValue(workplace);
			}

			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void print() {
		
	}

	private RangeCustom copyRangeDown(Range range, int extra) throws Exception {
		Range newRange = range.getWorksheet().getCells().createRange(range.getFirstRow() + range.getRowCount() + extra,
				range.getFirstColumn(), range.getRowCount(), range.getColumnCount());
		newRange.copyStyle(range);
		int offset = newRange.getFirstRow() - range.getFirstRow();
		return new RangeCustom(newRange, offset);
	}

	private RangeCustom copyRangeDown(Range range) throws Exception {
		return copyRangeDown(range, 0);
	}
}

class RangeCustom {
	int index;
	final int offset;
	Range range;
	Worksheet worksheet;
	WorksheetCollection worksheets;
	public RangeCustom(Range range, int offset) {
		this.offset = offset;
		this.range = range;
		this.worksheet = range.getWorksheet();
		this.worksheets = this.worksheet.getWorkbook().getWorksheets();
	}
	public RangeCustom(Range range, int offset, int index) {
		this.index = index;
		this.offset = offset;
		this.range = range;
		this.worksheet = range.getWorksheet();
		this.worksheets = this.worksheet.getWorkbook().getWorksheets();
	}

	public Cell cell(String name) {
		Cell orginCell = worksheets.getRangeByName(name).getCellOrNull(0, 0);
		return worksheet.getCells().get(orginCell.getRow() + offset, orginCell.getColumn());
	}

	public Cell firstCell() {
		return range.getCellOrNull(0, 0);
	}
}