package nts.uk.ctx.at.shared.app.command.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.ForwardSettingOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.ForwardSettingOfPublicHolidayRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekFourHolidayNumberSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekFourHolidayNumberSettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayCarryOverDeadline;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayPeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingDomainEvent;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekHolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class HolidaySettingConfigSaveCommandHandler.
 */
@Stateless
public class HolidaySettingConfigSaveCommandHandler extends CommandHandler<PublicHolidaySettingCommand> {
	@Inject
	private PublicHolidaySettingRepository pubHdSetRepo;	
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<PublicHolidaySettingCommand> context) {
		String companyId = AppContexts.user().companyId();		
		PublicHolidaySettingCommand command = context.getCommand();		
		
		PublicHolidaySetting publicHolidaySetting;
		// Get info from DB
		Optional<PublicHolidaySetting> optionalPubHdSet = this.pubHdSetRepo.get(companyId);
		if(optionalPubHdSet.isPresent()){
			publicHolidaySetting = optionalPubHdSet.get();
			publicHolidaySetting.setCompanyID(companyId);
			publicHolidaySetting.setIsManagePublicHoliday(command.getManagePublicHoliday());
			publicHolidaySetting.setPublicHolidayCarryOverDeadline(PublicHolidayCarryOverDeadline.valueOf(command.getPublicHdCarryOverDeadline()));
			publicHolidaySetting.setPublicHolidayPeriod(PublicHolidayPeriod.valueOf(command.getPublicHolidayPeriod()));
			publicHolidaySetting.setCarryOverNumberOfPublicHolidayIsNegative(command.getCarryOverNumberOfPublicHdIsNegative());
			this.pubHdSetRepo.update(publicHolidaySetting);	
		}else {
			publicHolidaySetting = new PublicHolidaySetting(
					companyId,
					command.getManagePublicHoliday(),
					PublicHolidayPeriod.valueOf(command.getPublicHolidayPeriod()),
					PublicHolidayCarryOverDeadline.valueOf(command.getPublicHdCarryOverDeadline()), 
					command.getCarryOverNumberOfPublicHdIsNegative() !=null ? command.getCarryOverNumberOfPublicHdIsNegative(): 0);
			this.pubHdSetRepo.insert(publicHolidaySetting);
		}
	}	
}
