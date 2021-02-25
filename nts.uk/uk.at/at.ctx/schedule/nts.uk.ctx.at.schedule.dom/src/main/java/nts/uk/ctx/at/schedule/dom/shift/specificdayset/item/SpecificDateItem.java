package nts.uk.ctx.at.schedule.dom.shift.specificdayset.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * 特定日項目
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecificDateItem extends AggregateRoot {
	
	private String companyId;
	
	private NotUseAtr useAtr;
	
	private SpecificDateItemNo specificDateItemNo;
	
	private SpecificName specificName;

	public static SpecificDateItem createFromJavaType(String companyId, Integer useAtr, Integer specificDateItemNo, String specificName) {
		return new SpecificDateItem(
				companyId,
				NotUseAtr.valueOf(useAtr), 
				new SpecificDateItemNo(specificDateItemNo), 
				new SpecificName(specificName));
	}

}
