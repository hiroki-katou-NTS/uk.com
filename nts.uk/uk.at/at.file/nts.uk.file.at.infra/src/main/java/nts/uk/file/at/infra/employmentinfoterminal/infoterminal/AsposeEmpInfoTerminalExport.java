package nts.uk.file.at.infra.employmentinfoterminal.infoterminal;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.export.WorkingFile;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmExportDto;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExport;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportDataSource;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public class AsposeEmpInfoTerminalExport extends AsposeCellsReportGenerator implements EmpInfoTerminalExport{
	
	private static final String TEMPLATE_FILE = "report/KNR001.xlsx";

	@Override
	public void export(FileGeneratorContext generatorContext, List<EmpInfoTerminalExportDataSource> dataSource) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			setHeaderAndHeaderColumn(reportContext);
			// set data source named "item"
			reportContext.setDataSource("item", dataSource);
			// process data binginds in template
			reportContext.processDesigner();

			// save as Excel file
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime = dateNow.toString("yyyyMMddHHmmss");
			String fileName = "KNR001就業情報端末の登録_" + dateTime + ".xlsx";
			OutputStream outputStream = this.createNewFile(generatorContext, fileName);
			reportContext.saveAsExcel(outputStream);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setHeaderAndHeaderColumn(AsposeCellsReportContext reportContext) {
		
	}

}
