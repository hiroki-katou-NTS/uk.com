/**
 * 4:27:55 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.ws.shift.businesscalendar.event;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event.AddComAndWorkplaceEventCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event.AddComAndWorkplaceEventCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event.CompanyEventCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event.CompanyEventCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event.WorkplaceEventCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event.WorkplaceEventCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event.CompanyEventDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event.EventDayFinder;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event.GetInitInforEventFinder;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event.KDL049Dto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event.WkpRequest;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event.WorkplaceEventDto;

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
	
	@Inject
	private AddComAndWorkplaceEventCommandHandler addComAndWorkplaceEventCommandHandler;
	
	@Inject
	private GetInitInforEventFinder initInfoEvent;
	
	
	@POST
	@Path("getCompanyEventsByListDate")
	public List<CompanyEventDto> getCompanyEventsByListDate(List<GeneralDate> lstDate) {
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
	
	@POST
	@Path("init")
	public KDL049Dto init(ParamKDL049 param) {
		return this.initInfoEvent.getInitInforEvent(param.workplaceID, param.targetDate);
	}
	
	@POST
	@Path("add")
	public void add(AddComAndWorkplaceEventCommand command) {
		this.addComAndWorkplaceEventCommandHandler.handle(command);
	}

}
