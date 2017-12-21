package nts.uk.ctx.at.record.app.command.bonuspay;

import lombok.Data;

@Data
public class RegisterErrorList {
	
	public boolean isBPTimeSheet; 
	
	public int timeSheetNO;
	
	public String errorId;
	
	public RegisterErrorList(boolean isBPTimeSheet, int timeSheetNO, String errorId) {
		this.isBPTimeSheet = isBPTimeSheet;
		this.timeSheetNO = timeSheetNO;
		this.errorId = errorId;
	}

}
