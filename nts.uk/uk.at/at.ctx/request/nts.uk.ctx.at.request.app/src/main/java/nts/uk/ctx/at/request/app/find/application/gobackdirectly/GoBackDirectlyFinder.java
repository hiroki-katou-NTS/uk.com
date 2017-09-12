package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.app.find.setting.applicationformreason.ApplicationFormReasonDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.setting.applicationformreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectBasicData;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectCommonService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyFinder {
	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;
	@Inject
	private GoBackDirectCommonService goBackCommon;

	public GoBackDirectlyDto getGoBackDirectlyByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		return goBackDirectRepo.findByApplicationID(companyID, appID).map(c -> convertToDto(c)).orElse(null);

	}

	public GoBackDirectSettingDto getGoBackDirectSettingBySID(String SID) {
		return convertSettingToDto(goBackCommon.getSettingData(SID));
	}

	// Convert to Dto
	public GoBackDirectlyDto convertToDto(GoBackDirectly domain) {
		return new GoBackDirectlyDto(domain.getCompanyID(), domain.getAppID(), domain.getWorkTypeCD().v(),
				domain.getSiftCd().v(), domain.getWorkChangeAtr().value, domain.getGoWorkAtr1().value,
				domain.getBackHomeAtr1().value, domain.getWorkTimeStart1().v(), domain.getWorkTimeEnd1().v(),
				domain.getWorkLocationCD1(), domain.getGoWorkAtr2().value, domain.getBackHomeAtr2().value,
				domain.getWorkTimeStart2().v(), domain.getWorkTimeEnd2().v(), domain.getWorkLocationCD2());
	}

	// Convert Data Setting to DTO
	public GoBackDirectSettingDto convertSettingToDto(GoBackDirectBasicData domain) {
		return new GoBackDirectSettingDto(
				domain.getGoBackDirectSet().map(c -> convertGoBackDirectlyCommonSettingDto(c)).get(),
				domain.getEmployeeName(),
				domain.getListAppReason().stream().map(x -> convertReasonDto(x)).collect(Collectors.toList()));
	}

	public ApplicationFormReasonDto convertReasonDto(ApplicationReason domain) {
		return new ApplicationFormReasonDto(domain.getCompanyId(), domain.getAppType().value, domain.getDisplayOrder(),
				domain.getDefaultFlg().value);
	}

	public GoBackDirectlyCommonSettingDto convertGoBackDirectlyCommonSettingDto(GoBackDirectlyCommonSetting domain) {
		return new GoBackDirectlyCommonSettingDto(
				domain.getCompanyID(),
				domain.getWorkChangeFlg().value,
				domain.getWorkChangeTimeAtr().value,
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
