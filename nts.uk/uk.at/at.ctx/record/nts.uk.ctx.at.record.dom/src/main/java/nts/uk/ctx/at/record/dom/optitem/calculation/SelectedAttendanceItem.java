/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

/**
 * The Class SelectedAttendanceItem.
 */
// 選択勤怠項目
@Getter
public class SelectedAttendanceItem extends DomainObject {

	/** The attendance item id. */
	// 勤怠項目ID
	private int attendanceItemId;

	/** The operator. */
	// 演算子
	private AddSubOperator operator;

	/**
	 * Instantiates a new selected attendance item.
	 *
	 * @param memento the memento
	 */
	public SelectedAttendanceItem(SelectedAttendanceItemGetMemento memento) {
		this.attendanceItemId = memento.getAttItemId();
		this.operator = memento.getOperator();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SelectedAttendanceItemSetMemento memento) {
		memento.setAttItemId(this.attendanceItemId);
		memento.setOperator(this.operator);
	}
	
	
	/**
	 * 自身の持つ演算子を基に計算
	 * @param itemValue
	 * @param result
	 * @return
	 */
	public Integer calc(ItemValue itemValue,Integer result) {
		Integer value = 0;
		if(itemValue.getValue()!=null) {
			if (itemValue.getValueType().isInteger()){
				value = itemValue.value();
			}
			if (itemValue.getValueType().isDouble()){
				Double doubleValue = itemValue.value();
				value = doubleValue.intValue();
			}
		}
		switch(this.operator) {
		case ADD:
			return result + value;
		case SUBTRACT:
			return result + (value * (-1));
		default:
			throw new RuntimeException("unknown operator:"+operator);
		}
	}
	
	
	
}
