package nts.uk.pr.file.infra.wageledger;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang3.mutable.MutableInt;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WLNewLayoutReportGenerator;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

@Stateless
public class AsposeWLNewLayoutReportGenerator extends WageLedgerBaseGenerator implements WLNewLayoutReportGenerator{
	
	/** The Constant ROW_START_REPORT. */
	private static final int ROW_START_REPORT = 5;
	
	/** The Constant COLUMN_START_REPORT. */
	private static final int COLUMN_START_REPORT = 0;
	
	@Override
	public void generate(FileGeneratorContext fileContext, WLNewLayoutReportData reportData) {
		try {
			AsposeCellsReportContext reportContext = this.createContext("");
			
			MutableInt currentRow = new MutableInt(ROW_START_REPORT);
			MutableInt currentColumn = new MutableInt(COLUMN_START_REPORT);
			
			// ======================== Fill header data.========================
			this.fillHeaderData(reportContext, reportData.headerData);
			
			// ======================== Fill Total part.========================
			this.setDataSourceForTotalPart(reportContext, reportData);
			
			// ======================== Fill Salary payment part.========================
			this.fillHeaderTable(reportContext, currentRow, currentColumn, reportData.salaryPaymentDateMap);
			this.fillReportItemsData(reportContext, reportData.salaryPaymentItems, currentColumn, currentRow, "SalaryPayment");
			
			// ======================== Fill Salary Attendance part.========================
			
			// ======================== Fill Bonus Payment part and Bonus Deduction part.========================
			
			// ======================== Fill Bonus Attendance part.========================
			
			// process data binginds in template
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, ""));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private void setDataSourceForTotalPart(AsposeCellsReportContext reportContext, WLNewLayoutReportData reportData) {
		
	}
	
	private void fillReportItemsData(AsposeCellsReportContext reportContext, List<ReportItemDto> reportItems,
			MutableInt startColumn, MutableInt startRow, String contentName) {
		
	}
	
	private void fillHeaderTable(AsposeCellsReportContext reportContext, MutableInt startRow,
			MutableInt startColumn, Map<Integer, Date> paymentDateMap) {
		
	}
	
}
