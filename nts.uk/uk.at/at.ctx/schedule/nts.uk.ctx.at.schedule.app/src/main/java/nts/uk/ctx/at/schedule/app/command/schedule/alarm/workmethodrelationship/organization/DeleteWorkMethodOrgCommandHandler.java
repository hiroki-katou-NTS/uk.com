package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.organization;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodHoliday;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrgRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 組織の勤務方法の関係性を削除する
 * 
 */
@Stateless
public class DeleteWorkMethodOrgCommandHandler extends CommandHandler<DeleteWorkMethodOrgCommand> {

	@Inject
	private WorkMethodRelationshipOrgRepo workMethodRelationshipOrgRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteWorkMethodOrgCommand> context) {

		DeleteWorkMethodOrgCommand command = context.getCommand();

        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(command.getUnit()),
                Optional.ofNullable(command.getWorkplaceId()),Optional.ofNullable(command.getWorkplaceGroupId()));

        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();
        WorkMethodAttendance workMethodAttendance1 = new WorkMethodAttendance(new WorkTimeCode(command.getWorkTimeCode()));

        workMethodRelationshipOrgRepo.deleteWorkMethod(AppContexts.user().companyId(),targetOrgIdenInfor,
                command.getTypeWorkMethod() == 1 ? workMethodAttendance1 : workMethodHoliday);
	}
}
