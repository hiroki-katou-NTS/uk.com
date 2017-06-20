package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.NumberOfTimeOverLimitType;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingAtr;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementOperationSetting extends AggregateRoot{
	
	private String companyId;
	
	private StartingMonthType startingMonth;
	
	private NumberOfTimeOverLimitType numberTimesOverLimitType;
	
	private ClosingDateType closingDateType;
	
	private ClosingDateAtr closingDateAtr;
	
	private TargetSettingAtr yearlyWorkTableAtr;
	
	private TargetSettingAtr alarmListAtr;
}
