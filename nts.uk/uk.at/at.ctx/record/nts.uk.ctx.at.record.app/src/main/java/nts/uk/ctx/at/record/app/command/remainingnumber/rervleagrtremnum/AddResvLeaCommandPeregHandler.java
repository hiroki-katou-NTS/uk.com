package nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

public class AddResvLeaCommandPeregHandler extends CommandHandlerWithResult<AddResvLeaRemainCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<AddResvLeaRemainCommand>{
	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00038";
	}

	@Override
	public Class<?> commandClass() {
		return AddResvLeaRemainCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddResvLeaRemainCommand> context) {
		AddResvLeaRemainCommand c = context.getCommand();
		ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(
				IdentifierUtil.randomUniqueId(), c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
				c.getExpirationStatus(), GrantRemainRegisterType.MANUAL.value, c.getGrantDays(), c.getUseDays(),
				c.getOverLimitDays(), c.getRemainingDays());
		resvLeaRepo.add(data, AppContexts.user().companyId());
		
		
		return new PeregAddCommandResult(data.getRsvLeaID());
	}

}
