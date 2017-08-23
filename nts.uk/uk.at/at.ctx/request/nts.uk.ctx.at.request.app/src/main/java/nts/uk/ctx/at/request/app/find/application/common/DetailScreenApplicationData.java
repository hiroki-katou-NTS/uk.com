package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 *  15.詳細画面申請データを取得する
 * @author tutk
 *
 */
@Stateless
public class DetailScreenApplicationData {
	
	@Inject
	private ApplicationRepository appRepo;
	
	/**
	 * 15.詳細画面申請データを取得する
	 * @param applicationID
	 * @return 申請データの内容
	 */
	public Optional<ApplicationDto> getAppById(String applicationID){
		String companyID = AppContexts.user().companyId();
		Optional<ApplicationDto> app = appRepo.getAppById(companyID, applicationID)
				.map(c->ApplicationDto.fromDomain(c));
		
		if(!app.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		return app;
	}
}
