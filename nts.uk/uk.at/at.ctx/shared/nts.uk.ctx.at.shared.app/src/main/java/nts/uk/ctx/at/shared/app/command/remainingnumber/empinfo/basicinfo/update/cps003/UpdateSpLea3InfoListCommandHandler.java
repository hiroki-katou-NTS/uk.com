package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.cps003;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave3informationCommand;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

@Stateless
public class UpdateSpLea3InfoListCommandHandler extends CommandHandler<List<UpdateSpecialleave3informationCommand>>
		implements PeregUpdateListCommandHandler<UpdateSpecialleave3informationCommand> {

	@Inject
	private SpLeaInfoCommandHandler updateSpLeaInfoCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00027";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateSpecialleave3informationCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<List<UpdateSpecialleave3informationCommand>> context) {
		String cid = AppContexts.user().companyId();
		List<UpdateSpecialleave3informationCommand> cmd = context.getCommand();
		List<SpecialLeaveBasicInfo> domains = cmd.parallelStream().map(c ->{return new SpecialLeaveBasicInfo(cid, c.getSID(), SpecialLeaveCode.CS00027.value,
				c.getUseAtr(), c.getAppSet(), c.getGrantDate(),
				c.getGrantDays() != null ? c.getGrantDays().intValue() : null, c.getGrantTable());}).collect(Collectors.toList());
		updateSpLeaInfoCommandHandler.updateAllHandler(domains);		
	}

}
