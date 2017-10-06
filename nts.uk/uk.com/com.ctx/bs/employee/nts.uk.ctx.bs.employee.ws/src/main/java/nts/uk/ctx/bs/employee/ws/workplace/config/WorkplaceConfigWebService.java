/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ws.workplace.config;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.workplace.config.DeleteWkpConfigCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.config.DeleteWkpConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.config.SaveWkpConfigCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.config.SaveWkpConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.find.workplace.config.WorkplaceConfigFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceConfigDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceBase;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceCommandDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceStateDto;

/**
 * The Class WorkplaceConfigWebService.
 */
@Path("bs/employee/workplace/config")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceConfigWebService extends WebService {

    /** The wkp config finder. */
    @Inject
    private WorkplaceConfigFinder wkpConfigFinder;

    /** The save handler. */
    @Inject
    private SaveWkpConfigCommandHandler saveHandler;

    /** The remove handler. */
    @Inject
    private DeleteWkpConfigCommandHandler removeHandler;
    
    /**
     * Find all wkp configure.
     *
     * @param dto the dto
     * @return the workplace config dto
     */
    @Path("findAll")
    @POST
    public WorkplaceConfigDto findAllWkpConfigure(WorkplaceCommandDto dto) {
        return this.wkpConfigFinder.findAllByCompanyId();
    }

    /**
     * Save wkp config.
     *
     * @param command the command
     */
    @Path("save")
    @POST
    public void saveWkpConfig(SaveWkpConfigCommand command) {
        this.saveHandler.handle(command);
    }
    
    /**
     * Removes the wkp config.
     *
     * @param command the command
     */
    @Path("remove")
    @POST
    public void removeWkpConfig(DeleteWkpConfigCommand command) {
        this.removeHandler.handle(command);
    }

    /**
     * Valid workplace.
     *
     * @param workplaceDto the workplace dto
     * @return true, if successful
     */
    @Path("validWkp")
    @POST
    public WorkplaceStateDto checkWorkplace(WorkplaceBase condition) {
        return this.wkpConfigFinder.checkWorkplace(condition);
    }
}
