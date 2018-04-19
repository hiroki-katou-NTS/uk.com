package nts.uk.ctx.at.request.dom.application;

import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
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
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hiep.ld Pending
 */
@Stateless
public class Application_New1 extends Application_New {
	@Inject
	private AppAbsenceRepository absenRepo;

	@Inject
	private GoBackDirectlyRepository goBackRepo;

	@Inject
	private HolidayInstructRepository holidayRepo;

	@Inject
	private LateOrLeaveEarlyRepository lateLeaveEarlyRepo;

	@Inject
	private OvertimeRepository overtimeRepo;

	@Inject
	private AppStampRepository appStampRepo;

	@Inject
	private IAppWorkChangeRepository workChangeRepo;

	public Application_New1(Long version, String companyID, String appID, PrePostAtr prePostAtr,
			GeneralDateTime inputDate, String enteredPersonID, AppReason reversionReason, GeneralDate appDate,
			AppReason appReason, ApplicationType appType, String employeeID, Optional<GeneralDate> startDate,
			Optional<GeneralDate> endDate, ReflectionInformation_New reflectionInformation) {
		super(version, companyID, appID, prePostAtr, inputDate, enteredPersonID, reversionReason, appDate, appReason,
				appType, employeeID, startDate, endDate, reflectionInformation);
		// TODO Auto-generated constructor stub
	}

	public String getAppContent() {
		// TO-DO
		String appReason = this.getAppReason().toString();
		ApplicationType appType = this.getAppType();
		String appID = this.getAppID();
		String companyID = AppContexts.user().companyId();
		switch (this.getAppType()) {
		case OVER_TIME_APPLICATION: {
			return this.getOverTimeAppContent(companyID, appID, appReason);
		}
		case ABSENCE_APPLICATION: {
			return this.getAbsenAppContent(companyID, appID, appReason);
		}
		case WORK_CHANGE_APPLICATION: {
			return this.getWorkChangeAppContent(companyID, appID, appReason);
		}
		case BUSINESS_TRIP_APPLICATION: {
			return this.getBreakTimeAppContent(companyID, appID, appReason);
		}
		case GO_RETURN_DIRECTLY_APPLICATION: {
			return this.getGoReturnDirectlyAppContent(companyID, appID, appReason);
		}
		case BREAK_TIME_APPLICATION: {
			return this.getBreakTimeAppContent(companyID, appID, appReason);
		}
		case STAMP_APPLICATION: {
			return this.getStampAppContent(companyID, appID, appReason);
		}
		case ANNUAL_HOLIDAY_APPLICATION: {
			return this.getAnnualAppContent(companyID, appID, appReason);
		}
		case EARLY_LEAVE_CANCEL_APPLICATION: {
			return getEarlyLeaveAppContent(companyID, appID, appReason);

		}
		case COMPLEMENT_LEAVE_APPLICATION: {
			return this.getComplementLeaveAppContent(companyID, appID, appReason);
		}
		case STAMP_NR_APPLICATION: {
			return this.getStampNrAppContent(companyID, appID, appReason);
		}
		case LONG_BUSINESS_TRIP_APPLICATION: {
			return this.getLongBusinessTripAppContent(companyID, appID, appReason);
		}
		case BUSINESS_TRIP_APPLICATION_OFFICE_HELPER: {
			return this.getBusinessTripOfficeAppContent(companyID, appID, appReason);
		}
		case APPLICATION_36: {
			return this.getApp36AppContent(companyID, appID, appReason);
		}
		}
		return null;
	}

	private String getOverTimeAppContent(String companyID, String appID, String appReason) {
		String content = "";
		Optional<AppOverTime> op_overTime = overtimeRepo.getAppOvertimeFrame(companyID, appID);
		if (op_overTime.isPresent()) {
			AppOverTime overTime = op_overTime.get();
			content = "";
			switch (this.getPrePostAtr()) {
			case PREDICT: {
				content += I18NText.getText("CMM045_268") + " " + clockShorHm(overTime.getWorkClockFrom1())
						+ I18NText.getText("CMM045_100") + clockShorHm(overTime.getWorkClockTo1());
				String moreInf = "";
				int count = 0;
				int totalWorkUnit = 0;
				for (val x : overTime.getOverTimeInput()) {
					if (x.getApplicationTime().v() > 0) {
						totalWorkUnit += x.getApplicationTime().v();
						if (count < 3)
							moreInf += x.getApplicationTime().v() + " ";
						count++;
					}
				}
				if (overTime.getOverTimeShiftNight() > 0) {
					totalWorkUnit += overTime.getOverTimeShiftNight();
					if (count < 3)
						moreInf += clockShorHm(overTime.getOverTimeShiftNight()) + " ";
					count++;
				}
				if (overTime.getFlexExessTime() > 0) {
					totalWorkUnit += overTime.getFlexExessTime();
					if (count < 3)
						moreInf += clockShorHm(overTime.getFlexExessTime()) + " ";
					count++;
				}
				String otherFrame = "";
				content += "残業合計" + totalWorkUnit + "（" + moreInf + (count>3 ? I18NText.getText("CMM045_279", count-3 + "") : "") + "）" + appReason;
				break;
			}
			case POSTERIOR: {
				// TODO
			}
			}

			// app.prePostAtr == 0 ? '事前' : '事後';
		}
		return content + "\n" + appReason;
	}

	private String getAbsenAppContent(String companyID, String appID, String appReason) {

		String content = I18NText.getText("CMM045_279");
		Optional<AppAbsence> op_appAbsen = absenRepo.getAbsenceByAppId(companyID, appID);
		if (op_appAbsen.isPresent()) {
			AppAbsence appAbsen = op_appAbsen.get();
			// TO-DO
			// content += I18NText.getText("CMM045_248") +
			// I18NText.getText("CMM045_230", appAbsen.get);
		}
		return content + "\n" + appReason;
	}

	private String getWorkChangeAppContent(String companyID, String appID, String appReason) {
		String content = I18NText.getText("CMM045_250");
		Optional<AppWorkChange> op_appWork = workChangeRepo.getAppworkChangeById(companyID, appID);
		if (op_appWork.isPresent()) {
			AppWorkChange appWork = op_appWork.get();
			if (appWork.getWorkTimeStart1() != 0 || appWork.getWorkTimeEnd1() != 0) {
				content += I18NText.getText("CMM045_252");
			}
		}

		return content + "\n" + appReason;
	}

	private String getBusinessTripContent(String companyID, String appID, String appReason) {
		String content = I18NText.getText("CMM045_254") + "\n" + appReason;
		return content;
	}

	private String getGoReturnDirectlyAppContent(String companyID, String appID, String appReason) {
		Optional<GoBackDirectly> op_appGoBack = goBackRepo.findByApplicationID(companyID, appID);
		String content = I18NText.getText("CMM045_258") + "\n" + appReason;
		if (op_appGoBack.isPresent()) {
			GoBackDirectly appGoBack = op_appGoBack.get();
			content += I18NText.getText("CMM045_259") + I18NText.getText("CMM045_258");
		}
		return content;
	}

	private String getBreakTimeAppContent(String companyID, String appID, String appReason) {
		return appReason;
	}

	private String getStampAppContent(String companyID, String appID, String appReason) {
		String content = "";
		AppStamp appStamp = appStampRepo.findByAppID(companyID, appID);
		if (!Objects.isNull(appStamp)) {
			switch (appStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT: {
				int k = 0;
				boolean checkAppend = false;
				for (val x : appStamp.getAppStampGoOutPermits()) {
					if (x.getStampAtr() == AppStampAtr.GO_OUT) {
						content += I18NText.getText("CMM045_232") + " "
								+ I18NText.getText("CMM045_230", x.getStampGoOutAtr().name) + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						if (k == 2)
							break;
						k++;
					} else if (x.getStampAtr() == AppStampAtr.CHILDCARE) {
						if (!checkAppend) {
							content += I18NText.getText("CMM045_233") + " ";
							checkAppend = true;
						}
						content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						if (k == 1)
							break;
						k++;
					} else if (x.getStampAtr() == AppStampAtr.CARE) {
						if (!checkAppend) {
							content += I18NText.getText("CMM045_234") + " ";
							checkAppend = true;
						}
						content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
						if (k == 1)
							break;
						k++;
					}
				}
				content += (appStamp.getAppStampGoOutPermits().size() - k > 0
						? I18NText.getText("CMM045_231", (appStamp.getAppStampGoOutPermits().size() - k) + "") : "");
				break;
			}
			case STAMP_WORK: {
				int k = 0;
				content += I18NText.getText("CMM045_235") + " ";
				for (val x : appStamp.getAppStampWorks()) {
					if (k == 2)
						break;
					k++;
					content += x.getStampAtr().name + " "
							+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
							+ I18NText.getText("CMM045_100") + " "
							+ (x.getStartTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
				}
				content += (appStamp.getAppStampGoOutPermits().size() - k > 0
						? I18NText.getText("CMM045_231", (appStamp.getAppStampGoOutPermits().size() - k) + "") : "");
				break;
			}
			case STAMP_ONLINE_RECORD: {
				// TO-DO
				int k = 0;
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
						if (k == 2)
							break;
						k++;
						content += x.getStampAtr().name + " ";
						content += (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
					}
					case CARE: {
						if (k == 2)
							break;
						k++;
						content += I18NText.getText("CMM045_234") + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
					}
					case CHILDCARE: {
						if (k == 2)
							break;
						k++;
						content += I18NText.getText("CMM045_233") + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
					}
					case GO_OUT: {
						if (k == 2)
							break;
						k++;
						content += I18NText.getText("CMM045_232") + " "
								+ I18NText.getText("CMM045_230", x.getStampGoOutAtr().name) + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
					}
					case SUPPORT: {
						if (k == 2)
							break;
						k++;
						content += I18NText.getText("CMM045_242") + " "
								+ (x.getStartTime().isPresent() ? x.getStartTime().get().toString() : "") + " "
								+ I18NText.getText("CMM045_100") + " "
								+ (x.getEndTime().isPresent() ? x.getEndTime().get().toString() : "") + " ";
					}
					}
				}
				content += (appStamp.getAppStampGoOutPermits().size() - k > 0
						? I18NText.getText("CMM045_231", (appStamp.getAppStampGoOutPermits().size() - k) + "") : "");
				break;
			}
			}
		}
		return content + "\n" + appReason;
	}

	private String getAnnualAppContent(String companyID, String appID, String appReason) {
		String content = I18NText.getText("CMM045_264") + "\n" + appReason;
		return content;
	}

	private String getEarlyLeaveAppContent(String companyID, String appID, String appReason) {
		String content = "";
		Optional<LateOrLeaveEarly> op_leaveApp = lateLeaveEarlyRepo.findByCode(companyID, appID);
		if (op_leaveApp.isPresent()) {
			LateOrLeaveEarly leaveApp = op_leaveApp.get();
			if (leaveApp.getActualCancelAtr() == 0) {
				if (this.getPrePostAtr().value == 0) {
					content += I18NText.getText("CMM045_243")
							+ (leaveApp.getEarly1().value == 0 ? ""
									: I18NText.getText("CMM045_246") + leaveApp.getLateTime1().toString())
							+ (leaveApp.getLate1().value == 0 ? ""
									: I18NText.getText("CMM045_247") + leaveApp.getEarlyTime1().toString())
							+ (leaveApp.getEarly2().value == 0 ? ""
									: I18NText.getText("CMM045_246") + leaveApp.getLateTime2().toString())
							+ (leaveApp.getLate2().value == 0 ? ""
									: I18NText.getText("CMM045_247") + leaveApp.getEarlyTime2().toString());
				} else if (this.getPrePostAtr().value == 1) {
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
			}
		}
		return content + "\n" + appReason;
	}

	private String getComplementLeaveAppContent(String companyID, String appID, String appReason) {
		return appReason;
	}

	private String getStampNrAppContent(String companyID, String appID, String appReason) {
		return appReason;
	}

	private String getLongBusinessTripAppContent(String companyID, String appID, String appReason) {
		return null;
	}

	private String getBusinessTripOfficeAppContent(String companyID, String appID, String appReason) {
		// TODO
		return appReason;
	}

	private String getApp36AppContent(String companyID, String appID, String appReason) {
		// TODO
		return appReason;
	}

	private String clockShorHm(Integer minute) {
		return (minute / 60 + ":" + (minute % 60 < 10 ? "0" + minute % 60 : minute % 60));
	}
}
