package nts.uk.ctx.at.record.app.command.remainingnumber.annualeave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteAnnuaLeaveCommandHandler extends CommandHandler<DeleteAnnuaLeaveCommand>
		implements PeregDeleteCommandHandler<DeleteAnnuaLeaveCommand> {

	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	@Inject
	private AnnLeaMaxDataRepository maxDataRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00024";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteAnnuaLeaveCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteAnnuaLeaveCommand> context) {
		DeleteAnnuaLeaveCommand command = context.getCommand();
		annLeaBasicInfoRepo.delete(command.getEmployeeId());
		maxDataRepo.delete(command.getEmployeeId());
	}

}
