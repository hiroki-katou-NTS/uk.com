package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddSpeLeaGrant18CommandHandler
		extends CommandHandlerWithResult<AddSpecialLeaveGrant18Command, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddSpecialLeaveGrant18Command> {

		
	@Inject
	private SpeLeaveGrantCommandHandler addSpeLeaveGrantCommandHandler;

	@Override
	public String targetCategoryCd() {
		return "CS00066";
	}

	@Override
	public Class<?> commandClass() {
		return AddSpecialLeaveGrant18Command.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddSpecialLeaveGrant18Command> context) {
		val command = context.getCommand();
		String specialId = IdentifierUtil.randomUniqueId();
		String cid = AppContexts.user().companyId();

		SpecialLeaveGrantRemainingData domain = SpecialLeaveGrantRemainingData.createFromJavaType(specialId, cid,
				command.getSid(), 18,
				command.getGrantDate(),command.getDeadlineDate(), 
				command.getExpStatus().intValue(),
				GrantRemainRegisterType.MANUAL.value, 
				command.getNumberDayGrant(), 
				command.getTimeGrant() != null ? command.getTimeGrant().intValue() : null ,
				command.getNumberDayUse(), 
				command.getTimeUse() != null ? command.getTimeUse().intValue() : null, 
				null,
				command.getNumberDaysOver(),
				command.getTimeOver() != null ? command.getTimeOver().intValue() : null,
				command.getNumberDayRemain(),
				command.getTimeRemain() != null ? command.getTimeRemain().intValue() : null,
				command.grantDateItemName, command.deadlineDateItemName);

		return new PeregAddCommandResult(addSpeLeaveGrantCommandHandler.addHandler(domain));
	}
}
