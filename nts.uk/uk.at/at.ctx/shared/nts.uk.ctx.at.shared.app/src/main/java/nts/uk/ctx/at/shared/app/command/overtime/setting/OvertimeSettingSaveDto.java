/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.overtime.OvertimeSaveDto;
import nts.uk.ctx.at.shared.app.command.overtime.breakdown.OvertimeBRDItemSaveDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeCalculationMethod;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItem;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento;

/**
 * The Class OvertimeSettingSaveDto.
 */
@Getter
@Setter
public class OvertimeSettingSaveDto implements OvertimeSettingGetMemento{

	/** The note. */
	private String note;
	
    /** The calculation method. */
    private Integer calculationMethod;
    
    /** The breakdown items. */
    private List<OvertimeBRDItemSaveDto> breakdownItems; 
    
    /** The overtimes. */
    private List<OvertimeSaveDto> overtimes;
    
    /** The company id. */
    private String companyId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento#
	 * getNote()
	 */
	@Override
	public OvertimeNote getNote() {
		return new OvertimeNote(this.note);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento#
	 * getCalculationMethod()
	 */
	@Override
	public OvertimeCalculationMethod getCalculationMethod() {
		return OvertimeCalculationMethod.valueOf(this.calculationMethod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento#
	 * getBreakdownItems()
	 */
	@Override
	public List<OvertimeBRDItem> getBreakdownItems() {
		return this.breakdownItems.stream().map(dto -> new OvertimeBRDItem(dto))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingGetMemento#
	 * getOvertimes()
	 */
	@Override
	public List<Overtime> getOvertimes() {
		return this.overtimes.stream().map(dto->new Overtime(dto)).collect(Collectors.toList());
	}

	

}
