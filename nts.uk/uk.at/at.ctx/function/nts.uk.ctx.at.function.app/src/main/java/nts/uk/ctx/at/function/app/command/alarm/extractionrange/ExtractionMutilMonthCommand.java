package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

@Data
public class ExtractionMutilMonthCommand {
		
	private String extractionId;

	private int extractionRange;

	private int month;
	
	public AverageMonth toDomain() {
		if(this.extractionId == null || this.extractionId.equals("")){
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		return null;
	}
}

