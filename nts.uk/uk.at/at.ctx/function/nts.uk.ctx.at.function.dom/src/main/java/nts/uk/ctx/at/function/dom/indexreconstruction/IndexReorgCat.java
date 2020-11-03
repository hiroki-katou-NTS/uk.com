package nts.uk.ctx.at.function.dom.indexreconstruction;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstructionCategoryNO;

/**
 * Domain インデックス再構成カテゴリ<br>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成カテゴリ
 */
@Getter
public class IndexReorgCat extends AggregateRoot {

	/**
	 * カテゴリNO
	 **/
	private IndexReconstructionCategoryNO categoryNo;

	/**
	 * カテゴリ名
	 **/
	private CategoryName categoryName;

	/**
	 * No args constructor.
	 */
	private IndexReorgCat() {
	}

	/**
	 * Creates domain from memento.
	 *
	 * @param memento the Memento getter
	 * @return the domain インデックス再構成カテゴリ
	 */
	public static IndexReorgCat createFromMemento(MementoGetter memento) {
		IndexReorgCat domain = new IndexReorgCat();
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
		this.categoryName = new CategoryName(memento.getCategoryName());
	}

	/**
	 * Sets memento.
	 *
	 * @param memento the Memento setter
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCategoryNo(this.categoryNo.v());
		memento.setCategoryName(this.categoryName.v());
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
		 * Sets category name.
		 *
		 * @param categoryName the category name
		 */
		void setCategoryName(String categoryName);
	}

	/**
	 * The interface Memento getter.
	 */
	public static interface MementoGetter {
		/**
		 * Gets category no.
		 *
		 * @return the category no
		 */
		int getCategoryNo();

		/**
		 * Gets category name.
		 *
		 * @return the category name
		 */
		String getCategoryName();
	}

}
