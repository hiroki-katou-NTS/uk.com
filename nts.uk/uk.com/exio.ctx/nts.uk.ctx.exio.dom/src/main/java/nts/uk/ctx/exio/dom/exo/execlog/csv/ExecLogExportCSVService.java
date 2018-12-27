package nts.uk.ctx.exio.dom.exo.execlog.csv;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

@Stateless
public class ExecLogExportCSVService extends ExportService<ExecLogFileDataCSV>{
	
	@Inject
	private ExecLogReportCSVGenerator generator;

	@Override
	protected void handle(ExportServiceContext<ExecLogFileDataCSV> context) {
		
		ExecLogFileDataCSV data = context.getQuery();
		
		this.generator.generate(context.getGeneratorContext(), data);
	}
	
}
