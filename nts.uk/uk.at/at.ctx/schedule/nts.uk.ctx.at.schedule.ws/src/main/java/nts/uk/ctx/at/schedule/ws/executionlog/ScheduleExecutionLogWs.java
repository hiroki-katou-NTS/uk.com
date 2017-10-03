/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.executionlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleExecutionLogFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.PeriodObject;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;



/**
 * The Class ScheduleExecutionLogWs.
 */
@Path("at/schedule/exelog")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleExecutionLogWs extends WebService {

	/** The schedule execution log finder. */
	@Inject
    private ScheduleExecutionLogFinder scheduleExecutionLogFinder;
	

    /**
     * Find all exe log.
     *
     * @return the schedule execution log dto
     */
    @POST
    @Path("findAll")
    public List<ScheduleExecutionLogDto> findAllExeLog(PeriodObject periodObj) {
        return this.scheduleExecutionLogFinder.findByDate(periodObj);
    }
    
    
}
