package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddSpLea20InfoCommandHandler
		extends CommandHandlerWithResult<AddSpecialleave20informationCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddSpecialleave20informationCommand> {

	@Inject
	private SpLeaInfoCommandHandler addSpLeaInfoCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00058";
	}

	@Override
	public Class<?> commandClass() {
		return AddSpecialleave20informationCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddSpecialleave20informationCommand> context) {

		val command = context.getCommand();

		String cid = AppContexts.user().companyId();

		SpecialLeaveBasicInfo domain = new SpecialLeaveBasicInfo(cid, command.getSID(), SpecialLeaveCode.CS00058.value,
				command.getUseAtr(), command.getAppSet(), command.getGrantDate(),
				command.getGrantDays() != null ? command.getGrantDays().intValue() : null, command.getGrantTable());
		return new PeregAddCommandResult(addSpLeaInfoCommandHandler.addHandler(domain));
	}

}
