package nts.uk.file.pr.app.export.comparingsalarybonus;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;

public interface ComparingSalaryBonusGenerator {
	/**
	 * Generate.
	 *
	 * @param generatorContext
	 *            the generator context
	 * @param dataSource
	 *            the data source
	 * @param query
	 *            the query
	 */
	void generate(FileGeneratorContext generatorContext, ComparingSalaryBonusReportData salaryBonusReportData, DeparmentInf depInf);
}
