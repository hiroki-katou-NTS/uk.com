/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.retentionyearly;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.EmploymentSaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.EmploymentSaveCommandHandler;
import nts.uk.ctx.at.shared.app.delete.vacation.setting.retentionyearly.DeleteEmploymentSettingUtil;
import nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly.EmploymentSettingFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly.dto.EmploymentSettingFindDto;

/**
 * The Class EmploymentSettingWebService.
 */
@Path("ctx/at/shared/vacation/setting/employmentsetting/")
@Produces("application/json")
public class EmploymentSettingWebService extends WebService {
	
	/** The save. */
	@Inject
	private EmploymentSaveCommandHandler save;
	
	/** The finder. */
	@Inject
	private EmploymentSettingFinder finder;
	
	/** The delete util */
	@Inject
	private DeleteEmploymentSettingUtil deleteUtil;
	
	/**
	 * Find.
	 *
	 * @param empCode the emp code
	 * @return the employment setting find dto
	 */
	@POST
	@Path("find/{empCode}")
	public EmploymentSettingFindDto find(@PathParam("empCode") String empCode) {
		return this.finder.find(empCode);
	}
	
	/**
	 * Delete.
	 *
	 * @param empCode the emp code
	 * @return the employment setting find dto
	 */
	@POST
	@Path("delete/{empCode}")
	public void delete(@PathParam("empCode") String empCode) {
		this.deleteUtil.delete(empCode);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(EmploymentSaveCommand command) {
		this.save.handle(command);
	}
	
	
	@POST
	@Path("findAll")
	public List<EmploymentSettingFindDto> findAll() {
		return this.finder.findAll();
	}
}
