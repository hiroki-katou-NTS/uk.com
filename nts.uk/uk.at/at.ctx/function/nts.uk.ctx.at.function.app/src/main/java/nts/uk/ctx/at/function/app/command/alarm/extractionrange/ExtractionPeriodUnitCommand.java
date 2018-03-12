package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;

@Data
public class ExtractionPeriodUnitCommand {

	private String extractionId;
	
	private int extractionRange;
	
	private int segmentationOfCycle;
	
	public ExtractionPeriodUnit toDomain(){
		if(this.extractionId == null || this.extractionId.equals("")){
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		return new ExtractionPeriodUnit(extractionId, extractionRange, segmentationOfCycle);
	}
}
