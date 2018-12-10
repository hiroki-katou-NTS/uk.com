package nts.uk.ctx.at.schedule.ws.shift.businesscalendar.daycalendar;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

//import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.AddCalendarClassCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.AddCalendarClassCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.AddCalendarCompanyCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.AddCalendarCompanyCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.AddCalendarWorkplaceCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.AddCalendarWorkplaceCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.DeleteCalendarClassCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.DeleteCalendarClassCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.DeleteCalendarCompanyCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.DeleteCalendarCompanyCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.DeleteCalendarWorkplaceCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.DeleteCalendarWorkplaceCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.UpdateCalendarClassCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.UpdateCalendarClassCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.UpdateCalendarCompanyCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.UpdateCalendarCompanyCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.UpdateCalendarWorkplaceCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar.UpdateCalendarWorkplaceCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar.CalendarClassDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar.CalendarClassFinder;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar.CalendarCompanyDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar.CalendarCompanyFinder;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar.CalendarWorkplaceDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar.CalendarWorkplaceFinder;

@Path("at/schedule/calendar")
@Produces("application/json")
public class CalendarWebService extends WebService {

	/**
	 * Calendar Company
	 */
	// get all Calendar Company
	@Inject
	private CalendarCompanyFinder getAllCalendarCompany;
	// add new Calendar Company
	@Inject
	private AddCalendarCompanyCommandHandler addCalendarCompany;
	// update Calendar Company
	@Inject
	private UpdateCalendarCompanyCommandHandler updateCalendarCompany;
	// delete  Calendar Company
	@Inject
	private DeleteCalendarCompanyCommandHandler deleteCalendarCompany;
	
	/**
	 * Calendar class
	 */
	// get all Calendar class
	@Inject
	private CalendarClassFinder getAllCalendarClass;
	// add new Calendar Class
	@Inject
	private AddCalendarClassCommandHandler addCalendarClass;
	// update Calendar Class
	@Inject
	private UpdateCalendarClassCommandHandler updateCalendarClass;
	// delete  Calendar Class
	@Inject
	private DeleteCalendarClassCommandHandler deleteCalendarClass;

	/**
	 * Calendar Workplace
	 */
	// get all Calendar Workplace
	@Inject
	private CalendarWorkplaceFinder getAllCalendarWorkplace;
	// add new Calendar Workplace
	@Inject
	private AddCalendarWorkplaceCommandHandler addCalendarWorkplace;
	// update Calendar Workplace
	@Inject
	private UpdateCalendarWorkplaceCommandHandler updateCalendarWorkplace;
	// delete  Calendar Workplace
	@Inject
	private DeleteCalendarWorkplaceCommandHandler deleteCalendarWorkplace;
	
	/**
	 * get all calendar company
	 * @param divTimeId
	 * @return
	 */
	@POST
	@Path("getallcalendarcompany")
	public List<CalendarCompanyDto> getAllCalendarCompany(){
		return this.getAllCalendarCompany.getAllCalendarCompany();
	}
	
	@POST
	@Path("getCalendarCompanySetByYear")
	public List<Integer> getCalendarCompanySetByYear(KeySearch keySearch){
		return this.getAllCalendarCompany.getCalendarCompanySetByYear(keySearch.yearMonth);
	}
	
	@POST
	@Path("getcalendarcompanyByYearMonth")
	public List<CalendarCompanyDto> getCalendarCompanyByYearMonth(KeySearch keySearch){
		return this.getAllCalendarCompany.getCalendarCompanyByYearMonth(keySearch.yearMonth);
	}
	
	/**
	 * add calendar company
	 * @param command
	 */
	@POST
	@Path("addcalendarcompany")
	public void addCalendarCompany(List<AddCalendarCompanyCommand> command){
		this.addCalendarCompany.handle(command);
	}
	/**
	 * update calendar company
	 * @param command
	 */
	@POST
	@Path("updatecalendarcompany")
	public void updateCalendarCompany(List<UpdateCalendarCompanyCommand> command){
		this.updateCalendarCompany.handle(command);
	}
	
	/**
	 * delete calendar company
	 * @param command
	 */
	@POST
	@Path("deletecalendarcompany")
	public void deleteCalendarCompany(DeleteCalendarCompanyCommand command){
		this.deleteCalendarCompany.handle(command);
	}
	
	//
	/**
	 * get all calendar Class
	 * @param divTimeId
	 * @return
	 */
	@POST
	@Path("getallcalendarclass/{classId}")
	public List<CalendarClassDto> getAllCalendarClass(@PathParam("classId") String classId){
		return this.getAllCalendarClass.getAllCalendarClass(classId);
	}
	
	@POST
	@Path("getCalendarClassSetByYear")
	public List<Integer> getCalendarClassSetByYear(KeySearch keySearch){
		return this.getAllCalendarClass.getCalendarClassSetByYear(keySearch.key, keySearch.yearMonth);
	}
	
	@POST
	@Path("getcalendarclassByYearMonth")
	public List<CalendarClassDto> getCalendarClassByYearMonth(KeySearch keySearch){
		return this.getAllCalendarClass.getCalendarClassByYearMonth(keySearch.key, keySearch.yearMonth);
	}
	/**
	 * add calendar Class
	 * @param command
	 */
	@POST
	@Path("addcalendarclass")
	public void addCalendarClass(List<AddCalendarClassCommand> command){
		this.addCalendarClass.handle(command);
	}
	/**
	 * update calendar Class
	 * @param command
	 */
	@POST
	@Path("updatecalendarclass")
	public void updateCalendarClass(List<UpdateCalendarClassCommand> command){
		this.updateCalendarClass.handle(command);
	}
	
	/**
	 * delete calendar Class
	 * @param command
	 */
	@POST
	@Path("deletecalendarclass")
	public void deleteCalendarClass(DeleteCalendarClassCommand command){
		this.deleteCalendarClass.handle(command);
	}
	//
	
	/**
	 * get all calendar Workplace
	 * @param divTimeId
	 * @return
	 */
	@POST
	@Path("getallcalendarworkplace")
	public List<CalendarWorkplaceDto> getAllCalendarWorkplace(KeySearch workPlaceID){
		return this.getAllCalendarWorkplace.getAllCalendarWorkplace(workPlaceID.key);
	}
	
	@POST
	@Path("getCalendarWorkplaceSetByYear")
	public List<Integer> getCalendarWorkPlaceSetByYear(KeySearch keySearch){
		return this.getAllCalendarWorkplace.getCalendarWorkPlaceSetByYear(keySearch.key, keySearch.yearMonth);
	}
	
	@POST
	@Path("getcalendarWorkPlaceByYearMonth")
	public List<CalendarWorkplaceDto> getCalendarWorkPlaceByYearMonth(KeySearch keySearch){
		return this.getAllCalendarWorkplace.getWorkplaceByYearMonth(keySearch.key, keySearch.yearMonth);
	}
	/**
	 * add calendar Workplace
	 * @param command
	 */
	@POST
	@Path("addcalendarworkplace")
	public void addCalendarWorkplace(List<AddCalendarWorkplaceCommand> command){
		this.addCalendarWorkplace.handle(command);
	}
	/**
	 * update calendar Workplace
	 * @param command
	 */
	@POST
	@Path("updatecalendarworkplace")
	public void updateCalendarWorkplace(List<UpdateCalendarWorkplaceCommand> command){
		this.updateCalendarWorkplace.handle(command);
	}
	
	/**
	 * delete calendar Workplace
	 * @param command
	 */
	@POST
	@Path("deletecalendarworkplace")
	public void deleteCalendarWorkplace(DeleteCalendarWorkplaceCommand command){
		this.deleteCalendarWorkplace.handle(command);
	}
}


@Data
class KeySearch {
	String key;
	String yearMonth;
}
