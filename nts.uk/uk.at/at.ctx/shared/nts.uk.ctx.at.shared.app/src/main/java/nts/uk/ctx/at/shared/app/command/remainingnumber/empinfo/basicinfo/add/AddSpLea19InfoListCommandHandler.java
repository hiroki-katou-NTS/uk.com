package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.SpLeaInfoCommandHandler;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddSpLea19InfoListCommandHandler extends CommandHandlerWithResult<List<AddSpecialleave19informationCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddSpecialleave19informationCommand>{
	@Inject
	private SpLeaInfoCommandHandler addSpLeaInfoCommandHandler;
	@Override
	public String targetCategoryCd() {
		return "CS00057";
	}

	@Override
	public Class<?> commandClass() {
		return AddSpecialleave19informationCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(
			CommandHandlerContext<List<AddSpecialleave19informationCommand>> context) {
		String cid = AppContexts.user().companyId();
		List<AddSpecialleave19informationCommand> cmd = context.getCommand();
		List<SpecialLeaveBasicInfo> domains = cmd.stream().map(c ->{return new SpecialLeaveBasicInfo(cid, c.getSID(), SpecialLeaveCode.CS00057.value,
				c.getUseAtr(), c.getAppSet(), c.getGrantDate(),
				c.getGrantDays() != null ? c.getGrantDays().intValue() : null, c.getGrantTable());}).collect(Collectors.toList());
		return addSpLeaInfoCommandHandler.addAllHandler(domains);
	}

}
