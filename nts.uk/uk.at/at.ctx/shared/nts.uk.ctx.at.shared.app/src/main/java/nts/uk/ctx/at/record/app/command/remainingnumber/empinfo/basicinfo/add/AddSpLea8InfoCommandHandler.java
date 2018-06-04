package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddSpLea8InfoCommandHandler
		extends CommandHandlerWithResult<AddSpecialleave8informationCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddSpecialleave8informationCommand> {

	@Inject
	private SpLeaInfoCommandHandler addSpLeaInfoCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00032";
	}

	@Override
	public Class<?> commandClass() {
		return AddSpecialleave8informationCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddSpecialleave8informationCommand> context) {

		val command = context.getCommand();

		String cid = AppContexts.user().companyId();

		SpecialLeaveBasicInfo domain = new SpecialLeaveBasicInfo(cid, command.getSID(), SpecialLeaveCode.CS00032.value,
				command.getUseAtr(), command.getAppSet(), command.getGrantDate(),
				command.getGrantDays() != null ? command.getGrantDays().intValue() : null, command.getGrantTable());
		return new PeregAddCommandResult(addSpLeaInfoCommandHandler.addHandler(domain));
	}

}
