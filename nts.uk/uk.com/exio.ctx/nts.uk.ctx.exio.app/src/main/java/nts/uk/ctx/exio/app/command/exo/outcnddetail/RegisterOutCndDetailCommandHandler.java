package nts.uk.ctx.exio.app.command.exo.outcnddetail;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterOutCndDetailCommandHandler extends CommandHandler<OutCndDetailInfoCommand> {
	@Inject
	private OutCndDetailService outCndDetailService;

	@Override
	protected void handle(CommandHandlerContext<OutCndDetailInfoCommand> context) {
		String cid = AppContexts.user().companyId();
		OutCndDetailInfoCommand cmd = context.getCommand();
		OutCndDetailCommand outCndDetailCmd = cmd.getOutCndDetail();
		Optional<OutCndDetail> outCndDetail = outCndDetailCmd == null ? Optional.ofNullable(null)
				: Optional.ofNullable(outCndDetailCmd.toDomain(cid));
		// アルゴリズム「外部出力条件登録」を実行する
		outCndDetailService.registerExOutCondition(outCndDetail, cmd.getStandardAtr(), cmd.getRegisterMode());
	}

}
