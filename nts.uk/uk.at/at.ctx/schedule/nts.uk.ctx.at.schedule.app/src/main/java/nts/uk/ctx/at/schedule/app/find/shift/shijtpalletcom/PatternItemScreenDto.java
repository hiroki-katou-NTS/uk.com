package nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom;

import java.util.List;

public class PatternItemScreenDto {
	public int patternNo;
	public String patternName;
	public List<WorkPairSetScreenDto> workPairSet; // tu 1 den 31
	
	public PatternItemScreenDto(int patternNo, String patternName, List<WorkPairSetScreenDto> workPairSet) {
		super();
		this.patternNo = patternNo;
		this.patternName = patternName;
		this.workPairSet = workPairSet;
	}
	
}
