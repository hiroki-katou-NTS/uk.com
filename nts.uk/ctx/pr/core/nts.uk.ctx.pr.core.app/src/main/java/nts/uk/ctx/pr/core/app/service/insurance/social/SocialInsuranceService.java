/**
 * 
 */
package nts.uk.ctx.pr.core.app.service.insurance.social;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;

public interface SocialInsuranceService  {
    /**
     * @param socialInsuranceOffice
     */
    void add(SocialInsuranceOffice socialInsuranceOffice);
    
    /**
     * @param socialInsuranceOffice
     */
    void update(SocialInsuranceOffice socialInsuranceOffice);

    /**
     * @param socialInsuranceOffice
     */
    void remove(SocialInsuranceOffice socialInsuranceOffice);
}
