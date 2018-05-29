package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;

@Data
public class ExtractionRangeYearCommand {
		
	private String extractionId;

	private int extractionRange;

	private int year;

	private int thisYear;
	
	public AYear toDomain() {
		if(this.extractionId == null || this.extractionId.equals("")){
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		return new AYear(extractionId, extractionRange, year, thisYear==1);
	}
}

