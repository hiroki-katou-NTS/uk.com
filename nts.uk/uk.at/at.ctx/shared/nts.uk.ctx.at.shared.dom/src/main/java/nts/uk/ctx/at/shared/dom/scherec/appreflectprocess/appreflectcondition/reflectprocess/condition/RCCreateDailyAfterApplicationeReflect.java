package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.AbsenceLeaveAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.ApplicationForHolidaysShare;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.RecruitmentAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.TypeApplicationHolidaysShare;
import nts.uk.ctx.at.shared.dom.scherec.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.optional.OptionalItemApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.optional.ReflectionOptionalItemApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * @author thanh_nx
 *
 *         [RQ667]??????????????????????????????(work????????????????????????????????????
 */
public class RCCreateDailyAfterApplicationeReflect {

	public static DailyAfterAppReflectResult process(Require require, String companyId, ApplicationShare application,
			DailyRecordOfApplication dailyApp, GeneralDate date) {
		//????????????????????????????????????????????????????????????
		Object domainSetReflect = GetDomainReflectModelApp.process(require, companyId, application.getAppType(),
				application.getAppType() != ApplicationTypeShare.COMPLEMENT_LEAVE_APPLICATION ? Optional.empty()
						: Optional.of(((ApplicationForHolidaysShare) application).getTypeApplicationHolidays()));
		
		//????????????????????????????????????????????????0??????????????????????????????????????????
		if(!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()) {
			dailyApp.setAttendanceTimeOfDailyPerformance(Optional.of(AttendanceTimeOfDailyAttendance.createDefault()));
		}
		
		List<Integer> itemIds = new ArrayList<Integer>();
		switch (application.getAppType()) {
		case OVER_TIME_APPLICATION:
			//0????????????????????????????????????????????????
			itemIds.addAll(((AppReflectOtHdWork) domainSetReflect).processOverRc(require, (AppOverTimeShare) application, dailyApp));
			break;
		case ABSENCE_APPLICATION:
			// 1??????????????????????????????(???????????????
			return ((VacationApplicationReflect) domainSetReflect).process(require, (ApplyForLeaveShare) application, dailyApp);
		case WORK_CHANGE_APPLICATION:
			// 2????????????????????????????????????(???????????????
			itemIds.addAll(((ReflectWorkChangeApp) domainSetReflect).reflectRecord(require, (AppWorkChangeShare) application, dailyApp));
			break;
		case BUSINESS_TRIP_APPLICATION:
			// 3??????????????????????????????????????????
			itemIds.addAll(((ReflectBusinessTripApp) domainSetReflect).reflectRecord(require,
					(BusinessTripInfoShare) application, dailyApp));
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4????????????????????????????????????(???????????????
		itemIds.addAll(((GoBackReflect) domainSetReflect).reflect(require, (GoBackDirectlyShare) application,
		dailyApp));
			break;
		case HOLIDAY_WORK_APPLICATION:
			// 6??????????????????????????????????????????????????????
			itemIds.addAll(((AppReflectOtHdWork) domainSetReflect).process(require, (AppHolidayWorkShare) application,
					dailyApp).getLstItemId());
			break;
		case STAMP_APPLICATION:
			// 7????????????????????????????????????????????????
			if (!application.getOpStampRequestMode().isPresent()
					|| application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ADDITIONAL) {
				itemIds.addAll(
						((StampAppReflect) domainSetReflect).reflectRecord(require, (AppStampShare) application, dailyApp));
			}
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			// 8????????????????????????????????????
			itemIds.addAll(((TimeLeaveApplicationReflect) domainSetReflect)
					.reflect((TimeLeaveApplicationShare) application, dailyApp).getLstItemId());
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 9: ????????????????????????
			itemIds.addAll(((LateEarlyCancelReflect) domainSetReflect).reflect((ArrivedLateLeaveEarlyShare) application,
					dailyApp).getLstItemId());
			break;
		case COMPLEMENT_LEAVE_APPLICATION:
			// [input. ??????.????????????????????????]???????????????
			if (((ApplicationForHolidaysShare) application)
					.getTypeApplicationHolidays() == TypeApplicationHolidaysShare.Abs) {
				// ?????????????????????????????????????????????
				itemIds.addAll(((SubstituteLeaveAppReflect) domainSetReflect).process(require, (AbsenceLeaveAppShare) application, dailyApp).getLstItemId());
			} else {
				// ?????????????????????????????????????????????
				itemIds.addAll(((SubstituteWorkAppReflect) domainSetReflect).reflectRC(require, (RecruitmentAppShare) application, dailyApp).getLstItemId());
			}
			break;

		case OPTIONAL_ITEM_APPLICATION:
			// ???????????????????????????
			((ReflectionOptionalItemApp) domainSetReflect).reflect((OptionalItemApplicationShare) application,
					dailyApp);
			break;

		default:
			break;
		}

		return new DailyAfterAppReflectResult(dailyApp, itemIds);
	}

	public static interface Require extends GetDomainReflectModelApp.Require, ReflectWorkChangeApp.Require,
			GoBackReflect.Require, StampAppReflect.Require, ReflectBusinessTripApp.Require,
			AppReflectOtHdWork.RequireRC, VacationApplicationReflect.Require, AppReflectOtHdWork.Require, SubstituteLeaveAppReflect.RequireRC,
			SubstituteWorkAppReflect.RequireRC
			{

	}
}
