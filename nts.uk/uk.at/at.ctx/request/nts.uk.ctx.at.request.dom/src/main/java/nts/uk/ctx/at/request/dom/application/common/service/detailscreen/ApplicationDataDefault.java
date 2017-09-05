package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 15.詳細画面申請データを取得する
 * @author tutk
 *
 */
@Stateless
public class ApplicationDataDefault implements ApplicationDataService {

	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public Optional<Application> detailScreenApplicationData(String appID) {
		String companyID = AppContexts.user().companyId();
		Optional<Application> app = appRepo.getAppById(companyID, appID);
		if(!app.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		return app;
	}

}
