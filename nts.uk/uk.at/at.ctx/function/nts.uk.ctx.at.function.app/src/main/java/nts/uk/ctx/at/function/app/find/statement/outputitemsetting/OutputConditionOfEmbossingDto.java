/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new output condition of embossing dto.
 */
@Setter
@Getter
@NoArgsConstructor
public class OutputConditionOfEmbossingDto {

	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The lst stamping output item set dto. */
	private List<StampingOutputItemSetDto> lstStampingOutputItemSetDto;
	
	/** The exist auth empl. */
	private boolean existAuthEmpl;
}
