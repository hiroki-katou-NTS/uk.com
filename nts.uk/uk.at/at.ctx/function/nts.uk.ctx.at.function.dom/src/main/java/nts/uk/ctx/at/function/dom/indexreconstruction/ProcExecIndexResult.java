package nts.uk.ctx.at.function.dom.indexreconstruction;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 	インデックス再構成結果
 * @author ngatt-nws
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcExecIndexResult extends DomainObject {

	/** 
	 * インデックス名 
	 **/
	private IndexName indexName;

	/** 
	 * テーブル物理名 
	 **/
	private TableName tablePhysicalName;

	/** 
	 * 処理前の断片化率
	 **/
	private FragmentationRate fragmentationRate;

	/** 
	 * 処理後の断片化率 
	 **/
	private FragmentationRate fragmentationRateAfterProcessing;

	public static ProcExecIndexResult createFromMemento(MementoGetter memento) {
		ProcExecIndexResult domain = new ProcExecIndexResult();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.indexName = new IndexName(memento.getIndexName());
		this.tablePhysicalName = new TableName(memento.getTablePhysicalName());
		this.fragmentationRate = new FragmentationRate(memento.getFragmentationRate());
		this.fragmentationRateAfterProcessing = new FragmentationRate(memento.getFragmentationRateAfterProcessing());
	}

	public void setMemento(MementoSetter memento) {
		memento.setIndexName(this.indexName.v());
		memento.setTablePhysicalName(this.tablePhysicalName.v());
		memento.setFragmentationRate(this.fragmentationRate.v());
		memento.setFragmentationRateAfterProcessing(this.fragmentationRateAfterProcessing.v());
	}

	public static interface MementoSetter {
		void setIndexName(String indexNo);

		void setTablePhysicalName(String tablePhysicalName);

		void setFragmentationRate(BigDecimal fragmentationRate);

		void setFragmentationRateAfterProcessing(BigDecimal fragmentationRateAfterProcessing);
	}

	public static interface MementoGetter {
		String getIndexName();
		
		String getTablePhysicalName();
		
		BigDecimal getFragmentationRate();
		
		BigDecimal getFragmentationRateAfterProcessing();
	}
}
