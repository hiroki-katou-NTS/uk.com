package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
<<<<<<< HEAD
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AllDayHalfDayLeaveAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppCompltLeaveSyncOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
=======
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.application.IApplicationContentService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMailRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
>>>>>>> pj/at/dev/Team_D/KDL034
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationContentServiceImpl implements IApplicationContentService {

	@Inject
	private AppAbsenceRepository absenRepo;

	@Inject
	private GoBackDirectlyRepository goBackRepo;

	@Inject
	private AppHolidayWorkRepository holidayRepo;

	@Inject
	private LateOrLeaveEarlyRepository lateLeaveEarlyRepo;

	@Inject
	private OvertimeRepository overtimeRepo;

	@Inject
	private AppStampRepository appStampRepo;

	@Inject
	private IAppWorkChangeRepository workChangeRepo;

	@Inject
	private ApplicationRepository_New repoApp;

	@Inject
	private AbsenceLeaveAppRepository appLeaveRepo;

	@Inject
	private RecruitmentAppRepository recAppRepo;

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Inject
<<<<<<< HEAD
	private WorkTypeRepository repoWorkType;

	@Inject
	private WorkTimeSettingRepository repoworkTime;

	@Inject
	private ApplicationRepository_New appRepo;
	
	@Inject
	private BPTimeItemRepository repoBonusTime;
	
	@Inject
	private WorkdayoffFrameRepository repoWork;
	
	@Inject
	private OvertimeWorkFrameRepository repoOverTimeFr;
	
	@Override
	public String getApplicationContent(Application_New app) {
		String appReason = app.getAppReason().toString();
		String appID = app.getAppID();
		String companyID = AppContexts.user().companyId();
		switch (app.getAppType()) {
		case OVER_TIME_APPLICATION: {
			/** 残業申請*/
			// OK
			return this.getOverTimeAppContent(app, companyID, appID, appReason);
		}
		case ABSENCE_APPLICATION: {
			/** 休暇申請*/
			// OK
			return this.getAbsenAppContent(app, companyID, appID, appReason);
		}
		case WORK_CHANGE_APPLICATION: {
			/** 勤務変更申請*/
			// OK
			return this.getWorkChangeAppContent(app, companyID, appID, appReason);
		}
		case BUSINESS_TRIP_APPLICATION: {
			/** 出張申請*/
			// TODO
			return this.getBusinessTripContent(app, companyID, appID, appReason);
		}
		case GO_RETURN_DIRECTLY_APPLICATION: {
			/** 直行直帰申請*/
			// OK
			return this.getGoReturnDirectlyAppContent(app, companyID, appID, appReason);
		}
		case BREAK_TIME_APPLICATION: {
			/** 休出時間申請*/
			// OK
			return this.getBreakTimeAppContent(app, companyID, appID, appReason);
		}
		case STAMP_APPLICATION: {
			/** 打刻申請*/
			// OK
			return this.getStampAppContent(app, companyID, appID, appReason);
		}
		case ANNUAL_HOLIDAY_APPLICATION: {
			/** 時間年休申請*/
			// TODO
			return this.getAnnualAppContent(app, companyID, appID, appReason);
		}
		case EARLY_LEAVE_CANCEL_APPLICATION: {
			/** 遅刻早退取消申請*/
			// OK
			return this.getEarlyLeaveAppContent(app, companyID, appID, appReason);
		}
		case COMPLEMENT_LEAVE_APPLICATION: {
			/** 振休振出申請*/
			// OK
			return this.getComplementLeaveAppContent(app, companyID, appID, appReason);
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

	private String getOverTimeAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		String content = "";
		Optional<AppOverTime> op_overTime = overtimeRepo.getAppOvertimeFrame(companyID, appID);
		if (op_overTime.isPresent()) {
			AppOverTime overTime = op_overTime.get();
			switch (app.getPrePostAtr()) {
			case PREDICT: {
				// OK
				content += I18NText.getText("CMM045_268") + " " + clockShorHm(overTime.getWorkClockFrom1())
						+ I18NText.getText("CMM045_100") + clockShorHm(overTime.getWorkClockTo1());
				content += (!Objects.isNull(overTime.getWorkClockFrom2())
						? " " + clockShorHm(overTime.getWorkClockFrom2()) + I18NText.getText("CMM045_100") : "");
				content += (!Objects.isNull(overTime.getWorkClockTo2()) ? clockShorHm(overTime.getWorkClockTo2()) : "");
				String moreInf = "";
				int count = 0;
				int totalWorkUnit = 0;
				if (overTime.getOverTimeShiftNight() > 0) {
					totalWorkUnit += overTime.getOverTimeShiftNight();
					if (count < 3)
						moreInf += I18NText.getText("CMM045_270") + " " + clockShorHm(overTime.getOverTimeShiftNight()) + " ";
					count++;
				}
				if (overTime.getFlexExessTime() > 0) {
					totalWorkUnit += overTime.getFlexExessTime();
					if (count < 3)
						moreInf += I18NText.getText("CMM045_271") + " " + clockShorHm(overTime.getFlexExessTime()) + " ";
					count++;
				}
				for (val x : overTime.getOverTimeInput()) {
					if (x.getApplicationTime().v() > 0) {
						totalWorkUnit += x.getApplicationTime().v();
						if (count < 3) {
							String type = "";
							String cid = AppContexts.user().companyId();
							List<Integer> listFrame = Arrays.asList(x.getFrameNo());
							switch (x.getAttendanceType()) {
							case BONUSPAYTIME: {
								List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(cid, listFrame);
								if (!lstFramBonus.isEmpty()){
									type = lstFramBonus.get(0).getTimeItemName().v();
								}
								break;
							}
							case BONUSSPECIALDAYTIME: {
								type = "特定日加給時間";
								break;
							}
							case BREAKTIME: {
								List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(cid,listFrame);
								if (!lstFramWork.isEmpty()){
									type = lstFramWork.get(0).getWorkdayoffFrName().v();
								}
								break;
							}
							case NORMALOVERTIME: {
								type = "残業時間";
								List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(cid, listFrame);
								if (!lstFramOt.isEmpty()){
									type = lstFramOt.get(0).getOvertimeWorkFrName().v();
								}
								break;
							}
							case RESTTIME: {
								type = "休憩時間";
								break;
							}
							}
							moreInf += type + " " + clockShorHm(x.getApplicationTime().v()) + " ";
						}
						count++;
					}
				}
				String frameInfo = moreInf + (count > 3 ? I18NText.getText("CMM045_231", count - 3 + " ") : "");
				frameInfo = frameInfo.length() > 0 ? frameInfo.substring(0, frameInfo.length() - 1) : frameInfo;
				content += " " + I18NText.getText("CMM045_269") + " " + clockShorHm(totalWorkUnit)
						+ I18NText.getText("CMM045_230", frameInfo);
				break;
			}
			case POSTERIOR: {
				// PRE
				List<Application_New> listPreApp = repoApp.getApp(app.getEmployeeID(), app.getAppDate(),
						PrePostAtr.PREDICT.value, ApplicationType.OVER_TIME_APPLICATION.value);
				Application_New preApp = (listPreApp.size() > 0 ? listPreApp.get(0) : null);
				AppOverTime preOverTime = !Objects.isNull(preApp)
						? overtimeRepo.getAppOvertimeFrame(companyID, preApp.getAppID()).orElse(null) : null;
				if (!Objects.isNull(preOverTime)) {
					content += I18NText.getText("CMM045_272") + " " + I18NText.getText("CMM045_268") + " "
							+ clockShorHm(preOverTime.getWorkClockFrom1()) + I18NText.getText("CMM045_100")
							+ clockShorHm(preOverTime.getWorkClockTo1());
					content += (!Objects.isNull(preOverTime.getWorkClockFrom2())
							? " " + clockShorHm(preOverTime.getWorkClockFrom2()) + I18NText.getText("CMM045_100") : "");
					content += (!Objects.isNull(preOverTime.getWorkClockTo2())
							? clockShorHm(preOverTime.getWorkClockTo2()) : "");
					String moreInf = "";
					int count = 0;
					int totalWorkUnit = 0;
					if (preOverTime.getOverTimeShiftNight() > 0) {
						totalWorkUnit += preOverTime.getOverTimeShiftNight();
						if (count < 3)
							moreInf += I18NText.getText("CMM045_270") + " " + clockShorHm(preOverTime.getOverTimeShiftNight()) + " ";
						count++;
					}
					if (preOverTime.getFlexExessTime() > 0) {
						totalWorkUnit += preOverTime.getFlexExessTime();
						if (count < 3)
							moreInf += I18NText.getText("CMM045_271") + " " + clockShorHm(preOverTime.getFlexExessTime()) + " ";
						count++;
					}
					for (val x : preOverTime.getOverTimeInput()) {
						if (x.getApplicationTime().v() > 0) {
							totalWorkUnit += x.getApplicationTime().v();
							if (count < 3) {
								String type = "";
								String cid = AppContexts.user().companyId();
								List<Integer> listFrame = Arrays.asList(x.getFrameNo());
								switch (x.getAttendanceType()) {
								case BONUSPAYTIME: {
									List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(cid, listFrame);
									if (!lstFramBonus.isEmpty()){
										type = lstFramBonus.get(0).getTimeItemName().v();
									}
									break;
								}
								case BONUSSPECIALDAYTIME: {
									type = "特定日加給時間";
									break;
								}
								case BREAKTIME: {
									List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(cid,listFrame);
									if (!lstFramWork.isEmpty()){
										type = lstFramWork.get(0).getWorkdayoffFrName().v();
									}
									break;
								}
								case NORMALOVERTIME: {
									type = "残業時間";
									List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(cid, listFrame);
									type = lstFramOt.get(0).getOvertimeWorkFrName().v();
									break;
								}
								case RESTTIME: {
									type = "休憩時間";
									break;
								}
								}
								moreInf += type + " " + clockShorHm(x.getApplicationTime().v()) + " ";
							}
							count++;
						}
					}
					String frameInfo = moreInf + (count > 3 ? I18NText.getText("CMM045_231", count - 3 + " ") : "");
					frameInfo = frameInfo.length() > 0 ? frameInfo.substring(0, frameInfo.length() - 1) : frameInfo;
					content += " " + I18NText.getText("CMM045_269") + " " + clockShorHm(totalWorkUnit)
							+ I18NText.getText("CMM045_230", frameInfo);
				}

				// AFTER
				content += "\n" + I18NText.getText("CMM045_274") + " " + I18NText.getText("CMM045_268") + " "
						+ clockShorHm(overTime.getWorkClockFrom1()) + I18NText.getText("CMM045_100")
						+ clockShorHm(overTime.getWorkClockTo1());
				content += (!Objects.isNull(overTime.getWorkClockFrom2())
						? overTime.getWorkClockFrom2() + I18NText.getText("CMM045_100") : "");
				content += (!Objects.isNull(overTime.getWorkClockTo2()) ? overTime.getWorkClockTo2() : "");
				String moreInf = "";
				int count = 0;
				int totalWorkUnit = 0;
				if (overTime.getOverTimeShiftNight() > 0) {
					totalWorkUnit += overTime.getOverTimeShiftNight();
					if (count < 3)
						moreInf += I18NText.getText("CMM045_270") + " " + clockShorHm(overTime.getOverTimeShiftNight()) + " ";
					count++;
				}
				if (overTime.getFlexExessTime() > 0) {
					totalWorkUnit += overTime.getFlexExessTime();
					if (count < 3)
						moreInf += I18NText.getText("CMM045_271") + " " + clockShorHm(overTime.getFlexExessTime()) + " ";
					count++;
				}
				for (val x : overTime.getOverTimeInput()) {
					if (x.getApplicationTime().v() > 0) {
						totalWorkUnit += x.getApplicationTime().v();
						if (count < 3) {
							if (count < 3) {
								String type = "";
								String cid = AppContexts.user().companyId();
								List<Integer> listFrame = Arrays.asList(x.getFrameNo());
								switch (x.getAttendanceType()) {
								case BONUSPAYTIME: {
									List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(cid, listFrame);
									if (!lstFramBonus.isEmpty()){
										type = lstFramBonus.get(0).getTimeItemName().v();
									}
									break;
								}
								case BONUSSPECIALDAYTIME: {
									type = "特定日加給時間";
									break;
								}
								case BREAKTIME: {
									List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(cid,listFrame);
									if (!lstFramWork.isEmpty()){
										type = lstFramWork.get(0).getWorkdayoffFrName().v();
									}
									break;
								}
								case NORMALOVERTIME: {
									type = "残業時間";
									List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(cid, listFrame);
									type = lstFramOt.get(0).getOvertimeWorkFrName().v();
									break;
								}
								case RESTTIME: {
									type = "休憩時間";
									break;
								}
								}
								moreInf += type + " " + clockShorHm(x.getApplicationTime().v()) + " ";
							}
							count++;
						}
					}
				}
				String frameInfo = moreInf + (count > 3 ? I18NText.getText("CMM045_231", count - 3 + " ") : "");
				frameInfo = frameInfo.length() > 0 ? frameInfo.substring(0, frameInfo.length() - 1) : frameInfo;
				content += " " + I18NText.getText("CMM045_269") + " " + clockShorHm(totalWorkUnit)
						+ I18NText.getText("CMM045_230", frameInfo);
			}
			case NONE: {
				
			}
			}
		}
		return content + "\n" + appReason;
	}

	private String getAbsenAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		String content = I18NText.getText("CMM045_279");
		Optional<AppAbsence> op_appAbsen = absenRepo.getAbsenceByAppId(companyID, appID);
		if (op_appAbsen.isPresent()) {
			AppAbsence appAbsen = op_appAbsen.get();
			if (appAbsen.getAllDayHalfDayLeaveAtr() == AllDayHalfDayLeaveAtr.ALL_DAY_LEAVE) {
				String holidayType = "";
				if (Objects.isNull(appAbsen.getAppForSpecLeave())) {
					switch (appAbsen.getHolidayAppType()) {
					case ANNUAL_PAID_LEAVE: {
						holidayType = "年休名称";
						break;
					}
					case SUBSTITUTE_HOLIDAY: {
						holidayType = "代休名称";
						break;
					}
					case REST_TIME: {
						holidayType = "振休名称";
						break;
					}
					case ABSENCE: {
						holidayType = "欠勤名称";
						break;
					}
					case SPECIAL_HOLIDAY: {
						holidayType = "特別休暇名称";
						break;
					}
					case YEARLY_RESERVE: {
						holidayType = "積立年休名称";
						break;
					}
					case HOLIDAY: {
						holidayType = "休日名称";
						break;
					}
					case DIGESTION_TIME: {
						holidayType = "時間消化名称";
						break;
					}
					}
					content += I18NText.getText("CMM045_248") + I18NText.getText("CMM045_230", holidayType);
				} else {
					holidayType = "特別休暇";
					content += holidayType + appAbsen.getAppForSpecLeave().getRelationshipCD().v();
					if (appAbsen.getAppForSpecLeave().isMournerFlag()) {
						content += I18NText.getText("CMM045_277") + (app.getStartDate().isPresent() && app.getEndDate().isPresent()
								? app.getEndDate().get().compareTo(app.getStartDate().get())
										+ I18NText.getText("CMM045_278")
								: "");
					}
				}

=======
	private ContentOfRemandMailRepository remandRepo;

	@Inject
	private UrlEmbeddedRepository urlEmbeddedRepo;

	@Inject 
	private IApplicationContentService appContentService;
	
	@Override
	public MailSenderResult doRemand(String companyID, String appID, Long version, Integer order, String returnReason) {
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		application.setReversionReason(new AppReason(returnReason));
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository
				.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value).get();
		MailSenderResult mailSenderResult = null;
		if (order != null) {
			// 差し戻し先が承認者の場合
			List<String> employeeList = approvalRootStateAdapter.doRemandForApprover(companyID, appID, order);
			if (appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
				mailSenderResult = this.getMailSenderResult(application, employeeList);
>>>>>>> pj/at/dev/Team_D/KDL034
			} else {
				content += I18NText.getText("CMM045_249")
						+ I18NText.getText("CMM045_230", appAbsen.getWorkTimeCode().v())
						+ (!Objects.isNull(appAbsen.getStartTime1())
								? appAbsen.getStartTime1().v() + I18NText.getText("CMM045_100") : "")
						+ (!Objects.isNull(appAbsen.getEndTime1()) ? appAbsen.getEndTime1().v() : "")
						+ (!Objects.isNull(appAbsen.getStartTime2())
								? appAbsen.getStartTime2().v() + I18NText.getText("CMM045_100") : "")
						+ (!Objects.isNull(appAbsen.getEndTime2()) ? appAbsen.getEndTime2().v() : "");
			}
<<<<<<< HEAD
		}
		return content + "\n" + appReason;
	}

	private String getWorkChangeAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		String content = I18NText.getText("CMM045_250");
		Optional<AppWorkChange> op_appWork = workChangeRepo.getAppworkChangeById(companyID, appID);
		if (op_appWork.isPresent()) {
			AppWorkChange appWork = op_appWork.get();
			String workTypeName = "";
			if (!Objects.isNull(appWork.getWorkTypeCd())){
				Optional<WorkType> workType = repoWorkType.findByPK(companyID, appWork.getWorkTypeCd());
				workTypeName = (workType.isPresent() ? workType.get().getName().v() : "");
			}
			String workTimeName = "";
			if (!Objects.isNull(appWork.getWorkTimeCd())){
				Optional<WorkTimeSetting> workTime = repoworkTime.findByCode(companyID, appWork.getWorkTimeCd());
				workTimeName = workTime.isPresent() ? workTime.get().getWorkTimeDisplayName().getWorkTimeName().v() : "";
			}
			content += workTypeName + workTimeName;
			if (!Objects.isNull(appWork.getWorkTimeStart1()) && !Objects.isNull(appWork.getWorkTimeEnd1())) {
				content += !Objects.isNull(appWork.getGoWorkAtr1())
						? (appWork.getGoWorkAtr1() == 1
								? " " + I18NText.getText("CMM045_252") + clockShorHm(appWork.getWorkTimeStart1())
										+ I18NText.getText("CMM045_100") + I18NText.getText("CMM045_252") + clockShorHm(appWork.getWorkTimeEnd1())
								: "")
						: "";
				content += !Objects.isNull(appWork.getGoWorkAtr2())
						? (appWork.getGoWorkAtr2() == 1
								? " " + I18NText.getText("CMM045_252") + clockShorHm(appWork.getWorkTimeStart2())
										+ I18NText.getText("CMM045_100") + I18NText.getText("CMM045_252") + clockShorHm(appWork.getWorkTimeEnd2())
								: "")
						: "";
			}
			if (!Objects.isNull(appWork.getBreakTimeStart1()) && !Objects.isNull(appWork.getBreakTimeEnd1())) {
				content += I18NText.getText("CMM045_251")
						+ (!Objects.isNull(appWork.getBreakTimeStart1()) ? clockShorHm(appWork.getBreakTimeStart1()) + I18NText.getText("CMM045_100")
								: "" + (!Objects.isNull(appWork.getBreakTimeEnd1())
										? clockShorHm(appWork.getBreakTimeEnd1()) : ""));
=======
		} else {
			// 差し戻し先が申請本人の場合
			approvalRootStateAdapter.doRemandForApplicant(companyID, appID);
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.REMAND);
			application.getReflectionInformation().setStateReflection(ReflectedState_New.REMAND);
			if (appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
				mailSenderResult = this.getMailSenderResult(application, Arrays.asList(application.getEmployeeID()));
			} else {
				mailSenderResult = new MailSenderResult(null, null);
>>>>>>> pj/at/dev/Team_D/KDL034
			}
		}

		return content + "\n" + appReason;
	}

<<<<<<< HEAD
	private String getBusinessTripContent(Application_New app, String companyID, String appID, String appReason) {
		// TODO
		String content = I18NText.getText("CMM045_254") + I18NText.getText("CMM045_255");
		return content + "\n" + appReason;
	}

	private String getGoReturnDirectlyAppContent(Application_New app, String companyID, String appID,
			String appReason) {
		// DONE
		Optional<GoBackDirectly> op_appGoBack = goBackRepo.findByApplicationID(companyID, appID);
		String content = I18NText.getText("CMM045_258") + " ";
		if (op_appGoBack.isPresent()) {
			GoBackDirectly appGoBack = op_appGoBack.get();
			content += (appGoBack.getGoWorkAtr1() == UseAtr.USE
					? I18NText.getText("CMM045_259") + appGoBack.getWorkTimeStart1().get() : "")
					+ (appGoBack.getBackHomeAtr1() == UseAtr.USE
							? " " + I18NText.getText("CMM045_260") + appGoBack.getWorkTimeEnd1().get() : "");
			content += (appGoBack.getGoWorkAtr2().isPresent() ? (appGoBack.getGoWorkAtr2().get() == UseAtr.USE
					? " " + I18NText.getText("CMM045_259") + appGoBack.getWorkTimeStart2().get() : "") : "");
			content += (appGoBack.getBackHomeAtr2().isPresent() ? (appGoBack.getBackHomeAtr2().get() == UseAtr.USE
					? " " + I18NText.getText("CMM045_260") + appGoBack.getWorkTimeEnd2().get() : "") : "");
		}
		return content + "\n" + appReason;
	}

	private String getBreakTimeAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		Optional<AppHolidayWork> op_appWork = holidayRepo.getAppHolidayWorkFrame(companyID, appID);
		String content = "";
		if (op_appWork.isPresent()) {
			AppHolidayWork appWork = op_appWork.get();
			appWork.setApplication(appRepo.findByID(companyID, appID).orElse(null));
			if (!Objects.isNull(appWork.getApplication())) {
				switch (appWork.getApplication().getPrePostAtr()) {
				case PREDICT: {
					Optional<WorkType> workType =  repoWorkType.findByPK(companyID, appWork.getWorkTypeCode().v());
					Optional<WorkTimeSetting> workTime = repoworkTime.findByCode(companyID, appWork.getWorkTimeCode().v());
					content += I18NText.getText("CMM045_275") + " " + (Objects.isNull(appWork.getWorkTypeCode()) ? ""
					:  (workType.isPresent() ? " " + workType.get().getName().v() : ""))
							+ (Objects.isNull(appWork.getWorkTimeCode()) ? ""
									: (workTime.isPresent() ? " " + workTime.get()
											.getWorkTimeDisplayName().getWorkTimeName() : "")) + " ";
					if (!Objects.isNull(appWork.getWorkClock1())) {
						if (!Objects.isNull(appWork.getWorkClock1().getStartTime())
								&& !Objects.isNull(appWork.getWorkClock1().getEndTime())) {
							content += clockShorHm(appWork.getWorkClock1().getStartTime().v()) + I18NText.getText("CMM045_100")
									+ clockShorHm(appWork.getWorkClock1().getEndTime().v());
						}
						if (!Objects.isNull(appWork.getWorkClock2().getStartTime())
								&& !Objects.isNull(appWork.getWorkClock2().getEndTime())) {
							content += clockShorHm(appWork.getWorkClock2().getStartTime().v()) + I18NText.getText("CMM045_100")
									+ clockShorHm(appWork.getWorkClock2().getEndTime().v());
						}
						String moreInf = "";
						int count = 0;
						int totalWorkUnit = 0;
						if (!Objects.isNull(appWork.getHolidayWorkInputs())){
							for (val x : appWork.getHolidayWorkInputs()) {
								if (x.getApplicationTime().v() > 0) {
									totalWorkUnit += x.getApplicationTime().v();
									if (count < 3) {
										String type = "";
										String cid = AppContexts.user().companyId();
										List<Integer> listFrame = Arrays.asList(x.getFrameNo());
										switch (x.getAttendanceType()) {
										case BONUSPAYTIME: {
											List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(cid, listFrame);
											if (!lstFramBonus.isEmpty()){
												type = lstFramBonus.get(0).getTimeItemName().v();
											}
											break;
										}
										case BONUSSPECIALDAYTIME: {
											type = "特定日加給時間";
											break;
										}
										case BREAKTIME: {
											List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(cid,listFrame);
											if (!lstFramWork.isEmpty()){
												type = lstFramWork.get(0).getWorkdayoffFrName().v();
											}
											break;
										}
										case NORMALOVERTIME: {
											type = "残業時間";
											List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(cid, listFrame);
											type = lstFramOt.get(0).getOvertimeWorkFrName().v();
											break;
										}
										case RESTTIME: {
											type = "休憩時間";
											break;
										}
										}
										moreInf += type + " " + clockShorHm(x.getApplicationTime().v()) + " ";
									}
									count++;
								}
							}
							String frameInfo = moreInf
									+ (count > 3 ? I18NText.getText("CMM045_230", count - 3 + "") : "");
							content += " " + I18NText.getText("CMM045_276") + clockShorHm(totalWorkUnit)
									+ I18NText.getText("CMM045_230", frameInfo);
						}
					}
					break;
				}
				case POSTERIOR: {
					List<Application_New> listPreApp = repoApp.getApp(app.getEmployeeID(), app.getAppDate(),
							PrePostAtr.PREDICT.value, ApplicationType.OVER_TIME_APPLICATION.value);
					Application_New preApp = (listPreApp.size() > 0 ? listPreApp.get(0) : null);
					AppHolidayWork preAppWork = !Objects.isNull(preApp)
							? holidayRepo.getAppHolidayWork(companyID, preApp.getAppID()).orElse(null) : null;
					if (!Objects.isNull(preAppWork)) {
						Optional<WorkType> workType =  repoWorkType.findByPK(companyID, preAppWork.getWorkTypeCode().v());
						Optional<WorkTimeSetting> workTime = repoworkTime.findByCode(companyID, preAppWork.getWorkTimeCode().v());
						content += I18NText.getText("CMM045_272") + I18NText.getText("CMM045_275") + " " + (Objects.isNull(appWork.getWorkTypeCode()) ? ""
						: (workType.isPresent() ? " " +  workType.get().getName().v() : ""))
								+ (Objects.isNull(appWork.getWorkTimeCode()) ? ""
										: (workTime.isPresent() ? " " +  workTime.get()
												.getWorkTimeDisplayName().getWorkTimeName() : "")) + " ";
						if (!Objects.isNull(preAppWork.getWorkClock1())) {
							if (!Objects.isNull(preAppWork.getWorkClock1().getStartTime())
									&& !Objects.isNull(preAppWork.getWorkClock1().getEndTime())) {
								content += clockShorHm(preAppWork.getWorkClock1().getStartTime().v()) + I18NText.getText("CMM045_100")
										+ clockShorHm(preAppWork.getWorkClock1().getEndTime().v());
							}
							if (!Objects.isNull(preAppWork.getWorkClock2().getStartTime())
									&& !Objects.isNull(preAppWork.getWorkClock2().getEndTime())) {
								content += clockShorHm(preAppWork.getWorkClock2().getStartTime().v()) + I18NText.getText("CMM045_100")
										+ clockShorHm(preAppWork.getWorkClock2().getStartTime().v());
							}
							String moreInf = "";
							int count = 0;
							int totalWorkUnit = 0;
							if (!Objects.isNull(preAppWork.getHolidayWorkInputs())){
								for (val x : preAppWork.getHolidayWorkInputs()) {
									if (x.getApplicationTime().v() > 0) {
										totalWorkUnit += x.getApplicationTime().v();
										if (count < 3) {
											String type = "";
											String cid = AppContexts.user().companyId();
											List<Integer> listFrame = Arrays.asList(x.getFrameNo());
											switch (x.getAttendanceType()) {
											case BONUSPAYTIME: {
												List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(cid, listFrame);
												if (!lstFramBonus.isEmpty()){
													type = lstFramBonus.get(0).getTimeItemName().v();
												}
												break;
											}
											case BONUSSPECIALDAYTIME: {
												type = "特定日加給時間";
												break;
											}
											case BREAKTIME: {
												List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(cid,listFrame);
												if (!lstFramWork.isEmpty()){
													type = lstFramWork.get(0).getWorkdayoffFrName().v();
												}
												break;
											}
											case NORMALOVERTIME: {
												type = "残業時間";
												List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(cid, listFrame);
												type = lstFramOt.get(0).getOvertimeWorkFrName().v();
												break;
											}
											case RESTTIME: {
												type = "休憩時間";
												break;
											}
											}
											moreInf += type + " " + clockShorHm(x.getApplicationTime().v()) + " ";
										}
										count++;
									}
								}
								String frameInfo = moreInf
										+ (count > 3 ? I18NText.getText("CMM045_230", count - 3 + "") : "");
								content += " " + I18NText.getText("CMM045_276") + clockShorHm(totalWorkUnit)
										+ I18NText.getText("CMM045_230", frameInfo);
							}
						}
					}
					Optional<WorkType> workType =  repoWorkType.findByPK(companyID, appWork.getWorkTypeCode().v());
					Optional<WorkTimeSetting> workTime = repoworkTime.findByCode(companyID, appWork.getWorkTimeCode().v());
					content += I18NText.getText("CMM045_275") + " " + (Objects.isNull(appWork.getWorkTypeCode()) ? ""
					:  (workType.isPresent() ? " " + workType.get().getName().v() : ""))
							+ (Objects.isNull(appWork.getWorkTimeCode()) ? ""
									:  (workTime.isPresent() ? " " + workTime.get()
											.getWorkTimeDisplayName().getWorkTimeName() : "")) + " ";
					if (!Objects.isNull(appWork.getWorkClock1())) {
						if (!Objects.isNull(appWork.getWorkClock1().getStartTime())
								&& !Objects.isNull(appWork.getWorkClock1().getEndTime())) {
							content += clockShorHm(appWork.getWorkClock1().getStartTime().v()) + I18NText.getText("CMM045_100")
									+ clockShorHm(appWork.getWorkClock1().getStartTime().v());
						}
						if (!Objects.isNull(appWork.getWorkClock2().getStartTime())
								&& !Objects.isNull(appWork.getWorkClock2().getEndTime())) {
							content += clockShorHm(appWork.getWorkClock2().getStartTime().v()) + I18NText.getText("CMM045_100")
									+ clockShorHm(appWork.getWorkClock2().getStartTime().v());
						}
						String moreInf = "";
						int count = 0;
						int totalWorkUnit = 0;
						if (!Objects.isNull(appWork.getHolidayWorkInputs())){
							for (val x : appWork.getHolidayWorkInputs()) {
								if (x.getApplicationTime().v() > 0) {
									totalWorkUnit += x.getApplicationTime().v();
									if (count < 3) {
										String type = "";
										String cid = AppContexts.user().companyId();
										List<Integer> listFrame = Arrays.asList(x.getFrameNo());
										switch (x.getAttendanceType()) {
										case BONUSPAYTIME: {
											List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(cid, listFrame);
											if (!lstFramBonus.isEmpty()){
												type = lstFramBonus.get(0).getTimeItemName().v();
											}
											break;
										}
										case BONUSSPECIALDAYTIME: {
											type = "特定日加給時間";
											break;
										}
										case BREAKTIME: {
											List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(cid,listFrame);
											if (!lstFramWork.isEmpty()){
												type = lstFramWork.get(0).getWorkdayoffFrName().v();
											}
											break;
										}
										case NORMALOVERTIME: {
											type = "残業時間";
											List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(cid, listFrame);
											type = lstFramOt.get(0).getOvertimeWorkFrName().v();
											break;
										}
										case RESTTIME: {
											type = "休憩時間";
											break;
										}
										}
										moreInf += type + " " + clockShorHm(x.getApplicationTime().v()) + " ";
									}
									count ++;
								}
							}
							String frameInfo = moreInf
									+ (count > 3 ? I18NText.getText("CMM045_230", count - 3 + "") : "");
							content += " " + I18NText.getText("CMM045_276") + clockShorHm(totalWorkUnit)
									+ I18NText.getText("CMM045_230", frameInfo);
						}
					}
				}
				case NONE: {
					
				}
				}
			}
		}
		return content + "\n" + appReason;
	}

	private String getStampAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		String content = "";
		AppStamp appStamp = appStampRepo.findByAppID(companyID, appID);
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
		return content + "\n" + appReason;
	}

	private String getAnnualAppContent(Application_New app, String companyID, String appID, String appReason) {
		// TODO
		String content = I18NText.getText("CMM045_264") + "\n" + appReason;
		return content;
	}

	private String getEarlyLeaveAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		String content = "";
		Optional<LateOrLeaveEarly> op_leaveApp = lateLeaveEarlyRepo.findByCode(companyID, appID);
		if (op_leaveApp.isPresent()) {
			LateOrLeaveEarly leaveApp = op_leaveApp.get();
			if (leaveApp.getActualCancelAtr() == 0) {
				if (app.getPrePostAtr().value == 0) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: I18NText.getText("CMM045_246") + leaveApp.getLateTime1().toString())
							+ (leaveApp.getLate1().value == 0 ? ""
									: I18NText.getText("CMM045_247") + leaveApp.getEarlyTime1().toString())
							+ (leaveApp.getEarly2().value == 0 ? ""
									: I18NText.getText("CMM045_246") + leaveApp.getLateTime2().toString())
							+ (leaveApp.getLate2().value == 0 ? ""
									: I18NText.getText("CMM045_247") + leaveApp.getEarlyTime2().toString());
				} else if (app.getPrePostAtr().value == 1) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: I18NText.getText("CMM045_246") + leaveApp.getLateTime1().toString())
							+ (leaveApp.getLate1().value == 0 ? ""
									: I18NText.getText("CMM045_247") + leaveApp.getEarlyTime1().toString())
							+ (leaveApp.getEarly2().value == 0 ? ""
									: I18NText.getText("CMM045_246") + leaveApp.getLateTime2().toString())
							+ (leaveApp.getLate2().value == 0 ? ""
									: I18NText.getText("CMM045_247") + leaveApp.getEarlyTime2().toString());
				}
			} else {
				if (leaveApp.getActualCancelAtr() == 0) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_246") + leaveApp.getLateTime1().toString())
							+ (leaveApp.getLate1().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_247") + leaveApp.getEarlyTime1().toString())
							+ (leaveApp.getEarly2().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_246") + leaveApp.getLateTime2().toString())
							+ (leaveApp.getLate2().value == 0 ? ""
									: "×" + I18NText.getText("CMM045_247") + leaveApp.getEarlyTime2().toString());
				}
=======
	@Override
	public MailSenderResult getMailSenderResult(Application_New application, List<String> employeeList) {
		String mailTitle = "";
		String mailBody = "";
		String cid = AppContexts.user().companyId();
		String appContent = appContentService.getApplicationContent(application);	
		ContentOfRemandMail remandTemp = remandRepo.getRemandMailById(cid).orElse(null);
		if (!Objects.isNull(remandTemp)) {
			mailTitle = remandTemp.getMailTitle().v();
			mailBody = remandTemp.getMailBody().v();
		}
		String emp = employeeAdapter.empEmail(AppContexts.user().employeeId());
		if (Strings.isEmpty(emp)) {
			emp = employeeAdapter.getEmployeeName(AppContexts.user().employeeId());
		}
		Optional<UrlEmbedded> urlEmbedded = urlEmbeddedRepo.getUrlEmbeddedById(AppContexts.user().companyId());
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		for (String employee : employeeList) {
			String employeeName = employeeAdapter.getEmployeeName(employee);
			// Using RQL 419 instead (1 not have mail)
			String employeeMail = employeeAdapter.empEmail(employee);
			employeeMail = "hiep.ld@3si.vn";
			// TODO
			String urlInfo = "";
			if (urlEmbedded.isPresent()) {
				int urlEmbeddedCls = urlEmbedded.get().getUrlEmbedded().value;
				NotUseAtr checkUrl = NotUseAtr.valueOf(urlEmbeddedCls);
				if (checkUrl == NotUseAtr.USE) {
					urlInfo = registerEmbededURL.obtainApplicationEmbeddedUrl(application.getAppID(),
							application.getAppType().value, application.getPrePostAtr().value, employee);
				}
			}
			if (!Strings.isBlank(urlInfo)) {
				appContent += "\n" + I18NText.getText("KDL030_30") + " " + application.getAppID() + "\n" + urlInfo;
			}
			String mailContentToSend = I18NText.getText("Msg_1060",
					employeeAdapter.getEmployeeName(AppContexts.user().employeeId()), mailBody,
					GeneralDate.today().toString(), application.getAppType().nameId,
					employeeAdapter.getEmployeeName(application.getEmployeeID()),
					application.getAppDate().toLocalDate().toString(), appContent,
					employeeAdapter.getEmployeeName(AppContexts.user().employeeId()), emp);
			if (Strings.isBlank(employeeMail)) {
				errorList.add(I18NText.getText("Msg_768", employeeName));
				continue;
			} else {
				// TODO
				mailsender.send("mailadmin@uk.com", employeeMail,
						new MailContents(mailTitle, mailContentToSend));
				successList.add(employeeName);
>>>>>>> pj/at/dev/Team_D/KDL034
			}
		}
		return content + "\n" + appReason;
	}

	private String getComplementLeaveAppContent(Application_New app, String companyID, String appID, String appReason) {
		// DONE
		AbsenceLeaveApp leaveApp = null;
		RecruitmentApp recApp = null;
		String content = "";
		AppCompltLeaveSyncOutput sync = otherCommonAlgorithm.getAppComplementLeaveSync(companyID, appID);
		if (!sync.isSync()) {
			if (sync.getType() == 0) {
				leaveApp = appLeaveRepo.findByAppId(appID).orElse(null);
				if (!Objects.isNull(leaveApp)) {
					Optional<WorkType> workType = repoWorkType.findByPK(companyID, leaveApp.getWorkTypeCD().v());
					content += I18NText.getText("CMM045_262") + " " + app.getAppDate().toString("MM/dd")
							+ (workType.isPresent() ? I18NText.getText("CMM045_230", workType.get().getName().v()) : "") ;
					if (!Objects.isNull(leaveApp.getWorkTime1()) && !Objects.isNull(leaveApp.getWorkTime2())) {
						content += clockShorHm(leaveApp.getWorkTime1().getStartTime().v())
								+ I18NText.getText("CMM045_100")
								+ clockShorHm(leaveApp.getWorkTime1().getEndTime().v());
					}
				}

			} else {
				recApp = recAppRepo.findByAppId(appID).orElse(null);
				if (!Objects.isNull(recApp)) {
					Optional<WorkType> workType = repoWorkType.findByPK(companyID, recApp.getWorkTypeCD().v());
					content += I18NText.getText("CMM045_263") + " " + app.getAppDate().toString("MM/dd")
							+ (workType.isPresent() ? I18NText.getText("CMM045_230", workType.get().getName().v()) : "") ;
					if (!Objects.isNull(recApp.getWorkTime1()) && !Objects.isNull(recApp.getWorkTime2())) {
						content += clockShorHm(recApp.getWorkTime1().getStartTime().v())
								+ I18NText.getText("CMM045_100") + clockShorHm(recApp.getWorkTime1().getEndTime().v());
					}
				}
			}
		} else {
			leaveApp = appLeaveRepo.findByAppId(sync.getAbsId()).orElse(null);
			recApp = recAppRepo.findByAppId(sync.getRecId()).orElse(null);
			if (!Objects.isNull(leaveApp) && !Objects.isNull(recApp)) {
				Optional<Application_New> leaveAppInfo = appRepo.findByID(companyID, leaveApp.getAppID());
				Optional<Application_New> recAppInfo = appRepo.findByID(companyID, recApp.getAppID());
				if (recAppInfo.isPresent()){
					Optional<WorkType> workType = repoWorkType.findByPK(companyID, recApp.getWorkTypeCD().v());
					content += I18NText.getText("CMM045_262") + " " + recAppInfo.get().getAppDate().toString("MM/dd") 
							+ (workType.isPresent() ? I18NText.getText("CMM045_230", workType.get().getName().v()) : "") ;
					if (!Objects.isNull(recApp.getWorkTime1().getStartTime().v())
							&& !Objects.isNull(recApp.getWorkTime1().getEndTime().v())) {
						content += " " + clockShorHm(recApp.getWorkTime1().getStartTime().v())
								+ I18NText.getText("CMM045_100") + clockShorHm(recApp.getWorkTime1().getEndTime().v());
					}
				}
				if (leaveAppInfo.isPresent()){
					Optional<WorkType> workType = repoWorkType.findByPK(companyID, leaveApp.getWorkTypeCD().v());
					content += "\n" + I18NText.getText("CMM045_263") + " " + leaveAppInfo.get().getAppDate().toString("MM/dd")
							+ (workType.isPresent() ? I18NText.getText("CMM045_230", workType.get().getName().v()) : "") ;
					if (!Objects.isNull(leaveApp.getWorkTime1().getStartTime().v())
							&& !Objects.isNull(leaveApp.getWorkTime1().getEndTime().v())) {
						content += " " + clockShorHm(leaveApp.getWorkTime1().getStartTime().v())
								+ I18NText.getText("CMM045_100") + clockShorHm(leaveApp.getWorkTime1().getEndTime().v());
					}
				}
			}
		}
		return content + "\n" + appReason;
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

	private String clockShorHm(Integer minute) {
		return (minute / 60 + ":" + (minute % 60 < 10 ? "0" + minute % 60 : minute % 60));
	}
}
