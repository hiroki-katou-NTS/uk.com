/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.schedule.setting.actualresult.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.processbatch.ErrorContentDto;
import nts.uk.ctx.at.schedule.app.export.schedule.setting.actualresult.ExtsSheduleErrorExportService;

/**
 * The Class ExtScheduleLogWs.
 */
@Path("ctx/at/schedule/processbatch/log")
@Produces(MediaType.APPLICATION_JSON)
public class ExtScheduleLogWs extends WebService {

    /** The export service. */
    @Inject
    private ExtsSheduleErrorExportService exportService;
    
    /**
     * Export csv error.
     *
     * @param executeId the execute id
     * @return the export service result
     */
    @POST
    @Path("export")
    public ExportServiceResult exportCsvError(List<ErrorContentDto> command) {    	
        return this.exportService.start(command);
    }
}
