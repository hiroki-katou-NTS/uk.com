/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.AddSubOperator;
import nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItemGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItemSetMemento;

/**
 * The Class SelectedAttendanceItemDto.
 */
public class SelectedAttendanceItemDto implements SelectedAttendanceItemGetMemento, SelectedAttendanceItemSetMemento {

	/** The attendance item id. */
	// 勤怠項目ID
	private String attendanceItemId;

	/** The operator. */
	// 演算子
	private int operator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.
	 * SelectedAttendanceItemSetMemento#setAttItemId(java.lang.String)
	 */
	@Override
	public void setAttItemId(String id) {
		this.attendanceItemId = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.
	 * SelectedAttendanceItemSetMemento#setOperator(nts.uk.ctx.at.record.dom.
	 * optitem.calculation.AddSubOperator)
	 */
	@Override
	public void setOperator(AddSubOperator operator) {
		this.operator = operator.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.
	 * SelectedAttendanceItemGetMemento#getAttItemId()
	 */
	@Override
	public String getAttItemId() {
		return this.attendanceItemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.
	 * SelectedAttendanceItemGetMemento#getOperator()
	 */
	@Override
	public AddSubOperator getOperator() {
		return EnumAdaptor.valueOf(this.operator, AddSubOperator.class);
	}
}
