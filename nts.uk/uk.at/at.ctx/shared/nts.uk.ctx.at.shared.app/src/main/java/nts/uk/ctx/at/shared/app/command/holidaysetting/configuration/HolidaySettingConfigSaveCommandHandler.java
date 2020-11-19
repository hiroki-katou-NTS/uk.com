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
	
	/** The forward set of pub hd repo. */
//	@Inject
//	private ForwardSettingOfPublicHolidayRepository forwardSetOfPubHdRepo;
//	
//	/** The four weekfour hd numb set repo. */
//	@Inject
//	private FourWeekFourHolidayNumberSettingRepository fourWeekfourHdNumbSetRepo;
//	
//	/** The week hd set repo. */
//	@Inject
//	private WeekHolidaySettingRepository weekHdSetRepo;
	
	/** The pub hd set repo. */
	@Inject
	private PublicHolidaySettingRepository pubHdSetRepo;	
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<PublicHolidaySettingCommand> context) {
		String companyId = AppContexts.user().companyId();
//		boolean storeStatusManageComPublicHd = false;
		
		PublicHolidaySettingCommand command = context.getCommand();
		
//		ForwardSettingOfPublicHoliday forwardSetOfPubHdDomain = new ForwardSettingOfPublicHoliday(command.getForwardSetOfPubHd());
//		FourWeekFourHolidayNumberSetting fourWeekfourHdNumbSetDomain = new FourWeekFourHolidayNumberSetting(command.getFourWeekfourHdNumbSet());
//		WeekHolidaySetting weekHdSetDomain = new WeekHolidaySetting(command.getWeekHdSet());
//		PublicHolidaySetting pubHdSetDomain = new PublicHolidaySetting(command.getPubHdSet());
		
		PublicHolidaySetting publicHolidaySetting;
		// Get info from DB
		Optional<PublicHolidaySetting> optionalPubHdSet = this.pubHdSetRepo.get(companyId);
		if(optionalPubHdSet.isPresent()){
//			storeStatusManageComPublicHd = optionalPubHdSet.get().getIsManagePublicHoliday();
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
					command.getCarryOverNumberOfPublicHdIsNegative());
			this.pubHdSetRepo.insert(publicHolidaySetting);
		}
//		if (pubHdSetDomain.isManageComPublicHd() == true) {
//			//save PublicHolidaySetting
//			if(optionalPubHdSet.isPresent()){
//				storeStatusManageComPublicHd = optionalPubHdSet.get().isManageComPublicHd();
//				this.pubHdSetRepo.update(pubHdSetDomain);
//			}else {
//				this.pubHdSetRepo.add(pubHdSetDomain);
//			}
//			
//			if (pubHdSetDomain.getPublicHdManagementClassification().value == 1) {	// 0  is 1 month, 1 is 4 week
//				// save FourWeekFourHolidayNumberSetting
//				Optional<FourWeekFourHolidayNumberSetting> optionalFourWeekfourHdNumbSet = this.fourWeekfourHdNumbSetRepo.findByCID(companyId);
//				if(optionalFourWeekfourHdNumbSet.isPresent()){
//					this.fourWeekfourHdNumbSetRepo.update(fourWeekfourHdNumbSetDomain);
//				}else {
//					this.fourWeekfourHdNumbSetRepo.add(fourWeekfourHdNumbSetDomain);
//				}
//			}
//			
//			// save ForwardSettingOfPublicHoliday
//			Optional<ForwardSettingOfPublicHoliday> optionalForwardSetOfPubHd = this.forwardSetOfPubHdRepo.findByCID(companyId);
//			if(optionalForwardSetOfPubHd.isPresent()){
//				this.forwardSetOfPubHdRepo.update(forwardSetOfPubHdDomain);
//			}else {
//				this.forwardSetOfPubHdRepo.add(forwardSetOfPubHdDomain);
//			}
//			
//			// save WeekHolidaySetting
//			Optional<WeekHolidaySetting> optionalWeekHdSet = this.weekHdSetRepo.findByCID(companyId);
//			if(optionalWeekHdSet.isPresent()){
//				this.weekHdSetRepo.update(weekHdSetDomain);
//			}else {
//				this.weekHdSetRepo.add(weekHdSetDomain);
//			}
//		} else {
//			//save PublicHolidaySetting
//			if(optionalPubHdSet.isPresent()){
//				PublicHolidaySetting publicHolidaySetting = optionalPubHdSet.get();
//				storeStatusManageComPublicHd = optionalPubHdSet.get().isManageComPublicHd();
//				// 会社の公休管理をする was set 管理しない, all element on UI will be set disable and set default value 
//				// so I use domain get from service and onlye set "isManageComPublicHd" to false  
//				publicHolidaySetting.setManageComPublicHd(false);
//				this.pubHdSetRepo.update(publicHolidaySetting);
//			}else {
//				this.pubHdSetRepo.add(pubHdSetDomain);
//			}
//		}
		
		//check managementCategory change
		// ドメインモデル「特別休暇」を新規登録した場合
		// Event： 特別休暇情報が変更された を発行する
//		if (!optionalPubHdSet.isPresent() || storeStatusManageComPublicHd != command
//				.getPubHdSet().getIsManageComPublicHd()) {
//			val publicHolidaySettingDomainEvent = new PublicHolidaySettingDomainEvent(
//					command.getPubHdSet().getIsManageComPublicHd());
//			publicHolidaySettingDomainEvent.toBePublished();
//		}
	}
	
}
