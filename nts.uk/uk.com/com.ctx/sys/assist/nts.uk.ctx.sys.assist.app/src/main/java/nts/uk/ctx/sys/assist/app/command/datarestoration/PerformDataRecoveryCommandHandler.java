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
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
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
	@Inject
	private DataRecoveryMngRepository repoDataRecoveryMng;

	private static final Logger LOGGER = LoggerFactory.getLogger(PerformDataRecoveryCommandHandler.class);

	public void handle(CommandHandlerContext<PerformDataRecoveryCommand> context) {
		PerformDataRecoveryCommand performDataCommand = context.getCommand();
		String dataRecoveryProcessId = context.getCommand().dataRecoveryProcessId;

		String recoveryDate = null;
		Integer categoryCnt = 0;
		Integer errorCount = 0;
		Integer categoryTotalCount = null;
		String processTargetEmpCode = null;
		Integer suspendedState = 0;
		Integer numOfProcesses = null;
		Integer totalNumOfProcesses = null;
		Integer operatingCondition = 4;
		// ドメインモデル「データ復旧動作管理」の動作状態を「準備中」で登録する
		DataRecoveryMng dataRecoveryMng = new DataRecoveryMng(dataRecoveryProcessId, errorCount, categoryCnt,
				categoryTotalCount, totalNumOfProcesses, numOfProcesses, processTargetEmpCode, suspendedState,
				operatingCondition, recoveryDate);
		repoDataRecoveryMng.add(dataRecoveryMng);

		// ドメインモデル「データ復旧動作管理」の動作状態を「準備中」で登録する
		// データ復旧の結果

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
				// 復旧期間の調整
			}
		}

	}

}
