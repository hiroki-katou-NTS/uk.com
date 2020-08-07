package nts.uk.ctx.at.request.infra.repository.application;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.cells.Workbook;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.print.ApplicationGenerator;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsposeApplication extends AsposeCellsReportGenerator implements ApplicationGenerator {

	@Override
	public void generate(FileGeneratorContext generatorContext, PrintContentOfApp printContentOfApp, ApplicationType appType) {
		try {
			
			val designer = this.createContext(this.getFileTemplate(appType));
			Workbook workbook = designer.getWorkbook();
			/*WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			printPage(worksheet, printContentOfApp);
			printContent(worksheet, printContentOfApp);*/
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(this.getFileName(appType))));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private String getFileTemplate(ApplicationType appType) {
		switch (appType) {
		default:
			return "testAppTemplate";
		}
	}
	
	private String getFileName(ApplicationType appType) {
		switch (appType) {
		default:
			return "testAppName";
		}
	}

}
