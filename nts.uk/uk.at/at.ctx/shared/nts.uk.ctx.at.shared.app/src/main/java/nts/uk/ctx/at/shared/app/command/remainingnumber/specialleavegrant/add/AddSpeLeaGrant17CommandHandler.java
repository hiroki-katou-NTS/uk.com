package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

//import java.math.BigDecimal;

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
public class AddSpeLeaGrant17CommandHandler
		extends CommandHandlerWithResult<AddSpecialLeaveGrant17Command, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddSpecialLeaveGrant17Command> {


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
	protected PeregAddCommandResult handle(CommandHandlerContext<AddSpecialLeaveGrant17Command> context) {
		val command = context.getCommand();
		String specialId = IdentifierUtil.randomUniqueId();
		String cid = AppContexts.user().companyId();
		SpecialLeaveGrantRemainingData domain = null;

		boolean exist = SpecialLeaveGrantRemainingData.validate(command.getGrantDate(), command.getDeadlineDate(),
				command.getNumberDayGrant(), command.getNumberDayUse(), null, command.getNumberDayRemain());

		if (!exist) {
			// #117982
			// ケース①：この辺が入力しないとき、登録しないと思います。
			return new PeregAddCommandResult(addSpeLeaveGrantCommandHandler.addHandler(domain));
		}

		// #117982
		// ケース②：「付与日」だけ入力する場合は、「登録」を押すと、エラーが表示されます。
		// ケース③：「付与日」と「期限日」だけ入力する場合は、 全部 区分①の値はゼロになります。
		SpecialLeaveGrantRemainingData.validate(command.getGrantDate(), command.getDeadlineDate(),
				command.getNumberDayGrant(), command.getNumberDayUse(), new BigDecimal(0), command.getNumberDayRemain(),
				command.grantDateItemName, command.deadlineDateItemName);

		domain = SpecialLeaveGrantRemainingData.createFromJavaType(
				specialId,
				command.getSid(),
				command.getGrantDate(),
				command.getDeadlineDate(),
				command.getExpStatus() != null ? command.getExpStatus().intValue() : 0,
				GrantRemainRegisterType.MANUAL.value,
				command.getNumberDayGrant() != null ? command.getNumberDayGrant().doubleValue() : 0,
				command.getTimeGrant() != null ? command.getTimeGrant().intValue() : null ,
				command.getNumberDayUse() != null ? command.getNumberDayUse().doubleValue() : 0,
				command.getTimeUse() != null ? command.getTimeUse().intValue() : null,
				null,
				command.getNumberDaysOver() != null ? command.getNumberDaysOver().doubleValue() : null, 
				command.getTimeOver() != null ? command.getTimeOver().intValue() : null,
				command.getNumberDayRemain() != null ? command.getNumberDayRemain().doubleValue() : 0,
				command.getTimeRemain() != null ? command.getTimeRemain().intValue() : null,
				0.0,
				17);

		return new PeregAddCommandResult(addSpeLeaveGrantCommandHandler.addHandler(domain));
	}
}
