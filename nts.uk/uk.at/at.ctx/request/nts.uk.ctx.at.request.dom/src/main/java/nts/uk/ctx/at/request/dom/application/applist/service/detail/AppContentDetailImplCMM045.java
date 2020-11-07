package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.util.Strings;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.AppPrePostGroup;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ArrivedLateLeaveEarlyItemContent;
import nts.uk.ctx.at.request.dom.application.applist.service.content.OptionalItemOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.datacreate.StampAppOutputTmp;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeDay;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository_Old;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp_Old;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeZoneApp;
import nts.uk.ctx.at.request.dom.application.stamp.StampFrameNo;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.stamp.StartEndClassification;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampApp;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppEnum;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppOther;
import nts.uk.ctx.at.request.dom.application.stamp.TimeZoneStampClassification;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationSetting;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * HOATT DOI UNG NOI DUNG DON XIN CMM045, KAF018, KDL030 GOP THANH 1 XU LY CHUNG
 * 2019.05.09 #107568
 * @author hoatt
 */
@Stateless
public class AppContentDetailImplCMM045 implements AppContentDetailCMM045 {

	@Inject
	private AppDetailInfoRepository appDetailInfoRepo;
	@Inject
	private AppStampRepository_Old appStampRepo;

	@Inject
	private ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;

	@Inject
	private AppContentService appContentService;

	@Inject
	private AppStampRepository appStampRepository;

	@Inject
	private AppRecordImageRepository appRecordImageRepository;

	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepository;

	@Inject
	private BusinessTripRepository businessTripRepository;

	@Inject
	private AppWorkChangeRepository appWorkChangeRepository;
	
	@Inject
	private OptionalItemApplicationRepository optionalItemApplicationRepository;
	
	@Inject
	private OptionalItemAppSetRepository optionalItemAppSetRepository;
	
	@Inject
	private OptionalItemRepository optionalItemRepository;

	private final static String KDL030 = "\n";
	private final static String CMM045 = "<br/>";

	/**
	 * get content OverTimeBf
	 * 残業申請 kaf005 - appType = 0
	 * ※申請モード、承認モード(事前)用レイアウト
	 * @param companyID
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentOverTimeBf(AppOverTimeInfoFull overTime, String companyId, String appId, int detailSet,
			Integer appReasonDisAtr, String appReason, int screenAtr) {
		if(overTime == null ){
			overTime = appDetailInfoRepo.getAppOverTimeInfo(companyId, appId);
		}
		String appC = this.content005(overTime.getWorkClockFrom1(), overTime.getWorkClockTo1(), overTime.getWorkClockFrom2(),
				overTime.getWorkClockFrom2(), overTime.getLstFrame(), detailSet);
		// No.417
		String timeNo417 = this.displayTimeNo417(overTime.getTimeNo417(), screenAtr);
		appC = Strings.isNotBlank(appC) ? Strings.isNotBlank(timeNo417) ? appC + "　" + timeNo417 : appC : timeNo417;
		return this.checkAddReason(appC, appReason, appReasonDisAtr, screenAtr);
	}

	private String content005(String workClockFrom1, String workClockTo1, String workClockFrom2, String workClockTo2,
			List<OverTimeFrame> lstFrame, int detailSet){
		String time1 = workClockFrom1 == "" ? "" : workClockFrom1 + I18NText.getText("CMM045_100") + workClockTo1;
		String time2 = workClockFrom2 == "" ? "" : workClockFrom2 + I18NText.getText("CMM045_100") + workClockTo2;
		String contentv4 = time1 + time2;
		String contentv42 = this.convertFrameTime(lstFrame);

		String appCt005 = detailSet == 1 && Strings.isNotBlank(contentv4) ? Strings.isNotBlank(contentv42) ?
				contentv4 + "　" + contentv42 : contentv4 : contentv42;
		return appCt005;
	}
	/**
	 * get content over timeAf
	 * 残業申請 kaf005 - appType = 0
	 * ※承認モード(事後)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentOverTimeAf(AppOverTimeInfoFull overTime, int detailSet, Integer appReasonDisAtr, String appReason, AppPrePostGroup subData) {
		String appContentPost = this.content005(overTime.getWorkClockFrom1(), overTime.getWorkClockTo1(), overTime.getWorkClockFrom2(),
				overTime.getWorkClockFrom2(), overTime.getLstFrame(), detailSet);
		String appContentPre = "";
		if(subData != null && subData.getAppPre() != null){
			AppOverTimeInfoFull appPre = subData.getAppPre();
			appContentPre = CMM045 + I18NText.getText("CMM045_273") + this.content005(appPre.getWorkClockFrom1(), appPre.getWorkClockTo1(),
					appPre.getWorkClockFrom2(), appPre.getWorkClockFrom2(), appPre.getLstFrame(), detailSet);
		}
		String appContentRes = "";
		if(subData != null){
			List<OverTimeFrame> lstFrameOv = subData.getTime();
			//add shiftNight
            lstFrameOv.add(new OverTimeFrame(1,11,I18NText.getText("CMM045_270"),0,subData.getShiftNightTime(), null, null));
            //add flex
            lstFrameOv.add(new OverTimeFrame(1,12,I18NText.getText("CMM045_271"),0,subData.getFlexTime(), null, null));
			appContentRes = CMM045 + I18NText.getText("CMM045_274") + this.content005(subData.getStrTime1(), subData.getEndTime1(),
					subData.getStrTime2(), subData.getEndTime2(), lstFrameOv, detailSet);
		}
		// No.417
		String timeNo417 = this.displayTimeNo417(overTime.getTimeNo417(), ScreenAtr.CMM045.value);
		String contentFull = I18NText.getText("CMM045_272") + appContentPost +
				appContentPre + appContentRes + timeNo417;
		return this.checkAddReason(contentFull, appReason, appReasonDisAtr, ScreenAtr.CMM045.value);
	}
	/**
	 * get content absence
	 * 休暇申請 kaf006 - appType = 1
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentAbsence(AppAbsenceFull absence, String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int day, int screenAtr, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime) {
        String appContent006 = "";
        if(absence == null){
        	absence = appDetailInfoRepo.getAppAbsenceInfo(companyId, appId, day, lstWkType, lstWkTime);
        }
        //ver46
        if(absence.getHolidayAppType() != 3){//休暇申請.休暇種類　≠ 特別休暇
            appContent006 = this.convertAbsenceNotSpecial(absence);
        }
        if(absence.getHolidayAppType() == 3){//休暇申請.休暇種類　＝ 特別休暇 ver39
            appContent006 = this.convertAbsenceSpecial(absence);
        }
		return this.checkAddReason(appContent006, appReason, appReasonDisAtr, screenAtr);
	}
	/**
	 * get content work change
	 * 勤務変更申請 kaf007 - appType = 2
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentWorkChange(Application application, DisplayAtr appReasonDisAtr, List<WorkTimeSetting> workTimeLst, List<WorkType> workTypeLst, String companyID) {
		// ドメインモデル「勤務変更申請」を取得
		AppWorkChange appWorkChange = appWorkChangeRepository.findbyID(companyID, application.getAppID()).get();
		// 勤務就業名称を作成
		String workTypeName = appDetailInfoRepo.findWorkTypeName(workTypeLst, appWorkChange.getOpWorkTypeCD().map(x -> x.v()).orElse(null));
		String workTimeName = appDetailInfoRepo.findWorkTimeName(workTimeLst, appWorkChange.getOpWorkTimeCD().map(x -> x.v()).orElse(null));
		// 申請内容　＝　申請内容（勤務変更申請、直行直帰申請）
		return appContentService.getWorkChangeGoBackContent(
				ApplicationType.WORK_CHANGE_APPLICATION,
				workTypeName,
				workTimeName,
				appWorkChange.getStraightGo(),
				appWorkChange.getTimeZoneWithWorkNoLst().stream().filter(x -> x.getWorkNo().v()==1).findAny().map(x -> x.getTimeZone().getStartTime()).orElse(null),
				appWorkChange.getStraightBack(),
				appWorkChange.getTimeZoneWithWorkNoLst().stream().filter(x -> x.getWorkNo().v()==1).findAny().map(x -> x.getTimeZone().getEndTime()).orElse(null),
				null,
				null,
				appReasonDisAtr,
				application.getOpAppReason().orElse(null),
				application);
//		if(wkChange == null){
//			//ドメインモデル「勤務変更申請」を取得
//			wkChange = appDetailInfoRepo.getAppWorkChangeInfo(companyId, appId, lstWkType, lstWkTime);
//		}
//		//申請内容　＝　申請内容（勤務変更申請、直行直帰申請）
//		String go1 = wkChange.getGoWorkAtr1() == 0 ? "" + I18NText.getText("CMM045_252") + wkChange.getWorkTimeStart1() :
//				wkChange.getWorkTimeStart1();
//		String back1 = wkChange.getBackHomeAtr1() == 0 ? I18NText.getText("CMM045_252") + wkChange.getWorkTimeEnd1() :
//				wkChange.getWorkTimeEnd1();
//		String time1 = go1 == "" ? "" : go1 + I18NText.getText("CMM045_100") + back1;
//		String go2 = (wkChange.getGoWorkAtr2() == null || wkChange.getGoWorkAtr2() == 1) ? wkChange.getWorkTimeStart2() :
//				"" + I18NText.getText("CMM045_252") + wkChange.getWorkTimeStart2();
//		String back2 = (wkChange.getBackHomeAtr2() == null || wkChange.getBackHomeAtr2() == 1) ? wkChange.getWorkTimeEnd2() :
//				I18NText.getText("CMM045_252") + wkChange.getWorkTimeEnd2();
//		String time2 = go2 == "" ? "" : go2 + I18NText.getText("CMM045_100") + back2;
//		String breakTime = wkChange.getBreakTimeStart1() == "" ? "" : I18NText.getText("CMM045_251") +
//				wkChange.getBreakTimeEnd1() + I18NText.getText("CMM045_100") + wkChange.getBreakTimeEnd1();
//		String workName = wkChange.getWorkTypeName() == "" ? wkChange.getWorkTimeName() : wkChange.getWorkTimeName() == "" ?
//				wkChange.getWorkTypeName() : wkChange.getWorkTypeName() + "　" + wkChange.getWorkTimeName();
//		String time = time1 == "" ? time2 : time2 == "" ? time1 : time1 + "　" + time2;
//		String cont1 = workName == "" ? time : time == "" ? workName : workName + "　" + time;
//        String cont2 = cont1 == "" ? breakTime : breakTime == "" ? cont1 : cont1 + "　" + breakTime;
//		return this.checkAddReason(cont2, appReason, appReasonDisAtr, screenAtr);
	}

	/**
	 * get content go back
	 * 直行直帰申請 kaf009 - appType = 4
	 * ※申請モード、承認モード(事前)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentGoBack(Application application, DisplayAtr appReasonDisAtr, List<WorkTimeSetting> workTimeLst, List<WorkType> workTypeLst, ScreenAtr screenAtr) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「直行直帰申請」を取得 ( Lấy domain 「直行直帰申請」)
		GoBackDirectly goBackDirectly = goBackDirectlyRepository.find(companyID, application.getAppID()).get();
		// 申請内容　＝　申請内容（勤務変更申請、直行直帰申請） ( Nội dung application = Nội dung application (Work change application, application đi thẳng về nhà)
		return appContentService.getWorkChangeGoBackContent(
				ApplicationType.GO_RETURN_DIRECTLY_APPLICATION,
				Strings.EMPTY,
				Strings.EMPTY,
				goBackDirectly.getStraightDistinction(),
				new TimeWithDayAttr(0),
				goBackDirectly.getStraightLine(),
				new TimeWithDayAttr(0),
				new TimeWithDayAttr(0),
				new TimeWithDayAttr(0),
				appReasonDisAtr,
				application.getOpAppReason().orElse(null),
				application);
//		if(goBack == null){
//			//ドメインモデル「直行直帰申請」を取得
//			goBack = appDetailInfoRepo.getAppGoBackInfo(companyId, appId);
//		}
//		//申請内容　＝　申請内容（勤務変更申請、直行直帰申請）
//		String go1 = goBack.getGoWorkAtr1() == 1 ? goBack.getWorkTimeStart1() == "" ? I18NText.getText("CMM045_259") + goBack.getWorkTimeStart1() :
//			I18NText.getText("CMM045_259") + "　" + goBack.getWorkTimeStart1() : "";
//		String back1 = goBack.getBackHomeAtr1() == 1 ? goBack.getWorkTimeEnd1() == "" ? I18NText.getText("CMM045_260") + goBack.getWorkTimeEnd1() :
//			I18NText.getText("CMM045_260") + "　" + goBack.getWorkTimeEnd1() : "";
//		String go2 = goBack.getGoWorkAtr2() != null && goBack.getGoWorkAtr2() == 1 ? goBack.getWorkTimeStart2() == "" ?
//				I18NText.getText("CMM045_259") + goBack.getWorkTimeStart2() : I18NText.getText("CMM045_259") + "　" + goBack.getWorkTimeStart2() : "";
//        String back2 = goBack.getBackHomeAtr2() != null && goBack.getBackHomeAtr2() == 1 ? goBack.getWorkTimeEnd2() == "" ?
//        		I18NText.getText("CMM045_260") + goBack.getWorkTimeEnd2() : I18NText.getText("CMM045_260") + "　" + goBack.getWorkTimeEnd2() : "";
//        String goback1 = go1 == "" ? back1 : back1 == "" ? go1 : go1 + "　" + back1;
//        String goback2 = go2 == "" ? back2 : back2 == "" ? go2 : go2 + "　" + back2;
//        String gobackA = goback1 == "" ? goback2 : goback2 == "" ? goback1 : goback1 + "　" + goback2;
//		return this.checkAddReason(gobackA, appReason, appReasonDisAtr, screenAtr);
	}
	/**
	 * get Content HdWorkBf
	 * 休日出勤時間申請 kaf010 - appTYpe = 6
	 * ※承認モード(事後)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentHdWorkBf(AppHolidayWorkFull hdWork, String companyId, String appId, Integer appReasonDisAtr, String appReason,
			int screenAtr, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime) {
		if(hdWork == null){
			hdWork = appDetailInfoRepo.getAppHolidayWorkInfo(companyId, appId, lstWkType, lstWkTime);
		}
		String appContent010 = this.content010(hdWork.getWorkTypeName(), hdWork.getWorkTimeName(), hdWork.getStartTime1(),
				hdWork.getEndTime1(), hdWork.getEndTime2(), hdWork.getStartTime2(), hdWork.getLstFrame());
        //No.417
		String timeNo417 = this.displayTimeNo417(hdWork.getTimeNo417(), screenAtr);
		String appCt010 = appContent010 + timeNo417;
		return this.checkAddReason(appCt010, appReason, appReasonDisAtr, screenAtr);
	}
	private String content010(String workTypeName, String workTimeName, String startTime1, String endTime1,
			String startTime2, String endTime2, List<OverTimeFrame> lstFrame){
		String ca1 = startTime1 == "" ? "" : startTime1 + I18NText.getText("CMM045_100") + endTime1;
		String ca2 = startTime2 == "" ? "" : startTime2 + I18NText.getText("CMM045_100") + endTime2;
		String name = workTypeName == "" ? workTimeName : workTimeName == "" ? workTypeName : workTypeName + "　" + workTimeName;
		String caF = ca1 == "" ? ca2 : ca2 == "" ? ca1 : ca1 + "　" + ca2;
		String cont1 = name == "" ? caF : caF == "" ? name : name + "　" + caF;
		String appContent010 = cont1 == "" ? this.convertFrameTimeHd(lstFrame) : cont1 + "　" + this.convertFrameTimeHd(lstFrame);
		return appContent010;
	}
	/**
	 * get Content HdWorkAf
	 * 休日出勤時間申請 kaf010 - appTYpe = 6
	 * ※承認モード(事後)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentHdWorkAf(AppHolidayWorkFull hdWork, Integer appReasonDisAtr, String appReason, AppPrePostGroup subData) {
		String appContentPost = this.content010(hdWork.getWorkTypeName(), hdWork.getWorkTimeName(), hdWork.getStartTime1(),
				hdWork.getEndTime1(), hdWork.getEndTime2(), hdWork.getStartTime2(), hdWork.getLstFrame());
		String appContentPre = "";
		if(subData != null && subData.getAppPreHd() != null){
			AppHolidayWorkFull appPre = subData.getAppPreHd();
			appContentPre = CMM045 + I18NText.getText("CMM045_273") + this.content010(appPre.getWorkTypeName(), appPre.getWorkTimeName(),
					appPre.getStartTime1(), appPre.getEndTime1(), appPre.getEndTime2(), appPre.getStartTime2(), appPre.getLstFrame());
		}
		String appContentRes = "";
		if(subData != null && subData.isDisplayRes()){
			appContentRes = CMM045 + I18NText.getText("CMM045_274") + this.content010(subData.getWorkTypeName(), subData.getWorkTimeName(),
					subData.getStrTime1(), subData.getEndTime1(), subData.getEndTime2(), subData.getStrTime2(), subData.getTime());
		}
		// No.417
		String timeNo417 = this.displayTimeNo417(hdWork.getTimeNo417(), ScreenAtr.CMM045.value);
		String contentFull = I18NText.getText("CMM045_272") + appContentPost +
				appContentPre + appContentRes + timeNo417;
		return this.checkAddReason(contentFull, appReason, appReasonDisAtr, ScreenAtr.CMM045.value);
	}
	/**
	 * get Content Stamp
	 * 打刻申請 kaf002 - appType = 7
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	@Override
	public String getContentStamp(String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr) {
		String content = "";
		AppStamp_Old appStamp = null;
		if (!Objects.isNull(appStamp)) {
			switch (appStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT: {
				int k = 0;
				boolean checkAppend = false;
				for (val x : appStamp.getAppStampGoOutPermits()) {
					if (x.getStampAtr() == AppStampAtr.GO_OUT) {
						if (k<3) {
							content += I18NText.getText("CMM045_232") + " "
									+ I18NText.getText("CMM045_230", x.getStampGoOutAtr().name) + " "
									+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					} else if (x.getStampAtr() == AppStampAtr.CHILDCARE) {
						if (!checkAppend) {
							content += I18NText.getText("CMM045_233") + " ";
							checkAppend = true;
						}
						if (k<2) {
							content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					} else if (x.getStampAtr() == AppStampAtr.CARE) {
						if (!checkAppend) {
							content += I18NText.getText("CMM045_234") + " ";
							checkAppend = true;
						}
						if (k<2) {
							content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					}
				}
				content += (k > 3 ? I18NText.getText("CMM045_230", k - 3 + "") : "");
				break;
			}
			case STAMP_WORK: {
				int k = 0;
				content += I18NText.getText("CMM045_235") + " ";
				for (val x : appStamp.getAppStampWorks()) {
					if (k<3) {
						content += x.getStampAtr().name + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getStartTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
					}
					k++;
				}
				content += (k > 3 ? I18NText.getText("CMM045_230", k - 3 + "") : "");
				break;
			}
			case STAMP_ONLINE_RECORD: {
				// TO-DO
				content += I18NText.getText("CMM045_240");
				Optional<AppStampOnlineRecord> appStampRecord = appStamp.getAppStampOnlineRecord();
				if (appStampRecord.isPresent()) {
					content += appStampRecord.get().getStampCombinationAtr().name
							+ appStampRecord.get().getAppTime().toString();
				}
				break;
			}
			case STAMP_CANCEL: {
				content += I18NText.getText("CMM045_235");
				for (val x : appStamp.getAppStampCancels()) {
					switch (x.getStampAtr()) {
					// TO-DO
					case ATTENDANCE: {
						content += " ×出勤　9:00　×退勤　17:00 ";
					}
					case CARE: {
						content += " ×出勤　9:00　×退勤　17:00 ";
					}
					case CHILDCARE: {
						content += " ×出勤　9:00　×退勤　17:00 ";
					}
					case GO_OUT: {
						content += " ×出勤　9:00　×退勤　17:00 ";
					}
					case SUPPORT: {
						content += " ×出勤　9:00　×退勤　17:00 ";
					}
					}
				}
				break;
			}
			case OTHER: {
				int k = 0;
				for (val x : appStamp.getAppStampWorks()) {
					switch (x.getStampAtr()) {
					case ATTENDANCE: {
						if (k<3) {
							content += x.getStampAtr().name + " " + (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "")
									+ " " + I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					}
					case CARE: {
						if (k<3) {
							content += I18NText.getText("CMM045_234") + " "
									+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					}
					case CHILDCARE: {
						if (k<3) {
							content += I18NText.getText("CMM045_233") + " "
									+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					}
					case GO_OUT: {
						if (k<3) {
							content += I18NText.getText("CMM045_232") + " "
									+ I18NText.getText("CMM045_230", x.getStampGoOutAtr().name) + " "
									+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					}
					case SUPPORT: {
						if (k<3) {
							content += I18NText.getText("CMM045_242") + " "
									+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
									+ I18NText.getText("CMM045_100") + " "
									+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						}
						k++;
					}
					}
				}
				content += (k > 3 ? I18NText.getText("CMM045_230", k - 3 + "") : "");
				break;
			}
			}
		}
		return this.checkAddReason(content, appReason, appReasonDisAtr, screenAtr);
	};
	/**
	 * get Content EarlyLeave
	 * 遅刻早退取消申請 kaf004 - appType = 9
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	@Override
	public String getContentEarlyLeave(String companyId, String appId, Integer appReasonDisAtr, String appReason, int prePostAtr, int screenAtr) {
		String content = "";
		Optional<LateOrLeaveEarly> op_leaveApp = Optional.empty();
//		Optional<LateOrLeaveEarly> op_leaveApp = lateLeaveEarlyRepo.findByCode(companyId, appId);
		if (op_leaveApp.isPresent()) {
			LateOrLeaveEarly leaveApp = op_leaveApp.get();
			if (leaveApp.getActualCancelAtr() == 0) {
				if (prePostAtr == 0) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: I18NText.getText("CMM045_246") + getAsString(leaveApp.getLateTime1()))
							+ (leaveApp.getLate1().value == 0 ? ""
									: I18NText.getText("CMM045_247") + getAsString(leaveApp.getEarlyTime1()))
							+ (leaveApp.getEarly2().value == 0 ? ""
									: I18NText.getText("CMM045_246") + getAsString(leaveApp.getLateTime2()))
							+ (leaveApp.getLate2().value == 0 ? ""
									: I18NText.getText("CMM045_247") + getAsString(leaveApp.getEarlyTime2()));
				} else if (prePostAtr == 1) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: I18NText.getText("CMM045_246") + getAsString(leaveApp.getLateTime1()))
							+ (leaveApp.getLate1().value == 0 ? ""
									: I18NText.getText("CMM045_247") + getAsString(leaveApp.getEarlyTime1()))
							+ (leaveApp.getEarly2().value == 0 ? ""
									: I18NText.getText("CMM045_246") + getAsString(leaveApp.getLateTime2()))
							+ (leaveApp.getLate2().value == 0 ? ""
									: I18NText.getText("CMM045_247") + getAsString(leaveApp.getEarlyTime2()));
				}
			} else {
				if (leaveApp.getActualCancelAtr() == 0) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_246") + getAsString(leaveApp.getLateTime1()))
							+ (leaveApp.getLate1().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_247") + getAsString(leaveApp.getEarlyTime1()))
							+ (leaveApp.getEarly2().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_246") + getAsString(leaveApp.getLateTime2()))
							+ (leaveApp.getLate2().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_247") + getAsString(leaveApp.getEarlyTime2()));
				}
			}
		}
		return this.checkAddReason(content, appReason, appReasonDisAtr, screenAtr);
	};
	/**
	 * get Content CompltLeave
	 * 振休振出申請 kaf011 - appType = 10
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	@Override
	public String getContentCompltLeave(AppCompltLeaveSync compltSync, String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int screenAtr, List<WorkType> lstWkType) {
		AppCompltLeaveSync complt = appDetailInfoRepo.getCompltLeaveSync(companyId, appId, lstWkType);
        //振出 rec typeApp = 1
        //振休 abs typeApp = 0
        String contentKAF011 = "";
        //振出 rec typeApp = 1
        //振休 abs typeApp = 0
        if(complt.getTypeApp() == 0){
            contentKAF011 = this.convertB(complt.getAppMain());
        }else{
            contentKAF011 = this.convertA(complt.getAppMain());
        }
		return this.checkAddReason(contentKAF011, appReason, appReasonDisAtr, screenAtr);
	}
	/**
	 * get Content CompltSync
	 * 振休振出申請 kaf011 - appType = 10
     * 同期
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	@Override
	public String getContentCompltSync(AppCompltLeaveSync compltSync, String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int type, int screenAtr, List<WorkType> lstWkType) {
		if(compltSync == null){
			compltSync = appDetailInfoRepo.getCompltLeaveSync(companyId, appId, lstWkType);
		}
		//振出 rec typeApp = 1
        //振休 abs typeApp = 0
        String content010 = this.convertC(compltSync, appReasonDisAtr, screenAtr);
        return this.checkAddReason(content010, appReason, appReasonDisAtr, screenAtr);
	}

	@Override
	public String getContentComplt(AppCompltLeaveSync complt, String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int screenAtr, List<WorkType> lstWkType) {
		if(complt == null){
			complt = appDetailInfoRepo.getCompltLeaveSync(companyId, appId, lstWkType);
		}
		if(complt.isSync()){
            return this.getContentCompltSync(complt, companyId, appId, appReasonDisAtr, appReason, complt.getTypeApp(),
            		screenAtr, lstWkType);
        }else{
            return this.getContentCompltLeave(complt, companyId, appId, appReasonDisAtr, appReason, screenAtr, lstWkType);
        }
	}
	/**
	 * Time 36
	 * @param timeNo417
	 * @param screenAtr
	 * @return
	 */
	private String displayTimeNo417(TimeNo417 timeNo417, int screenAtr) {

		if (timeNo417 == null || timeNo417.getTime36() <= 0) {
			// 「時間外時間の詳細」．36時間 > 0 の場合 追加
			return "";
		}

		if (timeNo417.getNumOfYear36Over() == 0) {
			// 未超過の場合
			return this.checkCRLF(screenAtr) + I18NText.getText("CMM045_282")
					+ this.convertTime_Short_HM(timeNo417.getTotalOv()) + "　" + I18NText.getText("CMM045_283")
					+ I18NText.getText("CMM045_284", String.valueOf(timeNo417.getNumOfYear36Over()));
		}
		// 超過した場合
		String a1 = I18NText.getText("CMM045_282") + this.convertTime_Short_HM(timeNo417.getTotalOv()) + "　"
				+ I18NText.getText("CMM045_283")
				+ I18NText.getText("CMM045_284", String.valueOf(timeNo417.getNumOfYear36Over()));
		List<Integer> lstMonth = timeNo417.getLstOverMonth().stream().sorted().map(c -> c % 100).collect(Collectors.toList());
		String a2 = "";

		for (Integer mon : lstMonth) {
			a2 = a2 == "" ? I18NText.getText("CMM045_285", String.valueOf(mon))
					: a2 + "、" + I18NText.getText("CMM045_285", String.valueOf(mon));
		}

		return a2 == "" ? this.checkCRLF(screenAtr) + a1 : this.checkCRLF(screenAtr) + a1 + "(" + a2 + ")";
	}

	private String convertTime_Short_HM(int time) {
		return (time / 60 + ":" + (time % 60 < 10 ? "0" + time % 60 : time % 60));
	}

	/**
	 * convert frame time over time
	 */
	private String convertFrameTime(List<OverTimeFrame> lstFrame) {
		String framName = "";
		// int time = 0;
		int count = 0;
		List<OverTimeFrame> lstSort = this.sortFrameTime(lstFrame, 0);
		for (OverTimeFrame item : lstSort) {
			if (item.getApplicationTime() != null && item.getApplicationTime() != 0) {// 時間外深夜時間
				if (count < 3) {
					// ver42
					framName += item.getName() + this.convertTime_Short_HM(item.getApplicationTime()) + "　";
				}
				// time += item.getApplicationTime();
				count += 1;
			}
		}
		int other = count > 3 ? count - 3 : 0;
		String otherInfo = other > 0 ? I18NText.getText("CMM045_231", String.valueOf(other)) : "";
		String result = framName + otherInfo;
		return result;
	}

	private List<OverTimeFrame> sortFrameTime(List<OverTimeFrame> lstFrame, int appType) {
		List<OverTimeFrame> result = new ArrayList<>();
		List<OverTimeFrame> lstA0 = lstFrame.stream().filter(c -> c.getAttendanceType() == 0)
				.collect(Collectors.toList());// RESTTIME
		List<OverTimeFrame> lstA1 = lstFrame.stream().filter(c -> c.getAttendanceType() == 1)
				.collect(Collectors.toList());// NORMALOVERTIME
		List<OverTimeFrame> lstA2 = lstFrame.stream().filter(c -> c.getAttendanceType() == 2)
				.collect(Collectors.toList());// BREAKTIME
		List<OverTimeFrame> lstA3 = lstFrame.stream().filter(c -> c.getAttendanceType() == 3)
				.collect(Collectors.toList());// BONUSPAYTIME
		List<OverTimeFrame> lstA4 = lstFrame.stream().filter(c -> c.getAttendanceType() == 4)
				.collect(Collectors.toList());// BONUSSPECIALDAYTIME

		Collections.sort(lstA0, Comparator.comparing(OverTimeFrame::getFrameNo));
		Collections.sort(lstA1, Comparator.comparing(OverTimeFrame::getFrameNo));
		Collections.sort(lstA2, Comparator.comparing(OverTimeFrame::getFrameNo));
		Collections.sort(lstA3, Comparator.comparing(OverTimeFrame::getFrameNo));
		Collections.sort(lstA4, Comparator.comparing(OverTimeFrame::getFrameNo));

		if (appType == 0) {// overtime
			// push list A1 (残業時間)
			result.addAll(lstA1);
			// push list A2 (休出時間)
			result.addAll(lstA2);
			// push list A3 (加給時間)
			result.addAll(lstA3);
			// push list A4 (特定日加給時間)
			result.addAll(lstA4);
		} else {// holiday
			// push list A2 (休出時間)
			result.addAll(lstA2);
			// push list A1 (残業時間)
			result.addAll(lstA1);
			// push list A3 (加給時間)
			result.addAll(lstA3);
		}
		return result;
	}
    //休暇申請.休暇種類　≠ 特別休暇 ver46
    private String convertAbsenceNotSpecial(AppAbsenceFull absence){
        return absence.getWorkTypeName();
    }
    //※休暇申請.休暇種類　＝ 特別休暇 ver39
    private String convertAbsenceSpecial(AppAbsenceFull absence){
        String day = absence.getMournerFlag() == true ? I18NText.getText("CMM045_277") + "　" +  absence.getDay() + I18NText.getText("CMM045_278") :
        	absence.getDay() + I18NText.getText("CMM045_278");
        String name = absence.getWorkTypeName() == "" ? absence.getRelationshipName() : absence.getRelationshipName() == "" ?
        		absence.getWorkTypeName() : absence.getWorkTypeName() + "　" + absence.getRelationshipName();
        String result = name == "" ? day : name + "　" + day;
        return result;
    }
    /**
     * convert frame time holiday work
     */
    private String convertFrameTimeHd(List<OverTimeFrame> lstFrame){
        String framName = "";
        int count = 0;
        List<OverTimeFrame> lstSort = this.sortFrameTime(lstFrame, 6);
        for(OverTimeFrame item : lstSort){
        	if (item.getApplicationTime() != null && item.getApplicationTime() != 0) {
                if (count < 3) {
                    framName += item.getName() + this.convertTime_Short_HM(item.getApplicationTime()) + "　";
                }
//                time += item.getApplicationTime();
                count += 1;
            }
        }
        int other = count > 3 ? count - 3 : 0;
        String otherInfo = other > 0 ? I18NText.getText("CMM045_231", String.valueOf(other)) : "";
        //#102010
        String result = framName + otherInfo;
        return result;
    }
  //※振出申請のみ同期なし・紐付けなし
    //申請/承認モード
    //申請日付(A6_C2_6)、入力日(A6_C2_8)、承認状況(A6_C2_9)の表示はない（１段）
    private String convertA(AppCompltLeaveFull compltLeave){
        String time = compltLeave.getStartTime() + I18NText.getText("CMM045_100") + compltLeave.getEndTime();
        String cont1 = compltLeave.getWorkTypeName() == "" ? time : compltLeave.getWorkTypeName() + "　" + time;
        return I18NText.getText("CMM045_262") + cont1;
    }
    //※振休申請のみ同期なし・紐付けなし
    //申請/承認モード
    //申請日付(A6_C2_6)、入力日(A6_C2_8)、承認状況(A6_C2_9)の表示はない（１段）
    private String convertB(AppCompltLeaveFull compltLeave){
    	String eTime = compltLeave.getEndTime() == "" ? "" : I18NText.getText("CMM045_100") + compltLeave.getEndTime();
    	String time = compltLeave.getStartTime() + eTime;
    	String cont1 = compltLeave.getWorkTypeName() == "" ? time : time == "" ? compltLeave.getWorkTypeName() : compltLeave.getWorkTypeName() + "　" + time;
        return I18NText.getText("CMM045_263") + cont1;
    }
    //※振休振出申請　同期あり・紐付けあり
    //申請モード/承認モード merge convert C + D
    //申請日付(A6_C2_6)、入力日(A6_C2_8)、承認状況(A6_C2_9)表示（２段）
    //振出(rec) -> 振休(abs)
    private String convertC(AppCompltLeaveSync compltSync, int appReasonDisAtr, int screenAtr){
    	AppCompltLeaveFull abs = null;
    	AppCompltLeaveFull rec = null;
    	String recContent = "";
    	String absContent = "";
        if(compltSync.getTypeApp() == 0){
            abs = compltSync.getAppMain();
            rec = compltSync.getAppSub();
            recContent = this.convertA(rec);
            absContent = this.convertB(abs);

        }else{
            rec = compltSync.getAppMain();
            abs = compltSync.getAppSub();
            absContent = this.convertB(abs);
            recContent = this.convertA(rec);
        }
//        String cont1 = "<div class = 'rec' >" + recContent + "</div>" + "<div class = 'abs' >" + absContent;
//        return appReasonDisAtr == 1 ? cont1 : "<div class = 'rec' >" + recContent + "</div>" + "<div class = 'abs' >" + absContent + "</div>";
        return recContent + this.checkCRLF(screenAtr) + absContent;
    }
    private String getAsString(TimeDay time) {
		return time == null ? "" : time.toString();
	}
    //CMM045: hien thi reason binh thuong
    //KAF018: khong hien thi reason
    //KDL030: them title truoc khi hien thi reason
    private String checkAddReason(String contentDetail, String appReason, Integer appReasonDisAtr, int screenAtr){
    	String appReasonEscape = StringEscapeUtils.escapeHtml4(appReason);
    	String contentFull = "";
    	String addReason = appReasonDisAtr != null && appReasonDisAtr == 1 ? appReasonEscape : "";
		if(screenAtr == ScreenAtr.KDL030.value){
			addReason = addReason == "" ? "" : "申請理由：" + KDL030 + addReason;
			contentFull = contentDetail == "" ? addReason : addReason == "" ? contentDetail : contentDetail + KDL030 + addReason;
		}else if(screenAtr == ScreenAtr.CMM045.value){//CMM045
			contentFull = contentDetail == "" ? addReason : addReason == "" ? contentDetail : contentDetail + CMM045 + addReason;
		}else{//KAF018
			contentFull = contentDetail;
		}
		return contentFull;
    }

    private String checkCRLF(int screenAtr){
    	if(screenAtr == ScreenAtr.KDL030.value){
    		return KDL030;
    	}else{
    		return CMM045;
    	}
    }

    @Override
	public AppStampDataOutput createAppStampData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
			String companyID, ListOfAppTypes listOfAppTypes) {
		// 申請.打刻申請モード
		if(application.getOpStampRequestMode().get()==StampRequestMode.STAMP_ONLINE_RECORD) {
			// ドメインモデル「レコーダイメージ申請」を取得する
			AppRecordImage appRecordImage = appRecordImageRepository.findByAppID(companyID, application.getAppID()).get();
			// 申請内容＝#CMM045_293＋'　'＋レコーダイメージ申請.打刻区分
			String result = I18NText.getText("CMM045_239") + " " + appRecordImage.getAppStampCombinationAtr().name;
			// レコーダイメージ申請.外出理由がemptyでない場合
			if(appRecordImage.getAppStampGoOutAtr().isPresent()) {
				// 申請内容＋＝#CMM045_230：{0}＝外出理由（Enum）
				result += I18NText.getText("CMM045_230", appRecordImage.getAppStampGoOutAtr().get().nameId);
			}
			// 申請内容＋＝’　’＋レコーダイメージ申請.申請時刻
			result += " " + new TimeWithDayAttr(appRecordImage.getAttendanceTime().v()).getFullText();
			// アルゴリズム「申請内容の申請理由」を実行する
			String appReasonContent = appContentService.getAppReasonContent(
					appReasonDisAtr,
					application.getOpAppReason().orElse(null),
					screenAtr,
					application.getOpAppStandardReasonCD().orElse(null),
					application.getAppType(),
					Optional.empty());
			if(Strings.isNotBlank(appReasonContent)) {
				result += "\n" + appReasonContent;
			}
			return new AppStampDataOutput(
					result,
					Optional.of(ApplicationTypeDisplay.STAMP_ONLINE_RECORD));
		}
		// ドメインモデル「打刻申請」を取得する
		AppStamp appStamp = appStampRepository.findByAppID(companyID, application.getAppID()).get();
		List<StampAppOutputTmp> listTmp = new ArrayList<>();
		// 「打刻申請.時刻の取消」よりリストを収集する
		appStamp.getListTimeStampApp().stream().collect(Collectors.groupingBy(x -> x.getDestinationTimeApp().getEngraveFrameNo()))
		.entrySet().forEach(entry -> {
			Optional<TimeStampApp> opStartTimeStampApp = entry.getValue().stream()
					.filter(x -> x.getDestinationTimeApp().getStartEndClassification()==StartEndClassification.START)
					.findAny();
			Optional<TimeStampApp> opEndTimeStampApp = entry.getValue().stream()
					.filter(x -> x.getDestinationTimeApp().getStartEndClassification()==StartEndClassification.END)
					.findAny();
			listTmp.add(new StampAppOutputTmp(
					0,
					false,
					entry.getValue().get(0).getDestinationTimeApp().getTimeStampAppEnum().value,
				 	new StampFrameNo(entry.getKey()),
					opStartTimeStampApp.map(x -> x.getTimeOfDay()),
					entry.getValue().get(0).getAppStampGoOutAtr(),
					Optional.empty(),
					opEndTimeStampApp.map(x -> x.getTimeOfDay())));
		});
//		for(TimeStampApp timeStampApp : appStamp.getListTimeStampApp()) {
//			listTmp.add(new StampAppOutputTmp(
//					0,
//					false,
//					timeStampApp.getDestinationTimeApp().getTimeStampAppEnum().value,
//				 	new StampFrameNo(timeStampApp.getDestinationTimeApp().getEngraveFrameNo()),
//					Optional.of(timeStampApp.getTimeOfDay()),
//					timeStampApp.getAppStampGoOutAtr(),
//					Optional.empty(),
//					Optional.of(timeStampApp.getTimeOfDay())));
//		}
		// 「打刻申請.時刻の取消」よりリストを収集する
		for(DestinationTimeApp destinationTimeApp : appStamp.getListDestinationTimeApp()) {
			listTmp.add(new StampAppOutputTmp(
					0,
					true,
					destinationTimeApp.getTimeStampAppEnum().value,
				 	new StampFrameNo(destinationTimeApp.getEngraveFrameNo()),
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty()));
		}
		// 「打刻申請.時間帯」よりリストを収集する
		for(TimeStampAppOther timeStampAppOther : appStamp.getListTimeStampAppOther()) {
			listTmp.add(new StampAppOutputTmp(
					1,
					false,
					timeStampAppOther.getDestinationTimeZoneApp().getTimeZoneStampClassification().value,
				 	new StampFrameNo(timeStampAppOther.getDestinationTimeZoneApp().getEngraveFrameNo()),
				 	Optional.of(timeStampAppOther.getTimeZone().getStartTime()),
					Optional.empty(),
					Optional.empty(),
					Optional.of(timeStampAppOther.getTimeZone().getEndTime())));
		}
		// 「打刻申請.時間帯の取消」よりリストを収集する
		for(DestinationTimeZoneApp destinationTimeZoneApp : appStamp.getListDestinationTimeZoneApp()) {
			listTmp.add(new StampAppOutputTmp(
					1,
					true,
					destinationTimeZoneApp.getTimeZoneStampClassification().value,
				 	new StampFrameNo(destinationTimeZoneApp.getEngraveFrameNo()),
				 	Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty()));
		}
		// 「打刻申請出力用Tmp」を並べて項目名をセットする
		listTmp.sort(Comparator.comparing((StampAppOutputTmp x) -> {
			return String.valueOf(x.getTimeItem()) + String.valueOf(x.getStampAtr()) + String.valueOf(x.getStampFrameNo().v());
		}));
		for(StampAppOutputTmp itemTmp : listTmp) {
			if(itemTmp.getTimeItem() == 0 && itemTmp.getStampAtr() == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT.value) {
				// 項目名＝#KAF002_65（勤務時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_65")));
			}
			if(itemTmp.getTimeItem() == 0 && itemTmp.getStampAtr() == TimeStampAppEnum.EXTRAORDINARY.value) {
				// 項目名＝#KAF002_66（臨時時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_66")));
			}
			if(itemTmp.getTimeItem() == 0 && itemTmp.getStampAtr() == TimeStampAppEnum.GOOUT_RETURNING.value) {
				// 項目名＝#KAF002_67（外出時間）：枠NO
				// 項目名＋＝#CMM045_230（）：{0}=打刻申請出力用Tmp.外出理由
				if(itemTmp.getOpGoOutReasonAtr().isPresent()) {
					itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_67") + I18NText.getText("CMM045_230", itemTmp.getOpGoOutReasonAtr().get().nameId)));
				} else {
					itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_67")));
				}
			}
			if(itemTmp.getTimeItem() == 1 && itemTmp.getStampAtr() == TimeZoneStampClassification.BREAK.value) {
				// 項目名＝#KAF002_75（休憩時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_75")));
			}
			if(itemTmp.getTimeItem() == 1 && itemTmp.getStampAtr() == TimeZoneStampClassification.PARENT.value) {
				// 項目名＝#KAF002_68（育児時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_68")));
			}
			if(itemTmp.getTimeItem() == 1 && itemTmp.getStampAtr() == TimeZoneStampClassification.NURSE.value) {
				// 項目名＝#KAF002_69（介護時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_69")));
			}
		}
		// アルゴリズム「申請内容（打刻申請）」を実行する
		String content = appContentService.getAppStampContent(
				appReasonDisAtr,
				application.getOpAppReason().orElse(null),
				screenAtr,
				listTmp,
				application.getAppType(),
				application.getOpAppStandardReasonCD().orElse(null));
		return new AppStampDataOutput(
				content,
				Optional.of(ApplicationTypeDisplay.STAMP_ADDITIONAL));
	}

	@Override
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
			String companyID) {
		// ドメインモデル「遅刻早退取消申請」
		ArrivedLateLeaveEarly arrivedLateLeaveEarly = arrivedLateLeaveEarlyRepository.getLateEarlyApp(companyID, application.getAppID(), application);
		List<ArrivedLateLeaveEarlyItemContent> itemContentLst = new ArrayList<>();
		// 「遅刻早退取消申請.時刻報告」
		for(TimeReport timeReport : arrivedLateLeaveEarly.getLateOrLeaveEarlies()) {
			String itemName = Strings.EMPTY;
			if(timeReport.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				itemName = I18NText.getText("CMM045_236");
			} else if(timeReport.getLateOrEarlyClassification() == LateOrEarlyAtr.EARLY) {
				itemName = I18NText.getText("CMM045_238");
			}
			itemContentLst.add(new ArrivedLateLeaveEarlyItemContent(
					itemName,
					timeReport.getWorkNo(),
					timeReport.getLateOrEarlyClassification(),
					Optional.of(timeReport.getTimeWithDayAttr()),
					false));
		}
		// 「遅刻早退取消申請.取消」
		for(LateCancelation lateCancelation : arrivedLateLeaveEarly.getLateCancelation()) {
			String itemName = Strings.EMPTY;
			if(lateCancelation.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				itemName = I18NText.getText("CMM045_236");
			} else if(lateCancelation.getLateOrEarlyClassification() == LateOrEarlyAtr.EARLY) {
				itemName = I18NText.getText("CMM045_238");
			}
			itemContentLst.add(new ArrivedLateLeaveEarlyItemContent(
					itemName,
					lateCancelation.getWorkNo(),
					lateCancelation.getLateOrEarlyClassification(),
					Optional.empty(),
					true));
		}
		// <List>を勤務NO+区分（遅刻、早退の順）で並べる
		itemContentLst.sort(Comparator.comparing((ArrivedLateLeaveEarlyItemContent x) -> {
			return String.valueOf(x.getWorkNo()) + String.valueOf(x.getLateOrEarlyAtr().value);
		}));
		// アルゴリズム「申請内容（遅刻早退取消）」を実行する
		return appContentService.getArrivedLateLeaveEarlyContent(
				application.getOpAppReason().orElse(null),
				appReasonDisAtr,
				screenAtr,
				itemContentLst,
				application.getAppType(),
				application.getOpAppStandardReasonCD().orElse(null));
	}

	@Override
	public String createBusinessTripData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
			String companyID) {
		String content = Strings.EMPTY;
		// ドメインモデル「出張申請」を取得する
		BusinessTrip businessTrip = businessTripRepository.findByAppId(companyID, application.getAppID()).get();
		// @＝''
		String paramString = "";
		// 出張申請.出発時刻が入力されている場合
		if(businessTrip.getDepartureTime().isPresent()) {
			// 申請内容＝#CMM045_290＋"　"＋出張申請.出発時刻＋”　”
			content += I18NText.getText("CMM045_290") + " " + new TimeWithDayAttr(businessTrip.getDepartureTime().get().v()).getFullText() + " ";
			// @＝'　'
			paramString = " ";
		}
		// 出張申請.帰着時刻が入力されている場合
		if(businessTrip.getReturnTime().isPresent()) {
			// 申請内容＋＝@＋#CMM045_291＋"　"＋出張申請.帰着時刻
			content += I18NText.getText("CMM045_291") + " " + new TimeWithDayAttr(businessTrip.getReturnTime().get().v()).getFullText();
		}
		// アルゴリズム「申請内容の申請理由」を実行する
		String appReasonContent = appContentService.getAppReasonContent(
				appReasonDisAtr,
				application.getOpAppReason().orElse(null),
				screenAtr,
				application.getOpAppStandardReasonCD().orElse(null),
				ApplicationType.BUSINESS_TRIP_APPLICATION,
				Optional.empty());
		if(Strings.isNotBlank(appReasonContent)) {
			content += "\n" + appReasonContent;
		}
		return content;
	}

	@Override
	public String createOptionalItemApp(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
			String companyID) {
		// ドメインモデル「任意項目申請」を取得する
		OptionalItemApplication optionalItemApp = optionalItemApplicationRepository.getByAppId(companyID, application.getAppID()).get();
		// ドメインモデル「任意項目申請設定」を取得する
		Optional<OptionalItemApplicationSetting> opOptionalItemApplicationSetting = optionalItemAppSetRepository
				.findByCompanyAndCode(companyID, optionalItemApp.getCode().toString());
		if(!opOptionalItemApplicationSetting.isPresent()) {
			return optionalItemApp.getCode().toString() + "未登録";
		}
		// ドメインモデル「任意項目」より取得する
		List<OptionalItem> optionalItemLst = optionalItemRepository.findByListNos(
				companyID, 
				optionalItemApp.getOptionalItems().stream().map(x -> x.getItemNo().v()).collect(Collectors.toList()));
		List<OptionalItemOutput> optionalItemOutputLst = new ArrayList<>();
		for(OptionalItem optionalItem : optionalItemLst) {
			// リストを作成する
			optionalItemOutputLst.add(new OptionalItemOutput(
					optionalItem.getOptionalItemName(), 
					optionalItemApp.getOptionalItems().stream().filter(x -> x.getItemNo().v()==optionalItem.getOptionalItemNo().v()).findAny().get(), 
					optionalItem.getOptionalItemAtr(), 
					optionalItem.getUnit()));
		}
		// アルゴリズム「申請内容（任意項目申請）」を実行する
		return appContentService.getOptionalItemAppContent(
				application.getOpAppReason().orElse(null), 
				appReasonDisAtr, 
				screenAtr, 
				opOptionalItemApplicationSetting.get().getName(), 
				optionalItemOutputLst, 
				application.getAppType(), 
				application.getOpAppStandardReasonCD().orElse(null)	);
	}
}
