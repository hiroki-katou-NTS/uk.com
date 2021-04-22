package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;
/**
 * 
 * @author HieuLt
 *
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddComAndWorkplaceEventCommandHandler extends CommandHandler<AddComAndWorkplaceEventCommand>  {

	@Inject
	private CompanyEventRepository companyEventRepository;

	
	@Inject
	private WorkplaceEventRepository workplaceEventRepository;
	
	@Inject
	private WorkplaceEventCommandHandler WorkplaceEventCommandHandler;
	
	@Inject
	private CompanyEventCommandHandler companyEventCommandHandler;


	@Override
	protected void handle(CommandHandlerContext<AddComAndWorkplaceEventCommand> context) {
		AddComAndWorkplaceEventCommand command = context.getCommand();
		GeneralDate targetDate = GeneralDate.fromString(command.targetDate, "yyyy/MM/dd");
		CompanyEventCommand comCommand = new CompanyEventCommand(targetDate, command.eventComName, "ADD");	
		WorkplaceEventCommand workCommand = new WorkplaceEventCommand(command.getWorkPlaceID(), targetDate, command.eventWorkplaceName, "ADD");
		if(command.eventComName != null && !"".equals(command.eventComName.trim())){
			command.setState("ADD");
			
			if (this.companyEventRepository.findByPK(AppContexts.user().companyId(), targetDate).isPresent()) {
				this.companyEventRepository.updateEvent(toDomainCom(comCommand));
			} else {
				this.companyEventRepository.addEvent(toDomainCom(comCommand));
			}
		}else{
			this.companyEventRepository.removeEvent(toDomainCom(comCommand));
		}
		if(command.eventWorkplaceName != null && !"".equals(command.eventWorkplaceName.trim()) && command.getWorkPlaceID() != null){
			if (this.workplaceEventRepository.findByPK(command.getWorkPlaceID(), targetDate).isPresent()) {
				this.workplaceEventRepository.updateEvent(toDomain(workCommand));
			} else {
				this.workplaceEventRepository.addEvent(toDomain(workCommand));
			}
		}
		if("".equals(command.eventWorkplaceName.trim()) && command.getWorkPlaceID() != null){
			this.workplaceEventRepository.removeEvent(toDomain(workCommand));
		}
	}

	private void insertCommand(AddComAndWorkplaceEventCommand command) {
		
	}
	private void deleteCommand(AddComAndWorkplaceEventCommand command) {
	
	}
	
	private WorkplaceEvent toDomain(WorkplaceEventCommand command) {
		return WorkplaceEvent.createFromJavaType(command.getWorkplaceId(), command.date, command.eventName);
	}
	
	private CompanyEvent toDomainCom(CompanyEventCommand command) {
		return CompanyEvent.createFromJavaType(AppContexts.user().companyId(), command.date, command.eventName);
	}
}
