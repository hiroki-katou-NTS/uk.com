package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.RecoveryMethod;
import nts.uk.ctx.sys.assist.dom.recoverystorage.RecoveryStorageService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerformDataRecoveryCommandHandler extends CommandHandlerWithResult<PerformDataRecoveryCommand, String> {
	@Inject
	private PerformDataRecoveryRepository repoPerformDataRecovery;
	@Inject
	private DataRecoveryResultRepository repoDataRecoveryResult;
	@Inject
	private RecoveryStorageService recoveryStorageService;
	@Inject
	private DataRecoveryMngRepository repoDataRecoveryMng;
	@Inject
	private RecoveryStogareAsysnCommandHandler recoveryStogareAsysnCommandHandler;

	public String handle(CommandHandlerContext<PerformDataRecoveryCommand> context) {
		PerformDataRecoveryCommand performDataCommand = context.getCommand();
		String dataRecoveryProcessId = performDataCommand.recoveryProcessingId;
		String recoveryDate = "";
		int categoryCnt = 0;
		int errorCount = 0;
		int categoryTotalCount = 0;
		String processTargetEmpCode = "";
		int suspendedState = 0;
		int numOfProcesses = 0;
		int totalNumOfProcesses = 0;
		int operatingCondition = 4;
		DataRecoveryMng dataRecoveryMng = new DataRecoveryMng(dataRecoveryProcessId, errorCount, categoryCnt,
				categoryTotalCount, totalNumOfProcesses, numOfProcesses, processTargetEmpCode, suspendedState,
				operatingCondition, recoveryDate);
		repoDataRecoveryMng.add(dataRecoveryMng);

		// ドメインモデル「データ復旧動作管理」の動作状態を「準備中」で登録する
		// データ復旧の結果
		recoveryStogareAsysnCommandHandler.handle(context);

		String cid = AppContexts.user().companyId();
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

		return dataRecoveryProcessId;

	}

}
