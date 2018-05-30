package nts.uk.ctx.sys.assist.dom.mastercopy;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MasterCopyCategory.
 */
// マスタコピーカテゴリ
@Getter
@Setter
public class MasterCopyCategory extends AggregateRoot {
	
	/** The system type. */
	// システム区分
	private SystemType systemType;
	
	/** The master copy id. */
	// マスタコピーID
	private String masterCopyId;
	
	/** The master copy category. */
	// マスタコピーカテゴリ
	private MasterCopyCategoryName masterCopyCategory;
	
	/** The order. */
	// 並び順
	private Integer order;

	/**
	 * Instantiates a new master copy category.
	 *
	 * @param memento the memento
	 */
	public MasterCopyCategory(MasterCopyCategoryGetMemento memento) {
		this.systemType = memento.getSystemType();
		this.masterCopyId = memento.getMasterCopyId();
		this.masterCopyCategory = memento.getMasterCopyCategory();
		this.order = memento.getOrder();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MasterCopyCategorySetMemento memento) {
		memento.setSystemType(this.systemType);
		memento.setMasterCopyId(this.masterCopyId);
		memento.setMasterCopyCategory(this.masterCopyCategory);
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
