package nts.uk.ctx.at.schedule.app.find.shift.workpairpattern;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComPattern;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class ComPatternDto {
	private int groupNo;
	private String groupName;
	private int groupUsageAtr;
	private String note;

	public static ComPatternDto fromDomain(ComPattern domain) {
		return new ComPatternDto(domain.getGroupNo(), domain.getGroupName().v(), domain.getGroupUsageAtr().value,
				domain.getNote());
	}
}
