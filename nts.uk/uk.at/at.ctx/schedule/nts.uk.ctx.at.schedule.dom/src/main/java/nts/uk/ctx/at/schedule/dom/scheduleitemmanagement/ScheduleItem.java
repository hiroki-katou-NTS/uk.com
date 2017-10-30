package nts.uk.ctx.at.schedule.dom.scheduleitemmanagement;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class ScheduleItem extends AggregateRoot {
	/* 会社ID */
	public String companyId;
    
    /*予定項目ID*/
	public String scheduleItemId;
    
    /*予定項目名称*/
	public String scheduleItemName;
    
    /*予定項目の属性*/
	public ScheduleItemAtr scheduleItemAtr;
	
	private int dispOrder;

	public ScheduleItem(String companyId, String scheduleItemId, String scheduleItemName,
			ScheduleItemAtr scheduleItemAtr, int dispOrder) {
		
		this.companyId = companyId;
		this.scheduleItemId = scheduleItemId;
		this.scheduleItemName = scheduleItemName;
		this.scheduleItemAtr = scheduleItemAtr;
		this.dispOrder = dispOrder;
	}

	public static ScheduleItem createFromJavaType(String companyId, String scheduleItemId, String scheduleItemName,
			int scheduleItemAtr, int dispOrder) {
		return new ScheduleItem(companyId, scheduleItemId, scheduleItemName,
				EnumAdaptor.valueOf(scheduleItemAtr, ScheduleItemAtr.class),
				dispOrder);
	}
    
    
}
