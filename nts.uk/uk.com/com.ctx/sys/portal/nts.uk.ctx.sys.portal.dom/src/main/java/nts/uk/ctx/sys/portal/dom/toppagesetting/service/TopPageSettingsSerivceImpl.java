package nts.uk.ctx.sys.portal.dom.toppagesetting.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.toppagesetting.LoginRoleSetCodeAdapter;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageRoleSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageRoleSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettings;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TopPageSettingsSerivceImpl implements TopPageSettingsSerivce {

	@Inject
	private TopPagePersonSettingRepository topPagePersonSettingRepo;
	
	@Inject
	private TopPageRoleSettingRepository topPageRoleSettingRepo;
	
	@Inject
	private LoginRoleSetCodeAdapter adapter;
	
	@Override
	public Optional<TopPageSettings> getTopPageSettings() {
		Optional<TopPagePersonSetting> topPagePersonSetting = this.topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(
				AppContexts.user().companyId(), 
				AppContexts.user().employeeId());
		if (topPagePersonSetting.isPresent()) {
			return Optional.of(topPagePersonSetting.get().getTopPageSettings());
		}
		String roleSetCode = this.adapter.getLoginRoleSet().getRoleSetCd();
		Optional<TopPageRoleSetting> topPageRoleSetting = this.topPageRoleSettingRepo.getByCompanyIdAndRoleSetCode(
				AppContexts.user().companyId(), roleSetCode);
		if (topPageRoleSetting.isPresent()) {
			return Optional.of(topPageRoleSetting.get().getTopPageSettings());
		}
		return Optional.empty();
	}

}
