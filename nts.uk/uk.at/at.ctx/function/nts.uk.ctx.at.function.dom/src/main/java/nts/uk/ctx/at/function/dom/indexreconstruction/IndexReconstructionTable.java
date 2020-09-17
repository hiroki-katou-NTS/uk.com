package nts.uk.ctx.at.function.dom.indexreconstruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 *	 インデックス再構成テーブル
 * 	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndexReconstructionTable extends AggregateRoot {

	/**  カテゴリNO */
	private IndexName indexNo;
	
	/** テーブル日本語名 */
	private TableName tableJapaneseName;
	
	/** テーブル物理名 */
	private TableName tablePhysicalName;
	
	public static IndexReconstructionTable createFromMemento(MementoGetter memento) {
		IndexReconstructionTable domain = new IndexReconstructionTable();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.indexNo = memento.getIndexNo();
		this.tableJapaneseName = memento.getTableJapaneseName();
		this.tablePhysicalName = memento.getTablePhysicalName();
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setIndexNo(this.indexNo);
		if (this.tablePhysicalName != null) {
			memento.setTablePhysicalName(this.tablePhysicalName);
		}
		if (this.tablePhysicalName != null) {
			memento.setTableJapaneseName(this.tableJapaneseName);
		}
	}
	
	public static interface MementoSetter {
		void setIndexNo(IndexName indexNo);
		void setTableJapaneseName(TableName tableJapaneseName);
		void setTablePhysicalName(TableName tablePhysicalName);
	}
	
	public static interface MementoGetter {
		TableName getTableJapaneseName();
		TableName getTablePhysicalName();
		IndexName getIndexNo();
	}
}
