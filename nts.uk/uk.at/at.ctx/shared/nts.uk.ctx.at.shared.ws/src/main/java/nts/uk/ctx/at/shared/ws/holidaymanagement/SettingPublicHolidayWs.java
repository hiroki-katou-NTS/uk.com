package nts.uk.ctx.at.shared.ws.holidaymanagement;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.holidaymanagement.SettingPublicHolidayAddCommand;
import nts.uk.ctx.at.shared.app.command.holidaymanagement.SettingPublicHolidayAddCommandHandler;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.HolidaySettingInfo;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.HolidaySettingInfoDto;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.StartProcessTreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;
import nts.uk.shr.com.context.AppContexts;

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
		HolidaySettingInfoDto data = HolidaySettingInfoDto.convertToDomain(startProcessTreatmentHoliday.startProcess(companyId));
		/*if(!data.getTreatmentHoliday().isPresent()){
			return null; 
		}
		HolidaySettingInfoDto infoDto = new HolidaySettingInfoDto();
		if (data.getHolidayCheckUnit().get().value == HolidayCheckUnit.FOUR_WEEK.value) {
			if (data.getWeekStart().get().value == StartDateClassification.SPECIFY_YMD.value) {
				HolidayAcqManageByYMD x = (HolidayAcqManageByYMD)data.getTreatmentHoliday().get().getHolidayManagement();
				infoDto.setStandardDate(x.getStartingDate());
				infoDto.setHolidayCheckUnit(data.getHolidayCheckUnit().get().value);
				infoDto.setSelectedClassification(data.getWeekStart().get().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setNonStatutory(data.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
			} else {
				HolidayAcqManageByMD x = (HolidayAcqManageByMD)data.getTreatmentHoliday().get().getHolidayManagement();
				infoDto.setHolidayCheckUnit(data.getHolidayCheckUnit().get().value);
				infoDto.setSelectedClassification(data.getWeekStart().get().value);
				infoDto.setMonthDay(x.getStartingMonthDay().getMonth()*100 + x.getStartingMonthDay().getDay());
				infoDto.setNonStatutory(data.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setAddHolidayValue(x.getNumberHolidayLastweek().v());
			}

		} else {
			WeeklyHolidayAcqMana x = (WeeklyHolidayAcqMana)data.getTreatmentHoliday().get().getHolidayManagement();
			infoDto.setHolidayCheckUnit(data.getHolidayCheckUnit().get().value);
			infoDto.setNonStatutory(data.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
			infoDto.setHolidayValue(x.getWeeklyDays().v());
		}
		*/
		return data;
	}
	
	
	
}
