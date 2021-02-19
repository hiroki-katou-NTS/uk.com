package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class HolidaySettingConfigFinder.
 */
@Stateless
public class HolidaySettingConfigFinder {
	
	/** The forward set of pub hd repo. */
//	@Inject
//	private ForwardSettingOfPublicHolidayRepository forwardSetOfPubHdRepo;
	
	/** The four weekfour hd numb set repo. */
//	@Inject
//	private FourWeekFourHolidayNumberSettingRepository fourWeekfourHdNumbSetRepo;
	
	/** The week hd set repo. */
//	@Inject
//	private WeekHolidaySettingRepository weekHdSetRepo;
	
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

		PublicHolidaySettingFindDto pubHdSetDto = new PublicHolidaySettingFindDto();
		Optional<PublicHolidaySetting> optPubHdSetting = this.pubHdSetRepo.get(companyId);
		if(optPubHdSetting.isPresent()){
			PublicHolidaySetting pubHdSetDomain = optPubHdSetting.get();

		} else {
			pubHdSetDto = null;
		}
		
		HolidaySettingConfigDto dto = new HolidaySettingConfigDto(null, null, null, pubHdSetDto);
		
		return dto;
	}
	
	public PublicHolidaySettingFindDto findIsManage() {
		String companyId = AppContexts.user().companyId();
		PublicHolidaySettingFindDto pubHdSetDto = new PublicHolidaySettingFindDto();
		
		Optional<PublicHolidaySetting> optPubHDSet = this.pubHdSetRepo.get(companyId);
		if (optPubHDSet.isPresent()) {
			PublicHolidaySetting pubHDSet = optPubHDSet.get();
		} else {
			pubHdSetDto = null;
		}
		return pubHdSetDto;
	}
}
