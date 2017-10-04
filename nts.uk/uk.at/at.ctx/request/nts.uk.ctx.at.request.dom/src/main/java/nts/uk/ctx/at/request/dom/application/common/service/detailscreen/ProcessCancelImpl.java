package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;

/**
 * 12.詳細画面取消の処理
 * @author tutk
 *
 */
@Stateless
public class ProcessCancelImpl implements ProcessCancel {

	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public void detailScreenCancelProcess(String companyID, String appID) {
		Optional<Application> app = appRepo.getAppById(companyID, appID);
		if(app.isPresent()) {
			appRepo.updateById(companyID,appID );
			//throw new BusinessException("Msg_224");
		}else {
			throw new BusinessException("K ton tai");
		}
		
		
		
	}

}
