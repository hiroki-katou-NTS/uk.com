package nts.uk.ctx.at.function.dom.indexreconstruction;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 	インデックス再構成結果
 * @author ngatt-nws
 *
 */
@Getter
@NoArgsConstructor
public class IndexReconstructionResult {

	/** インデックス名 */
	private IndexName indexName;

	/** テーブル物理名 */
	// QA111441
	private String tablePhysicalName;

	/** 処理前の断片化率 */
	private FragmentationRate fragmentationRate;

	/** 処理後の断片化率 */
	private FragmentationRate fragmentationRateAfterProcessing;

	public static IndexReconstructionResult createFromMemento(MementoGetter memento) {
		IndexReconstructionResult domain = new IndexReconstructionResult();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.indexName = memento.getIndexName();
		this.fragmentationRate = memento.getFragmentationRate();
		this.tablePhysicalName = memento.getTablePhysicalName();
		this.fragmentationRateAfterProcessing = memento.getFragmentationRateAfterProcessing();
	}

	public void setMemento(MementoSetter memento) {
		memento.setIndexName(this.indexName);
		if (this.tablePhysicalName != null) {
			memento.setTablePhysicalName(this.tablePhysicalName);
		}
		memento.setFragmentationRate(this.fragmentationRate);
		memento.setFragmentationRateAfterProcessing(this.fragmentationRateAfterProcessing);
	}

	public static interface MementoSetter {
		void setIndexName(IndexName indexNo);

		void setFragmentationRate(FragmentationRate fragmentationRate);

		void setFragmentationRateAfterProcessing(FragmentationRate fragmentationRateAfterProcessing);

		void setTablePhysicalName(String tablePhysicalName);
	}

	public static interface MementoGetter {
		FragmentationRate getFragmentationRate();
		
		FragmentationRate getFragmentationRateAfterProcessing();
		
		String getTablePhysicalName();
		
		IndexName getIndexName();
	}
}
