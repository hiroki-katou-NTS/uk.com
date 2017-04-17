/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import java.util.List;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
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
     * @param companyCode the company code
     * @param loginPersonId the login person id
     * @param salaryQuery the salary query
     * @param healInsuAvgearns the heal insu avgearns
     * @param pensionAvgearns the pension avgearns
     * @return the list
     */
    List<SalarySocialInsuranceReportData> findReportData(String companyCode, String loginPersonId,
            SalarySocialInsuranceQuery salaryQuery, List<HealthInsuranceAvgearn> healInsuAvgearns,
            List<PensionAvgearn> pensionAvgearns);
}
