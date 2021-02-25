package nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom;

import java.util.List;

public class ComPatternScreenDto {
	public int groupNo;
	public String groupName;
	public int groupUsageAtr;
	public String note;
	public List<PatternItemScreenDto> patternItem; // tu 1 den 20
	public ComPatternScreenDto(int groupNo, String groupName, int groupUsageAtr, String note,
			List<PatternItemScreenDto> patternItem) {
		super();
		this.groupNo = groupNo;
		this.groupName = groupName;
		this.groupUsageAtr = groupUsageAtr;
		this.note = note;
		this.patternItem = patternItem;
	}
	
	
	
	
}
