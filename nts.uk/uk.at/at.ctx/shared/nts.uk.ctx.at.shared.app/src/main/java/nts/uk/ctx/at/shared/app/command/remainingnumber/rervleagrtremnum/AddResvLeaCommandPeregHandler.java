package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
@Stateless
public class AddResvLeaCommandPeregHandler extends CommandHandlerWithResult<AddResvLeaRemainPeregCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<AddResvLeaRemainPeregCommand>{
	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00038";
	}

	@Override
	public Class<?> commandClass() {
		return AddResvLeaRemainPeregCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddResvLeaRemainPeregCommand> context) {
		AddResvLeaRemainPeregCommand c = context.getCommand();
		ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(
				IdentifierUtil.randomUniqueId(), c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
				c.getExpirationStatus() == null? 1: c.getExpirationStatus().intValue(), GrantRemainRegisterType.MANUAL.value, c.getGrantDays() == null? 0d: c.getGrantDays().doubleValue(),  c.getUseDays() == null? 0d:c.getUseDays().doubleValue(),
				c.getOverLimitDays()== null? 0d: c.getOverLimitDays().doubleValue(), c.getRemainingDays() == null? 0d: c.getRemainingDays().doubleValue());
		resvLeaRepo.add(data, AppContexts.user().companyId());
		
		
		return new PeregAddCommandResult(data.getRsvLeaID());
	}

}
