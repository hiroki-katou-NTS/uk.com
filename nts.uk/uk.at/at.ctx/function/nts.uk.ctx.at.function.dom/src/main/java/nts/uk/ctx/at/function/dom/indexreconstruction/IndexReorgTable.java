package nts.uk.ctx.at.function.dom.indexreconstruction;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstructionCategoryNO;

/**
 *	Domain インデックス再構成テーブル
 * 	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */
@Getter
public class IndexReorgTable extends AggregateRoot {

	/**  カテゴリNO */
	private IndexReconstructionCategoryNO categoryNo;
	
	/** テーブル日本語名 */
	private TableName tableJpName;
	
	/** テーブル物理名 */
	private TableName tablePhysName;
	
	private IndexReorgTable() {}
	
	public static IndexReorgTable createFromMemento(MementoGetter memento) {
		IndexReorgTable domain = new IndexReorgTable();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.categoryNo = new IndexReconstructionCategoryNO(memento.getCategoryNo().toString());
		this.tableJpName = new TableName(memento.getTableJpName());
		this.tablePhysName = new TableName(memento.getTablePhysName());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setCategoryNo(new BigDecimal(this.categoryNo.v()));
		memento.setTablePhysName(this.tablePhysName.v());
		memento.setTableJpName(this.tableJpName.v());
	}
	
	public static interface MementoSetter {
		void setCategoryNo(BigDecimal indexNo);
		void setTableJpName(String tableJapaneseName);
		void setTablePhysName(String tablePhysicalName);
	}
	
	public static interface MementoGetter {
		String getTableJpName();
		String getTablePhysName();
		BigDecimal getCategoryNo();
	}
}
