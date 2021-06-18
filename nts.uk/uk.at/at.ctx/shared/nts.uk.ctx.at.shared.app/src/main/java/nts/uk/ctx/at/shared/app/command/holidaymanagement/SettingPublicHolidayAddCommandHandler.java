package nts.uk.ctx.at.shared.app.command.holidaymanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.common.days.WeeklyDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHolidayRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.calendar.MonthDay;
/** UKDesign.UniversalK.就業.KDW_日別実績.KMF_休暇マスタ.KMF001_休暇の設定.M：休日の設定.ユースケース.変更登録する **/
@Stateless
public class SettingPublicHolidayAddCommandHandler extends CommandHandler<SettingPublicHolidayAddCommand> {
	
	@Inject
	private TreatmentHolidayRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SettingPublicHolidayAddCommand> context) {
		String companyID = AppContexts.user().companyId();
		SettingPublicHolidayAddCommand command = context.getCommand();
		TreatmentHoliday holiday = null; 
		
		boolean checkHolidayCheckUnit = (command.getHolidayCheckUnit() == HolidayCheckUnit.FOUR_WEEK.value);
		if (checkHolidayCheckUnit) {
			if (command.getSelectedClassification() == StartDateClassification.SPECIFY_MD.value) {
				holiday = new TreatmentHoliday(companyID, NotUseAtr.valueOf(command.nonStatutory),
						new HolidayAcqManageByMD(new MonthDay(command.getMonthDay() / 100, command.getMonthDay() % 100),
								new FourWeekDays(command.getHolidayValue()),
								new WeeklyDays(command.getAddHolidayValue() )));
			} else {
				holiday = new TreatmentHoliday(companyID, NotUseAtr.valueOf(command.nonStatutory),
						                       new HolidayAcqManageByYMD(command.getStandardDate(), new FourWeekDays(command.getHolidayValue()))
						                      );
			}
		} else {
			holiday = new TreatmentHoliday(companyID, NotUseAtr.valueOf(command.nonStatutory),
					new WeeklyHolidayAcqMana(new WeeklyDays(command.getHolidayValue())));
		}
		
		
		Optional<TreatmentHoliday> opt = repo.get(companyID);
		if(opt.isPresent()){
			repo.update(holiday);
		}
		else{
			repo.insert(holiday);
		}
	}

}
