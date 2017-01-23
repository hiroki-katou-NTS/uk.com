package nts.uk.ctx.pr.core.app.service.healthinsurance;

import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;

public interface HealthInsuranceService {
	  /**
     * @param healthInsuranceRate
     */
    void add(HealthInsuranceRate healthInsuranceRate);
    
    /**
     * @param healthInsuranceRate
     */
    void update(HealthInsuranceRate healthInsuranceRate);
}
