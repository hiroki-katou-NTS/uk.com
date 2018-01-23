package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHoliday;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.ForwardSettingOfPublicHolidayRepository;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingRepository;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class HolidaySettingConfigFinder.
 */
@Stateless
public class HolidaySettingConfigFinder {
	
	/** The forward set of pub hd repo. */
	@Inject
	private ForwardSettingOfPublicHolidayRepository forwardSetOfPubHdRepo;
	
	/** The four weekfour hd numb set repo. */
	@Inject
	private FourWeekFourHolidayNumberSettingRepository fourWeekfourHdNumbSetRepo;
	
	/** The week hd set repo. */
	@Inject
	private WeekHolidaySettingRepository weekHdSetRepo;
	
	/** The pub hd set repo. */
	@Inject
	private PublicHolidaySettingRepository pubHdSetRepo;
	
	/**
	 * Find holiday setting config data.
	 *
	 * @return the holiday setting config dto
	 */
	public HolidaySettingConfigDto findHolidaySettingConfigData(){
		String companyId = AppContexts.user().companyId();
		
		ForwardSettingOfPublicHolidayFindDto forwardSetOfPubHdDto = new ForwardSettingOfPublicHolidayFindDto();
		FourWeekFourHolidayNumberSettingFindDto fourWeekfourHdNumbSetDto = new FourWeekFourHolidayNumberSettingFindDto();
		WeekHolidaySettingFindDto weekHdSetDto = new WeekHolidaySettingFindDto();
		PublicHolidaySettingFindDto pubHdSetDto = new PublicHolidaySettingFindDto();
		
		Optional<ForwardSettingOfPublicHoliday> optionalForwardSetOfPubHd = this.forwardSetOfPubHdRepo.findByCID(companyId);
		if(optionalForwardSetOfPubHd.isPresent()){
			ForwardSettingOfPublicHoliday forwardSetOfPubHdDomain = optionalForwardSetOfPubHd.get();
			forwardSetOfPubHdDomain.saveToMemento(forwardSetOfPubHdDto);
		}
		
		Optional<FourWeekFourHolidayNumberSetting> optionalFourWeekfourHdNumbSet = this.fourWeekfourHdNumbSetRepo.findByCID(companyId);
		if(optionalFourWeekfourHdNumbSet.isPresent()){
			FourWeekFourHolidayNumberSetting fourWeekfourHdNumbSetDomain = optionalFourWeekfourHdNumbSet.get();
			fourWeekfourHdNumbSetDomain.saveToMemento(fourWeekfourHdNumbSetDto);
		}
		
		Optional<WeekHolidaySetting> optionalWeekHdSet = this.weekHdSetRepo.findByCID(companyId);
		if(optionalWeekHdSet.isPresent()){
			WeekHolidaySetting weekHdSetDomain = optionalWeekHdSet.get();
			weekHdSetDomain.saveToMemento(weekHdSetDto);
		}
		
		Optional<PublicHolidaySetting> optionalPubHdSet = this.pubHdSetRepo.findByCID(companyId);
		if(optionalPubHdSet.isPresent()){
			PublicHolidaySetting pubHdSetDomain = optionalPubHdSet.get();
			pubHdSetDomain.saveToMemento(pubHdSetDto);
		}
		
		HolidaySettingConfigDto dto = new HolidaySettingConfigDto(forwardSetOfPubHdDto, fourWeekfourHdNumbSetDto, weekHdSetDto, pubHdSetDto);
		
		return dto;
	}
}
