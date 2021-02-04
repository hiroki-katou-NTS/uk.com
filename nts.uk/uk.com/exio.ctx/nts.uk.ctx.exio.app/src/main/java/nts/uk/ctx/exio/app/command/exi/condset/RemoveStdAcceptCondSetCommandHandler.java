package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand> {

	@Inject
	private StdAcceptCondSetService condsetService;

	@Override
	protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
		// 会社ＩＤ
		String cid = AppContexts.user().companyId();
		// システム種類
		int sysType = context.getCommand().getSystemType();
		// 選択中の条件設定コード
		String conditionSetCd = context.getCommand().getConditionSetCode();

		// アルゴリズム「受入設定の削除」を実行する
		condsetService.deleteConditionSetting(cid, sysType, conditionSetCd);
	}
}
