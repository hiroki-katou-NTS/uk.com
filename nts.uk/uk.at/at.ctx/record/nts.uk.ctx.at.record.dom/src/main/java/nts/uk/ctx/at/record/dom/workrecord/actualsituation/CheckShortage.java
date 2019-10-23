package nts.uk.ctx.at.record.dom.workrecord.actualsituation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckShortage {
	
	private boolean checkShortage;

	private boolean retiredFlag;

	public static CheckShortage defaultShortage(boolean checkShortage) {
		return new CheckShortage(checkShortage, false);
	}
    
	public CheckShortage createRetiredFlag(boolean retiredFlag) {
		this.retiredFlag = retiredFlag;
		return this;
	}
	
	public CheckShortage createCheckShortage(boolean checkShortage) {
		this.checkShortage = checkShortage;
		return this;
	}
}
