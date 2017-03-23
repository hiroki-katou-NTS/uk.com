/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance.salary;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceQuery;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceRepository;

/**
 * @author duongnd
 *
 */

@Stateless
public class JpaSalarySocialInsuranceRepository implements SalarySocialInsuranceRepository {

    /* (non-Javadoc)
     * @see nts.uk.file.pr.app.export.insurance.salary.SalarySocailInsuranceRepository#fincReportData(nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceQuery)
     */
    @Override
    public SalarySocialInsuranceReportData findReportData(SalarySocialInsuranceQuery query) {
        // TODO Auto-generated method stub
        return null;
    }
}
