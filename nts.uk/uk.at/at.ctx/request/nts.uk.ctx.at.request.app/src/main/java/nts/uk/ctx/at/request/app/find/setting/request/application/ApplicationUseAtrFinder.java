package nts.uk.ctx.at.request.app.find.setting.request.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
//HOATT - CMM018_2
@Stateless
public class ApplicationUseAtrFinder {

	@Inject
	private RequestOfEachWorkplaceRepository repoRequestWkp;
	@Inject
	private RequestOfEachCompanyRepository repoRequestCom;
	
	public List<AppUseAtrDto> getAppUseAtr(){
		return null;
	}
}
