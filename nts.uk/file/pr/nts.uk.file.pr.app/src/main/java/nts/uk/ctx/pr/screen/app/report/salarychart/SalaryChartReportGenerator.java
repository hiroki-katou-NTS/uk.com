/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.SalaryChartDataSource;
import nts.uk.ctx.pr.screen.app.report.salarychart.query.SalaryChartReportQuery;

/**
 * The Interface SalaryChartReportGenerator.
 */
public interface SalaryChartReportGenerator {

//	/**
//	 * Generate.
//	 *
//	 * @param generatorContext the generator context
//	 * @param dataSource the data source
//	 */
//	void generate(FileGeneratorContext generatorContext, SalaryChartDataSource dataSource);
	
	/**
	 * Generate.
	 *
	 * @param generatorContext the generator context
	 * @param dataSource the data source
	 * @param query the query
	 */
	void generate(FileGeneratorContext generatorContext, SalaryChartDataSource dataSource, SalaryChartReportQuery query);	
}
