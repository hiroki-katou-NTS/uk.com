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

/**
 * The Interface TopPageSettingsSerivce.
 * DomainService トップページ設定を取得する
 */
@Stateless
public class TopPageSettingService {

	@Inject
	private TopPagePersonSettingRepository topPagePersonSettingRepo;
	
	@Inject
	private TopPageRoleSettingRepository topPageRoleSettingRepo;
	
	@Inject
	private LoginRoleSetCodeAdapter adapter;
	
	/**
	 * Gets the top page settings.
	 * 自分のトップページ設定を取得する
	 *
	 * @return the top page settings
	 */
	public Optional<TopPageSettings> getTopPageSettings(String companyId, String employeeId) {
		Optional<TopPagePersonSetting> topPagePersonSetting = this.topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(
				companyId, 
				employeeId);
		if (topPagePersonSetting.isPresent()) {
			return Optional.of(topPagePersonSetting.get().getTopPageSettings());
		}
		String roleSetCode = this.adapter.getLoginRoleSet().getRoleSetCd();
		Optional<TopPageRoleSetting> topPageRoleSetting = this.topPageRoleSettingRepo.getByCompanyIdAndRoleSetCode(
				companyId, roleSetCode);
		if (topPageRoleSetting.isPresent()) {
			return Optional.of(topPageRoleSetting.get().getTopPageSettings());
		}
		return Optional.empty();
	}
}
