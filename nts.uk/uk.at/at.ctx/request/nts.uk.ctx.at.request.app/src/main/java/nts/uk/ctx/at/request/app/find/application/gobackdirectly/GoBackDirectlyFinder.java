package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSetService;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectCommonService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyFinder {
	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;
	@Inject
	private GoBackDirectCommonService goBackCommon;
	@Inject
	private GoBackDirectAppSetService goBackAppSet;

	/**
	 * Get GoBackDirectlyDto
	 * 
	 * @param appID
	 * @return
	 */
	public GoBackDirectlyDto getGoBackDirectlyByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		return goBackDirectRepo.findByApplicationID(companyID, appID)
				.map(c -> GoBackDirectlyDto.convertToDto(c))
				.orElse(null);
	}
	

	/**
	 * convert to GoBackDirectSettingDto
	 * 
	 * @param SID
	 * @return
	 */
	public GoBackDirectSettingDto getGoBackDirectCommonSetting(String SID) {
		String companyID = AppContexts.user().companyId();
		return GoBackDirectSettingDto.convertToDto(goBackCommon.getSettingData(companyID, SID));
	}

	/**
	 * get Detail Data to
	 * 
	 * @param appID
	 * @return
	 */
	public GoBackDirectDetailDto getGoBackDirectDetailByAppId(String appID) {
		return GoBackDirectDetailDto.convertToDto(
				goBackAppSet.getGoBackDirectAppSet(appID)
			);
	}

	
}
