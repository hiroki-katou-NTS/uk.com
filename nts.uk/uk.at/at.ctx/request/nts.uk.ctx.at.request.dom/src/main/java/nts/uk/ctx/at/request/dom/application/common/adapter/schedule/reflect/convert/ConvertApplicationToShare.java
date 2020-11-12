package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.reflect.convert;

import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.common.AppReasonShare;
import nts.uk.ctx.at.shared.dom.application.common.AppStandardReasonCodeShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationDateShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.common.DailyAttendanceUpdateStatusShare;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonForReversionShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectDailyShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectionStatusOfDayShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectionStatusShare;
import nts.uk.ctx.at.shared.dom.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.stamp.AppRecordImageShare;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeZoneAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppOtherShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

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
			// TODO: wait new domain
			return appShare;

		case ABSENCE_APPLICATION:
			// TODO: wait new domain
			return appShare;

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
			// TODO: wait new domain
			return appShare;

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
			// TODO: wait new domain
			return appShare;

		case EARLY_LEAVE_CANCEL_APPLICATION:
			// TODO: wait new domain
			return appShare;

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
}
