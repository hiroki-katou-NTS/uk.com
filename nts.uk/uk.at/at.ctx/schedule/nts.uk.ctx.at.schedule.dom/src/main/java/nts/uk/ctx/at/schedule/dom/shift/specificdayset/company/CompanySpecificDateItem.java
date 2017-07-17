package nts.uk.ctx.at.schedule.dom.shift.specificdayset.company;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDate;

@Getter
public class CompanySpecificDateItem extends AggregateRoot {
	private String companyId;
	private SpecificDate specificDate;
	private SpecificDateItemNo specificDateItemNo;
	private SpecificName specificDateItemName;

	public CompanySpecificDateItem(String companyId, SpecificDate specificDate, SpecificDateItemNo specificDateItemNo,SpecificName specificDateItemName) {
		this.companyId = companyId;
		this.specificDate = specificDate;
		this.specificDateItemNo = specificDateItemNo;
		this.specificDateItemName = specificDateItemName;
	}

	public static CompanySpecificDateItem createFromJavaType(String companyId, BigDecimal specificDate, BigDecimal specificDateItemNo,String specificDateItemName) {
		return new CompanySpecificDateItem(companyId, 
				new SpecificDate(specificDate),
				new SpecificDateItemNo(specificDateItemNo),
				new SpecificName(specificDateItemName));
	}
}
