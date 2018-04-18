package nts.uk.ctx.exio.dom.exi.execlog.csv;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

@Stateless
public class ExecLogCSVExportService extends ExportService<ExecLogCSVFileData>{
	
	@Inject
	private ExecLogCSVReportGenerator generator;

	@Override
	protected void handle(ExportServiceContext<ExecLogCSVFileData> context) {
		
		ExecLogCSVFileData data = context.getQuery();
		
		this.generator.generate(context.getGeneratorContext(), data);
	}
	
}
