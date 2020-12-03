package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.cps003;

import java.util.ArrayList;
import java.util.List;

//import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant17Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.SpeLeaveGrantCommandHandler;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;

@Stateless
public class AddSpeLeaGrant17ListCommandHandler
extends CommandHandlerWithResult<List<AddSpecialLeaveGrant17Command>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddSpecialLeaveGrant17Command> {


	@Inject
	private SpeLeaveGrantCommandHandler addSpeLeaveGrantCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00065";
	}

	@Override
	public Class<?> commandClass() {
		return AddSpecialLeaveGrant17Command.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddSpecialLeaveGrant17Command>> context) {
		List<AddSpecialLeaveGrant17Command> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<MyCustomizeException> result = new ArrayList<>();
		List<SpecialLeaveGrantRemainingData> insertLst = new ArrayList<>();
		cmd.stream().forEach(c ->{
			String specialId = IdentifierUtil.randomUniqueId();
			SpecialLeaveGrantRemainingData domain = SpecialLeaveGrantRemainingData.createFromJavaType(
					specialId,
					cid,
					c.getSid(),
					c.getGrantDate(),
					c.getDeadlineDate(),
					c.getExpStatus().intValue(),
					GrantRemainRegisterType.MANUAL.value,
					c.getNumberDayGrant().doubleValue(),
					c.getTimeGrant() != null ? c.getTimeGrant().intValue() : null ,
					c.getNumberDayUse().doubleValue(),
					c.getTimeUse() != null ? c.getTimeUse().intValue() : null,
					null,
					c.getNumberDayRemain().doubleValue(),
					c.getTimeRemain() != null ? c.getTimeRemain().intValue() : null,
					0.0,
					false,
					17);
			insertLst.add(domain);

		});
		if(!insertLst.isEmpty()) {
			result.addAll(addSpeLeaveGrantCommandHandler.addHandler(insertLst));
		}

		return result;
	}

}
