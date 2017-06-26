package nts.uk.ctx.at.record.app.find.standardtime;

import lombok.Data;

@Data
public class AgreementOperationSettingDto {
	
	private int startingMonth;

	private int numberTimesOverLimitType;

	private int closingDateType;

	private int closingDateAtr;

	private int yearlyWorkTableAtr;

	private int alarmListAtr;
}
