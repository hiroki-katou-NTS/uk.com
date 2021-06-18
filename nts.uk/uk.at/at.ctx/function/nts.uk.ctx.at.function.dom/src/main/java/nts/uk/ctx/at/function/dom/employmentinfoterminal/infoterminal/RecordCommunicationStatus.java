package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.FuncEmpInfoTerminalImport;

/**
 * @author thanh_nx
 *
 *         通信状況を記録する
 */
public class RecordCommunicationStatus {

	// [1] 通信状況を記録する
	public static AtomTask recordStatus(Require require, ContractCode contractCode, EmpInfoTerminalCode terCode) {

		AtomTask task = AtomTask.none();
		GeneralDateTime systemTime = GeneralDateTime.now();

		// 就業情報端末を取得する
		Optional<FuncEmpInfoTerminalImport> empTerInfo = require.getEmpInfoTerminal(terCode.v(), contractCode.v());
		if (!empTerInfo.isPresent())
			return task;

		// 就業情報端末通信状況を取得する
		Optional<EmpInfoTerminalComStatus> empTerComstatus = require.getEmpTerComStatus(contractCode, terCode);
		if(!empTerComstatus.isPresent()) {
			empTerComstatus = Optional.of(new EmpInfoTerminalComStatus(contractCode, terCode, systemTime));
			val  empTerComstatusNew = empTerComstatus.get();
			task = task.then(() -> require.insertEmpTerStatus(empTerComstatusNew));
		}

		// 通信異常があったか判断する
		if (empTerComstatus.get().isCommunicationError(new MonitorIntervalTime(empTerInfo.get().getIntervalTime()), systemTime)) {
			val empTerComStsNew = empTerComstatus.get().createAbnormalPeriod(systemTime);

			task = task.then(() -> {
				require.insertEmpComAbPeriod(empTerComStsNew);
			});

		}

		// $就業情報端末通信状況Update ＝ $就業情報端末通信状況.最終通信日時を更新する($システム日時)
		val  empTerComstatusNew = empTerComstatus.get();
		task = task.then(() -> {
			require.updateEmpTerStatus(empTerComstatusNew.updateLastTime(systemTime));

			// 過去の「就業情報端末通信異常期間」は削除する。
			require.deleteEmpTerComAbPast(contractCode, terCode, EmpInfoTerComAbPeriod.getDayRemoveInfo());
		});

		return task;
	}

	public static interface Require {

		// [R-1]就業情報端末を取得する
		// RQEmpInfoTerminalAdapter.getEmpInfoTerminalCode
		public Optional<FuncEmpInfoTerminalImport> getEmpInfoTerminal(String empInfoTerCode, String contractCode);

		// [R-2]就業情報端末通信状況を取得する
		// EmpInfoTerminalComStatusRepository.get
		public Optional<EmpInfoTerminalComStatus> getEmpTerComStatus(ContractCode contractCode,
				EmpInfoTerminalCode empInfoTerCode);

		// [R-3]就業情報端末通信異常期間をInsertする
		// EmpInfoTerComAbPeriodRepository.insert
		void insertEmpComAbPeriod(EmpInfoTerComAbPeriod empInfoTerComAbPeriod);

		// [R-4]就業情報端末通信状況をUpdateする
		// EmpInfoTerminalComStatusRepository.update
		public void updateEmpTerStatus(EmpInfoTerminalComStatus empInfoTerComStatus);
		
		// [R-5]過去の就業情報端末通信異常期間をDeleteする
		// EmpInfoTerComAbPeriodRepository.deletePast
		void deleteEmpTerComAbPast(ContractCode contractCode, EmpInfoTerminalCode code, GeneralDate dateDelete);
		
	    void insertEmpTerStatus(EmpInfoTerminalComStatus empInfoTerComStatus);
	}
}
