package nts.uk.ctx.at.request.app.find.request.gobackdirectlycommon;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyCommonSettingFinder {
	
	@Inject 
	private GoBackDirectlyCommonSettingRepository goBackRepo;
	
	
	/**
	 * @param appID
	 * @return
	 */
	public GoBackDirectlyCommonSettingDto findGoBackDirectlyCommonSettingbyAppID(String appID){
		String companyID = AppContexts.user().companyId();
		return this.goBackRepo.findByAppID(companyID, appID)
				.map(x-> convertToDTo(x))
				.orElse(null);
	}
	
	/**
	 * @param domain
	 * @return
	 */
	private GoBackDirectlyCommonSettingDto convertToDTo(GoBackDirectlyCommonSetting domain) {
		return new GoBackDirectlyCommonSettingDto(
				domain.getCompanyID(), 
				domain.getAppID(), 
				domain.getWorkChangeFlg().value,
				domain.getWorkChangeFlg().value,
				domain.getPerformanceDisplayAtr().value,
				domain.getContraditionCheckAtr().value,
				domain.getGoBackWorkType().value,
				domain.getLateLeaveEarlySettingAtr().value,
				domain.getCommentContent1().v(),
				domain.getCommentFontWeight1().value,
				domain.getCommentFontColor1().v(),
				domain.getCommentContent2().v(),
				domain.getCommentFontWeight2().value,
				domain.getCommentFontColor2().v());
	}

}
