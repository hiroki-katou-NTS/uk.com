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
import nts.uk.ctx.bs.employee.app.command.workplace.config.SaveWorkplaceConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.config.WorkplaceConfigCommand;
import nts.uk.ctx.bs.employee.app.find.workplace.WorkplaceConfigFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceCommandDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceConfigDto;

/**
 * The Class WorkplaceConfigWs.
 */
@Path("bs/employee/workplace/configure")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceConfigWs extends WebService {

    /** The wkp config finder. */
    @Inject
    private WorkplaceConfigFinder wkpConfigFinder;

    /** The save handler. */
    @Inject
    private SaveWorkplaceConfigCommandHandler saveHandler;

    /**
     * Find all wkp configure.
     *
     * @param dto
     *            the dto
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
     * @param command
     *            the command
     */
    @Path("save")
    @POST
    public void saveWkpConfig(WorkplaceConfigCommand command) {
        this.saveHandler.handle(command);
    }

}
