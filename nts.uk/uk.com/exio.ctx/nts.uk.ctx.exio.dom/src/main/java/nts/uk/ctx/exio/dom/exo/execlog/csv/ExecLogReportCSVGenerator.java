package nts.uk.ctx.exio.dom.exo.execlog.csv;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ExecLogReportCSVGenerator {

	void generate(FileGeneratorContext generatorContext, ExecLogFileDataCSV dataSource);
}
