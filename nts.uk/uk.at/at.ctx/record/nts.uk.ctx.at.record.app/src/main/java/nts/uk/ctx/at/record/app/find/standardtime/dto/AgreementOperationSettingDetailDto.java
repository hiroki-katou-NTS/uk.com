package nts.uk.ctx.at.record.app.find.standardtime.dto;

import lombok.Data;

@Data
public class AgreementOperationSettingDetailDto {
	
	private int startingMonth;

	private int numberTimesOverLimitType;

	private int closingDateType;

	private int closingDateAtr;

	private int yearlyWorkTableAtr;

	private int alarmListAtr;
}
