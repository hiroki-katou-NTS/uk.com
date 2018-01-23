/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class BsEmploymentFindDto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BsEmploymentFindDto {

	/** The employment code. */
	// 雇用コード.
	private String employmentCode;

	/** The employment name. */
	// 雇用名称.
	private String employmentName;

	/** The emp external code. */
	// 雇用外部コード.
	private String empExternalCode;

	/** The memo. */
	// メモ.
	private String memo;
}
