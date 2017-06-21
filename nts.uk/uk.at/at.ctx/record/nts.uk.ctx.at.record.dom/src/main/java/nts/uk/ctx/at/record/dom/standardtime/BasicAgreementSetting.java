package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmWeek;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorWeek;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitWeek;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class BasicAgreementSetting extends AggregateRoot {

	private String basicSettingId;
	
	private AlarmWeek alarmWeek;
	
	private ErrorWeek errorWeek;
	
	private LimitWeek limitWeek;
	
	private AlarmTwoWeeks alarmTwoWeeks;
	
	private ErrorTwoWeeks errorTwoWeeks;
	
	private LimitTwoWeeks limitTwoWeeks;
	
	private AlarmFourWeeks alarmFourWeeks;
	
	private ErrorFourWeeks errorFourWeeks;
	
	private LimitFourWeeks limitFourWeeks;
	
	private AlarmOneMonth alarmOneMonth;
	
	private ErrorOneMonth errorOneMonth;
	
	private LimitOneMonth limitOneMonth;
	
	private AlarmTwoMonths alarmTwoMonths;
	
	private ErrorTwoMonths errorTwoMonths;
	
	private LimitTwoMonths limitTwoMonths;
	
	private AlarmThreeMonths alarmThreeMonths;
	
	private ErrorThreeMonths errorThreeMonths;
	
	private LimitThreeMonths limitThreeMonths;
	
	private AlarmOneYear alarmOneYear;
	
	private ErrorOneYear errorOneYear;
	
	private LimitOneYear limitOneYear;
}
