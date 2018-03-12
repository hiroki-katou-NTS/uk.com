package nts.uk.ctx.at.shared.ws.holidaysetting.configuration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.holidaysetting.configuration.PublicHolidayManagementUsageUnitSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.PublicHolidayManagementUsageUnitFindDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.PublicHolidayManagementUsageUnitFinder;

/**
 * The Class PublicHolidayManagementUsageUnitWs.
 */
@Path("at/shared/publicholidaymanagementusageunit")
@Produces(MediaType.APPLICATION_JSON)
public class PublicHolidayManagementUsageUnitWs extends WebService {
	
	/** The finder. */
	@Inject
	private PublicHolidayManagementUsageUnitFinder finder;
	
	/** The save handler. */
	@Inject
	private PublicHolidayManagementUsageUnitSaveCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the public holiday management usage unit find dto
	 */
	@Path("find")
	@POST
	public PublicHolidayManagementUsageUnitFindDto findData(){
		return this.finder.findData();
	}
	
	/**
	 * Save data.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveData(PublicHolidayManagementUsageUnitFindDto command){
		this.saveHandler.handle(command);
	}
}
