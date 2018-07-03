package nts.uk.file.at.app.export.attendancerecord;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * The Interface AttendanceRecordReportGenerator.
 */
public interface AttendanceRecordReportGenerator {
	
	/**
	 * Generate.
	 *
	 * @param generatorContext the generator context
	 * @param dataSource the data source
	 */
	void generate(FileGeneratorContext generatorContext, AttendanceRecordReportDatasource dataSource);
}
