package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BfReqSetFinder {
	@Inject
	private RequestSettingRepository reqRep;
	
	public List<BfReqSetDto> findByCom(){
		String companyId = AppContexts.user().companyId();
		Optional<RequestSetting> req = reqRep.findByCompany(companyId);
		if(req.isPresent()){
			return BfReqSetDto.convertToDto(req.get());
		}
		return null;
	}
}
