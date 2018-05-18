package nts.uk.ctx.at.request.infra.repository.application.remainingnumber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.dom.application.remainingnumer.ExcelInforCommand;
import nts.uk.ctx.at.request.dom.application.remainingnumer.RemainingNumberGenerator;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class RemainingNumberGeneratorImpl extends AsposeCellsReportGenerator implements RemainingNumberGenerator{
	
	private static final String FILE_TEMPLATE = "remainingNumberTemplate.xlsx";
	
	
	@Override
	public void generate(FileGeneratorContext generatorContext, List<ExcelInforCommand> dataSource) {
		// TODO Auto-generated method stub
		try (val reportContext = this.createContext(FILE_TEMPLATE)) {
			   val designer = this.createContext(FILE_TEMPLATE);
			   Workbook workbook = designer.getWorkbook();
			   WorksheetCollection worksheets = workbook.getWorksheets();
			   Worksheet worksheet = worksheets.get(0);

			   printTemplate(worksheet);

			    printDataSource(worksheet, dataSource);


			   designer.getDesigner().setWorkbook(workbook);
			   designer.processDesigner();
			   LoginUserContext loginUserContext = AppContexts.user();
		    	Date now = new Date();
				String fileName = "KDM002_" +new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime()).toString()+ "_"+loginUserContext.employeeCode() + ".xlsx";

			   designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(fileName)));

			  } catch (Exception e) {
			   throw new RuntimeException(e);
			  }
		
	}
	
	private void printTemplate(Worksheet worksheet) throws Exception {

		  Cells cells = worksheet.getCells();

		  cells.get(0, 0).setValue(TextResource.localize("KDM002_11"));
		  cells.get(0, 1).setValue(TextResource.localize("KDM002_12"));
		  cells.get(0, 2).setValue(TextResource.localize("KDM002_13"));
		  cells.get(0, 3).setValue(TextResource.localize("KDM002_14"));
		  cells.get(0, 4).setValue(TextResource.localize("KDM002_16"));
		  cells.get(0, 5).setValue(TextResource.localize("KDM002_17"));
		  cells.get(0, 6).setValue(TextResource.localize("KDM002_18"));

		  
		 }
	
	private void printDataSource(Worksheet worksheet, List<ExcelInforCommand> dataSource) throws Exception{
		int firstRow = 1;
		for (ExcelInforCommand excelInforCommand : dataSource) {
			firstRow = fillDataToExcel(worksheet,firstRow,excelInforCommand);
		}
	}
	private int fillDataToExcel(Worksheet worksheet, int firstRow, ExcelInforCommand excelInforCommand){
		Cells cells = worksheet.getCells();
		cells.get(firstRow,0).setValue(excelInforCommand.getName());
		cells.get(firstRow,1).setValue(excelInforCommand.getDateStart());
		cells.get(firstRow,2).setValue(excelInforCommand.getDateEnd());
		cells.get(firstRow,3).setValue(excelInforCommand.getDateOffYear());
		cells.get(firstRow,4).setValue(excelInforCommand.getDateTargetRemaining());
		cells.get(firstRow,5).setValue(excelInforCommand.getDateAnnualRetirement());
		cells.get(firstRow,6).setValue(excelInforCommand.getDateAnnualRest());
		firstRow +=1;
		return firstRow;
	}
}
