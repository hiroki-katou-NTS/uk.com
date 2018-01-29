package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import java.util.List;
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
		} else {
			forwardSetOfPubHdDto = null;
		}
		
		Optional<FourWeekFourHolidayNumberSetting> optionalFourWeekfourHdNumbSet = this.fourWeekfourHdNumbSetRepo.findByCID(companyId);
		if(optionalFourWeekfourHdNumbSet.isPresent()){
			FourWeekFourHolidayNumberSetting fourWeekfourHdNumbSetDomain = optionalFourWeekfourHdNumbSet.get();
			fourWeekfourHdNumbSetDomain.saveToMemento(fourWeekfourHdNumbSetDto);
		} else {
			fourWeekfourHdNumbSetDto = null;
		}
		
		Optional<WeekHolidaySetting> optionalWeekHdSet = this.weekHdSetRepo.findByCID(companyId);
		if(optionalWeekHdSet.isPresent()){
			WeekHolidaySetting weekHdSetDomain = optionalWeekHdSet.get();
			weekHdSetDomain.saveToMemento(weekHdSetDto);
		} else {
			weekHdSetDto = null;
		}
		
		List<PublicHolidaySetting> lstPubHdSet = this.pubHdSetRepo.findByCIDToList(companyId);
		if(lstPubHdSet != null && !lstPubHdSet.isEmpty()){
			PublicHolidaySetting pubHdSetDomain = lstPubHdSet.get(0);
			pubHdSetDomain.saveToMemento(pubHdSetDto, 0);
			
			pubHdSetDomain = lstPubHdSet.get(1);
			pubHdSetDomain.saveToMemento(pubHdSetDto, 1);
		} else {
			pubHdSetDto = null;
		}
		
		HolidaySettingConfigDto dto = new HolidaySettingConfigDto(forwardSetOfPubHdDto, fourWeekfourHdNumbSetDto, weekHdSetDto, pubHdSetDto);
		
		return dto;
	}
}
