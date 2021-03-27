package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.reflect.convert;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.ApplyforSpecialLeaveShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.HolidayAppTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.ReflectFreeTimeAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.RelationshipCDPrimitiveShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.RelationshipReasonPrimitiveShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.SupplementInfoVacationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.VacationRequestInfoShare;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.AppReasonShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.AppStandardReasonCodeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationDateShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.DailyAttendanceUpdateStatusShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReasonForReversionShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReasonNotReflectDailyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReasonNotReflectShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReflectionStatusOfDayShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReflectionStatusShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateCancelationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateOrEarlyAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.TimeReportShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOvertimeDetailShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AttendanceTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.DivergenceReasonShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.NumberOfMonthShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OverTimeShiftNightShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OvertimeAppAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ReasonDivergenceShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeAnnualShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeMonthShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeUpperLimitAverageShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeUpperLimitMonthShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeUpperLimitPerMonthShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.Time36AgreeUpperLimitShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppRecordImageShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.DestinationTimeAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.DestinationTimeZoneAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppOtherShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;

public class ConvertApplicationToShare {

	public static ApplicationShare toAppliction(Application application) {

		ApplicationShare appShare = new ApplicationShare(application.getVersion(), application.getAppID(),
				PrePostAtrShare.valueOf(application.getPrePostAtr().value), application.getEmployeeID(),
				ApplicationTypeShare.valueOf(application.getAppType().value),
				new ApplicationDateShare(application.getAppDate().getApplicationDate()),
				application.getEnteredPersonID(), application.getInputDate(),
				convertReflectStatus(application.getReflectionStatus()));
		appShare.setOpStampRequestMode(
				application.getOpStampRequestMode().map(x -> StampRequestModeShare.valueOf(x.value)));

		appShare.setOpReversionReason(application.getOpReversionReason().map(x -> new ReasonForReversionShare(x.v())));

		appShare.setOpAppStartDate(
				application.getOpAppStartDate().map(x -> new ApplicationDateShare(x.getApplicationDate())));

		appShare.setOpAppEndDate(
				application.getOpAppEndDate().map(x -> new ApplicationDateShare(x.getApplicationDate())));

		appShare.setOpAppReason(application.getOpAppReason().map(x -> new AppReasonShare(x.v())));

		appShare.setOpAppStandardReasonCD(
				application.getOpAppStandardReasonCD().map(x -> new AppStandardReasonCodeShare(x.v())));

		return appDetail(appShare, application);
	}

	private static ReflectionStatusShare convertReflectStatus(ReflectionStatus reflect) {

		return new ReflectionStatusShare(reflect.getListReflectionStatusOfDay().stream().map(x -> {
			return new ReflectionStatusOfDayShare(ReflectedStateShare.valueOf(x.getActualReflectStatus().value),
					ReflectedStateShare.valueOf(x.getScheReflectStatus().value), x.getTargetDate(),
					updateStatus(x.getOpUpdateStatusAppReflect()), updateStatus(x.getOpUpdateStatusAppCancel()));
		}).collect(Collectors.toList()));
	}

	private static Optional<DailyAttendanceUpdateStatusShare> updateStatus(
			Optional<DailyAttendanceUpdateStatus> status) {

		if (!status.isPresent())
			return Optional.empty();

		return Optional.of(new DailyAttendanceUpdateStatusShare(status.get().getOpActualReflectDateTime(),
				status.get().getOpScheReflectDateTime(),
				status.get().getOpReasonActualCantReflect().map(x -> ReasonNotReflectDailyShare.valueOf(x.value)),
				status.get().getOpReasonScheCantReflect().map(x -> ReasonNotReflectShare.valueOf(x.value))));
	}

	private static ApplicationShare appDetail(ApplicationShare appShare, Application application) {

		switch (application.getAppType()) {
		case OVER_TIME_APPLICATION:

			AppOverTime appOver = (AppOverTime) application;

			// 申請時間
			ApplicationTimeShare appTimeShare = converAppTime(appOver.getApplicationTime());

			// 時間外時間の詳細
			val detailOverTimeOp = appOver.getDetailOverTimeOp().map(x ->convertAppOverDetail(x));

			AppOverTimeShare overShare = new AppOverTimeShare(
					EnumAdaptor.valueOf(appOver.getOverTimeClf().value, OvertimeAppAtrShare.class), appTimeShare,
					appOver.getBreakTimeOp().orElse(new ArrayList<>()),
					appOver.getWorkHoursOp().orElse(new ArrayList<>()), appOver.getWorkInfoOp(), detailOverTimeOp);
			overShare.setApplication(appShare);
			return overShare;

		case ABSENCE_APPLICATION:
			ApplyForLeave appAbsence = (ApplyForLeave) application;
			val reflectFreeTimeDom = appAbsence.getReflectFreeTimeApp();
			ReflectFreeTimeAppShare reflectFreeTimeApp = new ReflectFreeTimeAppShare(
					reflectFreeTimeDom.getWorkingHours().orElse(new ArrayList<>()),
					reflectFreeTimeDom.getTimeDegestion().map(x -> convertTimeDigest(x)),
					reflectFreeTimeDom.getWorkInfo(), reflectFreeTimeDom.getWorkChangeUse());
			val vacationInfoDom = appAbsence.getVacationInfo();

			VacationRequestInfoShare vacationInfo = new VacationRequestInfoShare(
					EnumAdaptor.valueOf(vacationInfoDom.getHolidayApplicationType().value, HolidayAppTypeShare.class),
					new SupplementInfoVacationShare(vacationInfoDom.getInfo().getDatePeriod(),
							vacationInfoDom.getInfo().getApplyForSpeLeaveOptional().map(x -> {
								return new ApplyforSpecialLeaveShare(x.isMournerFlag(),
										x.getRelationshipCD().map(y -> new RelationshipCDPrimitiveShare(y.v())),
										x.getRelationshipReason()
												.map(y -> new RelationshipReasonPrimitiveShare(y.v())));
							})));
			return new ApplyForLeaveShare(appShare, reflectFreeTimeApp, vacationInfo);

		case WORK_CHANGE_APPLICATION:
			AppWorkChange appWCh = (AppWorkChange) application;
			return new AppWorkChangeShare(appWCh.getStraightGo(), appWCh.getStraightBack(), appWCh.getOpWorkTypeCD(),
					appWCh.getOpWorkTimeCD(), appWCh.getTimeZoneWithWorkNoLst(), appShare);

		case BUSINESS_TRIP_APPLICATION:
			BusinessTrip bussinessTrip = (BusinessTrip) application;

			return new BusinessTripShare(
					bussinessTrip.getInfos().stream()
							.map(x -> new BusinessTripInfoShare(x.getWorkInformation(), x.getDate(),
									x.getWorkingHours()))
							.collect(Collectors.toList()),
					bussinessTrip.getDepartureTime().map(x -> x.v()).orElse(null),
					bussinessTrip.getReturnTime().map(x -> x.v()).orElse(null), appShare);

		case GO_RETURN_DIRECTLY_APPLICATION:
			GoBackDirectly goBack = (GoBackDirectly) application;
			return new GoBackDirectlyShare(goBack.getStraightDistinction(), goBack.getStraightLine(),
					goBack.getIsChangedWork(), goBack.getDataWork(), appShare);

		case HOLIDAY_WORK_APPLICATION:
			// AppHolidayWorkShare
			AppHolidayWork appHolWork = (AppHolidayWork) application;

			return new AppHolidayWorkShare(appShare, 
					appHolWork.getWorkInformation(), 
					converAppTime(appHolWork.getApplicationTime()),
					appHolWork.getBackHomeAtr() == NotUseAtr.USE, 
					appHolWork.getGoWorkAtr() == NotUseAtr.USE,
					appHolWork.getBreakTimeList().orElse(new ArrayList<>()), 
					appHolWork.getWorkingTimeList().orElse(new ArrayList<>()), 
					appHolWork.getAppOvertimeDetail().map(x -> convertAppOverDetail(x)));

		case STAMP_APPLICATION:
			if (!application.getOpStampRequestMode().isPresent()
					|| application.getOpStampRequestMode().get() == StampRequestMode.STAMP_ADDITIONAL) {
				// AppStampShare
				AppStamp appStamp = (AppStamp) application;
				return new AppStampShare(appStamp.getListTimeStampApp().stream().map(x -> {
					return new TimeStampAppShare(converDesTimeApp(x.getDestinationTimeApp()), x.getTimeOfDay(),
							x.getWorkLocationCd().map(y -> new WorkLocationCD(y.v())), x.getAppStampGoOutAtr());
				}).collect(Collectors.toList()), // 時刻

						appStamp.getListDestinationTimeApp().stream().map(y -> converDesTimeApp(y))
								.collect(Collectors.toList()), // 時刻の取消

						appStamp.getListTimeStampAppOther().stream().map(x -> {
							return new TimeStampAppOtherShare(new DestinationTimeZoneAppShare(
									x.getDestinationTimeZoneApp().getTimeZoneStampClassification().value,
									x.getDestinationTimeZoneApp().getEngraveFrameNo()), x.getTimeZone());
						}).collect(Collectors.toList()), // 時間帯

						appStamp.getListDestinationTimeZoneApp().stream().map(x -> {
							return new DestinationTimeZoneAppShare(
									TimeZoneStampClassificationShare.valueOf(x.getTimeZoneStampClassification().value),
									x.getEngraveFrameNo());
						}).collect(Collectors.toList()), // 時間帯の取消
						appShare);
			} else {
				// レコーダイメージ申請
				AppRecordImage appImg = (AppRecordImage) application;

				return new AppRecordImageShare(EngraveShareAtr.valueOf(appImg.getAppStampCombinationAtr().value),
						appImg.getAttendanceTime(), appImg.getAppStampGoOutAtr(), appShare);
			}

		case ANNUAL_HOLIDAY_APPLICATION:
			TimeLeaveApplication appTimeLeav = (TimeLeaveApplication) application;

			return new TimeLeaveApplicationShare(appShare, appTimeLeav.getLeaveApplicationDetails().stream().map(x -> {
				return new TimeLeaveApplicationDetailShare(x.getAppTimeType(), x.getTimeZoneWithWorkNoLst(),
						convertTimeDigest(x.getTimeDigestApplication()));
			}).collect(Collectors.toList()));

		case EARLY_LEAVE_CANCEL_APPLICATION:
			ArrivedLateLeaveEarly early = (ArrivedLateLeaveEarly) application;
			return new ArrivedLateLeaveEarlyShare(
					early.getLateCancelation().stream()
							.map(x -> new LateCancelationShare(x.getWorkNo(),
									LateOrEarlyAtrShare.valueOf(x.getLateOrEarlyClassification().value)))
							.collect(Collectors.toList()),
					early.getLateOrLeaveEarlies().stream()
							.map(x -> new TimeReportShare(x.getWorkNo(),
									LateOrEarlyAtrShare.valueOf(x.getLateOrEarlyClassification().value),
									x.getTimeWithDayAttr()))
							.collect(Collectors.toList()),
					appShare);

		case COMPLEMENT_LEAVE_APPLICATION:
			// TODO: wait new domain
			return appShare;

		case OPTIONAL_ITEM_APPLICATION:
			// TODO: wait new domain
			return appShare;

		default:
			return null;
		}
	}

	private static DestinationTimeAppShare converDesTimeApp(DestinationTimeApp app) {
		return new DestinationTimeAppShare(TimeStampAppEnumShare.valueOf(app.getTimeStampAppEnum().value),
				app.getEngraveFrameNo(), StartEndClassificationShare.valueOf(app.getStartEndClassification().value),
				app.getSupportWork());

	}

	private static Time36AgreeUpperLimitShare toTime36Limit(Time36AgreeUpperLimit dom) {
		return new Time36AgreeUpperLimitShare(dom.getApplicationTime(),
				new Time36AgreeUpperLimitMonthShare(dom.getAgreeUpperLimitMonth().getOverTime(),
						dom.getAgreeUpperLimitMonth().getUpperLimitTime()),
				new Time36AgreeUpperLimitAverageShare(
						dom.getAgreeUpperLimitAverage().getAverageTimeLst().stream()
								.map(x -> new Time36AgreeUpperLimitPerMonthShare(x.getPeriod(),
										x.calculationAverageTime(), x.getTotalTime()))
								.collect(Collectors.toList()),
						dom.getAgreeUpperLimitAverage().getUpperLimitTime()));
	}

	private static TimeDigestApplicationShare convertTimeDigest(TimeDigestApplication data) {
		return new TimeDigestApplicationShare(data.getOvertime60H(), data.getNursingTime(), data.getChildTime(),
				data.getTimeOff(), data.getTimeSpecialVacation(), data.getTimeAnnualLeave(),
				data.getSpecialVacationFrameNO());
	}
	
	private static ApplicationTimeShare converAppTime(ApplicationTime appTime) {
		return new ApplicationTimeShare(
				appTime.getApplicationTime().stream().map(x -> {
					return new OvertimeApplicationSettingShare(x.getFrameNo().v(),
							EnumAdaptor.valueOf(x.getAttendanceType().value, AttendanceTypeShare.class),
							x.getApplicationTime().v());
				}).collect(Collectors.toList()), appTime.getFlexOverTime(),
				appTime.getOverTimeShiftNight().map(x -> {
					return new OverTimeShiftNightShare(null, x.getMidNightOutSide(), x.getOverTimeMidNight());// TODO
				}), appTime.getAnyItem().orElse(new ArrayList<>()), //
				appTime.getReasonDissociation()
						.map(x -> x.stream()
								.map(y -> new ReasonDivergenceShare(y.getReason() == null ? null : new DivergenceReasonShare(y.getReason().v()),
										y.getReasonCode(), y.getDiviationTime()))
								.collect(Collectors.toList()))
						.orElse(new ArrayList<>()));
	}
	
	private static AppOvertimeDetailShare convertAppOverDetail(AppOvertimeDetail detail) {
		return new AppOvertimeDetailShare(detail.getCid(), detail.getAppId(), detail.getYearMonth(),
				new Time36AgreeShare(detail.getTime36Agree().getApplicationTime(),
						new Time36AgreeMonthShare(detail.getTime36Agree().getAgreeMonth().getActualTime(),
								detail.getTime36Agree().getAgreeMonth().getLimitAlarmTime(),
								detail.getTime36Agree().getAgreeMonth().getLimitErrorTime(),
								new NumberOfMonthShare(
										detail.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v()), // TODO
								detail.getTime36Agree().getAgreeMonth().getYear36OverMonth(),
								detail.getTime36Agree().getAgreeMonth().getExceptionLimitAlarmTime(),
								detail.getTime36Agree().getAgreeMonth().getExceptionLimitErrorTime()),
						new Time36AgreeAnnualShare(detail.getTime36Agree().getAgreeAnnual().getActualTime(),
								detail.getTime36Agree().getAgreeAnnual().getLimitTime())),
				toTime36Limit(detail.getTime36AgreeUpperLimit()));
	}
	}
