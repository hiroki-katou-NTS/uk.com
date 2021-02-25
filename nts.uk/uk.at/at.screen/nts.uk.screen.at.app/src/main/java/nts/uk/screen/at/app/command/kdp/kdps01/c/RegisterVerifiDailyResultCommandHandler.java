package nts.uk.screen.at.app.command.kdp.kdps01.c;

import java.util.Collections;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.RegisterIdentityConfirmDay;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDP_打刻.KDPS01_打刻入力(スマホ).C:打刻結果確認実績.メニュー別OCD.打刻結果(スマホ)より日別実績の本人確認を登録する
 * 
 *         日の本人確認を登録する
 */
@Stateless
public class RegisterVerifiDailyResultCommandHandler extends CommandHandler<RegisterVerifiDailyResultCommand> {

	@Inject
	private RegisterIdentityConfirmDay registerIdentityConfirmDay;

	@Override
	protected void handle(CommandHandlerContext<RegisterVerifiDailyResultCommand> context) {
		this.registerIdentityConfirmDay.registerIdentity(context.getCommand().toParam(), Collections.emptyList(),
				new HashSet<>());

	}

}
