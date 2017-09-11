/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class CalculationItemSelection.
 */
// 計算式設定
@Getter
public class ItemSelection extends AggregateRoot {

	/** The minus segment. */
	// マイナス区分
	private MinusSegment minusSegment;

	/** The selected attendance items. */
	// 選択勤怠項目
	private List<SelectedAttendanceItem> selectedAttendanceItems;

}
