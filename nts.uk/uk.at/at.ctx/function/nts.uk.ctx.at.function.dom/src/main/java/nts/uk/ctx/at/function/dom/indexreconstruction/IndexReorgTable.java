package nts.uk.ctx.at.function.dom.indexreconstruction;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstructionCategoryNO;

import java.math.BigDecimal;

/**
 * Domain インデックス再構成テーブル<br>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */
@Getter
public class IndexReorgTable extends AggregateRoot {

	/**
	 * カテゴリNO
	 */
	private IndexReconstructionCategoryNO categoryNo;

	/**
	 * テーブル日本語名
	 */
	private TableName tableJpName;

	/**
	 * テーブル物理名
	 */
	private TableName tablePhysName;

	/**
	 * No args constructor.
	 */
	private IndexReorgTable() {
	}

	/**
	 * Creates domain from memento.
	 *
	 * @param memento the Memento getter
	 * @return the domain インデックス再構成テーブル
	 */
	public static IndexReorgTable createFromMemento(MementoGetter memento) {
		IndexReorgTable domain = new IndexReorgTable();
		domain.getMemento(memento);
		return domain;
	}

	/**
	 * Gets memento.
	 *
	 * @param memento the Memento getter
	 */
	public void getMemento(MementoGetter memento) {
		this.categoryNo = new IndexReconstructionCategoryNO(memento.getCategoryNo());
		this.tableJpName = new TableName(memento.getTableJpName());
		this.tablePhysName = new TableName(memento.getTablePhysName());
	}

	/**
	 * Sets memento.
	 *
	 * @param memento the Memento setter
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCategoryNo(this.categoryNo.v());
		memento.setTablePhysName(this.tablePhysName.v());
		memento.setTableJpName(this.tableJpName.v());
	}

	/**
	 * The interface Memento setter.
	 */
	public static interface MementoSetter {
		/**
		 * Sets category no.
		 *
		 * @param categoryNo the category no
		 */
		void setCategoryNo(int categoryNo);

		/**
		 * Sets table jp name.
		 *
		 * @param tableJapaneseName the table japanese name
		 */
		void setTableJpName(String tableJapaneseName);

		/**
		 * Sets table phys name.
		 *
		 * @param tablePhysicalName the table physical name
		 */
		void setTablePhysName(String tablePhysicalName);
	}

	/**
	 * The interface Memento getter.
	 */
	public static interface MementoGetter {
		/**
		 * Gets table jp name.
		 *
		 * @return the table jp name
		 */
		String getTableJpName();

		/**
		 * Gets table phys name.
		 *
		 * @return the table phys name
		 */
		String getTablePhysName();

		/**
		 * Gets category no.
		 *
		 * @return the category no
		 */
		int getCategoryNo();
	}

}
