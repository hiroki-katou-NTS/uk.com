package nts.uk.ctx.at.function.dom.indexreconstruction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;

/**
 * 	Domain インデックス再構成結果履歴
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProcExecIndex extends AggregateRoot {

	/** 実行ID*/
	private ExecutionCode executionId;
	
	/** 結果詳細*/
	private List<ProcExecIndexResult> indexReconstructionResult;
	
	private ProcExecIndex() {}
	
	public static ProcExecIndex createFromMemento(MementoGetter memento) {
		ProcExecIndex domain = new ProcExecIndex();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.executionId = new ExecutionCode(memento.getExecutionId());
		this.indexReconstructionResult = memento.getIndexReconstructionResult();
	}

	public void setMemento(MementoSetter memento) {
		memento.setExecutionId(this.executionId.v());
		memento.setIndexReconstructionResult(this.indexReconstructionResult);
	}

	public static interface MementoSetter {
		void setExecutionId(String indexNo);
		void setIndexReconstructionResult(List<ProcExecIndexResult> indexReconstructionResult);
	}

	public static interface MementoGetter {
		String getExecutionId();
		List<ProcExecIndexResult> getIndexReconstructionResult();
	}
}
