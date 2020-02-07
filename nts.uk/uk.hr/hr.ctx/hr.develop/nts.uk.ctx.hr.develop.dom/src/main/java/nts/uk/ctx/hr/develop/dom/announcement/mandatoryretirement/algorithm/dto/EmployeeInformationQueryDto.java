/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeInformationQueryDto.
 */
// 社員情報
@AllArgsConstructor
@Getter
public class EmployeeInformationQueryDto {

	/** The employee ids. */
	List<String> employeeIds; // 社員一覧

	/** The reference date. */
	GeneralDate referenceDate; // 年月日

	/** The to get workplace. */
	boolean toGetWorkplace; // 職場を取得する

	/** The to get department. */
	boolean toGetDepartment; // 部門を取得する

	/** The to get position. */
	boolean toGetPosition; // 職位を取得する

	/** The to get employment. */
	boolean toGetEmployment; // 雇用を取得する

	/** The to get classification. */
	boolean toGetClassification; // 分類を取得する

	/** The to get employment cls. */
	boolean toGetEmploymentCls; // 就業区分を取得する
}
