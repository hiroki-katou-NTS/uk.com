package nts.uk.screen.at.app.shift.workpairpattern;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class ComPatternScreenDto {
	private int groupNo;
	private String groupName;
	private int groupUsageAtr;
	private String note;
	private List<PatternItemScreenDto> patternItem;
}
