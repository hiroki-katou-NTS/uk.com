/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.insurance.SocialInsuranceQuery;
import nts.uk.file.pr.app.export.insurance.SocialInsuranceRepository;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceReportData;

/**
 * @author duongnd
 *
 */

@Stateless
public class JpaSocialInsuranceRepository implements SocialInsuranceRepository {
    
    private static final String a = "";
    
    @Override
    public SocialInsuranceReportData findByCode(SocialInsuranceQuery query) {
        return null;
    }

}
