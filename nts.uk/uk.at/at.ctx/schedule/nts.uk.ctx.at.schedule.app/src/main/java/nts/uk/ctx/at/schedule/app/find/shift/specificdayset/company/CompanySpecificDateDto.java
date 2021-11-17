package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;

@Value
public class CompanySpecificDateDto {

	public GeneralDate specificDate;
	public Integer specificDateItemNo;
	public String specificDateItemName;

	public static CompanySpecificDateDto fromDomain(CompanySpecificDateItem domain) {
		return new CompanySpecificDateDto(
				domain.getSpecificDate(), 
				null, null//TODO dev fix
				);
	}
}
