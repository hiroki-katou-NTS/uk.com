package nts.uk.ctx.at.shared.ws.holidaymanagement;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.flex.AddFlexWorkSettingCommand;
import nts.uk.ctx.at.shared.app.command.holidaymanagement.SettingPublicHolidayAddCommand;
import nts.uk.ctx.at.shared.app.command.holidaymanagement.SettingPublicHolidayAddCommandHandler;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.HolidaySettingInfo;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.StartProcessTreatmentHoliday;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.common.days.WeeklyDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManaPeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.calendar.MonthDay;

@Path("at/share/holidaymanagement")
@Produces(MediaType.APPLICATION_JSON)
public class SettingPublicHolidayWs extends WebService {
	
	@Inject
	private SettingPublicHolidayAddCommandHandler settingPublicHolidayHandler;

	@Inject
	private StartProcessTreatmentHoliday startProcessTreatmentHoliday;
	
	
	@Path("save")
	@POST
	public void registerFlexWorkSet(SettingPublicHolidayAddCommand command) {
		settingPublicHolidayHandler.handle(command);
	}
	//StartProcessTreatmentHoliday
	@Path("startUp")
	@POST
	public HolidaySettingInfoDto getData() {
		String companyId  = AppContexts.user().companyId();
		HolidaySettingInfo data = startProcessTreatmentHoliday.startProcess(companyId);
		HolidaySettingInfoDto infoDto = new HolidaySettingInfoDto();
		if (data.getHolidayCheckUnit().value == HolidayCheckUnit.FOUR_WEEK.value) {
			if (data.getWeekStart().get().value == StartDateClassification.SPECIFY_YMD.value) {
				HolidayAcqManageByYMD x = (HolidayAcqManageByYMD)data.getTreatmentHoliday().getHolidayManagement();
				infoDto.setStandardDate(x.getStartingDate());
				infoDto.setHolidayCheckUnit(data.getHolidayCheckUnit().value);
				infoDto.setSelectedClassification(data.getWeekStart().get().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setNonStatutory(data.getTreatmentHoliday().getAddNonstatutoryHolidays().value);
			} else {
				HolidayAcqManageByMD x = (HolidayAcqManageByMD)data.getTreatmentHoliday().getHolidayManagement();
				infoDto.setHolidayCheckUnit(data.getHolidayCheckUnit().value);
				infoDto.setSelectedClassification(data.getWeekStart().get().value);
				infoDto.setMonthDay(x.getStartingMonthDay().getMonth()*100 + x.getStartingMonthDay().getDay());
				infoDto.setNonStatutory(data.getTreatmentHoliday().getAddNonstatutoryHolidays().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setAddHolidayValue(x.getNumberHolidayLastweek().v());
			}

		} else {
			WeeklyHolidayAcqMana x = (WeeklyHolidayAcqMana)data.getTreatmentHoliday().getHolidayManagement();
			infoDto.setHolidayCheckUnit(data.getHolidayCheckUnit().value);
			infoDto.setNonStatutory(data.getTreatmentHoliday().getAddNonstatutoryHolidays().value);
			infoDto.setHolidayValue(x.getWeeklyDays().v());
		}
		
		return infoDto;
	}
	
	
	
}
