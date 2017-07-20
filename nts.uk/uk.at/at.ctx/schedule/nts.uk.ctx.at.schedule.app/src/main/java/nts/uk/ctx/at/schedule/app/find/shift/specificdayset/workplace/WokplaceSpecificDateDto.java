package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;

@Value
public class WokplaceSpecificDateDto {

	public BigDecimal specificDate;
	public BigDecimal specificDateItemNo;
	public String specificDateItemName;

	public static WokplaceSpecificDateDto fromDomain(WorkplaceSpecificDateItem domain) {
		return new WokplaceSpecificDateDto(domain.getSpecificDate().v(), 
				domain.getSpecificDateItemNo().v(),
				domain.getSpecificDateItemName().v());

	}
}
