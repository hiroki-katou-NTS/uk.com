package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EmpInfoTerminalExport {

	void export(FileGeneratorContext generatorContext, EmpInfoTerminalExportDatasource datasource);
}
