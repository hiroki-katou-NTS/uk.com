package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.company;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodHoliday;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipComRepo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 組織の勤務方法の関係性を削除する
 * 
 */
@Stateless
public class DeleteWorkMethodCmpCommandHandler extends CommandHandler<DeleteWorkMethodCmpCommand> {

	@Inject
	private WorkMethodRelationshipComRepo relationshipComRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteWorkMethodCmpCommand> context) {

		DeleteWorkMethodCmpCommand command = context.getCommand();

        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();
        WorkMethodAttendance workMethodAttendance = new WorkMethodAttendance(new WorkTimeCode(command.getWorkTimeCode()));

		relationshipComRepo.deleteWithWorkMethod(AppContexts.user().companyId(),command.getTypeWorkMethod() == WorkMethodClassfication.ATTENDANCE.value ? workMethodAttendance : workMethodHoliday);
	}

}
