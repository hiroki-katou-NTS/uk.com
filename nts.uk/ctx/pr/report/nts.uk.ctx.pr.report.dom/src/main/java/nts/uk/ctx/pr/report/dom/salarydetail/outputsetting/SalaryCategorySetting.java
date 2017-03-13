/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Class SalaryCategorySetting.
 */
@Getter
public class SalaryCategorySetting extends DomainObject {

	/** The category. */
	private SalaryCategory category;

	/** The items. */
	private List<SalaryOutputItem> items;

	/**
	 * Instantiates a new salary category setting.
	 *
	 * @param memento the memento
	 */
	public SalaryCategorySetting(SalaryCategorySettingGetMemento memento) {
		super();
		this.category = memento.getSalaryCategory();
		this.items = memento.getSalaryOutputItems();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SalaryCategorySettingSetMemento memento) {
		memento.setSalaryCategory(this.category);
		memento.setSalaryOutputItems(this.items);
	}
}
