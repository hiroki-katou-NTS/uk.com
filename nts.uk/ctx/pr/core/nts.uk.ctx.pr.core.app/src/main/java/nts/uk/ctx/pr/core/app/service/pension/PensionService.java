package nts.uk.ctx.pr.core.app.service.pension;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;

public interface PensionService {
	/**
     * @param pensionRate
     */
    void add(PensionRate  pensionRate);
    
    /**
     * @param pensionRate
     */
    void update(PensionRate  pensionRate);
}
