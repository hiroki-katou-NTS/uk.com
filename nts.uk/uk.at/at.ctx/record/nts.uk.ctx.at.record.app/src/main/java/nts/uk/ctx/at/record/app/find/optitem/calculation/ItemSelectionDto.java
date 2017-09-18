/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItem;

/**
 * The Class ItemSelectionDto.
 */
public class ItemSelectionDto implements ItemSelectionGetMemento, ItemSelectionSetMemento {

	/** The minus segment. */
	// マイナス区分
	private int minusSegment;

	/** The selected attendance items. */
	// 選択勤怠項目
	private List<SelectedAttendanceItemDto> selectedAttendanceItems;

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
		items.stream().map(item -> {
			SelectedAttendanceItemDto dto = new SelectedAttendanceItemDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento#
	 * getMinusSegment()
	 */
	@Override
	public MinusSegment getMinusSegment() {
		return EnumAdaptor.valueOf(this.minusSegment, MinusSegment.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento#
	 * getListSelectedAttendanceItem()
	 */
	@Override
	public List<SelectedAttendanceItem> getListSelectedAttendanceItem() {
		return this.selectedAttendanceItems.stream().map(item -> new SelectedAttendanceItem(item))
				.collect(Collectors.toList());
	}
}
