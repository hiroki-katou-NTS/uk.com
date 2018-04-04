package nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddResvLeaCommandHandler extends AsyncCommandHandler<AddResvLeaRemainCommand> {

	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<AddResvLeaRemainCommand> context) {
		AddResvLeaRemainCommand c = context.getCommand();
		c.setEmployeeId(AppContexts.user().employeeId());
		ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(
				IdentifierUtil.randomUniqueId(), c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
				c.getExpirationStatus(), GrantRemainRegisterType.MANUAL.value, c.getGrantDays(), c.getUseDays(),
				c.getOverLimitDays(), c.getRemainingDays());
		resvLeaRepo.add(data, AppContexts.user().companyId());
	}

}
