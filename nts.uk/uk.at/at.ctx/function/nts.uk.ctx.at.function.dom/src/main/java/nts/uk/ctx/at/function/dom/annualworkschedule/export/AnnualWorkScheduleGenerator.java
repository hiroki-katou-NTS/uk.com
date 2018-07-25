package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface AnnualWorkScheduleGenerator {

	/**
	 * Generate.
	 *
	 * @param fileContext the file context
	 * @param exportData the export data
	 */
	void generate(FileGeneratorContext fileContext, ExportData dataSource);
}
