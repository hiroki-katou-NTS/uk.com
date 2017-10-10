package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 9-1.詳細画面否認前の処理
 * @author tutk
 *
 */
@Stateless
public class BeforeProcessDenialImpl implements BeforeProcessDenial {
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegisterRepo;

	@Override
	public void detailedScreenProcessBeforeDenial() {
		detailBeforeProcessRegisterRepo.exclusiveCheck();
		
	}
}
