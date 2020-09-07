package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.UseAtr;
/**
 * 
 * 特定日項目
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecificDateItem extends AggregateRoot {
	
	private String companyId;
	
	private UseAtr useAtr;
	
	private SpecificDateItemNo specificDateItemNo;
	
	private SpecificName specificName;

	public static SpecificDateItem createFromJavaType(String companyId, Integer useAtr, Integer specificDateItemNo, String specificName) {
		return new SpecificDateItem(
				companyId,
				EnumAdaptor.valueOf(useAtr, UseAtr.class), 
				new SpecificDateItemNo(specificDateItemNo), 
				new SpecificName(specificName));
	}

}
