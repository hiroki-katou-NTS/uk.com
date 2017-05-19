package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;

@Getter
public class YearVacationManageSetting {

	/** The remaining number setting. */
	private RemainingNumberSetting remainingNumberSetting;
	
	/** The acquisition setting. */
	private AcquisitionSetting acquisitionSetting;
	
	/** The working hour calculate setting. */
	private WorkingHourCalculateSetting workingHourCalculateSetting;//dont care this pharse
	
	/** The work day calculate. */
	private boolean workDayCalculate;
	
	/** The half day manage. */
	private HalfDayManage halfDayManage;
	
	/** The display setting. */
	private DisplaySetting displaySetting; 
	
	/** The max day vacation. */
	private MaxDayVacation maxDayVacation;//TODO check lại kiểu này
}
