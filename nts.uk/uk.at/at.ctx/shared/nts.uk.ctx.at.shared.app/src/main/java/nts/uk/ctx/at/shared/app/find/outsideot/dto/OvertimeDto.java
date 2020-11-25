/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeValue;

/**
 * The Class OvertimeDto.
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
	private Boolean useClassification;
	
	/** The super holiday 60 H occurs. */
	private Boolean superHoliday60HOccurs;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento#setSuperHoliday60HOccurs(boolean)
	 */
	@Override
	public void setSuperHoliday60HOccurs(boolean superHoliday60HOccurs) {
		this.superHoliday60HOccurs = superHoliday60HOccurs;
	}

	/**
	 * Sets the use classification.
	 *
	 * @param useClassification the new use classification
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.useClassification = (useClassification.value == UseClassification.UseClass_Use.value);
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
