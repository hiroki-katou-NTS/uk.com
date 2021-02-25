package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkPlaceHistBySIDImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationContentServiceImpl implements IApplicationContentService {
	@Inject
	private AppContentDetailCMM045 contentDtail;
	@Inject
	private AppListInitialRepository appLstInitRepo;
	@Inject
	private WorkplaceAdapter wkpAdapter;
	@Inject
	private OtherCommonAlgorithm otherComAlg;
	
	@Override
	public String getApplicationContent(Application app) {
		String appReason = app.getOpAppReason().map(x -> x.v()).orElse(null);
		String appID = app.getAppID();
		String companyID = AppContexts.user().companyId();
		//EA3478　#107902
		//hoatt 2019.05.28
		//アルゴリズム「申請理由出力_共通」を実行する
		Integer appReasonDisAtr = otherComAlg.appReasonOutFlg(app, Optional.empty()) ? 1 : 0;
		GeneralDate appDate = app.getAppDate().getApplicationDate();
		switch (app.getAppType()) {
			case OVER_TIME_APPLICATION: {
				/** 残業申請*/
				WorkPlaceHistBySIDImport wkp = wkpAdapter.findWpkBySIDandPeriod(app.getEmployeeID(), new DatePeriod(appDate,appDate));
				String wkpId = wkp.getLstWkpInfo().isEmpty() ? "" : wkp.getLstWkpInfo().get(0).getWpkID();
				int detailSet = appLstInitRepo.detailSet(companyID, wkpId, app.getAppType().value, appDate);
				return contentDtail.getContentOverTimeBf(null, companyID, appID, detailSet, appReasonDisAtr, appReason, ScreenAtr.KDL030.value);
			}
			case ABSENCE_APPLICATION: {
				/** 休暇申請*/
				Integer day = 0;
				if(app.getOpAppStartDate().isPresent()&& app.getOpAppEndDate().isPresent()){
					day = app.getOpAppStartDate().get().getApplicationDate().daysTo(app.getOpAppEndDate().get().getApplicationDate()) + 1;
				}
				return contentDtail.getContentAbsence(null, companyID, appID, appReasonDisAtr, appReason, day, ScreenAtr.KDL030.value, Collections.emptyList(), Collections.emptyList());
			}
			case WORK_CHANGE_APPLICATION: {
				/** 勤務変更申請*/
//				return contentDtail.getContentWorkChange(null, companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value, Collections.emptyList(), Collections.emptyList());
				return "";
			}
			case BUSINESS_TRIP_APPLICATION: {
				/** 出張申請*/
				// TODO
				return this.getBusinessTripContent(app, companyID, appID, appReason);
			}
			case GO_RETURN_DIRECTLY_APPLICATION: {
				/** 直行直帰申請*/
//				return contentDtail.getContentGoBack(null, companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value);
				return "";
			}
			case HOLIDAY_WORK_APPLICATION: {
				/** 休出時間申請*/
				return contentDtail.getContentHdWorkBf(null, companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value, Collections.emptyList(), Collections.emptyList());
			}
			case STAMP_APPLICATION: {
				/** 打刻申請*/
				return contentDtail.getContentStamp(companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value);
			}
			case ANNUAL_HOLIDAY_APPLICATION: {
				/** 時間年休申請*/
				// TODO
				return this.getAnnualAppContent(app, companyID, appID, appReason);
			}
			case EARLY_LEAVE_CANCEL_APPLICATION: {
				/** 遅刻早退取消申請*/
				return contentDtail.getContentEarlyLeave(companyID, appID, appReasonDisAtr, appReason, app.getPrePostAtr().value, ScreenAtr.KAF018.value);
			}
			case COMPLEMENT_LEAVE_APPLICATION: {
				/** 振休振出申請*/
				return contentDtail.getContentComplt(null, companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value, Collections.emptyList());
			}
			default:
				break;
		}
		return "";
	}

	private String getBusinessTripContent(Application app, String companyID, String appID, String appReason) {
		// TODO
		String content = I18NText.getText("CMM045_254") + I18NText.getText("CMM045_255");
		return content + "\n" + appReason;
	}

	private String getAnnualAppContent(Application app, String companyID, String appID, String appReason) {
		// TODO
		String content = I18NText.getText("CMM045_264") + "\n" + appReason;
		return content;
	}

}
