package nts.uk.ctx.at.function.dom.indexreconstruction;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 *	 インデックス再構成カテゴリ
 * 	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
 */
@Getter
public class IndexReconstructionCategory extends AggregateRoot {

	/**  カテゴリNO */
	private IndexName indexNo;
	
	/** カテゴリ名 */
	private CategoryName categoryName;
	
	private IndexReconstructionCategory() {}
	
	public static IndexReconstructionCategory createFromMemento(MementoGetter memento) {
		IndexReconstructionCategory domain = new IndexReconstructionCategory();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.indexNo = new IndexName(memento.getIndexNo());
		this.categoryName = new CategoryName(memento.getCategoryName());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setIndexNo(this.indexNo);
		memento.setCategoryName(this.categoryName);
	}
	
	public static interface MementoSetter {
		void setIndexNo(IndexName indexNo);
		void setCategoryName(CategoryName categoryName);
	}
	
	public static interface MementoGetter {
		String getIndexNo();
		String getCategoryName();
	}
}
