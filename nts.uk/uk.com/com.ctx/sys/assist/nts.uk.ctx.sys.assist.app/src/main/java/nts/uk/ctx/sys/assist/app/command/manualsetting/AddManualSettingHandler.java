/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveService;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployeesRepository;

/**
 * @author nam.lh
 *
 */
@Stateless
public class AddManualSettingHandler extends AsyncCommandHandler<ManualSettingCommand> {
	@Inject
	private ManualSetOfDataSaveRepository repo;
	@Inject
	private TargetEmployeesRepository repoTargetEmp;
	@Inject
	private ManualSetOfDataSaveService manualSetOfDataSaveService;

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
		// 画面の保存対象社員から「社員指定の有無」を判定する ( check radio button )presenceOfEmployee
		if (manualSetCmd.getPresenceOfEmployee() == 1) {
			// 指定社員の有無＝「する」
			repoTargetEmp.addAll(domain.getEmployees());
		}

		if (manualSetCmd.getPresenceOfEmployee() == 0) {
			// 指定社員の有無＝「しない」の場合」

		}
		
		repo.addManualSetting(domain);

		manualSetOfDataSaveService.start(storeProcessingId);

	}
}
