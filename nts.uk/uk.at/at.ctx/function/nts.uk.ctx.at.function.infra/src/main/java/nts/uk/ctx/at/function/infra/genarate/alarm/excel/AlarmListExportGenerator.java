package nts.uk.ctx.at.function.infra.genarate.alarm.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.export.WorkingFile;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmListGenerator;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AlarmListExportGenerator extends AsposeCellsReportGenerator
		implements AlarmListGenerator {

private static final String TEMPLATE_FILE = "report/KAL001-アラームリスト(個人別).xlsx";
	
		@Override
	public InputStream generate(FileGeneratorContext generatorContext, List<ValueExtractAlarmDto> dataSource) {
		
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {

			// set data source named "item"
			reportContext.setDataSource("item", dataSource);

			// process data binginds in template
			reportContext.processDesigner();

			// save as Excel file
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime = dateNow.toString("yyyymmddhhss");
			String fileName = "AlarmList_"+dateTime+".xlsx";
			OutputStream outputStream = this.createNewFile(generatorContext, fileName);
			reportContext.saveAsExcel(outputStream);
			WorkingFile workingFile = generatorContext.getWorkingFiles().get(0);
			
			return workingFile.getTempFile().createInputStream();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}