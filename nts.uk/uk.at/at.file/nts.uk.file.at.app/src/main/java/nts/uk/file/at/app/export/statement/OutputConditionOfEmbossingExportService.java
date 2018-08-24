/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.statement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * The Class OutputConditionOfEmbossingExportService.
 */
@Stateless
public class OutputConditionOfEmbossingExportService extends ExportService<OutputConditionOfEmbossingQuery>{

	@Inject
	OutputConditionOfEmbossingGenerator generator;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<OutputConditionOfEmbossingQuery> context) {
		OutputConditionOfEmbossingQuery query = context.getQuery();
		this.generator.generate(context.getGeneratorContext(), query);
	}

}
