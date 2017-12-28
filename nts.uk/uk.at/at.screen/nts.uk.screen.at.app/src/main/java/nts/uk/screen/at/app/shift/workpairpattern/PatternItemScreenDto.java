package nts.uk.screen.at.app.shift.workpairpattern;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class PatternItemScreenDto {
	private int patternNo;
	private String patternName;
	private List<WorkPairSetScreenDto> workPairSet;
}
