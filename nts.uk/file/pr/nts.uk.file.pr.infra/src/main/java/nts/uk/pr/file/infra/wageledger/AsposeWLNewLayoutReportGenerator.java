package nts.uk.pr.file.infra.wageledger;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WLNewLayoutReportGenerator;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

@Stateless
public class AsposeWLNewLayoutReportGenerator extends WageLedgerBaseGenerator implements WLNewLayoutReportGenerator{

	@Override
	public void generate(FileGeneratorContext fileContext, WLNewLayoutReportData reportData) {
		try {
			AsposeCellsReportContext reportContext = this.createContext("");
			
			//MutableInt currentRow = ROW_START_REPORT;
			
			// Fill header data.
			reportContext.setDataSource("Employee", reportData.headerData);
			
			// Fill Salary Total Items content.
			
			// Fill Bonus Total Items content.
			
			// Fill Other Items before year end.
			
			// Fill Salary Payment items.
			
			// Fill Salary Deduction items.
			
			// Fill Salary Attendance items.
			
			// Fill Bonus payment and bonus deduction items.
			
			// Fill Bonus Attendance items
			
			// process data binginds in template
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, ""));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
