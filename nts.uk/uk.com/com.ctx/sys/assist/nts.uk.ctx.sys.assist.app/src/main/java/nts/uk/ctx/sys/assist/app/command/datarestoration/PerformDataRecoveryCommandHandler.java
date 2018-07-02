package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.*;


@Stateless
public class PerformDataRecoveryCommandHandler extends AsyncCommandHandler<PerformDataRecoveryCommand> {
	@Inject
	private PerformDataRecoveryRepository repoPerformDataRecovery;
	@Inject
	private DataRecoveryMngRepository repoDataRecoveryMng;
	@Inject
	private RecoveryStogareAsysnCommandHandler recoveryStogareAsysnCommandHandler;

	public void handle(CommandHandlerContext<PerformDataRecoveryCommand> context) {
		PerformDataRecoveryCommand performDataCommand = context.getCommand();
		String dataRecoveryProcessId = performDataCommand.recoveryProcessingId;
		String recoveryDate = GeneralDateTime.now().toString("yyyy-MM-dd");
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
		performData.ifPresent(x -> {
			x.setRecoveryMethod(EnumAdaptor.valueOf(performDataCommand.recoveryMethodOptions, RecoveryMethod.class));
			repoPerformDataRecovery.update(x);
		});
		// 復旧対象カテゴリ選別
		// 抽出した非対象カテゴリをドメインモデル「テーブル一覧」の「復旧対象選択」を0（；復旧しない）に更新
		List<String> listCheckCate = performDataCommand.recoveryCategoryList.stream().map(x -> x.categoryId)
				.collect(Collectors.toList());
		int selectionTarget = 1;
		repoPerformDataRecovery.updateCategorySelect(selectionTarget, dataRecoveryProcessId, listCheckCate);

		// 「復旧方法」の判別
		if (EnumAdaptor.valueOf(performDataCommand.recoveryMethodOptions,
				RecoveryMethod.class) == RecoveryMethod.RESTORE_SELECTED_RANGE) {

			// 復旧期間の調整
			for (int i = 0; i < listCheckCate.size(); i++) {
				String checkCate = listCheckCate.get(i);
				String startOfPeriod = performDataCommand.recoveryCategoryList.get(i).startOfPeriod;
				String endOfPeriod = performDataCommand.recoveryCategoryList.get(i).endOfPeriod;
				repoPerformDataRecovery.updateCategorySelectByDateFromTo(startOfPeriod, endOfPeriod,
						dataRecoveryProcessId, checkCate);
			}

			// 対象社員の選別 performDataCommand.employeeList
			List<String> employeeIdList = performDataCommand.employeeList.stream().map(x -> x.id)
					.collect(Collectors.toList());
			repoPerformDataRecovery.deleteEmployeeDataRecovery(dataRecoveryProcessId, employeeIdList);
		}
		recoveryStogareAsysnCommandHandler.handle(context);
	}
}
