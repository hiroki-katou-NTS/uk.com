package nts.uk.pr.file.infra.accumulatedpayment;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.Stateless;

import com.aspose.cells.BorderType;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.ctx.pr.screen.app.report.qet002.AccPaymentReportGenerator;
import nts.uk.ctx.pr.screen.app.report.qet002.data.AccPaymentDataSource;
import nts.uk.ctx.pr.screen.app.report.qet002.data.AccPaymentItemData;

@Stateless
public class AsposeAccPaymentReportGenerator extends AsposeCellsReportGenerator
		implements AccPaymentReportGenerator {

	private static final String REPORT_FILE_NAME = "AccumulatedPaymentReport.pdf";

	private static final String TEMPLATE_FILE = "report/AccumulatedPaymentReport.xlsx";


	private static final int FIRST_ROW_INDEX = 13;


	private static final int FIRSTCOLUMN = 0;


	private static final int BREAKINGPAGE1INDEX = 50;


	private static final int TOTALCOLUMNS = 8;


	private static final int BREAKINGPAGE2INDEX = 112;

	@Override
	public void generate(FileGeneratorContext generatorContext, AccPaymentDataSource dataSource) {
		 ArrayList<AccPaymentItemData> accumulatedPaymentList = new ArrayList<>();
		 for (int i = 0; i < 130; i++) {
				AccPaymentItemData accumulatedPayment =  AccPaymentItemData
						.builder()
						.empDesignation("Designation" + (i + 1))
						.empCode("Code " + (i + 1))
						.empName("Name " + (i + 1))
						.taxAmount(1.0 + i)
						.socialInsuranceAmount(1.0 + i)
						.widthHoldingTaxAmount(1.0 + i)
						.amountAfterTaxDeduction(1.0 + i)
						.enrollmentStatus("Enrolment Status")
						.directionalStatus("onloan")
						.build();				
				accumulatedPaymentList.add(accumulatedPayment);
			}			
		
		try{
			//accumulatedPaymentList = (ArrayList<AccPaymentItemData>) dataSource.getAccPaymentItemData();
		//WorkbookDesigner designer = new WorkbookDesigner();
		val designer = this.createContext(TEMPLATE_FILE);	
		Workbook workbook = designer.getWorkbook();		
		
		// create worksheet and Formatting ...
		WorksheetCollection worksheets = workbook.getWorksheets();
		// FIRST WORKSHEET
					createSheet(worksheets, 0, 0, BREAKINGPAGE1INDEX, FIRST_ROW_INDEX, accumulatedPaymentList);
					// WORKSHEET Number 2
					createSheet(worksheets, 1, BREAKINGPAGE1INDEX, BREAKINGPAGE2INDEX, 0, accumulatedPaymentList);
					// FINAL WORKSHEET
					createSheet(worksheets, 2, BREAKINGPAGE2INDEX, accumulatedPaymentList.size(), 0, accumulatedPaymentList);
					designer.getDesigner().setWorkbook(workbook);
					designer.processDesigner();
					designer.saveAsPdf(this.createNewFile(generatorContext, REPORT_FILE_NAME));
					//designer.getWorkbook().save("D:/test.pdf", FileFormatType.PDF);
		} catch (Exception e) {
			throw new RuntimeException(e);
			//e.printStackTrace();
		}
	}


	// Set Columns Width Method
	private void setColumnWidth(Cells cells) {
		cells.setColumnWidth(0, 12);
		cells.setColumnWidth(1, 8);
		cells.setColumnWidth(2, 12);
		cells.setColumnWidth(3, 12);
		cells.setColumnWidth(4, 12);
		cells.setColumnWidth(5, 12);
		cells.setColumnWidth(6, 7);
		cells.setColumnWidth(7, 40);
	}

	// Create Sheet
	private void createSheet(WorksheetCollection worksheets, int sheetIndex, int firstIndex, int breakingPageIndex,
			int rowIndex, ArrayList<AccPaymentItemData> accumulatedPaymentList) {
		int addSheet = worksheets.add();
		Worksheet worksheet = worksheets.get(sheetIndex);
		Cells cells = worksheet.getCells();
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setPrintArea("A1:H63");
		pageSetup.setFitToPagesTall(1);
		pageSetup.setFitToPagesWide(1);
		Style style = cells.getStyle();
		style.setBackgroundColor(Color.getGreen());
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getGray());
		// Set Columns Width
		setColumnWidth(cells);
		// Print Items
		for (int i = firstIndex, j = rowIndex; i < breakingPageIndex; i++, j++) {
			cells.get(j, 0).setValue(accumulatedPaymentList.get(i).getEmpCode());
			cells.get(j, 0).setStyle(style);
			cells.get(j, 1).setValue(accumulatedPaymentList.get(i).getEmpName());
			cells.get(j, 1).setStyle(style);
			cells.get(j, 2).setValue(accumulatedPaymentList.get(i).getTaxAmount());
			cells.get(j, 2).setStyle(style);
			cells.get(j, 3).setValue(accumulatedPaymentList.get(i).getSocialInsuranceAmount());
			cells.get(j, 3).setStyle(style);
			cells.get(j, 4).setValue(accumulatedPaymentList.get(i).getAmountAfterTaxDeduction());
			cells.get(j, 4).setStyle(style);
			cells.get(j, 5).setValue(accumulatedPaymentList.get(i).getWidthHoldingTaxAmount());
			cells.get(j, 5).setStyle(style);
			cells.get(j, 6).setValue(accumulatedPaymentList.get(i).getEnrollmentStatus());
			cells.get(j, 6).setStyle(style);
			cells.get(j, 7).setValue(accumulatedPaymentList.get(i).getDirectionalStatus());
			cells.get(j, 7).setStyle(style);
		}
	}

}
