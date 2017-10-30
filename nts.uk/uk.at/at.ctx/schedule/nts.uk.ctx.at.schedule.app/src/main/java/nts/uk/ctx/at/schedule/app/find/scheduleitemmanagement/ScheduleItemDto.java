package nts.uk.ctx.at.schedule.app.find.scheduleitemmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleItemDto {
	/* 会社ID */
	private String companyId;
    
    /*予定項目ID*/
	private String scheduleItemId;
    
    /*予定項目名称*/
	private String scheduleItemName;
    
    /*予定項目の属性*/
	private int scheduleItemAtr;
	
	/* 順番 */
    private int dispOrder;

	public static ScheduleItemDto fromDomain(ScheduleItem domain) {
		return new ScheduleItemDto(
				domain.getCompanyId(),
				domain.scheduleItemId,
				domain.getScheduleItemName(),
				domain.getScheduleItemAtr().value,
				domain.getDispOrder()
			);
	}
}
