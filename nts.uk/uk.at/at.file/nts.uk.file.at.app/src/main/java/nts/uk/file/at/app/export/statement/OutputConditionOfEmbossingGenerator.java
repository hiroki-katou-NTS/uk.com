/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.statement;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * The Interface OutputConditionOfEmbossingGenerator.
 */
public interface OutputConditionOfEmbossingGenerator {
	
	/**
	 * Generate.
	 *
	 * @param fileGeneratorContext the file generator context
	 * @param query the query
	 */
	void generate (FileGeneratorContext fileGeneratorContext, OutputConditionOfEmbossingQuery query);
}
