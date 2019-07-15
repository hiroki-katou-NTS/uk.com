package nts.uk.ctx.pereg.app.command.process.checkdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CheckDataFromUI {
	
	private String dateTime;
	// A2_001 
	private boolean perInfoCheck;
	// A3_001
	private boolean masterCheck;
	// A3_008
	private boolean scheduleMngCheck;
	// A3_010
	private boolean dailyPerforMngCheck;
	// A3_012
	private boolean monthPerforMngCheck;
	// A3_014
	private boolean payRollMngCheck;
	// A3_016
	private boolean bonusMngCheck;
	// A3_018
	private boolean yearlyMngCheck;
	// A3_020
	private boolean monthCalCheck;
	
	public CheckDataFromUI(String dateTime, boolean perInfoCheck, boolean masterCheck, boolean scheduleMngCheck,
			boolean dailyPerforMngCheck, boolean monthPerforMngCheck, boolean payRollMngCheck, boolean bonusMngCheck,
			boolean yearlyMngCheck, boolean monthCalCheck) {
		super();
		this.dateTime = dateTime;
		this.perInfoCheck = perInfoCheck;
		this.masterCheck = masterCheck;
		this.scheduleMngCheck = scheduleMngCheck;
		this.dailyPerforMngCheck = dailyPerforMngCheck;
		this.monthPerforMngCheck = monthPerforMngCheck;
		this.payRollMngCheck = payRollMngCheck;
		this.bonusMngCheck = bonusMngCheck;
		this.yearlyMngCheck = yearlyMngCheck;
		this.monthCalCheck = monthCalCheck;
	}
	
	
}
