/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.denominationtable.data.DenominationTableDataSource;
import nts.uk.file.pr.app.export.denominationtable.query.DenominationTableReportQuery;

/**
 * The Interface SalaryChartReportGenerator.
 */
public interface DenominationTblReportGenerator {

	/**
	 * Generate.
	 *
	 * @param generatorContext the generator context
	 * @param dataSource the data source
	 * @param query the query
	 */
	void generate(FileGeneratorContext generatorContext, 
			DenominationTableDataSource dataSource, DenominationTableReportQuery query);
}
