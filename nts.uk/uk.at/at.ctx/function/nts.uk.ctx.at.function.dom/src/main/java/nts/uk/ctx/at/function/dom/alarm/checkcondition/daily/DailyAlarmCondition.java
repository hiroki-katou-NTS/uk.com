package nts.uk.ctx.at.function.dom.alarm.checkcondition.daily;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.FixedConditionWorkRecord;

/**
 * 日次のアラームチェック条件
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class DailyAlarmCondition extends ExtractionCondition  {

	private String dailyAlarmConID;
	
	private ConExtractedDaily conExtractedDaily;
	
	private boolean addApplication;
	
	private List<FixedConditionWorkRecord> listFixedConditionWorkRecord = new ArrayList<>();

	public DailyAlarmCondition(String dailyAlarmConID, ConExtractedDaily conExtractedDaily, boolean addApplication,
			List<FixedConditionWorkRecord> listFixedConditionWorkRecord) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.conExtractedDaily = conExtractedDaily;
		this.addApplication = addApplication;
		this.listFixedConditionWorkRecord = listFixedConditionWorkRecord;
	}
	
	

}
