package nts.uk.ctx.at.record.app.command.worklocation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * IPアドレス削除
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM040_勤務場所の登録.B：IPアドレス設定.メニュー別OCD.IPアドレス削除
 * @author tutk
 *
 */
@Stateless
public class DeleteIPAddressCmdHandler extends CommandHandler<DeleteIPAddressCmd> {
	
	@Inject
	private WorkLocationRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteIPAddressCmd> context) {
		DeleteIPAddressCmd command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		// 1:
		// 2:
		if(!repo.getIPAddressByIP(contractCode, command.getNet1(), command.getNet2(), command.getHost1(), command.getHost2()).isEmpty()) {
			// 3: 
			repo.deleteByIP(contractCode, command.getWorkLocationCode(), command.getNet1(), command.getNet2(), command.getHost1(), command.getHost2());
		}else {
			throw new BusinessException("Msg_1969");
		}
	}

}
