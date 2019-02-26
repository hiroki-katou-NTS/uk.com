package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

@Data
public class ExtractionAverageMonthCommand {
		
	private String extractionId;

	private int extractionRange;

	private int strMonth;
	
	public AverageMonth toDomain() {
		if(this.extractionId == null || this.extractionId.equals("")){
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		return new AverageMonth(extractionId, extractionRange, strMonth);
	}
}

