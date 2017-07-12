package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingAtr;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementOperationSetting extends AggregateRoot {

	private String companyId;

	private StartingMonthType startingMonth;

	private TimeOverLimitType numberTimesOverLimitType;

	private ClosingDateType closingDateType;

	private ClosingDateAtr closingDateAtr;

	private TargetSettingAtr yearlyWorkTableAtr;

	private TargetSettingAtr alarmListAtr;

	public AgreementOperationSetting(String companyId, StartingMonthType startingMonth,
			TimeOverLimitType numberTimesOverLimitType, ClosingDateType closingDateType,
			ClosingDateAtr closingDateAtr, TargetSettingAtr yearlyWorkTableAtr, TargetSettingAtr alarmListAtr) {
		super();
		this.companyId = companyId;
		this.startingMonth = startingMonth;
		this.numberTimesOverLimitType = numberTimesOverLimitType;
		this.closingDateType = closingDateType;
		this.closingDateAtr = closingDateAtr;
		this.yearlyWorkTableAtr = yearlyWorkTableAtr;
		this.alarmListAtr = alarmListAtr;
	}

	public static AgreementOperationSetting createFromJavaType(String companyId, int startingMonth,
			int numberTimesOverLimitType, int closingDateType, int closingDateAtr, int yearlyWorkTableAtr,
			int alarmListAtr) {
		return new AgreementOperationSetting(
				companyId,
				EnumAdaptor.valueOf(startingMonth, StartingMonthType.class),
				EnumAdaptor.valueOf(numberTimesOverLimitType, TimeOverLimitType.class),
				EnumAdaptor.valueOf(closingDateType, ClosingDateType.class),
				EnumAdaptor.valueOf(closingDateAtr, ClosingDateAtr.class),
				EnumAdaptor.valueOf(yearlyWorkTableAtr, TargetSettingAtr.class),
				EnumAdaptor.valueOf(alarmListAtr, TargetSettingAtr.class));
	}
}
