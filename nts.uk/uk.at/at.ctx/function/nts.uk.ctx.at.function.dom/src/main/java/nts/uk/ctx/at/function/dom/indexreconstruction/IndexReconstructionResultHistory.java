package nts.uk.ctx.at.function.dom.indexreconstruction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;

/**
 * 	インデックス再構成結果履歴
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */

@Getter
@NoArgsConstructor
public class IndexReconstructionResultHistory extends AggregateRoot {

	/** 実行ID*/
	private ExecutionCode executionId;
	
	/** 結果詳細*/
	private IndexReconstructionResult indexReconstructionResult;
	
	public static IndexReconstructionResultHistory createFromMemento(MementoGetter memento) {
		IndexReconstructionResultHistory domain = new IndexReconstructionResultHistory();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.executionId = memento.getexecutionId();
		this.indexReconstructionResult = memento.getIndexReconstructionResult();
	}

	public void setMemento(MementoSetter memento) {
		memento.setexecutionId(this.executionId);
		memento.setIndexReconstructionResult(this.indexReconstructionResult);
	}

	public static interface MementoSetter {
		void setexecutionId(ExecutionCode indexNo);
		void setIndexReconstructionResult(IndexReconstructionResult fragmentationRate);
	}

	public static interface MementoGetter {
		IndexReconstructionResult getIndexReconstructionResult();
		ExecutionCode getexecutionId();
	}
}
