package nts.uk.ctx.at.request.dom.application.common.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 10-1.詳細画面解除前の処理
 * @author tutk
 *
 */
@Stateless
public class DetailScreenProcessBeforeReleasingDefault implements DetailScreenProcessBeforeReleasingService {
	@Inject
	private ProcessBeforeDetailScreenRegistrationService repo; 

	@Override
	public void detailScreenProcessBeforeReleasing() {
		repo.exclusiveCheck();
		
	}
}
