package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ResultOfDeletion;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.就業情報端末の削除.就業情報端末の削除
 * @author dungbn
 *
 */
public class DeleteEmpInfoTerminalService {
	
	public static ResultOfDeletion create(Require require, String contractCode, String empInfoTerCode) {
	
		// 1: get(契約コード、就業情報端末コード): Optional<就業情報端末>
		Optional<EmpInfoTerminal> empInfoTerminal = require.getEmpInfoTerminal(new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode));
		Optional<EmpInfoTerminalComStatusImport> empInfoTerminalComStatusImport = require.get(new ContractCode(contractCode), new EmpInfoTerminalCode(empInfoTerCode));
		boolean isError = !empInfoTerminal.isPresent();
		Optional<AtomTask> deleteEmpInfoTerminal = Optional.empty();
		
		if (empInfoTerminal.isPresent()) {
			deleteEmpInfoTerminal = Optional.of(AtomTask.of(() -> {
				// 2: [データがある]:delete(取得した「就業情報端末」)
				require.delete(empInfoTerminal.get());
			}));
		}
		
		Optional<AtomTask> deleteEmpInfoTerminalComStatus = Optional.empty();
		
		if (empInfoTerminalComStatusImport.isPresent()) {
			deleteEmpInfoTerminalComStatus = Optional.of(AtomTask.of(() -> {
				// 3: 削除する(契約コード, 就業情報端末コード)
				require.delete(empInfoTerminalComStatusImport.get());
			}));
		}
		// 4: create()
		return new ResultOfDeletion(isError, deleteEmpInfoTerminal, deleteEmpInfoTerminalComStatus);
	}

	public static interface Require {
		
		// [R-1]就業情報端末を取得する(契約コード,就業情報端末コード)
		Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
		
		// 	[R-2] 就業情報端末を削除する(就業情報端末)
		void delete(EmpInfoTerminal empInfoTerminal);
		
		// 	[R-3] 就業情報端末通信状況を取得する(契約コード,就業情報端末コード)
		Optional<EmpInfoTerminalComStatusImport> get(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode);
		
		// [R-4] 就業情報端末通信状況を削除する(就業情報端末通信状況)
		void delete(EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport);
	}
}
