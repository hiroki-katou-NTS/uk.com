package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.eclipse.persistence.sessions.coordination.Command;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 作業予定を登録する
 * @author HieuLt
 */
@Stateless
public class AddWorkScheduleCommandHandler extends CommandHandler<AddWorkScheduleCommand>{
	
	@Inject
	private WorkScheduleRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddWorkScheduleCommand> context) {
		
		AddWorkScheduleCommand command = context.getCommand();
		List<TaskScheduleDetailEmp> lst = command.lstTaskScheduleDetailEmp;
		List<String> lstEmp = command.getLstTaskScheduleDetailEmp().stream().map(c ->c.getEmpId()).collect(Collectors.toList());
		
		for(TaskScheduleDetailEmp item : lst){
			//1.1:get(社員ID、年月日) : Optional<勤務予定>
			Optional<WorkSchedule> workSchedule =  repo.get(item.getEmpId() ,command.getYmd());
			//1.2:not Optional<勤務予定>．isPresent
			if(!workSchedule.isPresent()){
				throw new BusinessException("Msg_1541");
			}
			//1.3:Optional<勤務予定>.isPresent :$新の作業予定 = 作る(List<作業予定詳細>): 作業予定
			// TaskSchedule  a = TaskSchedule.create(item.taskScheduleDetail); 
		}
		
	}


	
	
	
	
}
