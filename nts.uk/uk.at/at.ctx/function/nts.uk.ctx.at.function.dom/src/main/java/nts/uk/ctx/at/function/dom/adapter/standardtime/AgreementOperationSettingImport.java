package nts.uk.ctx.at.function.dom.adapter.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
public class AgreementOperationSettingImport {

	private StartingMonthTypeImport startingMonth;

	private TimeOverLimitTypeImport numberTimesOverLimitType;

	private ClosingDateTypeImport closingDateType;

	private ClosingDateAtrImport closingDateAtr;

	private TargetSettingAtrImport yearlyWorkTableAtr;

	private TargetSettingAtrImport alarmListAtr;

	public AgreementOperationSettingImport(int startingMonth, int numberTimesOverLimitType, int closingDateType,
			int closingDateAtr, int yearlyWorkTableAtr, int alarmListAtr) {
		this.startingMonth = EnumAdaptor.valueOf(startingMonth, StartingMonthTypeImport.class);
		this.numberTimesOverLimitType = EnumAdaptor.valueOf(numberTimesOverLimitType, TimeOverLimitTypeImport.class);
		this.closingDateType = EnumAdaptor.valueOf(closingDateType, ClosingDateTypeImport.class);
		this.closingDateAtr = EnumAdaptor.valueOf(closingDateAtr, ClosingDateAtrImport.class);
		this.yearlyWorkTableAtr = EnumAdaptor.valueOf(yearlyWorkTableAtr, TargetSettingAtrImport.class);
		this.alarmListAtr = EnumAdaptor.valueOf(alarmListAtr, TargetSettingAtrImport.class);
	}
}
