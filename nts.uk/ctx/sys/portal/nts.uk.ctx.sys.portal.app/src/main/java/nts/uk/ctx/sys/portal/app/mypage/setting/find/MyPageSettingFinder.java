package nts.uk.ctx.sys.portal.app.mypage.setting.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;


@Stateless
public class MyPageSettingFinder {

	@Inject
	MyPageSettingRepository myPageSettingRepository;

	/**
	 * Find by company id.
	 *
	 * @param CompanyId the company id
	 * @return the my page setting dto
	 */
	public MyPageSettingDto findByCompanyId(String CompanyId) {
		Optional<MyPageSetting> myPageSetting = myPageSettingRepository.findByCompanyId(CompanyId);
		// convert toppage domain to dto
		if (myPageSetting.isPresent()) {
			MyPageSetting mps = myPageSetting.get();
			return MyPageSettingDto.fromDomain(mps);
		}
		return null;
	}
}
