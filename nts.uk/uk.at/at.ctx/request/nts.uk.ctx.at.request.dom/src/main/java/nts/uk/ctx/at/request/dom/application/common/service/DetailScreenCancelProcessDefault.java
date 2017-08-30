package nts.uk.ctx.at.request.dom.application.common.service;

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
public class DetailScreenCancelProcessDefault implements DetailScreenCancelProcessService {

	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public void detailScreenCancelProcess(String companyID, String appID) {
		Optional<Application> app = appRepo.getAppById(companyID, appID);
		if(app.isPresent()) {
			appRepo.updateById(companyID,appID );
		}else {
			throw new BusinessException("K ton tai");
		}
	}

}
