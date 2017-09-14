/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.breakdown;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.overtime.ProductNumber;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento;

/**
 * The Class OvertimeBRDItemSaveDto.
 */
@Getter
@Setter
public class OvertimeBRDItemSaveDto implements OvertimeBRDItemGetMemento{

	/** The use classification. */
	private Boolean useClassification;

	/** The breakdown item no. */
	private Integer breakdownItemNo;

	/** The name. */
	private String name;

	/** The product number. */
	private Integer productNumber;
	
	/** The attendance item ids. */
	private List<Integer> attendanceItemIds;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeGetMemento#getUseClassification()
	 */
	@Override
	public UseClassification getUseClassification() {
		return this.useClassification ? UseClassification.UseClass_Use
				: UseClassification.UseClass_NotUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getBreakdownItemNo()
	 */
	@Override
	public BreakdownItemNo getBreakdownItemNo() {
		return BreakdownItemNo.valueOf(this.breakdownItemNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getName()
	 */
	@Override
	public BreakdownItemName getName() {
		return new BreakdownItemName(this.name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getProductNumber()
	 */
	@Override
	public ProductNumber getProductNumber() {
		return ProductNumber.valueOf(this.productNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemGetMemento#
	 * getAttendanceItemIds()
	 */
	@Override
	public List<Integer> getAttendanceItemIds() {
		return this.attendanceItemIds;
	}
	

}
