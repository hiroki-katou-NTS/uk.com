/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class SelectedAttendanceItem.
 */
// 選択勤怠項目
@Getter
public class SelectedAttendanceItem extends DomainObject {

	/** The attendance item id. */
	// 勤怠項目ID
	private String attendanceItemId;

	/** The operator. */
	// 演算子
	private AddSubOperator operator;

}
