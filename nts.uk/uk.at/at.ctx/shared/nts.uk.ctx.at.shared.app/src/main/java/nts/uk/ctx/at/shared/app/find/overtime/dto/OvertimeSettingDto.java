/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeCalculationMethod;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItem;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingSetMemento;

/**
 * The Class OvertimeSettingDto.
 */

@Getter
@Setter
public class OvertimeSettingDto implements OvertimeSettingSetMemento{

	/** The note. */
	private String note;
	
    /** The calculation method. */
    private Integer calculationMethod;
    
    /** The breakdown items. */
    private List<OvertimeBRDItemDto> breakdownItems; 
    
    /** The overtimes. */
    private List<OvertimeDto> overtimes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#setCompanyId(
	 * nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#setNote(nts.
	 * uk.ctx.at.shared.dom.overtime.OvertimeNote)
	 */
	@Override
	public void setNote(OvertimeNote note) {
		this.note = note.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingSetMemento#
	 * setBreakdownItems(java.util.List)
	 */
	@Override
	public void setBreakdownItems(List<OvertimeBRDItem> breakdownItems) {
		this.breakdownItems = breakdownItems.stream().map(overtimeBRDItem -> {
			OvertimeBRDItemDto dto = new OvertimeBRDItemDto();
			overtimeBRDItem.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#
	 * setCalculationMethod(nts.uk.ctx.at.shared.dom.overtime.
	 * OvertimeCalculationMethod)
	 */
	@Override
	public void setCalculationMethod(OvertimeCalculationMethod calculationMethod) {
		this.calculationMethod = calculationMethod.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingSetMemento#setOvertimes(
	 * java.util.List)
	 */
	@Override
	public void setOvertimes(List<Overtime> overtimes) {
		this.overtimes = overtimes.stream().map(overtime->{
			OvertimeDto dto =new OvertimeDto();
			overtime.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	

}
