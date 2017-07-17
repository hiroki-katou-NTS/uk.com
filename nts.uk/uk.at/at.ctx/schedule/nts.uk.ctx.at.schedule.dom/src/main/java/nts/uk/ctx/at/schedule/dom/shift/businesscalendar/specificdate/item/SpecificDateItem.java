package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.UseAtr;

@Getter
@Setter
public class SpecificDateItem extends AggregateRoot {
	
	private String companyId;
	
	private String timeItemId;
	
	private UseAtr useAtr;
	
	private SpecificDateItemNo specificDateItemNo;
	
	private SpecificName specificName;

	public static SpecificDateItem createFromJavaType(String companyId, String timeItemId, BigDecimal useAtr, BigDecimal specificDateItemNo, String specificName) {
		return new SpecificDateItem(companyId, timeItemId,
				EnumAdaptor.valueOf(useAtr.intValue(), UseAtr.class), new SpecificDateItemNo(specificDateItemNo), new SpecificName(specificName));
	}
	
	public SpecificDateItem(String companyId, String timeItemId, UseAtr useAtr, SpecificDateItemNo specificDateItemNo,
			SpecificName specificName) {
		super();
		this.companyId = companyId;
		this.timeItemId = timeItemId;
		this.useAtr = useAtr;
		this.specificDateItemNo = specificDateItemNo;
		this.specificName = specificName;
	}

}
