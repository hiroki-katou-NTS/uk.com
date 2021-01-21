package nts.uk.ctx.at.schedule.ws.scheduleteam;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.schedule.app.command.scheduleteam.DeleteScheduleTeamCommandHandler;
import nts.uk.ctx.at.schedule.app.command.scheduleteam.RegisterScheduleTeamCommandHandler;
import nts.uk.ctx.at.schedule.app.command.scheduleteam.ScheduleTeamDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.scheduleteam.ScheduleTeamSaveCommand;
import nts.uk.ctx.at.schedule.app.command.scheduleteam.UpdateScheduleTeamCommandHandler;
import nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam.ScheduleTeamDetailDto;
import nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam.ScheduleTeamDetailQuery;
import nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam.ScheduleTeamDto;
import nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam.ScheduleTeamQuery;

/**
 * 
 * @author quytb
 *
 */
@Path("ctx/at/schedule/scheduleteam")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleTeamWS {
	@Inject
	private ScheduleTeamQuery scheduleTeamQuery;
	
	@Inject
	private ScheduleTeamDetailQuery scheduleTeamDetailQuery;
	
	@Inject
	private RegisterScheduleTeamCommandHandler registerScheduleTeamCommandHandler;
	
	@Inject
	private UpdateScheduleTeamCommandHandler updateScheduleTeamCommandHandler;
	
	@Inject
	private DeleteScheduleTeamCommandHandler deleteScheduleTeamCommandHandler;
	
	@POST
	@Path("/findByWKPGRPID/{id}")
	public List<ScheduleTeamDto> getAllScheduleTeam(@PathParam("id") String WKPGRPID){
		return this.scheduleTeamQuery.getListScheduleTeam(WKPGRPID);
	}
	
	@POST
	@Path("findDetail/{id}/{code}")
	public ScheduleTeamDetailDto getDetailScheduleTeam(@PathParam("id") String WKPGRPID, @PathParam("code") String scheduleTeamCd){
		return this.scheduleTeamDetailQuery.getDetailScheduleTeam(WKPGRPID,scheduleTeamCd);
	}
	
	@POST
	@Path("register")
	public void registerScheduleTeam(ScheduleTeamSaveCommand command){
		this.registerScheduleTeamCommandHandler.handle(command);		
	}
	
	@POST
	@Path("update")
	public void updateScheduleTeam(ScheduleTeamSaveCommand command){
		this.updateScheduleTeamCommandHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void deleteScheduleTeam(ScheduleTeamDeleteCommand command){
		this.deleteScheduleTeamCommandHandler.handle(command);
	}
}
