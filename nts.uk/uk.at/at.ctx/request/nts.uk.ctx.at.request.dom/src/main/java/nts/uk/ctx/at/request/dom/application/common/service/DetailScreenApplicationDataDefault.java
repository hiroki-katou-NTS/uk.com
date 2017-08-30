package nts.uk.ctx.at.request.dom.application.common.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailScreenApplicationDataDefault implements DetailScreenApplicationDataService {

	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public Optional<Application> detailScreenApplicationDataService(String appID) {
		String companyID = AppContexts.user().companyId();
		Optional<Application> app = appRepo.getAppById(companyID, appID);
		if(!app.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		return app;
	}

}
