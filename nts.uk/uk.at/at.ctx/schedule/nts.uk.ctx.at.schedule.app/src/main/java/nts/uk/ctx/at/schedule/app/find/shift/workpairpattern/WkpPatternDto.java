package nts.uk.ctx.at.schedule.app.find.shift.workpairpattern;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplacePattern;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class WkpPatternDto {
	private String workplaceId;
	private int groupNo;
	private String groupName;
	private int groupUsageAtr;
	private String note;

	public static WkpPatternDto fromDomain(WorkplacePattern domain) {
		return new WkpPatternDto(domain.getWorkplaceId(), domain.getGroupNo(), domain.getGroupName().v(),
				domain.getGroupUsageAtr().value, domain.getNote());
	}
}
