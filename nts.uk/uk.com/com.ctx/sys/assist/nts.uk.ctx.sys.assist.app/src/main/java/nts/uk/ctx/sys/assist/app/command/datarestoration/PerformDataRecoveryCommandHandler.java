package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.RecoveryMethod;
import nts.uk.ctx.sys.assist.dom.recoverystorage.RecoveryStorageService;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;

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
	@Inject
	private TableListRepository repoTableList;

	public String handle(CommandHandlerContext<PerformDataRecoveryCommand> context) {
		PerformDataRecoveryCommand performDataCommand = context.getCommand();
		String dataRecoveryProcessId = performDataCommand.recoveryProcessingId;
		String recoveryDate = "";
		int categoryCnt = 0;
		int errorCount = 0;
		int categoryTotalCount = performDataCommand.recoveryCategoryList.size();
		String processTargetEmpCode = "";
		int suspendedState = 0;
		int numOfProcesses = 0;
		int totalNumOfProcesses = 0;
		int operatingCondition = 4;
		DataRecoveryMng dataRecoveryMng = new DataRecoveryMng(dataRecoveryProcessId, errorCount, categoryCnt,
				categoryTotalCount, totalNumOfProcesses, numOfProcesses, processTargetEmpCode, suspendedState,
				operatingCondition, recoveryDate);
		repoDataRecoveryMng.add(dataRecoveryMng);

		// 復旧条件の調整
		// 画面の「復旧方法」をドメインモデル「データ復旧の実行」に更新する
		Optional<PerformDataRecovery> performData = repoPerformDataRecovery
				.getPerformDatRecoverById(dataRecoveryProcessId);
		if (performData.isPresent()) {
			PerformDataRecovery performDataRecovery = performData.get();
			performDataRecovery.setRecoveryMethod(
					EnumAdaptor.valueOf(performDataCommand.recoveryMethodOptions, RecoveryMethod.class));
			repoPerformDataRecovery.update(performDataRecovery);
		}
		// 復旧対象カテゴリ選別
		// 抽出した非対象カテゴリをドメインモデル「テーブル一覧」の「復旧対象選択」を0（；復旧しない）に更新
		List<TableList> listTableList = repoPerformDataRecovery.getByRecoveryProcessingId(dataRecoveryProcessId);
		List<String> listCheckCate = performDataCommand.recoveryCategoryList.stream().map(x -> x.categoryId)
				.collect(Collectors.toList());
		List<TableList> resultList = listTableList.stream().filter(x -> !(listCheckCate.contains(x.getCategoryId())))
				.collect(Collectors.toList());
		for (int i = 0; i < resultList.size(); i++) {
			TableList tableList = resultList.get(i);
			tableList.setSelectionTargetForRes(Optional.of(0));
			repoTableList.update(resultList.get(i));
		}
		// 「復旧方法」の判別
		if (EnumAdaptor.valueOf(performDataCommand.recoveryMethodOptions,
				RecoveryMethod.class) == RecoveryMethod.RESTORE_SELECTED_RANGE) {
			// 復旧期間の調整
			List<TableList> resultListChecked = listTableList.stream()
					.filter(x -> (listCheckCate.contains(x.getCategoryId()))).collect(Collectors.toList());
			for (int i = 0; i < resultListChecked.size(); i++) {
				TableList tableList = resultList.get(i);
				tableList.setSaveDateTo(Optional.of(performDataCommand.recoveryCategoryList.get(i).startOfPeriod));
				tableList.setSaveDateFrom(Optional.of(performDataCommand.recoveryCategoryList.get(i).endOfPeriod));
				repoTableList.update(resultList.get(i));
			}
			//対象社員の選別 performDataCommand.employeeList
			List<String> employeeIdList = performDataCommand.employeeList.stream().map(x -> x.id)
					.collect(Collectors.toList());
			repoPerformDataRecovery.deleteEmployeeDataRecovery(dataRecoveryProcessId, employeeIdList);
		}

		// Server I : ThuyetTd
		recoveryStogareAsysnCommandHandler.handle(context);

		return dataRecoveryProcessId;

		/*
		 * String cid = AppContexts.user().companyId(); String saveSetCode =
		 * null; String practitioner = null; String executionResult = null;
		 * GeneralDateTime startDateTime = null; GeneralDateTime endDateTime =
		 * null; Integer saveForm = null; String saveName = null;
		 * DataRecoveryResult dataRecoveryResult = new
		 * DataRecoveryResult(dataRecoveryProcessId, cid, saveSetCode,
		 * practitioner, executionResult, startDateTime, endDateTime, saveForm,
		 * saveName); repoDataRecoveryResult.add(dataRecoveryResult);
		 * 
		 * return dataRecoveryProcessId;
		 */

	}

}
