package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
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

	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;

	@Inject
	private ApprovalRootAdapter rootAdapter;

	String companyID, employeeID;

	GeneralDate baseDate;

	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
	final static String DATE_FORMAT = "yyyy/MM/dd";

	public HolidayShipmentDto startPage(String sid, GeneralDate baseDate, int uiType) {
		companyID = AppContexts.user().companyId();
		employeeID = AppContexts.user().employeeId();

		HolidayShipmentDto output = aFinder.commonProcessBeforeStart(appType, companyID, employeeID, baseDate);
		// アルゴリズム「事前事後区分の判断」を実行する
		output.setPreOrPostType(
				otherCommonAlgorithm.judgmentPrePostAtr(appType, baseDate, uiType == 0 ? true : false).value);

		// アルゴリズム「振休振出申請設定の取得」を実行する
		Optional<WithDrawalReqSet> withDrawalReqSetOpt = withDrawRepo.getWithDrawalReqSet();
		if (withDrawalReqSetOpt.isPresent()) {
			output.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSetOpt.get()));
		}

		// アルゴリズム「振休振出申請定型理由の取得」を実行する

		output.setAppReasons(appResonRepo.getReasonByCompanyId(companyID).stream()
				.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList()));

		return output;
	}

	public HolidayShipmentDto changeAppDate(String absDateInput) {

		GeneralDate recDate = null;

		String companyID = AppContexts.user().companyId();

		String employeeID = AppContexts.user().employeeId();

		GeneralDate absDate = GeneralDate.fromString(absDateInput, DATE_FORMAT);

		HolidayShipmentDto output = aFinder.commonProcessBeforeStart(appType, companyID, employeeID, baseDate);
		// アルゴリズム「基準申請日の決定」を実行する
		GeneralDate baseDate = aFinder.DetRefDate(recDate, absDate);
		int rootAtr = EmploymentRootAtr.APPLICATION.value;
		AppCommonSettingOutput appCommonSet = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID, rootAtr, ApplicationType.BREAK_TIME_APPLICATION, baseDate);

		ApplicationSetting appSet = appCommonSet.applicationSetting;

		if (appSet.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)) {
			// アルゴリズム「社員の対象申請の承認ルートを取得する」を実行する
			List<ApprovalRootImport> approvalRoots = rootAdapter.getApprovalRootOfSubjectRequest(companyID, employeeID,
					rootAtr, appType.value, baseDate);

			// アルゴリズム「基準日別設定の取得」を実行する
			aFinder.getDateSpecificSetting(companyID, employeeID, baseDate, true, null, null, null, null, appCommonSet,
					output);

		}
		// アルゴリズム「事前事後区分の最新化」を実行する
		output.setPreOrPostType(otherCommonAlgorithm.judgmentPrePostAtr(appType, baseDate, true).value);

		return output;

	}

}
