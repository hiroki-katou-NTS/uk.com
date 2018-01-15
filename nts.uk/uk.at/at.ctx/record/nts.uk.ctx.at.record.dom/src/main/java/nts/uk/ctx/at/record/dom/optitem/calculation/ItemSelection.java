/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CalculationItemSelection.
 */
// 計算式設定
@Getter
public class ItemSelection extends DomainObject {

	/** The minus segment. */
	// マイナス区分
	private MinusSegment minusSegment;

	/** The selected attendance items. */
	// 選択勤怠項目
	private List<SelectedAttendanceItem> selectedAttendanceItems;

	/**
	 * Instantiates a new item selection.
	 *
	 * @param memento the memento
	 */
	public ItemSelection(ItemSelectionGetMemento memento) {
		this.minusSegment = memento.getMinusSegment();
		this.selectedAttendanceItems = memento.getListSelectedAttendanceItem();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ItemSelectionSetMemento memento) {
		memento.setMinusSegment(this.minusSegment);
		memento.setListSelectedAttendanceItem(this.selectedAttendanceItems);
	}
}
