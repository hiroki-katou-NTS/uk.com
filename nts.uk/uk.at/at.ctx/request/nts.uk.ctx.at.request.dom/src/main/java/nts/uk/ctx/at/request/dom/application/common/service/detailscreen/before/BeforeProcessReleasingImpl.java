package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 10-1.詳細画面解除前の処理
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
