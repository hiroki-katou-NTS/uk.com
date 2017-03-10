/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance;

import java.util.List;

import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceReportData;

/**
 * @author duongnd
 *
 */
public interface SocialInsuranceRepository {

    SocialInsuranceReportData findByCode(SocialInsuranceQuery query);
}
