/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeValue;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;

/**
 * The Class OvertimeDto.
 */

/**
 * Gets the use classification.
 *
 * @return the use classification
 */
@Getter
@Setter
public class OvertimeDto implements OvertimeSetMemento{
	
	/** The name. */
	private String name;
	
	/** The overtime. */
	private Integer overtime;
	
	/** The overtime no. */
	private Integer overtimeNo;
	
	/** The use classification. */
	private Integer useClassification;

	/**
	 * Sets the super holiday 60 H occurs.
	 *
	 * @param superHoliday60HOccurs the new super holiday 60 H occurs
	 */
	@Override
	public void setSuperHoliday60HOccurs(boolean superHoliday60HOccurs) {
		// No thing code
	}

	/**
	 * Sets the use classification.
	 *
	 * @param useClassification the new use classification
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.useClassification = useClassification.value;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	@Override
	public void setName(OvertimeName name) {
		this.name = name.v();
	}

	/**
	 * Sets the overtime.
	 *
	 * @param overtime the new overtime
	 */
	@Override
	public void setOvertime(OvertimeValue overtime) {
		this.overtime = overtime.v();
	}

	/**
	 * Sets the overtime no.
	 *
	 * @param overtimeNo the new overtime no
	 */
	@Override
	public void setOvertimeNo(OvertimeNo overtimeNo) {
		this.overtimeNo = overtimeNo.value;
	}
	
}
