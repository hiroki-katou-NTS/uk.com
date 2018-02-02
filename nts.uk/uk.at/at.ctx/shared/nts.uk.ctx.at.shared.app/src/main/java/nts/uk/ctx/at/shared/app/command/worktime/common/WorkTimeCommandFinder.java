package nts.uk.ctx.at.shared.app.command.worktime.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkTimeCommandFinder {
	
	private List<String> codelist;
	private Integer startAtr;
	private Integer startTime;
	private int endAtr;
	private Integer endTime;
}