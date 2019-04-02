package nts.uk.file.at.app.export.alarm.checkcondition;

import lombok.Getter;

@Getter
public class Schedule4WeekReportData {
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
	private int w4k4CheckCond;
	
	public Schedule4WeekReportData(String code, String name, 
			String filterEmp, String employees,  
			String filterClas,String classifications, 
			String filterJobTitles, String jobtitles, 
			String filterWorkType, String worktypeselections, int w4k4CheckCond) {
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
		this.w4k4CheckCond = w4k4CheckCond;
	}
	
	public static Schedule4WeekReportData createFromJavaType(String code, String name, 
			String filterEmp, String employees,  
			String filterClas,String classifications, 
			String filterJobTitles, String jobtitles, 
			String filterWorkType, String worktypeselections, int w4k4CheckCond) {
		return new Schedule4WeekReportData(
				code, name, filterEmp, employees, filterClas, classifications, 
				filterJobTitles, jobtitles, filterWorkType, worktypeselections, w4k4CheckCond);
	}
}
