package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateSpLea4InfoCommandHandler extends CommandHandler<UpdateSpecialleave4informationCommand>
		implements PeregUpdateCommandHandler<UpdateSpecialleave4informationCommand> {

	@Inject
	private SpLeaInfoCommandHandler updateSpLeaInfoCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00028";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateSpecialleave4informationCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateSpecialleave4informationCommand> context) {

		val command = context.getCommand();

		String cid = AppContexts.user().companyId();
		SpecialLeaveBasicInfo domain = new SpecialLeaveBasicInfo(cid, command.getSID(), SpecialLeaveCode.CS00028.value,
				command.getUseAtr(), command.getAppSet(), command.getGrantDate(),
				command.getGrantDays() != null ? command.getGrantDays().intValue() : null, command.getGrantTable());
		updateSpLeaInfoCommandHandler.updateHandler(domain);
	}

}
