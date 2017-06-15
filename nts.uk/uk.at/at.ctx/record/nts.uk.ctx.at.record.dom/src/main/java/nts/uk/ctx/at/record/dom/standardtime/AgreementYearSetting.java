package nts.uk.ctx.at.record.dom.standardtime;

import java.time.Year;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementYearSetting {

	private String companyId;

	private Year yearValue;
	
	private ErrorOneYear errorOneYear;
	
	private AlarmOneYear alarmOneYear;
	
}
