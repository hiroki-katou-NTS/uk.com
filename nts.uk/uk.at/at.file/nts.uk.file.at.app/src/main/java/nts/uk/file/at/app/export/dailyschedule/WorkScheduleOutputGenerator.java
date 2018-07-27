package nts.uk.file.at.app.export.dailyschedule;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.task.data.TaskDataSetter;

/**
 * The Interface WorkScheduleOutputGenerator.
 * @author HoangNDH
 */
public interface WorkScheduleOutputGenerator {
	
	/**
	 * Generate.
	 *
	 * @param fileContext the file context
	 * @param reportData the report data
	 */
	void generate (FileGeneratorContext generatorContext, TaskDataSetter setter, WorkScheduleOutputQuery query);
}
