package nts.uk.file.at.app.export.alarm.checkcondition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.text.StringUtil;

@Getter
public class Agree36ReportData {
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
	private String agreeCondErrors;
	private String agreeCondOtErrors;
	private List<Agree36CondError> agree36CondErrors;
	private List<Agree36OTError> agree36OTErrors;
	
	
	public Agree36ReportData(String code, String name, 
			String filterEmp, String employees,  
			String filterClas,String classifications, 
			String filterJobTitles, String jobtitles, 
			String filterWorkType, String worktypeselections, String agreeCondErrors, String agreeCondOtErrors) {
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
		this.agreeCondErrors = agreeCondErrors;
		this.agree36CondErrors = parseCondErrors(this.agreeCondErrors);
		this.agreeCondOtErrors = agreeCondOtErrors;
		this.agree36OTErrors = parseOTErrors(this.agreeCondOtErrors);
	}
	
	public static Agree36ReportData createFromJavaType(String code, String name, 
			String filterEmp, String employees,  
			String filterClas,String classifications, 
			String filterJobTitles, String jobtitles, 
			String filterWorkType, String worktypeselections,String agreeCondErrors, String agreeCondOtErrors) {
		return new Agree36ReportData(
				code, name, filterEmp, employees, filterClas, classifications, 
				filterJobTitles, jobtitles, filterWorkType, worktypeselections,agreeCondErrors,agreeCondOtErrors);
	}
	
	private List<Agree36CondError> parseCondErrors(String agreeCondErrors) {
		List<Agree36CondError> agree36CondErrors = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(agreeCondErrors, true)) {
			String[] agree36CondErrorArray = agreeCondErrors.split("\\|");
			for(int i = 0; i < agree36CondErrorArray.length; i++) {
				String[] agree36CondError = agree36CondErrorArray[i].split(",");
				if (agree36CondError.length == 5) {
					int useAtrInt = Integer.valueOf(agree36CondError[4]);
					if (useAtrInt == 1) {
						Agree36CondError obj = new Agree36CondError(agree36CondError[0], Integer.valueOf(agree36CondError[1]), 
								Integer.valueOf(agree36CondError[2]), agree36CondError[3], useAtrInt);
						agree36CondErrors.add(obj);
					}
				}
			}
			agree36CondErrors = agree36CondErrors.stream().sorted(
					Comparator.comparing(Agree36CondError::getErrorAlarm)
					.thenComparing(Agree36CondError::getPeriorAtr)).collect(Collectors.toList());
		}
		return agree36CondErrors;
	}
	
	@AllArgsConstructor
	@Getter
	public static class Agree36CondError {
		private String name;
		private Integer errorAlarm;
		private Integer periorAtr;
		private String message;
		private Integer useAtr;
	}
	
	
	private List<Agree36OTError> parseOTErrors(String agreeCondOtErrors) {
		List<Agree36OTError> agree36OTErrors = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(agreeCondOtErrors, true)) {
			String[] agree36OTErrorArray = agreeCondOtErrors.split("\\|");
			for(int i = 0; i < agree36OTErrorArray.length; i++) {
				String[] agree36OTError = agree36OTErrorArray[i].split(",", -1);
				if (agree36OTError.length == 4) {
					Agree36OTError obj = new Agree36OTError(Integer.valueOf(agree36OTError[0]), Integer.valueOf(agree36OTError[1]), 
							agree36OTError[2], agree36OTError[3]);
					agree36OTErrors.add(obj);
				}
			}
			agree36OTErrors = agree36OTErrors.stream().sorted(
					Comparator.comparing(Agree36OTError::getNo)).collect(Collectors.toList());
		}
		return agree36OTErrors;
	}
	@AllArgsConstructor
	@Getter
	public static class Agree36OTError {
		private Integer no;
		private Integer overtime;
		private String excessNum;
		private String message;
	}
}
