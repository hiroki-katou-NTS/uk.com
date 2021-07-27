package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.ArrayList;
import java.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOvertimeDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppStampDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.CompLeaveAppDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationContentServiceImpl implements IApplicationContentService {
	
	@Inject
	private AppContentDetailCMM045 appContentDetailCMM045;
	
	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepository;
	
	@Override
	public String getApplicationContent(Application app) {
		String companyID = AppContexts.user().companyId();
		ApprovalListDisplaySetting approvalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID).get();
		String content = "";
		switch (app.getAppType()) {
		case COMPLEMENT_LEAVE_APPLICATION:
			// 振休振出申請データを作成( Tạo data application nghỉ bù làm bù)
			CompLeaveAppDataOutput compLeaveAppDataOutput = appContentDetailCMM045.getContentComplementLeave(
					app, 
					companyID, 
					new ArrayList<>(), 
					DisplayAtr.DISPLAY, 
					ScreenAtr.KDL030);
			content = compLeaveAppDataOutput.getContent();
			break;
		case ABSENCE_APPLICATION:
			// 申請一覧リスト取得休暇 (Ngày nghỉ lấy  Application list)
			content = appContentDetailCMM045.getContentApplyForLeave(
					app, 
					companyID, 
					new ArrayList<>(), 
					DisplayAtr.DISPLAY,
					ScreenAtr.KDL030);
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 直行直帰申請データを作成 ( Tạo dữ liệu đơn xin đi làm, về nhà thẳng)
			content = appContentDetailCMM045.getContentGoBack(
					app, 
					DisplayAtr.DISPLAY, 
					new ArrayList<>(), 
					new ArrayList<>(), 
					ScreenAtr.KDL030);
			break;
		case WORK_CHANGE_APPLICATION:
			// 勤務変更申請データを作成
			content = appContentDetailCMM045.getContentWorkChange(
					app, 
					DisplayAtr.DISPLAY, 
					new ArrayList<>(), 
					new ArrayList<>(), 
					companyID);
			break;
		case OVER_TIME_APPLICATION: 
			// 残業申請データを作成
			AppOvertimeDataOutput appOvertimeDataOutput = appContentDetailCMM045.createOvertimeContent(
					app, 
					new ArrayList<>(), 
					new ArrayList<>(), 
					new ArrayList<>(), 
					ApplicationListAtr.APPLICATION, 
					approvalListDisplaySetting, 
					companyID, 
					Collections.emptyMap(),
					ScreenAtr.KDL030);
			content = appOvertimeDataOutput.getAppContent();
			break;
		case HOLIDAY_WORK_APPLICATION:
			// 休出時間申請データを作成
			AppHolidayWorkDataOutput appHolidayWorkDataOutput = appContentDetailCMM045.createHolidayWorkContent(
					app, 
					new ArrayList<>(), 
					new ArrayList<>(), 
					new ArrayList<>(), 
					ApplicationListAtr.APPLICATION, 
					approvalListDisplaySetting, 
					companyID,
					Collections.emptyMap(),
					ScreenAtr.KDL030);
			content = appHolidayWorkDataOutput.getAppContent();
			break;
		case BUSINESS_TRIP_APPLICATION:
			// 出張申請データを作成
			content = appContentDetailCMM045.createBusinessTripData(
					app, 
					DisplayAtr.DISPLAY, 
					ScreenAtr.KDL030, 
					companyID);
			break;
		case OPTIONAL_ITEM_APPLICATION:
			// 任意申請データを作成
			content = appContentDetailCMM045.createOptionalItemApp(
					app, 
					DisplayAtr.DISPLAY, 
					ScreenAtr.KDL030, 
					companyID);
			break;
		case STAMP_APPLICATION:
			// 打刻申請データを作成
			AppStampDataOutput appStampDataOutput = appContentDetailCMM045.createAppStampData(
					app, 
					DisplayAtr.DISPLAY, 
					ScreenAtr.KDL030, 
					companyID, 
					null);
			content = appStampDataOutput.getAppContent();
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			// 時間休暇申請データを作成
			content = appContentDetailCMM045.createAnnualHolidayData(
					app, 
					approvalListDisplaySetting.getAppReasonDisAtr(), 
					ScreenAtr.KDL030, 
					companyID);
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 遅刻早退取消申請データを作成
			content = appContentDetailCMM045.createArrivedLateLeaveEarlyData(
					app, 
					DisplayAtr.DISPLAY, 
					ScreenAtr.KDL030, 
					companyID);
			break;
		default:
			break;
		}
		
		return content;
	}

}
