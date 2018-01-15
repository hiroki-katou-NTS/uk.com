package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import lombok.Data;

@Data
public class HolidaySettingConfigDto {
	
	private ForwardSettingOfPublicHolidayFindDto forwardSetOfPubHd;
	
	private FourWeekFourHolidayNumberSettingFindDto fourWeekfourHdNumbSet;
	
	private WeekHolidaySettingFindDto weekHdSet;
	
	private PublicHolidaySettingFindDto pubHdSet;
	
	public HolidaySettingConfigDto(ForwardSettingOfPublicHolidayFindDto forwardSetOfPubHd, FourWeekFourHolidayNumberSettingFindDto fourWeekfourHdNumbSet,
			 WeekHolidaySettingFindDto weekHdSet, PublicHolidaySettingFindDto pubHdSet){
		
		this.forwardSetOfPubHd = forwardSetOfPubHd;
		this.fourWeekfourHdNumbSet = fourWeekfourHdNumbSet;
		this.weekHdSet = weekHdSet;
		this.pubHdSet = pubHdSet;
	}
}
