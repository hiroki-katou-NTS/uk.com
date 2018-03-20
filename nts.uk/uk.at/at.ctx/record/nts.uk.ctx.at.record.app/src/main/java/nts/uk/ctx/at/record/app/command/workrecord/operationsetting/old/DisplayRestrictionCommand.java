/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Data;

/**
 * @author danpv
 *
 */
@Data
public class DisplayRestrictionCommand {
	
	private Boolean yearDisplayAtr;
	
	private Boolean yearRemainingNumberCheck;
	
	private Boolean savingYearDisplayAtr;
	
	private Boolean savingYearRemainingNumberCheck;
	
	private Boolean compensatoryDisplayAtr;
	
	private Boolean compensatoryRemainingNumberCheck;
	
	private Boolean substitutionDisplayAtr;
	
	private Boolean substitutionRemainingNumberCheck;

}
