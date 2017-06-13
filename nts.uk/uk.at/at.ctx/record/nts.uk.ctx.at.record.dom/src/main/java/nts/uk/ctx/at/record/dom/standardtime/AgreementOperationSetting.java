package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.standardtime.enums.NumberOfTimeOverLimitAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingType;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementOperationSetting {
	
	private String companyId;
	
	private StartingMonthAtr startingMonth;
	
	private NumberOfTimeOverLimitAtr numberOfTimesOverLimitAtr;
	
	private ClosingDateAtr closingDateAtr;
	
	private ClosingDateType closingDateType;
	
	private TargetSettingType targetSettingType;
}
