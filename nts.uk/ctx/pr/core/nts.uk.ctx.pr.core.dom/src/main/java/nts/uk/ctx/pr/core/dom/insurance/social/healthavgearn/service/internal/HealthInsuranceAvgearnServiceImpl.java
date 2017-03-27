package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.service.internal;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.service.HealthInsuranceAvgearnService;

@Stateless
public class HealthInsuranceAvgearnServiceImpl implements HealthInsuranceAvgearnService {

	@Override
	public void validateRequiredItem(HealthInsuranceAvgearn healthInsuranceAvgearn) {
		// TODO check null then throw new BusinessException("ER001");

	}

}
