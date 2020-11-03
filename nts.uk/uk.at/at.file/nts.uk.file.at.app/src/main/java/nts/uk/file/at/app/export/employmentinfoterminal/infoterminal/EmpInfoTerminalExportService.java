package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

public class EmpInfoTerminalExportService extends ExportService<List<EmpInfoTerminalExportDataSource>>{
	
	@Inject
	private EmpInfoTerminalExport empInfoTerminalExport;

	@Override
	protected void handle(ExportServiceContext<List<EmpInfoTerminalExportDataSource>> context) {
		List<EmpInfoTerminalExportDataSource> dataSource = context.getQuery();
		
		empInfoTerminalExport.export(context.getGeneratorContext(), dataSource);
	}

}
