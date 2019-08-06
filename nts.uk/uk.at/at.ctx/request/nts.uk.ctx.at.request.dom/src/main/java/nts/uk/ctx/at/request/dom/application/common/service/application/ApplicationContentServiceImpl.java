package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkPlaceHistBySIDImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	public String getApplicationContent(Application_New app) {
		String appReason = app.getAppReason().toString();
		String appID = app.getAppID();
		String companyID = AppContexts.user().companyId();
		//EA3478　#107902
		//hoatt 2019.05.28
		//アルゴリズム「申請理由出力_共通」を実行する
		Integer appReasonDisAtr = otherComAlg.appReasonOutFlg(app, Optional.empty()) ? 1 : 0;
		GeneralDate appDate = app.getAppDate();
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
			if(app.getStartDate().isPresent()&& app.getEndDate().isPresent()){
				day = app.getStartDate().get().daysTo(app.getEndDate().get()) + 1;
			}
			return contentDtail.getContentAbsence(null, companyID, appID, appReasonDisAtr, appReason, day, ScreenAtr.KDL030.value, Collections.emptyList(), Collections.emptyList());
		}
		case WORK_CHANGE_APPLICATION: {
			/** 勤務変更申請*/
			return contentDtail.getContentWorkChange(null, companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value, Collections.emptyList(), Collections.emptyList());
		}
		case BUSINESS_TRIP_APPLICATION: {
			/** 出張申請*/
			// TODO
			return this.getBusinessTripContent(app, companyID, appID, appReason);
		}
		case GO_RETURN_DIRECTLY_APPLICATION: {
			/** 直行直帰申請*/
			return contentDtail.getContentGoBack(null, companyID, appID, appReasonDisAtr, appReason, ScreenAtr.KDL030.value);
		}
		case BREAK_TIME_APPLICATION: {
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
		case STAMP_NR_APPLICATION: {
			/** 打刻申請（NR形式）*/
			// TODO
			return this.getStampNrAppContent(app, companyID, appID, appReason);
		}
		case LONG_BUSINESS_TRIP_APPLICATION: {
			/** 連続出張申請*/
			// TODO
			return this.getLongBusinessTripAppContent(app, companyID, appID, appReason);
		}
		case BUSINESS_TRIP_APPLICATION_OFFICE_HELPER: {
			/** 出張申請オフィスヘルパー*/
			// TODO
			return this.getBusinessTripOfficeAppContent(app, companyID, appID, appReason);
		}
		case APPLICATION_36: {
			/** ３６協定時間申請*/
			// TODO
			return this.getApp36AppContent(app, companyID, appID, appReason);
		}
		}
		return "";
	}

	private String getBusinessTripContent(Application_New app, String companyID, String appID, String appReason) {
		// TODO
		String content = I18NText.getText("CMM045_254") + I18NText.getText("CMM045_255");
		return content + "\n" + appReason;
	}

	private String getAnnualAppContent(Application_New app, String companyID, String appID, String appReason) {
		// TODO
		String content = I18NText.getText("CMM045_264") + "\n" + appReason;
		return content;
	}

	private String getStampNrAppContent(Application_New app, String companyID, String appID, String appReason) {
		// TODO
		return appReason;
	}

	private String getLongBusinessTripAppContent(Application_New app, String companyID, String appID,
			String appReason) {
		// TODO
		return appReason;
	}

	private String getBusinessTripOfficeAppContent(Application_New app, String companyID, String appID,
			String appReason) {
		// TODO
		return appReason;
	}

	private String getApp36AppContent(Application_New app, String companyID, String appID, String appReason) {
		// TODO
		return appReason;
	}

}
