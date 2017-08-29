package nts.uk.ctx.at.request.dom.application.common.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 9-1.詳細画面否認前の処理
 * @author tutk
 *
 */
@Stateless
public class DetailedScreenProcessBeforeDenialDefault implements DetailedScreenProcessBeforeDenialService {
//	@Inject
//	private ProcessBeforeDetailScreenRegistrationService repo; 

	@Override
	public void detailedScreenProcessBeforeDenial() {
		//repo.exclusiveCheck();
		
	}
}
