package nts.uk.file.com.infra.generate;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.VerticalPageBreakCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.file.com.app.EmployeeUnregisterOutputDataSoure;
import nts.uk.file.com.app.EmployeeUnregisterOutputGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeEmployeeUnregisterOutputReportGenerator extends AsposeCellsReportGenerator
		implements EmployeeUnregisterOutputGenerator {

	private static final String TEMPLATE_FILE = "report/承認ルート未登録の社員のEXCEL出力.xlsx";

	private static final String REPORT_FILE_NAME = "承認ルート未登録の社員.xlsx";

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6 };
	
	private static final String  HEADER ="HEADER";

	@Override
	public void generate(FileGeneratorContext generatorContext, EmployeeUnregisterOutputDataSoure dataSource) {

		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			// set up page prepare print
			this.printPage(worksheet, dataSource);
			this.printEmployee(worksheets, dataSource);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * PRINT PAGE
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPage(Worksheet worksheet,  EmployeeUnregisterOutputDataSoure dataSource) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:G");
		pageSetup.setHeader(0, "【会社】 " + dataSource.getHeaderEmployee().getNameCompany());

	}

	/**
	 * PRINT EACH EMPLOYEE
	 *
	 * @param worksheets
	 *            the worksheets
	 */
	private void printEmployee(WorksheetCollection worksheets, EmployeeUnregisterOutputDataSoure employee) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();

		List<EmployeeUnregisterOutput> employeeUnregisLst = employee.getEmployeeUnregisterOutputLst();
		int x = 3;
		for (int j = 0; j < employeeUnregisLst.size(); j++) {

			int totalRowOfEmployee = employeeUnregisLst.get(j).getAppType().size();
			if (totalRowOfEmployee > 1) {
				int numberOfPage = (x + totalRowOfEmployee) / 52;

				int numberOfRowMerge = (52 * numberOfPage) - x;
				if (numberOfRowMerge > 0) {
					cells.merge(x, 1, numberOfRowMerge, 1, true);
					Cell sCd = cells.get(x, COLUMN_INDEX[1]);
					sCd.setValue(employeeUnregisLst.get(j).getEmpInfor().getSCd());

					cells.merge(x, 2, numberOfRowMerge, 1, true);
					Cell pName = cells.get(x, COLUMN_INDEX[2]);
					pName.setValue(employeeUnregisLst.get(j).getEmpInfor().getPName());

					cells.merge(x, 3, numberOfRowMerge, 1, true);
					Cell wpCode = cells.get(x, COLUMN_INDEX[3]);
					wpCode.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpCode());

					cells.merge(x, 4, numberOfRowMerge, 1, true);
					Cell wpName = cells.get(x, COLUMN_INDEX[4]);
					wpName.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpName());

					for (int k = 0; k < numberOfRowMerge; k++) {
						Cell sCode = cells.get(x + k, COLUMN_INDEX[1]);
						setTitleStyle(sCode);

						Cell perName = cells.get(x + k, COLUMN_INDEX[2]);
						setTitleStyle(perName);

						Cell wpCd = cells.get(x + k, COLUMN_INDEX[3]);
						setTitleStyle(wpCd);

						Cell worplaceName = cells.get(x + k, COLUMN_INDEX[4]);
						setTitleStyle(worplaceName);
					}

					for (int i = 0; i < numberOfRowMerge; i++) {

						Cell appType = cells.get(x, COLUMN_INDEX[5]);
						appType.setValue(employeeUnregisLst.get(j).getAppType().get(i));
						Style style = appType.getStyle();
						style.setPattern(BackgroundType.SOLID);
						style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
						style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
						appType.setStyle(style);
						x++;
					}
					 x = x + 1;
					HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
					hPageBreaks.add("H" + x);
					VerticalPageBreakCollection vPageBreaks = worksheet.getVerticalPageBreaks();
					vPageBreaks.add("H" + x);

					 x = x - 1;
					cells.merge(x, 1, totalRowOfEmployee - numberOfRowMerge, 1, true);
					Cell sCd1 = cells.get(x, COLUMN_INDEX[1]);
					sCd1.setValue(employeeUnregisLst.get(j).getEmpInfor().getSCd());

					cells.merge(x, 2, totalRowOfEmployee - numberOfRowMerge, 1, true);
					Cell pName1 = cells.get(x, COLUMN_INDEX[2]);
					pName1.setValue(employeeUnregisLst.get(j).getEmpInfor().getPName());

					cells.merge(x, 3, totalRowOfEmployee - numberOfRowMerge, 1, true);
					Cell wpCode1 = cells.get(x, COLUMN_INDEX[3]);
					wpCode1.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpCode());

					cells.merge(x, 4, totalRowOfEmployee - numberOfRowMerge, 1, true);
					Cell wpName1 = cells.get(x, COLUMN_INDEX[4]);
					wpName1.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpName());

					for (int k = 0; k < totalRowOfEmployee - numberOfRowMerge; k++) {
						Cell sCode = cells.get(x + k, COLUMN_INDEX[1]);
						setTitleStyle(sCode);

						Cell perName = cells.get(x + k, COLUMN_INDEX[2]);
						setTitleStyle(perName);

						Cell wpCd = cells.get(x + k, COLUMN_INDEX[3]);
						setTitleStyle(wpCd);

						Cell worplaceName = cells.get(x + k, COLUMN_INDEX[4]);
						setTitleStyle(worplaceName);
					}

					for (int i = numberOfRowMerge; i < totalRowOfEmployee; i++) {

						Cell appType = cells.get(x, COLUMN_INDEX[5]);
						appType.setValue(employeeUnregisLst.get(j).getAppType().get(i));
						Style style = appType.getStyle();
						style.setPattern(BackgroundType.SOLID);
						style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
						style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
						appType.setStyle(style);
						x++;
					}

				} else {

					cells.merge(x, 1, totalRowOfEmployee, 1, true);
					Cell sCd = cells.get(x, COLUMN_INDEX[1]);
					sCd.setValue(employeeUnregisLst.get(j).getEmpInfor().getSCd());

					cells.merge(x, 2, totalRowOfEmployee, 1, true);
					Cell pName = cells.get(x, COLUMN_INDEX[2]);
					pName.setValue(employeeUnregisLst.get(j).getEmpInfor().getPName());

					cells.merge(x, 3, totalRowOfEmployee, 1, true);
					Cell wpCode = cells.get(x, COLUMN_INDEX[3]);
					wpCode.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpCode());

					cells.merge(x, 4, totalRowOfEmployee, 1, true);
					Cell wpName = cells.get(x, COLUMN_INDEX[4]);
					wpName.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpName());

					for (int k = 0; k < totalRowOfEmployee; k++) {
						Cell sCode = cells.get(x + k, COLUMN_INDEX[1]);
						setTitleStyle(sCode);

						Cell perName = cells.get(x + k, COLUMN_INDEX[2]);
						setTitleStyle(perName);

						Cell wpCd = cells.get(x + k, COLUMN_INDEX[3]);
						setTitleStyle(wpCd);

						Cell worplaceName = cells.get(x + k, COLUMN_INDEX[4]);
						setTitleStyle(worplaceName);
					}
					for (int i = 0; i < totalRowOfEmployee; i++) {

						Cell appType = cells.get(x, COLUMN_INDEX[5]);
						appType.setValue(employeeUnregisLst.get(j).getAppType().get(i));
						Style style = appType.getStyle();
						style.setPattern(BackgroundType.SOLID);
						style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
						style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
						appType.setStyle(style);
						x++;
					}

				}

			} else {

				Cell sCd = cells.get(x, COLUMN_INDEX[1]);
				sCd.setValue(employeeUnregisLst.get(j).getEmpInfor().getSCd());
				setTitleStyle(sCd);

				Cell pName = cells.get(x, COLUMN_INDEX[2]);
				pName.setValue(employeeUnregisLst.get(j).getEmpInfor().getPName());
				setTitleStyle(pName);

				Cell wpCode = cells.get(x, COLUMN_INDEX[3]);
				wpCode.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpCode());
				setTitleStyle(wpCode);

				Cell wpName = cells.get(x, COLUMN_INDEX[4]);
				wpName.setValue(employeeUnregisLst.get(j).getEmpInfor().getWpName());
				setTitleStyle(wpName);

				Cell appType = cells.get(x, COLUMN_INDEX[5]);
				appType.setValue(employeeUnregisLst.get(j).getAppType().get(0));
				Style style = appType.getStyle();
				style.setPattern(BackgroundType.SOLID);
				style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
				appType.setStyle(style);
				x++;
			}

		}

	}

	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setTitleStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOTTED, Color.getGray());
		cell.setStyle(style);
	}

}
