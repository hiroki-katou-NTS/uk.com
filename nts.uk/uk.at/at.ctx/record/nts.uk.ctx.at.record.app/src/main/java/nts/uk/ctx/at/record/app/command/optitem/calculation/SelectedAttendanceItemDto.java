/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.AddSubOperator;
import nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItemGetMemento;

/**
 * The Class SelectedAttendanceItemDto.
 */
@Getter
@Setter
public class SelectedAttendanceItemDto implements SelectedAttendanceItemGetMemento {

	/** The attendance item id. */
	// 勤怠項目ID
	private String id;

	/** The operator. */
	// 演算子
	private int operator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.
	 * SelectedAttendanceItemGetMemento#getAttItemId()
	 */
	@Override
	public String getAttItemId() {
		return this.id;
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
