package nts.uk.ctx.bs.employee.ws.holidaysetting.configuration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration.HolidaySettingConfigSaveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration.HolidaySettingConfigSaveCommandHandler;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration.HolidaySettingConfigDto;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration.HolidaySettingConfigFinder;

/**
 * The Class HolidaySettingConfigWebService.
 */
@Path("bs/employee/holidaysetting/config")
@Produces(MediaType.APPLICATION_JSON)
public class HolidaySettingConfigWebService extends WebService {
	
	/** The finder. */
	@Inject
	private HolidaySettingConfigFinder finder;
	
	/** The save handler. */
	@Inject
	private HolidaySettingConfigSaveCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the holiday setting config dto
	 */
	@Path("find")
	@POST
	public HolidaySettingConfigDto findData(){
		return this.finder.findHolidaySettingConfigData();
	}
	
	/**
	 * Save data.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveData(HolidaySettingConfigSaveCommand command){
		this.saveHandler.handle(command);
	}
}
