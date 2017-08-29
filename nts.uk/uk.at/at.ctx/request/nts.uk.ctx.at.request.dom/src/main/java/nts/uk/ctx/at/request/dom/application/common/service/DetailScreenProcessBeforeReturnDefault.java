package nts.uk.ctx.at.request.dom.application.common.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;

/**
 * 11-1.詳細画面差し戻し前の処理
 * @author tutk
 *
 */
@Stateless
public class DetailScreenProcessBeforeReturnDefault implements DetailScreenProcessBeforeReturnService {
	
//	@Inject
//	private ProcessBeforeDetailScreenRegistrationService repo;

	@Override
	public boolean detailScreenProcessBeforeReturn() {
		//repo.exclusiveCheck();
		boolean check = true;
		
		if(!check)
		{
			throw new BusinessException("Msg_197");
			//close KDL034
			//load lại màn hình
		}		
		return check;
	}
}
