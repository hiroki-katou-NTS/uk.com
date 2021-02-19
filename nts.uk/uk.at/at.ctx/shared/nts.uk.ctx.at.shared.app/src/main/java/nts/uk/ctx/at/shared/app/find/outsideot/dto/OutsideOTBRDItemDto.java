/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;

/**
 * The Class OutsideOTBRDItemDto.
 */
@Getter
@Setter
public class OutsideOTBRDItemDto {
	
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
	
	/** The premium extra 60 H rates. */
	private List<PremiumExtra60HRateDto> premiumExtra60HRates;
	
	public OutsideOTBRDItemDto(){
		
		this(0);
	}
	
	public OutsideOTBRDItemDto(int breakdownItemNo){
		this.useClassification = true;
		this.breakdownItemNo = breakdownItemNo;
		this.name = "";
		this.productNumber = breakdownItemNo;
		this.attendanceItemIds = new ArrayList<>();
		this.premiumExtra60HRates = new ArrayList<>();
	}

	public static OutsideOTBRDItemDto of(OutsideOTBRDItem domain) {
		OutsideOTBRDItemDto dto = new OutsideOTBRDItemDto();
		dto.useClassification = domain.getUseClassification() == UseClassification.UseClass_Use;
		dto.breakdownItemNo = domain.getBreakdownItemNo().value;
		dto.name = domain.getName().v();
		dto.productNumber = domain.getProductNumber().value;
		dto.attendanceItemIds = domain.getAttendanceItemIds();
		dto.premiumExtra60HRates = domain.getPremiumExtra60HRates().stream().map(c -> new PremiumExtra60HRateDto(
																								domain.getBreakdownItemNo().value, 
																								c.getPremiumRate().v(), 
																								c.getOvertimeNo().value))
																	.collect(Collectors.toList());
		
		return dto;
	}
	
	public OutsideOTBRDItem domain() {
		
		return new OutsideOTBRDItem(useClassification ? UseClassification.UseClass_Use : UseClassification.UseClass_NotUse,
				EnumAdaptor.valueOf(breakdownItemNo, BreakdownItemNo.class), 
				new BreakdownItemName(name), EnumAdaptor.valueOf(productNumber, ProductNumber.class),
				attendanceItemIds, 
				premiumExtra60HRates.stream().map(c -> c.domain()).collect(Collectors.toList()));
	}
}
