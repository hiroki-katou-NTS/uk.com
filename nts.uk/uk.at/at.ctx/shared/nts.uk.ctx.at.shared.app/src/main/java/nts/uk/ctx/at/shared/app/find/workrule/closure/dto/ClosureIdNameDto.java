/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;

/**
 * The Class ClosureIdNameDto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureIdNameDto {

	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The closure name. */
	// 名称: 締め名称
	private String closureName;
	
	
	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the closure cd name dto
	 */
	public static ClosureIdNameDto fromDomain(ClosureHistory domain) {
		return new ClosureIdNameDto(domain.getClosureId().value, domain.getClosureName().v());
	}
}
