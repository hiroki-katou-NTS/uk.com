package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GoBackDirectAppSetDefault implements GoBackDirectAppSetService {
	@Inject
	private GoBackDirectlyRepository goBackRepo;

	@Inject
	private ApplicationSettingRepository appSetRepo;

	@Override
	public GoBackDirectAppSet getGoBackDirectAppSet(String appID) {
		String companyID = AppContexts.user().companyId();
		GoBackDirectAppSet data = new GoBackDirectAppSet();
		GoBackDirectly goBackDirect = goBackRepo.findByApplicationID(companyID, appID).get();
		data.goBackDirectly = goBackDirect;
		int prePostAtr = appSetRepo.getApplicationSettingByComID(companyID).get().getDisplayPrePostFlg().value;
		data.prePostAtr = prePostAtr;
		return data;
	}
}
