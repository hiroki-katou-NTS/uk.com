/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualsetting;

import java.util.Base64;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMng;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveService;
import nts.uk.ctx.sys.assist.dom.storage.OperatingCondition;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployeesRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author nam.lh
 *
 */
@Stateless
public class AddManualSettingHandler extends AsyncCommandHandler<ManualSettingCommand> {
	@Inject
	private ManualSetOfDataSaveRepository manualSetOfDataSaveRepo;
	@Inject
	private TargetEmployeesRepository targetEmployeesRepo;
	@Inject
	private ManualSetOfDataSaveService manualSetOfDataSaveService;
	@Inject
	private DataStorageMngRepository dataStorageMngRepo;
	
	@Override
	protected void handle(CommandHandlerContext<ManualSettingCommand> context) {
		ManualSettingCommand manualSetCmd = context.getCommand();
		
		// 手動保存の圧縮パスワードを暗号化する
		if (manualSetCmd.getCompressedPassword() != null) {
			manualSetCmd.setCompressedPassword(
					Base64.getEncoder().encodeToString(manualSetCmd.getCompressedPassword().getBytes()));
		}

		String storeProcessingId = IdentifierUtil.randomUniqueId();
		ManualSetOfDataSave domain = manualSetCmd.toDomain(storeProcessingId);
		manualSetOfDataSaveRepo.addManualSetting(domain);
		
		// ドメインモデル「データ保存動作管理」に登録する
		DataStorageMng dataStorageMng = new DataStorageMng(storeProcessingId, NotUseAtr.NOT_USE, 0, 0,
				0, OperatingCondition.INPREPARATION);
		dataStorageMngRepo.add(dataStorageMng);
		
		// 画面の保存対象社員から「社員指定の有無」を判定する 
		if (manualSetCmd.getPresenceOfEmployee() == 1) {
			// 指定社員の有無＝「する」
			targetEmployeesRepo.addAll(domain.getEmployees());
		}

		if (manualSetCmd.getPresenceOfEmployee() == 0) {
			// 指定社員の有無＝「しない」の場合」

		}

		manualSetOfDataSaveService.start(storeProcessingId);

	}
}
