/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;

/**
 * @author nam.lh
 *
 */
@Stateless
public class AddManualSettingHandler extends CommandHandler<ManualSettingCommand> {
	@Inject
	private ManualSetOfDataSaveRepository repo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ManualSettingCommand> context) {
		ManualSettingCommand manualSetCmd = context.getCommand();
		String storeProcessingId = IdentifierUtil.randomUniqueId();

		ManualSetOfDataSave domain = manualSetCmd.toDomain(storeProcessingId);
		// 画面の保存対象社員から「社員指定の有無」を判定する ( check radio button )
		if (manualSetCmd.getSelectionTarget() == 1) {
			//指定社員の有無＝「する」
		}

		if (manualSetCmd.getSelectionTarget() == 0) {
			//指定社員の有無＝「しない」の場合」
		}

		repo.addManualSetting(domain);
	}
}
