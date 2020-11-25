/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItemSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;

/**
 * The Class OutsideOTBRDItemDto.
 */
@Getter
@Setter
public class OutsideOTBRDItemDto implements OutsideOTBRDItemSetMemento{
	
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
	
	public void defaultData(int breakdownItemNo){
		this.useClassification = true;
		this.breakdownItemNo = breakdownItemNo;
		this.name = "";
		this.productNumber = breakdownItemNo;
		this.attendanceItemIds = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setUseClassification(nts.uk.ctx.at.shared.dom.overtime.UseClassification)
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.useClassification = (useClassification.value == UseClassification.UseClass_Use.value); 

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setBreakdownItemNo(nts.uk.ctx.at.shared.dom.overtime.breakdown.
	 * BreakdownItemNo)
	 */
	@Override
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo) {
		this.breakdownItemNo = breakdownItemNo.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setName(nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName)
	 */
	@Override
	public void setName(BreakdownItemName name) {
		this.name = name.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setProductNumber(nts.uk.ctx.at.shared.dom.overtime.ProductNumber)
	 */
	@Override
	public void setProductNumber(ProductNumber productNumber) {
		this.productNumber = productNumber.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemSetMemento#
	 * setAttendanceItemIds(java.util.List)
	 */
	@Override
	public void setAttendanceItemIds(List<Integer> attendanceItemIds) {
		this.attendanceItemIds = attendanceItemIds;
		
	}

}
