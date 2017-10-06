/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ws.workplace.info;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WkpInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceInfoDto;
import nts.uk.ctx.bs.employee.app.find.workplace.info.WorkplaceInfoFinder;

/**
 * The Class WorkplaceInfoWebService.
 */
@Path("bs/employee/workplace/info")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceInfoWebService extends WebService {

    /** The workplace info finder. */
    @Inject
    private WorkplaceInfoFinder workplaceInfoFinder;
    
    /**
     * Gets the workplace info by history id.
     *
     * @param findObj the find obj
     * @return the workplace info by history id
     */
    @Path("findHistInfo")
    @POST
    public WorkplaceInfoDto getWorkplaceInfoByHistoryId(WkpInfoFindObject findObj) {
        return this.workplaceInfoFinder.find(findObj);
    }

}
