package nts.uk.ctx.at.record.dom.standardtime;

import java.time.YearMonth;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementMonthSetting {

	private String companyId;

	private YearMonth yearMonthValue;
	
	private ErrorOneMonth errorOneMonth;
	
	private AlarmOneMonth alarmOneMonth;
}
