package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

public class EmpInfoTerminalExportService extends ExportService<EmpInfoTerminalExportDatasource>{
	
	@Inject
	private EmpInfoTerminalExport empInfoTerminalExport;

	@Override
	protected void handle(ExportServiceContext<EmpInfoTerminalExportDatasource> context) {
		// TODO Auto-generated method stub
		
	}

}
