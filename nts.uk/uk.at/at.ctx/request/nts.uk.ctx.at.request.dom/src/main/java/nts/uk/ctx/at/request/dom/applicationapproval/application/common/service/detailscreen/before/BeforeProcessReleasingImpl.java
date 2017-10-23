package nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class BeforeProcessReleasingImpl implements BeforeProcessReleasing {
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegisterRepo;

	@Override
	public void detailScreenProcessBeforeReleasing() {
		detailBeforeProcessRegisterRepo.exclusiveCheck();
		
	}
}
