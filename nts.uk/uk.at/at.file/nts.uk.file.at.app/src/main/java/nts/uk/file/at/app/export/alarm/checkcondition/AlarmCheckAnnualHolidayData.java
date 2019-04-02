package nts.uk.file.at.app.export.alarm.checkcondition;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.val;
import nts.uk.shr.com.i18n.TextResource;

@Data
public class AlarmCheckAnnualHolidayData {
	private String code;
	private String name;
	private String filterEmp;
	private String employees;
	private String filterClas;
	private String classifications;
	private String filterJobTitles;
	private String jobtitles;
	private String filterWorkType;
	private String worktypeselections;
	private String alarmCheckHdpaidText;
	private String alarmCheckHdpaidOblText;
	private AlarmCheckHdpaid alarmCheckHdpaid;
	private AlarmCheckHdpaidObl alarmCheckHdpaidObl;
	
	
	public AlarmCheckAnnualHolidayData(String code, String name, 
			String filterEmp, String employees,  
			String filterClas,String classifications, 
			String filterJobTitles, String jobtitles, 
			String filterWorkType, String worktypeselections, String alarmCheckHdpaid, String alarmCheckHdpaidObl) {
		super();
		this.code = code;
		this.name = name;
		this.filterEmp = filterEmp;
		this.employees = employees;
		this.filterClas = filterClas;
		this.classifications = classifications;
		this.filterJobTitles = filterJobTitles;
		this.jobtitles = jobtitles;
		this.filterWorkType = filterWorkType;
		this.worktypeselections = worktypeselections;
		this.alarmCheckHdpaidText = alarmCheckHdpaid;
		this.alarmCheckHdpaid = parseAlarmCheckHdpaid(this.alarmCheckHdpaidText);
		this.alarmCheckHdpaidOblText = alarmCheckHdpaidObl;
		this.alarmCheckHdpaidObl = parseAlarmCheckHdpaidObl(this.alarmCheckHdpaidOblText);
	}
	
	public static AlarmCheckAnnualHolidayData createFromJavaType(String code, String name, 
			String filterEmp, String employees,  
			String filterClas,String classifications, 
			String filterJobTitles, String jobtitles, 
			String filterWorkType, String worktypeselections,String alarmCheckHdpaid, String alarmCheckHdpaidObl) {
		return new AlarmCheckAnnualHolidayData(
				code, name, filterEmp, employees, filterClas, classifications, 
				filterJobTitles, jobtitles, filterWorkType, worktypeselections,alarmCheckHdpaid,alarmCheckHdpaidObl);
	}
	
	private AlarmCheckHdpaid parseAlarmCheckHdpaid(String alarmCheckHdpaid) {
		if (alarmCheckHdpaid == null)
			return null;
		String[] hdpaidSplit = alarmCheckHdpaid.split(",");
		if (hdpaidSplit.length == 4) {
			val nextPeriodAtr = Integer.valueOf(hdpaidSplit[0]) == 1 ? Optional.of(1) : Optional.of(0);
			val lastTimeDayAtr = Integer.valueOf(hdpaidSplit[2]) == 1 ? Optional.of(1) : Optional.of(0);
			AlarmCheckHdpaid obj = new AlarmCheckHdpaid(AlarmCheckConditionUtils.getUseAtrStr(nextPeriodAtr),
					(Integer.valueOf(hdpaidSplit[1]) < 0 ||  Integer.valueOf(hdpaidSplit[0]) == 0 ) ? null : (Integer.valueOf(hdpaidSplit[1]) + AlarmCheckConditionUtils.TEXT_KAL003_324),
							AlarmCheckConditionUtils.getUseAtrStr(lastTimeDayAtr),
					(Integer.valueOf(hdpaidSplit[3]) < 0 ||  Integer.valueOf(hdpaidSplit[2]) == 0) ? null : Integer.valueOf(hdpaidSplit[3]) + AlarmCheckConditionUtils.TEXT_KAL003_325);
			return obj;
		}
		return null;
	}
	
	@AllArgsConstructor
	@Getter
	public static class AlarmCheckHdpaid{
		private String nextPeriodAtr;
		private String nextPeriod;
		private String lastTimeDayAtr;
		private String lastTimeDay;
	}
	
	
	private AlarmCheckHdpaidObl parseAlarmCheckHdpaidObl(String alarmCheckHdpaidObl) {
		if (alarmCheckHdpaidObl == null)
			return null;
		String[] hdpaidOblSplit = alarmCheckHdpaidObl.split(",", -1);
		if (hdpaidOblSplit.length == 3) {
			val divideAtr = Integer.valueOf(hdpaidOblSplit[0]) == 1 ? Optional.of(1) : Optional.of(0);
			AlarmCheckHdpaidObl obj = new AlarmCheckHdpaidObl(AlarmCheckConditionUtils.getUseAtrStr(divideAtr), hdpaidOblSplit[1],
					Integer.valueOf(hdpaidOblSplit[2]) + AlarmCheckConditionUtils.TEXT_KAL003_326);
			return obj;
		}
		return null;
	}
	
	@AllArgsConstructor
	@Getter
	public static class AlarmCheckHdpaidObl {
		private String divideAtr;
		private String disMessage;
		private String underLimitDay;
	}
}
