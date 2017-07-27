/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.WeeklyWorkSettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.UserInfoDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WeeklyWorkSettingDto;

/**
 * The Class WeeklyWorkSettingWs.
 */
@Path("ctx/at/schedule/pattern/work/weekly/setting")
@Produces(MediaType.APPLICATION_JSON)
public class WeeklyWorkSettingWs extends WebService {
	
	/** The finder. */
	@Inject
	private  WeeklyWorkSettingFinder finder;
	
	
	/**
	 * Check weekly work setting.
	 *
	 * @param baseDate the base date
	 * @return the weekly work setting dto
	 */
	@POST
	@Path("checkDate")
	public WeeklyWorkSettingDto checkWeeklyWorkSetting(GeneralDate baseDate){
		return this.finder.checkWeeklyWorkSetting(baseDate);
	}	

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<WeeklyWorkSettingDto> findAll(){
		return this.finder.findAll();
	}
	
	/**
	 * Gets the user info.
	 *
	 * @return the user info
	 */
	@POST
	@Path("userinfo")
	public UserInfoDto getUserInfo(){
		return this.finder.getUserInfo();
	}
}
