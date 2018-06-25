package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.text.ParseException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.RecoveryMethod;
import nts.uk.ctx.sys.assist.dom.recoverystorage.RecoveryStorageService;

@Stateless
@Transactional
public class PerformDataRecoveryCommandHandler extends CommandHandler<PerformDataRecoveryCommand> {
	@Inject
	private PerformDataRecoveryRepository repoPerformDataRecovery;
	@Inject
	private DataRecoveryResultRepository repoDataRecoveryResult;
	
	@Inject
	private RecoveryStorageService recoveryStorageService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PerformDataRecoveryCommandHandler.class);

	public void handle(CommandHandlerContext<PerformDataRecoveryCommand> context) {
		PerformDataRecoveryCommand performDataCommand = context.getCommand();
		// ドメインモデル「データ復旧動作管理」の動作状態を「準備中」で登録する
		// repoPerformDataRecovery.add(domain);

		// データ復旧の結果
		String dataRecoveryProcessId = context.getCommand().dataRecoveryProcessId;
		String cid = context.getCommand().cid;
		String saveSetCode = null;
		String practitioner = null;
		String executionResult = null;
		GeneralDateTime startDateTime = null;
		GeneralDateTime endDateTime = null;
		Integer saveForm = null;
		String saveName = null;
		DataRecoveryResult dataRecoveryResult = new DataRecoveryResult(dataRecoveryProcessId, cid, saveSetCode,
				practitioner, executionResult, startDateTime, endDateTime, saveForm, saveName);
		repoDataRecoveryResult.add(dataRecoveryResult);
		// 復旧条件の調整, update recoveryMethod
		repoPerformDataRecovery.updatePerformDataRecoveryById(context.getCommand().dataRecoveryProcessId);
		// 復旧対象カテゴリ選別

		// 「復旧方法」の判別

		// 復旧条件の調整
		// 「選択範囲で復旧」の場合
		
		
		// サーバー復旧処理
		try {
			recoveryStorageService.recoveryStorage(dataRecoveryProcessId);
		} catch (ParseException e) {
			LOGGER.error("Fail recovery data by " + dataRecoveryProcessId);
			LOGGER.error(e.toString());
		}
		Optional<PerformDataRecovery> otpPerformDataRecovery = repoPerformDataRecovery
				.getPerformDatRecoverById(context.getCommand().dataRecoveryProcessId);
		if (otpPerformDataRecovery.isPresent()) {
			if (otpPerformDataRecovery.get().getRecoveryMethod() == RecoveryMethod.RESTORE_SELECTED_RANGE) {
				//復旧期間の調整	
			}
		}

	}

}
