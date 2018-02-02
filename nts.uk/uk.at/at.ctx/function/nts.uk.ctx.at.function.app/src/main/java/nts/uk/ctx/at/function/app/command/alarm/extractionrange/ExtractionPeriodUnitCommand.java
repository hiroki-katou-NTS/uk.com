package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;

@Data
public class ExtractionPeriodUnitCommand {

	private String extractionId;
	
	private int extractionRange;
	
	private int segmentationOfCycle;
	
	public ExtractionPeriodUnit toDomain(){
		return new ExtractionPeriodUnit(extractionId, extractionRange, segmentationOfCycle);
	}
}
