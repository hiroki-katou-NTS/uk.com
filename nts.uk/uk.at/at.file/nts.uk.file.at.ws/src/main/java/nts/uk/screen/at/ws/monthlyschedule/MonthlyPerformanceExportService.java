/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.ws.monthlyschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleGenerator;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleQuery;

/**
 * The Class MonthlyPerformanceExportService.
 */
@Stateless
public class MonthlyPerformanceExportService extends ExportService<MonthlyWorkScheduleQuery> {

	/** The generator. */
	@Inject
	MonthlyWorkScheduleGenerator generator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<MonthlyWorkScheduleQuery> context) {
		TaskDataSetter setter = context.getDataSetter();
		this.generator.generate(context.getGeneratorContext(), setter, context.getQuery());
	}
}
