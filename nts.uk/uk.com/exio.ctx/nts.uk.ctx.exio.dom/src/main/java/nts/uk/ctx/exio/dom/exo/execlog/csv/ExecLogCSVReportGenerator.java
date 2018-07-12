package nts.uk.ctx.exio.dom.exo.execlog.csv;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ExecLogCSVReportGenerator {

	void generate(FileGeneratorContext generatorContext, ExecLogCSVFileData dataSource);
}
