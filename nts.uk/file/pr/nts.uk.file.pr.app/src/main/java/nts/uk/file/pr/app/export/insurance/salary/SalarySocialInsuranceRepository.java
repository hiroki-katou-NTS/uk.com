/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;

/**
 * The Interface SalarySocailInsuranceRepository.
 *
 * @author duongnd
 */
public interface SalarySocialInsuranceRepository {

    /**
     * Find report data.
     *
     * @param query
     *            the query
     * @return the salary social insurance report data
     */
    SalarySocialInsuranceReportData findReportData(SalarySocialInsuranceQuery query);
}
