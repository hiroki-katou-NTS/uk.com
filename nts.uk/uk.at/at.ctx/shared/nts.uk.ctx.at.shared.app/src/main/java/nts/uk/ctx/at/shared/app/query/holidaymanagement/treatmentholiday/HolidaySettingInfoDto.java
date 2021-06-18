package nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.FourWeekHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidaySettingInfoDto {
	
	public Double addHolidayValue;
	public Double holidayValue;
	public Integer monthDay;
	public int nonStatutory;
	public GeneralDate standardDate;
	public int holidayCheckUnit;
	public Integer selectedClassification;
	public String hdDay;
	
	public static HolidaySettingInfoDto convertToDomain(HolidaySettingInfo holidaySettingInfo) {
		
		if(holidaySettingInfo == null) {
			return null;
		}
		HolidaySettingInfoDto infoDto = new HolidaySettingInfoDto();
		if (holidaySettingInfo.getHolidayCheckUnit().get().value == HolidayCheckUnit.FOUR_WEEK.value) {
			FourWeekHolidayAcqMana fourWeekHolidayAcqMana = (FourWeekHolidayAcqMana) holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
			if (fourWeekHolidayAcqMana.getStartDateType().value == StartDateClassification.SPECIFY_YMD.value) {
				HolidayAcqManageByYMD x = (HolidayAcqManageByYMD)holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
				infoDto.setStandardDate(x.getStartingDate());
				infoDto.setHolidayCheckUnit(holidaySettingInfo.getHolidayCheckUnit().get().value);
				infoDto.setSelectedClassification(holidaySettingInfo.getStartDateClassification().get().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setNonStatutory(holidaySettingInfo.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
				if(holidaySettingInfo.getWeekStart().isPresent()){
					infoDto.setHdDay(getTextDayOfWeek(holidaySettingInfo.getWeekStart().get()));
				}
			} else {
				HolidayAcqManageByMD x = (HolidayAcqManageByMD)holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
				infoDto.setHolidayCheckUnit(holidaySettingInfo.getHolidayCheckUnit().get().value);
				infoDto.setSelectedClassification(holidaySettingInfo.getStartDateClassification().get().value);
				infoDto.setMonthDay(x.getStartingMonthDay().getMonth()*100 + x.getStartingMonthDay().getDay());
				infoDto.setNonStatutory(holidaySettingInfo.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setAddHolidayValue(x.getNumberHolidayLastweek().v());
				if(holidaySettingInfo.getWeekStart().isPresent()){
					infoDto.setHdDay(getTextDayOfWeek(holidaySettingInfo.getWeekStart().get()));
				}		
			}

		} else {
			WeeklyHolidayAcqMana x = (WeeklyHolidayAcqMana)holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
			infoDto.setHolidayCheckUnit(holidaySettingInfo.getHolidayCheckUnit().get().value);
			infoDto.setNonStatutory(holidaySettingInfo.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
			infoDto.setHolidayValue(x.getWeeklyDays().v());
			if(holidaySettingInfo.getWeekStart().isPresent()){
				infoDto.setHdDay(getTextDayOfWeek(holidaySettingInfo.getWeekStart().get()));
			}
		}
		return infoDto;
	}
	
	private static String getTextDayOfWeek(DayOfWeek dayOfWeek) {
		if (dayOfWeek == DayOfWeek.MONDAY) {
			return "月曜日";
		} else if (dayOfWeek == DayOfWeek.TUESDAY) {
			return "火曜日";
		} else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
			return "水曜日";
		} else if (dayOfWeek == DayOfWeek.THURSDAY) {
			return "木曜日";
		} else if (dayOfWeek == DayOfWeek.FRIDAY) {
			return "金曜日";
		} else if (dayOfWeek == DayOfWeek.SATURDAY) {
			return "土曜日";
		} else if (dayOfWeek == DayOfWeek.SUNDAY) {
			return "日曜日";
		}
		return "";
	}
}
