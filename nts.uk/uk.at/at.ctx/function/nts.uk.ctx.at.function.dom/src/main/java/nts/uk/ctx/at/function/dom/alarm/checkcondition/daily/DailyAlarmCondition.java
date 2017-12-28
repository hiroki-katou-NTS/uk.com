package nts.uk.ctx.at.function.dom.alarm.checkcondition.daily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * 日次のアラームチェック条件
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class DailyAlarmCondition extends DomainObject  {

	private String dailyAlarmConID;
	
	private ConExtractedDaily conExtractedDaily;
	
	private boolean addApplication;

}
