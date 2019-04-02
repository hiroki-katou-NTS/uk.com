package nts.uk.file.at.app.export.yearholidaymanagement;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface OutputYearHolidayManagementGenerator {
	/**
	 * Generate.
	 *
	 * @param fileGeneratorContext
	 *            the file generator context
	 * @param query
	 *            the query
	 */
	void generate(FileGeneratorContext fileGeneratorContext, OutputYearHolidayManagementQuery query);
}
