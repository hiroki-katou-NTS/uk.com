/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.monthlyschedule;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * The Interface MonthlyWorkScheduleGenerator.
 */
public interface MonthlyWorkScheduleGenerator {

	/**
	 * Generate.
	 *
	 * @param generatorContext the generator context
	 * @param query the query
	 */
	void generate (FileGeneratorContext generatorContext, MonthlyWorkScheduleQuery query);
}
