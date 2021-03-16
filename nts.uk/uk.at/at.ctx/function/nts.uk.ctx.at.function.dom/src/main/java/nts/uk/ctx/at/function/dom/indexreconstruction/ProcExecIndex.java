package nts.uk.ctx.at.function.dom.indexreconstruction;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * Domain インデックス再構成結果履歴
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcExecIndex extends AggregateRoot {

	/** 実行ID */
	private String executionId;

	/** 結果詳細 */
	private List<ProcExecIndexResult> indexReconstructionResult;

	public static ProcExecIndex createFromMemento(MementoGetter memento) {
		ProcExecIndex domain = new ProcExecIndex();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.executionId = memento.getExecutionId();
		this.indexReconstructionResult = memento.getIndexReconstructionResult();
	}

	public void setMemento(MementoSetter memento) {
		memento.setExecutionId(this.executionId);
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
