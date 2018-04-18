package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck.result;

import java.util.HashMap;
import java.util.Map;

import nts.arc.time.GeneralDate;

public class ContinuousHolidayCheckResult {

	private Map<GeneralDate, Integer> errorDatas;
	
	private String message;
	
	public ContinuousHolidayCheckResult() {
		super();
		this.message = "";
		this.errorDatas = new HashMap<>();
	}

	public String message(){
		return message;
	}
	
	public void message(String message){
		this.message = message;
	}
	
	public Integer getErrorDaysByDate(GeneralDate date){
		return this.errorDatas.get(date);
	}
	
	public Map<GeneralDate, Integer> getCheckResult(){
		return new HashMap<>(errorDatas);
	}
	
	public void setErrorDate(GeneralDate date, int errorDays){
		this.errorDatas.put(date, errorDays);
	}
	
	public void setErrorDate(Map<GeneralDate, Integer> errorDatas){
		this.errorDatas.putAll(errorDatas);
	}
}
