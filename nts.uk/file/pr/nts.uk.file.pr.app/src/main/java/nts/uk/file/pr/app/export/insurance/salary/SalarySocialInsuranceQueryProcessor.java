/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

/**
 * @author duongnd
 *
 */
public class SalarySocialInsuranceQueryProcessor {
    
    /** The social insurance office repository. */
    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepo;

    private void findOffices(String officeCode) {
        // TODO: find list information of office.
    }
}
