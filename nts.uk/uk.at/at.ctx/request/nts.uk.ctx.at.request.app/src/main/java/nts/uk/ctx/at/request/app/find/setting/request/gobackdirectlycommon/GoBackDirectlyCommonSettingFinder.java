package nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon;

/*import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectCommonDefault;*/
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

//import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectCommonService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyCommonSettingFinder {
	
//	@Inject
//	private GoBackDirectlyCommonSettingRepository goBackRepo;
	
	
//	@Inject 
//	private GoBackDirectCommonService dataSetting;
	
	
	/**
	 * @param appID
	 * @return
	 */
	public GoBackDirectlyCommonSettingDto findGoBackDirectlyCommonSettingbyAppID(){
		String companyID = AppContexts.user().companyId();
//		return this.goBackRepo.findByCompanyID(companyID)
//				.map(x-> convertToDto(x))
//				.orElse(null);
		return null;
		//dataSetting.getSettingData()
	}
	
	/**
	 * @param domain
	 * @return
	 */
//	private GoBackDirectlyCommonSettingDto convertToDto(GoBackDirectlyCommonSetting domain) {
//		return new GoBackDirectlyCommonSettingDto(
//				domain.getCompanyID(),
//				domain.getWorkChangeFlg().value,
//				domain.getWorkChangeTimeAtr().value,
//				domain.getPerformanceDisplayAtr().value,
//				domain.getContraditionCheckAtr().value,
//				domain.getGoBackWorkType().value,
//				domain.getLateLeaveEarlySettingAtr().value,
//				domain.getCommentContent1().v(),
//				domain.getCommentFontWeight1().value,
//				domain.getCommentFontColor1().v(),
//				domain.getCommentContent2().v(),
//				domain.getCommentFontWeight2().value,
//				domain.getCommentFontColor2().v());
//	}

}
