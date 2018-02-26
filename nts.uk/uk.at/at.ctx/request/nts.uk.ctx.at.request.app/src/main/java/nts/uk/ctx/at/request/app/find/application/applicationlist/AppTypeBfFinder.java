package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * befor and after request setting finder
 * @author yennth
 *
 */
@Stateless
public class AppTypeBfFinder {
	@Inject
	private RequestSettingRepository reqRep;
	
	public AppTypeBfDto findByCom(){
		String companyId = AppContexts.user().companyId();
		Optional<RequestSetting> req = reqRep.findByCompany(companyId);
		if(req.isPresent()){
			return AppTypeBfDto.convertToDto(req.get());
		}
		return null;
	}
}
