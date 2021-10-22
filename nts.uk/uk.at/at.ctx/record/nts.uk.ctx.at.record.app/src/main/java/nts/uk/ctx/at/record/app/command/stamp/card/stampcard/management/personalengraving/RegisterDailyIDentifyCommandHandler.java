package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.ParamIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.RegisterIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.SelfConfirmDay;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 打刻結果より日別実績の本人確認を登録する
 */
@Stateless
public class RegisterDailyIDentifyCommandHandler extends CommandHandler<RegisterDailyIDentifyCommand> {

	
	@Inject
	private RegisterIdentityConfirmDay registerIdentityConfirmDay;

	@Override
	protected void handle(CommandHandlerContext<RegisterDailyIDentifyCommand> context) {
		String employeId = context.getCommand().getSid();
		SelfConfirmDay selfConfirm = new SelfConfirmDay(GeneralDate.today(), true);
		List<SelfConfirmDay> selfConfirms = new ArrayList<>(Arrays.asList(selfConfirm));
		ParamIdentityConfirmDay param = new ParamIdentityConfirmDay(employeId, selfConfirms);
//		Pair<String, GeneralDate> update = new Pair <String, GeneralDate> ();
		boolean register = registerIdentityConfirmDay.registerIdentity(param, new ArrayList<>(), new HashSet<>());
	}
}
