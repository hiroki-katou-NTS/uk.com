/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.salarytable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.salarytable.data.SalaryTableDataSource;
import nts.uk.file.pr.app.export.salarytable.query.SalaryTableReportQuery;

/**
 * The Interface SalaryChartReportGenerator.
 */
public interface SalaryTableReportGenerator {

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
	void generate(FileGeneratorContext generatorContext, SalaryTableDataSource dataSource, SalaryTableReportQuery query);	
}
