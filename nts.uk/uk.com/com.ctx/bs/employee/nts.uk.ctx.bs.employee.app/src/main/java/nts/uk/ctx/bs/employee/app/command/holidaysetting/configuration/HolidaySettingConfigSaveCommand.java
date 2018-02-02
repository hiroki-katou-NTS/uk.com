package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class HolidaySettingConfigSaveCommand.
 */
@Data
@NoArgsConstructor
public class HolidaySettingConfigSaveCommand {
	
	/** The forward set of pub hd. */
	private ForwardSettingOfPublicHolidayCommandDto forwardSetOfPubHd;
	
	/** The four weekfour hd numb set. */
	private FourWeekFourHolidayNumberSettingCommandDto fourWeekfourHdNumbSet;
	
	/** The week hd set. */
	private WeekHolidaySettingCommandDto weekHdSet;
	
	/** The pub hd set. */
	private PublicHolidaySettingCommandDto pubHdSet;
	
	/**
	 * Instantiates a new holiday setting config save command.
	 *
	 * @param forwardSetOfPubHd the forward set of pub hd
	 * @param fourWeekfourHdNumbSet the four weekfour hd numb set
	 * @param weekHdSet the week hd set
	 * @param pubHdSet the pub hd set
	 */
	public HolidaySettingConfigSaveCommand(ForwardSettingOfPublicHolidayCommandDto forwardSetOfPubHd, FourWeekFourHolidayNumberSettingCommandDto fourWeekfourHdNumbSet,
			 WeekHolidaySettingCommandDto weekHdSet, PublicHolidaySettingCommandDto pubHdSet){
		
		this.forwardSetOfPubHd = forwardSetOfPubHd;
		this.fourWeekfourHdNumbSet = fourWeekfourHdNumbSet;
		this.weekHdSet = weekHdSet;
		this.pubHdSet = pubHdSet;
	}
}
