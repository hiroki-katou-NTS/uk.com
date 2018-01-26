package nts.uk.ctx.at.shared.app.find.breaktime.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreakTimeDayDto {
	
	/** The break time dto. */
	private List<BreakTimeDto> breakTimeDto;
	
	/** The break break time dto. */
	private List<BreakBeakTimeDto> breakBreakTimeDto;
}
