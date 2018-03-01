package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

@Stateless
public class ExecLogAsposeExportService extends ExportService<Object> {

	@Inject
	private ExecLogAsposeGenerator generator;
	
	@Override
	protected void handle(ExportServiceContext<Object> context) {
		List<ExacErrorLog> exacErrorLog = new ArrayList<>();
		List<ExacExeResultLog> exacExeResultLog = new ArrayList<>();
		
		ExacErrorExportFile exportFile = new ExacErrorExportFile(exacErrorLog, exacExeResultLog);
		generator.generateFile(context.getGeneratorContext(), exportFile);
	}
	
}
