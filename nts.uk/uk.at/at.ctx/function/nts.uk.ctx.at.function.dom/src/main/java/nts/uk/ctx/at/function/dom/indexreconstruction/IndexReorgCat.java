package nts.uk.ctx.at.function.dom.indexreconstruction;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstructionCategoryNO;

/**
 *	 インデックス再構成カテゴリ
 * 	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.インデックス再構成.インデックス再構成
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
	
	private IndexReorgCat() {}
	
	public static IndexReorgCat createFromMemento(MementoGetter memento) {
		IndexReorgCat domain = new IndexReorgCat();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.categoryNo = new IndexReconstructionCategoryNO(memento.getCategoryNo().toString());
		this.categoryName = new CategoryName(memento.getCategoryName());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setCategoryNo(new BigDecimal(this.categoryNo.v()));
		memento.setCategoryName(this.categoryName.v());
	}
	
	public static interface MementoSetter {
		void setCategoryNo(BigDecimal indexNo);
		void setCategoryName(String categoryName);
	}
	
	public static interface MementoGetter {
		BigDecimal getCategoryNo();
		String getCategoryName();
	}
}
