package nts.uk.ctx.sys.assist.dom.mastercopy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MasterCopyCategory.
 */
// マスタコピーカテゴリ
@Getter
@Setter
@NoArgsConstructor
public class MasterCopyCategory extends AggregateRoot {
	
	/** The system type. */
	// システム区分
	private SystemType systemType;
	
	/** The master copy id. */
	// マスタコピーID
	private MasterCopyCategoryNo categoryNo;
	
	/** The master copy category. */
	// マスタコピーカテゴリ
	private MasterCopyCategoryName categoryName;
	
	/** The order. */
	// 並び順
	private MasterCopyCategoryOrder order;

	/**
	 * Instantiates a new master copy category.
	 *
	 * @param memento the memento
	 */
	public MasterCopyCategory(MasterCopyCategoryGetMemento memento) {
		this.systemType = memento.getSystemType();
		this.categoryNo = memento.getCategoryNo();
		this.categoryName = memento.getCategoryName();
		this.order = memento.getOrder();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MasterCopyCategorySetMemento memento) {
		memento.setSystemType(this.systemType);
		memento.setCategoryNo(this.categoryNo);
		memento.setMasterCopyCategory(this.categoryName);
		memento.setOrder(this.order);
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
}
