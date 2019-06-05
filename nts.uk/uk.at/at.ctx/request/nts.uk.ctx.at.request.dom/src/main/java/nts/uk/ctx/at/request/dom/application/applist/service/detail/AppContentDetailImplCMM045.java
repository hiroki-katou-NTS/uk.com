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

import org.apache.logging.log4j.util.Strings;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeDay;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;

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
	private AppStampRepository appStampRepo;
	@Inject
	private LateOrLeaveEarlyRepository lateLeaveEarlyRepo;
	
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
	public String getContentOverTimeBf(String companyId, String appId, int detailSet, Integer appReasonDisAtr, String appReason, int screenAtr) {
		AppOverTimeInfoFull overTime = appDetailInfoRepo.getAppOverTimeInfo(companyId, appId);
		String time1 = overTime.getWorkClockFrom1() == "" ? ""
				: overTime.getWorkClockFrom1() + I18NText.getText("CMM045_100") + overTime.getWorkClockTo1();
		String time2 = overTime.getWorkClockFrom2() == "" ? ""
				: overTime.getWorkClockFrom2() + I18NText.getText("CMM045_100") + overTime.getWorkClockFrom2();
		String contentv4 = time1 + time2;
		String contentv42 = this.convertFrameTime(overTime.getLstFrame());
		// No.417
		String timeNo417 = this.displayTimeNo417(overTime.getTimeNo417(), screenAtr);
		String cont1 = Strings.isBlank(contentv42) ? timeNo417 : Strings.isBlank(timeNo417) ? contentv42 : contentv42 + "　" + timeNo417;
		String setTrue = Strings.isBlank(contentv4) ? cont1 : Strings.isBlank(cont1) ? contentv4 : contentv4 + "　" + cont1;
		String appCt005 = detailSet == 1 ? setTrue : cont1;
		return this.checkAddReason(appCt005, appReason, appReasonDisAtr, screenAtr);
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
	public String getContentOverTimeAf(String companyId, String appId, int detailSet, Integer appReasonDisAtr, String appReason) {
		//XU LY TREN UI
		return "";
	}
	/**
	 * get content absence
	 * 休暇申請 kaf006 - appType = 1
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	@Override
	public String getContentAbsence(String companyId, String appId, Integer appReasonDisAtr, String appReason, int day, int screenAtr) {
        String appContent006 = "";
        AppAbsenceFull absence = appDetailInfoRepo.getAppAbsenceInfo(companyId, appId, day);
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
	public String getContentWorkChange(String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr) {
		AppWorkChangeFull wkChange = appDetailInfoRepo.getAppWorkChangeInfo(companyId, appId);
		String go1 = wkChange.getGoWorkAtr1() == 0 ? "" + I18NText.getText("CMM045_252") + wkChange.getWorkTimeStart1() :
				wkChange.getWorkTimeStart1();
		String back1 = wkChange.getBackHomeAtr1() == 0 ? I18NText.getText("CMM045_252") + wkChange.getWorkTimeEnd1() :
				wkChange.getWorkTimeEnd1();
		String time1 = go1 == "" ? "" : go1 + I18NText.getText("CMM045_100") + back1;
		String go2 = (wkChange.getGoWorkAtr2() == null || wkChange.getGoWorkAtr2() == 1) ? wkChange.getWorkTimeStart2() : 
				"" + I18NText.getText("CMM045_252") + wkChange.getWorkTimeStart2();
		String back2 = (wkChange.getBackHomeAtr2() == null || wkChange.getBackHomeAtr2() == 1) ? wkChange.getWorkTimeEnd2() :
				I18NText.getText("CMM045_252") + wkChange.getWorkTimeEnd2();
		String time2 = go2 == "" ? "" : go2 + I18NText.getText("CMM045_100") + back2;
		String breakTime = wkChange.getBreakTimeStart1() == "" ? "" : I18NText.getText("CMM045_251") +
				wkChange.getBreakTimeEnd1() + I18NText.getText("CMM045_100") + wkChange.getBreakTimeEnd1();
		String workName = wkChange.getWorkTypeName() == "" ? wkChange.getWorkTimeName() : wkChange.getWorkTimeName() == "" ?  
				wkChange.getWorkTypeName() : wkChange.getWorkTypeName() + "　" + wkChange.getWorkTimeName();
		String time = time1 == "" ? time2 : time2 == "" ? time1 : time1 + "　" + time2;
		String cont1 = workName == "" ? time : time == "" ? workName : workName + "　" + time;
        String cont2 = cont1 == "" ? breakTime : breakTime == "" ? cont1 : cont1 + "　" + breakTime;
		return this.checkAddReason(cont2, appReason, appReasonDisAtr, screenAtr);
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
	public String getContentGoBack(String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr) {
		AppGoBackInfoFull goBack = appDetailInfoRepo.getAppGoBackInfo(companyId, appId);
		String go1 = goBack.getGoWorkAtr1() == 1 ? goBack.getWorkTimeStart1() == "" ? I18NText.getText("CMM045_259") + goBack.getWorkTimeStart1() : I18NText.getText("CMM045_259") + "　" + goBack.getWorkTimeStart1() : "";
		String back1 = goBack.getBackHomeAtr1() == 1 ? goBack.getWorkTimeEnd1() == "" ? I18NText.getText("CMM045_260") + goBack.getWorkTimeEnd1() : I18NText.getText("CMM045_260") + "　" + goBack.getWorkTimeEnd1() : "";
		String go2 = goBack.getGoWorkAtr2() != null && goBack.getGoWorkAtr2() == 1 ? goBack.getWorkTimeStart2() == "" ? I18NText.getText("CMM045_259") + goBack.getWorkTimeStart2() : I18NText.getText("CMM045_259") + "　" + goBack.getWorkTimeStart2() : "";
        String back2 = goBack.getBackHomeAtr2() != null && goBack.getBackHomeAtr2() == 1 ? goBack.getWorkTimeEnd2() == "" ? I18NText.getText("CMM045_260") + goBack.getWorkTimeEnd2() : I18NText.getText("CMM045_260") + "　" + goBack.getWorkTimeEnd2() : "";
        String goback1 = go1 == "" ? back1 : back1 == "" ? go1 : go1 + "　" + back1;
        String goback2 = go2 == "" ? back2 : back2 == "" ? go2 : go2 + "　" + back2;
        String gobackA = goback1 == "" ? goback2 : goback2 == "" ? goback1 : goback1 + "　" + goback2;
		return this.checkAddReason(gobackA, appReason, appReasonDisAtr, screenAtr);
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
	public String getContentHdWorkBf(String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr) {
		AppHolidayWorkFull hdWork = appDetailInfoRepo.getAppHolidayWorkInfo(companyId, appId);
		String ca1 = hdWork.getStartTime1() == "" ? "" : hdWork.getStartTime1() + I18NText.getText("CMM045_100") + hdWork.getEndTime1();
		String ca2 = hdWork.getStartTime2() == "" ? "" : hdWork.getStartTime2() + I18NText.getText("CMM045_100") + hdWork.getEndTime2();
		String name = hdWork.getWorkTypeName() == "" ? hdWork.getWorkTimeName() : hdWork.getWorkTimeName() == "" ?
				hdWork.getWorkTypeName() : hdWork.getWorkTypeName() + "　" + hdWork.getWorkTimeName();
		String caF = ca1 == "" ? ca2 : ca2 == "" ? ca1 : ca1 + "　" + ca2;
		String cont1 = name == "" ? caF : caF == "" ? name : name + "　" + caF;
		String appContent010 = cont1 == "" ? this.convertFrameTimeHd(hdWork.getLstFrame()) : cont1 + "　" + this.convertFrameTimeHd(hdWork.getLstFrame());
        //No.417
		String timeNo417 = this.displayTimeNo417(hdWork.getTimeNo417(), screenAtr);
		String appCt010 = appContent010 + timeNo417;
		return this.checkAddReason(appCt010, appReason, appReasonDisAtr, screenAtr);
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
	public String getContentHdWorkAf(String companyId, String appId, Integer appReasonDisAtr, String appReason) {
		//XU LY TREN UI
		return "";
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
		AppStamp appStamp = appStampRepo.findByAppID(companyId, appId);
		if (!Objects.isNull(appStamp)) {
			switch (appStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT: {
				int k = 0;
				boolean checkAppend = false;
				for (val x : appStamp.getAppStampGoOutPermits()) {
					if (x.getStampAtr() == AppStampAtr.GO_OUT) {
						if (k<3)
						content += I18NText.getText("CMM045_232") + " "
								+ I18NText.getText("CMM045_230", x.getStampGoOutAtr().name) + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						k++;
					} else if (x.getStampAtr() == AppStampAtr.CHILDCARE) {
						if (!checkAppend) {
							content += I18NText.getText("CMM045_233") + " ";
							checkAppend = true;
						}
						if (k<2)
						content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						k++;
					} else if (x.getStampAtr() == AppStampAtr.CARE) {
						if (!checkAppend) {
							content += I18NText.getText("CMM045_234") + " ";
							checkAppend = true;
						}
						if (k<2)
						content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
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
					if (k<3)
					content += x.getStampAtr().name + " "
							+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
							+ I18NText.getText("CMM045_100") + " "
							+ (x.getStartTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
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
						if (k<3)
						content += x.getStampAtr().name + " " + (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						k++;
					}
					case CARE: {
						if (k<3)
						content += I18NText.getText("CMM045_234") + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						k++;
					}
					case CHILDCARE: {
						if (k<3)
						content += I18NText.getText("CMM045_233") + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						k++;
					}
					case GO_OUT: {
						if (k<3)
						content += I18NText.getText("CMM045_232") + " "
								+ I18NText.getText("CMM045_230", x.getStampGoOutAtr().name) + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						k++;
					}
					case SUPPORT: {
						if (k<3)
						content += I18NText.getText("CMM045_242") + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
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
		Optional<LateOrLeaveEarly> op_leaveApp = lateLeaveEarlyRepo.findByCode(companyId, appId);
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
	public String getContentCompltLeave(AppCompltLeaveSync compltSync, String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr) {
		AppCompltLeaveSync complt = appDetailInfoRepo.getCompltLeaveSync(companyId, appId);
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
	public String getContentCompltSync(AppCompltLeaveSync compltSync, String companyId, String appId, Integer appReasonDisAtr, String appReason, int type, int screenAtr) {
		if(compltSync == null){
			compltSync = appDetailInfoRepo.getCompltLeaveSync(companyId, appId);
		}
		//振出 rec typeApp = 1
        //振休 abs typeApp = 0
        String content010 = this.convertC(compltSync, appReasonDisAtr, screenAtr);
        return this.checkAddReason(content010, appReason, appReasonDisAtr, screenAtr);
	}
	
	@Override
	public String getContentComplt(String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr) {
		AppCompltLeaveSync complt = appDetailInfoRepo.getCompltLeaveSync(companyId, appId);
		if(complt.isSync()){
            return this.getContentCompltSync(complt, companyId, appId, appReasonDisAtr, appReason, complt.getTypeApp(), screenAtr);
        }else{
            return this.getContentCompltLeave(complt, companyId, appId, appReasonDisAtr, appReason, screenAtr);
        }
	}
	private String displayTimeNo417(TimeNo417 timeNo417, int screenAtr) {
		if (timeNo417 == null) {
			return "";
		}
		if (timeNo417.getTime36() <= 0) {// 「時間外時間の詳細」．36時間 > 0 の場合 追加
			return "";
		}
		if (timeNo417.getNumOfYear36Over() == 0) {// 未超過の場合
			return this.checkCRLF(screenAtr) + I18NText.getText("CMM045_282") + this.convertTime_Short_HM(timeNo417.getTotalOv()) + "　"
					+ I18NText.getText("CMM045_283")
					+ I18NText.getText("CMM045_284", String.valueOf(timeNo417.getNumOfYear36Over()));
		}
		// 超過した場合
		String a1 = I18NText.getText("CMM045_282") + this.convertTime_Short_HM(timeNo417.getTotalOv()) + "　"
				+ I18NText.getText("CMM045_283")
				+ I18NText.getText("CMM045_284", String.valueOf(timeNo417.getNumOfYear36Over()));
		List<Integer> listMY = timeNo417.getLstOverMonth().stream().sorted().collect(Collectors.toList());
		List<Integer> lstMonth = listMY.stream().map(c -> c % 100).collect(Collectors.toList());
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
			if (item.getApplicationTime() != 0) {// 時間外深夜時間
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
        String day = absence.getMournerFlag() == true ? I18NText.getText("CMM045_277") + "　" +  absence.getDay() + I18NText.getText("CMM045_278") : absence.getDay() + I18NText.getText("CMM045_278");
        String name = absence.getWorkTypeName() == "" ? absence.getRelationshipName() : absence.getRelationshipName() == "" ? absence.getWorkTypeName() : absence.getWorkTypeName() + "　" + absence.getRelationshipName();
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
        	if (item.getApplicationTime() != 0) {
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
    //CMM045: viec hien thi reason xu ly tren UI
    //KAF018: khong hien thi reason
    //KDL030: them title truoc khi hien thi reason
    private String checkAddReason(String contentDetail, String appReason, Integer appReasonDisAtr, int screenAtr){
    	String contentFull = "";
    	String addReason = appReasonDisAtr != null && appReasonDisAtr == 1 ? appReason : "";
		if(screenAtr == ScreenAtr.KDL030.value){
			addReason = addReason == "" ? "" : "申請理由：" + this.checkCRLF(screenAtr) + addReason;
			contentFull = contentDetail == "" ? addReason : addReason == "" ? contentDetail : contentDetail + this.checkCRLF(screenAtr) + addReason; 
		}else{//KAF018 + CMM045
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
}
