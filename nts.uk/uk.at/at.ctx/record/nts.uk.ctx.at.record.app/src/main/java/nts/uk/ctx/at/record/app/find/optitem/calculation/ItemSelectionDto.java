/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.ItemSelectionSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SelectedAttendanceItem;

/**
 * The Class ItemSelectionDto.
 */
@Getter
@Setter
public class ItemSelectionDto implements ItemSelectionSetMemento {

	/** The minus segment. */
	private int minusSegment;

	/** The selected attendance items. */
	private List<SelectedAttendanceItemDto> attendanceItems;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionSetMemento#
	 * setMinusSegment(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * MinusSegment)
	 */
	@Override
	public void setMinusSegment(MinusSegment segment) {
		this.minusSegment = segment.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionSetMemento#
	 * setListSelectedAttendanceItem(java.util.List)
	 */
	@Override
	public void setListSelectedAttendanceItem(List<SelectedAttendanceItem> items) {
		this.attendanceItems = items.stream().map(item -> {
			SelectedAttendanceItemDto dto = new SelectedAttendanceItemDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
