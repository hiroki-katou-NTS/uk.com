package nts.uk.ctx.at.schedule.app.find.shift.workpairpattern;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class WorkPairPatternDto {
	private List<ComPatternDto> listComPatternDto;
	private List<WkpPatternDto> listWkpPatternDto;
}
