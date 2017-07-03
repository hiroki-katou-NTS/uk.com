/**
 * 4:27:55 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.ws.event;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.event.CompanyEventCommand;
import nts.uk.ctx.at.schedule.app.command.event.CompanyEventCommandHandler;
import nts.uk.ctx.at.schedule.app.command.event.WorkplaceEventCommand;
import nts.uk.ctx.at.schedule.app.command.event.WorkplaceEventCommandHandler;
import nts.uk.ctx.at.schedule.app.find.event.CompanyEventDto;
import nts.uk.ctx.at.schedule.app.find.event.EventDayFinder;
import nts.uk.ctx.at.schedule.app.find.event.WkpRequest;
import nts.uk.ctx.at.schedule.app.find.event.WorkplaceEventDto;

/**
 * @author hungnm
 *
 */
@Path("at/schedule/eventday")
@Produces("application/json")
public class EventDayWebService extends WebService {
	
	@Inject
	private EventDayFinder eventDayFinder;
	
	@Inject
	private CompanyEventCommandHandler companyEventCommandHandler;
	
	@Inject
	private WorkplaceEventCommandHandler workplaceEventCommandHandler;
	
	@POST
	@Path("getCompanyEventsByListDate")
	public List<CompanyEventDto> getCompanyEventsByListDate(List<BigDecimal> lstDate) {
		return this.eventDayFinder.getCompanyEventsByListDate(lstDate);
	}
	
	@POST
	@Path("getWorkplaceEventsByListDate")
	public List<WorkplaceEventDto> getWorkplaceEventsByListDate(WkpRequest data) {
		return this.eventDayFinder.getWorkplaceEventsByListDate(data.getWorkplaceId(), data.getLstDate());
	}
	
	@POST
	@Path("addCompanyEvent")
	public void addCompanyEvent(CompanyEventCommand command){
		command.setState("ADD");
		this.companyEventCommandHandler.handle(command);
	}
	
	@POST
	@Path("removeCompanyEvent")
	public void removeCompanyEvent(CompanyEventCommand command){
		command.setState("DELETE");
		this.companyEventCommandHandler.handle(command);
	}

	@POST
	@Path("addWorkplaceEvent")
	public void addWorkplaceEvent(WorkplaceEventCommand command){
		command.setState("ADD");
		this.workplaceEventCommandHandler.handle(command);
	}
	
	@POST
	@Path("removeWorkplaceEvent")
	public void removeWorkplaceEvent(WorkplaceEventCommand command){
		command.setState("DELETE");
		this.workplaceEventCommandHandler.handle(command);
	}

}
