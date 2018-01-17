package nts.uk.ctx.at.function.dom.alarm.checkcondition.daily;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

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
	
	private List<String> errorAlarmWk = new ArrayList<String>();
	
	private List<String> errorAlarmFixed = new ArrayList<String>();
	
	private List<String > errorAlarmCode = new ArrayList<String>();

	public DailyAlarmCondition(String dailyAlarmConID, ConExtractedDaily conExtractedDaily, boolean addApplication,
			List<String> errorAlarmWk, List<String> errorAlarmFixed, List<String> errorAlarmCode) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.conExtractedDaily = conExtractedDaily;
		this.addApplication = addApplication;
		this.errorAlarmWk = errorAlarmWk;
		this.errorAlarmFixed = errorAlarmFixed;
		this.errorAlarmCode = errorAlarmCode;
	}



	

}
