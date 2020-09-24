package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

public class NRHelper {

	private NRHelper() {
	};

	public static GeneralDate createGeneralDate(String date) {
		int yy = GeneralDate.today().year() / 100;
		int ymdTemp = Integer.parseInt(String.valueOf(yy) + date);
		GeneralDate result = GeneralDate.ymd(ymdTemp / 10000, (ymdTemp - (ymdTemp / 10000) * 10000) / 100, ymdTemp % 100);
		return result;
	}

	public static GeneralDateTime getDateTime(String ymd, String time) {
		int yy = GeneralDate.today().year() / 100;
		int ymdTemp = Integer.parseInt(String.valueOf(yy) + ymd);
		
		int timeNumber = Integer.parseInt(time);
		
		return GeneralDateTime.ymdhms(ymdTemp / 10000, (ymdTemp - (ymdTemp / 10000) * 10000) / 100, ymdTemp % 100,
				timeNumber / 10000, (timeNumber - (timeNumber / 10000) * 10000) / 100, timeNumber % 100);
	}

	public static Integer toMinute(String time) {
		return (Integer.parseInt(time) / 100) * 60 + Integer.parseInt(time) % 100;
	}

	public static ApplicationType convertAppType(String appTypeNR) {
		ApplicationCategory cate = ApplicationCategory.valueStringOf(appTypeNR);
		switch (cate) {

		// 打刻申請
		case STAMP:
			return ApplicationType.STAMP_APPLICATION;

		// 残業申請
		case OVERTIME:
			return ApplicationType.OVER_TIME_APPLICATION;

		// 休暇申請
		case VACATION:
			return ApplicationType.ABSENCE_APPLICATION;

		// 勤務変更申請
		case WORK_CHANGE:
			return ApplicationType.WORK_CHANGE_APPLICATION;

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			return ApplicationType.HOLIDAY_WORK_APPLICATION;

		// 遅刻早退取消申請
		case LATE:
			return ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION;

		// 時間年休申請
		case ANNUAL:
			return ApplicationType.ANNUAL_HOLIDAY_APPLICATION;

		default:
			return ApplicationType.STAMP_APPLICATION;
		}
	}

	public static HolidayAppType convertHolidayType(WorkTypeClassification workTypeClass) {

		switch (workTypeClass) {
		case AnnualHoliday:
			return HolidayAppType.ANNUAL_PAID_LEAVE;

		case SubstituteHoliday:
			return HolidayAppType.SUBSTITUTE_HOLIDAY;

		case Absence:
			return HolidayAppType.ABSENCE;

		case SpecialHoliday:
			return HolidayAppType.SPECIAL_HOLIDAY;

		case YearlyReserved:
			return HolidayAppType.YEARLY_RESERVE;

		case Holiday:
			return HolidayAppType.HOLIDAY;

		case TimeDigestVacation:
			return HolidayAppType.DIGESTION_TIME;

//		case Pause:
//			return HolidayAppType.REST_TIME;

		default:
			return null;
		}
	}

}
