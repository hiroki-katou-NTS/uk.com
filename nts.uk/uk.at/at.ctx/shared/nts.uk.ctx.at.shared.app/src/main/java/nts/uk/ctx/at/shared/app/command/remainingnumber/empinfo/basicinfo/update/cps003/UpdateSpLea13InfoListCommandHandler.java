package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.cps003;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave13informationCommand;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

@Stateless
public class UpdateSpLea13InfoListCommandHandler extends CommandHandlerWithResult<List<UpdateSpecialleave13informationCommand>, List<MyCustomizeException>>
		implements PeregUpdateListCommandHandler<UpdateSpecialleave13informationCommand> {

	@Inject
	private SpLeaInfoCommandHandler updateSpLeaInfoCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00051";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateSpecialleave13informationCommand.class;
	}
	
	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateSpecialleave13informationCommand>> context) {
		String cid = AppContexts.user().companyId();
		List<UpdateSpecialleave13informationCommand> cmd = context.getCommand();
		List<SpecialLeaveBasicInfo> domains = cmd.parallelStream().map(c ->{return new SpecialLeaveBasicInfo(cid, c.getSID(), SpecialLeaveCode.CS00051.value,
				c.getUseAtr(), c.getAppSet(), c.getGrantDate(),
				c.getGrantDays() != null ? c.getGrantDays().intValue() : null, c.getGrantTable());}).collect(Collectors.toList());
		updateSpLeaInfoCommandHandler.updateAllHandler(domains);
		return new ArrayList<MyCustomizeException>();
	}
	
	

}
