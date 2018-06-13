package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HolidayShipmentScreenCFinder {

	@Inject
	private HolidayShipmentScreenAFinder aFinder;

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Inject
	private WithDrawalReqSetRepository withDrawRepo;

	@Inject
	private ApplicationReasonRepository appResonRepo;

	String companyID, employeeID;

	GeneralDate baseDate;

	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
	final static String DATE_FORMAT = "yyyy/MM/dd";

	public HolidayShipmentDto startPage(String sid, GeneralDate baseDate, int uiType) {
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();
		AppCommonSettingOutput appSet = aFinder.getAppCommonSet(companyID, employeeID, baseDate);
		// アルゴリズム「起動前共通処理（新規）」を実行する
		HolidayShipmentDto output = aFinder.commonProcessBeforeStart(appType, companyID, employeeID, baseDate,appSet);
		// アルゴリズム「事前事後区分の判断」を実行する
		output.setPreOrPostType(
				otherCommonAlgorithm.judgmentPrePostAtr(appType, baseDate, uiType == 0 ? true : false).value);

		// アルゴリズム「振休振出申請設定の取得」を実行する
		Optional<WithDrawalReqSet> withDrawalReqSetOpt = withDrawRepo.getWithDrawalReqSet();
		if (withDrawalReqSetOpt.isPresent()) {
			output.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSetOpt.get()));
		}

		// アルゴリズム「振休振出申請定型理由の取得」を実行する
		output.setAppReasonComboItems(appResonRepo.getReasonByAppType(companyID, appType.value).stream()
				.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList()));

		return output;
	}

}
