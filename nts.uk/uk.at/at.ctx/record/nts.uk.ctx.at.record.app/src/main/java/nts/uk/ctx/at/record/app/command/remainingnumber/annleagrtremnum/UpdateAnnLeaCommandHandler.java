package nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;

@Stateless
public class UpdateAnnLeaCommandHandler extends AsyncCommandHandler<AnnLeaGrantRemnNumCommand> {

	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<AnnLeaGrantRemnNumCommand> context) {
		AnnLeaGrantRemnNumCommand command = context.getCommand();
		AnnualLeaveGrantRemainingData data = AnnualLeaveGrantRemainingData.createFromJavaType(command.getEmployeeId(), 
				command.getGrantDate(), command.getDeadline(), command.getExpirationStatus().value, GrantRemainRegisterType.MANUAL.value,
				command.getGrantDays(), command.getGrantMinutes(), command.getUsedDays(), command.getUsedMinutes(), 
				null, command.getRemainingDays(), command.getRemainingMinutes(), 0d, null, null, null);
		annLeaRepo.update(data);
	}

}
