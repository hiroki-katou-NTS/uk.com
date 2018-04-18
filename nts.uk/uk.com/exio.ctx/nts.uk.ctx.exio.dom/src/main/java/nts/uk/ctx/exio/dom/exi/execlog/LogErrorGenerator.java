package nts.uk.ctx.exio.dom.exi.execlog;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.infra.file.csv.CSVFileData;

public interface LogErrorGenerator {
	
	/**
     * Generate.
     *
     * @param fileContext the file context
     * @param exportData the export data
     */
	void generate(FileGeneratorContext generatorContext, CSVFileData dataSource);
}
