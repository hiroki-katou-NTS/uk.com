package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.cps003;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave17informationCommand;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

@Stateless
public class UpdateSpLea17InfoListCommandHandler extends CommandHandlerWithResult<List<UpdateSpecialleave17informationCommand>, List<MyCustomizeException>>
		implements PeregUpdateListCommandHandler<UpdateSpecialleave17informationCommand> {

	@Inject
	private SpLeaInfoCommandHandler updateSpLeaInfoCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00055";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateSpecialleave17informationCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateSpecialleave17informationCommand>> context) {
		String cid = AppContexts.user().companyId();
		List<UpdateSpecialleave17informationCommand> cmd = context.getCommand();
		List<SpecialLeaveBasicInfo> domains = cmd.stream().map(c ->{return new SpecialLeaveBasicInfo(cid, c.getSID(), SpecialLeaveCode.CS00055.value,
				c.getUseAtr(), c.getAppSet(), c.getGrantDate(),
				c.getGrantDays() != null ? c.getGrantDays().intValue() : null, c.getGrantTable());}).collect(Collectors.toList());
		updateSpLeaInfoCommandHandler.updateAllHandler(domains);
		return new ArrayList<MyCustomizeException>();
	}

}
