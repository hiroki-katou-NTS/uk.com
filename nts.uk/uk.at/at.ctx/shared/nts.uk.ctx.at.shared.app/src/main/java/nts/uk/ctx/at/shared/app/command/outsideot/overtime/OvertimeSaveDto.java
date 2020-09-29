/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.overtime;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeValue;

/**
 * The Class OvertimeSaveDto.
 */
@Getter
@Setter
public class OvertimeSaveDto implements OvertimeGetMemento{

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
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeGetMemento#getUseClassification()
	 */
	@Override
	public UseClassification getUseClassification() {
		return this.useClassification ? UseClassification.UseClass_Use
				: UseClassification.UseClass_NotUse;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeGetMemento#getSuperHoliday60HOccurs()
	 */
	@Override
	public boolean getSuperHoliday60HOccurs() {
		return this.superHoliday60HOccurs;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeGetMemento#getOvertimeNo()
	 */
	@Override
	public OvertimeNo getOvertimeNo() {
		return OvertimeNo.valueOf(this.overtimeNo);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeGetMemento#getOvertime()
	 */
	@Override
	public OvertimeValue getOvertime() {
		return new OvertimeValue(this.overtime);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeGetMemento#getName()
	 */
	@Override
	public OvertimeName getName() {
		return new OvertimeName(this.name);
	}

}
