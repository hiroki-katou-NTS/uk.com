package nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDate;

@Getter
public class WorkplaceSpecificDateItem extends AggregateRoot {
	private String workplaceId;
	private SpecificDate specificDate;
	private SpecificDateItemNo specificDateItemNo;

	public WorkplaceSpecificDateItem(String workplaceId, SpecificDate specificDate, SpecificDateItemNo specificDateItemNo) {
		this.workplaceId = workplaceId;
		this.specificDate = specificDate;
		this.specificDateItemNo = specificDateItemNo;
	}

	public static WorkplaceSpecificDateItem createFromJavaType(String workplaceId, BigDecimal specificDate,
			BigDecimal specificDateItemNo) {
		return new WorkplaceSpecificDateItem(workplaceId, new SpecificDate(specificDate),
				new SpecificDateItemNo(specificDateItemNo));
	}
}
