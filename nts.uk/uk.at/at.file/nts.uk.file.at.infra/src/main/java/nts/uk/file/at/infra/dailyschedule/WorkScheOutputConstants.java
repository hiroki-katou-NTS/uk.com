package nts.uk.file.at.infra.dailyschedule;

public class WorkScheOutputConstants {
	// Remark text
	public static final String ERAL = "ER/AL";
	public static final String DATE = "日付";
	public static final String DAYMONTH = "月日";
	public static final String DAY = "曜";
	public static final String REMARK = "備考";
	public static final String PERSONAL_NAME = "個人名";
	
	public static final String PERIOD = "[期間]";
	
	// File/Sheet name
	public static final String SHEET_FILE_NAME = "日別勤務表";
	
	// Calculation text
	public static final String TOTAL_EMPLOYEE = "個人計";
	public static final String TOTAL_DAY = "日数計";
	
	// Record part
	public static final String WORKPLACE = "【職場】";
	public static final String EMPLOYEE = "【個人】";
	public static final String EMPLOYMENT = "【雇用】";
	public static final String POSITION = "【職位】";
	public static final String DATE_BRACKET = "[日付]";
	
	// ER / AL
	public static final String ER = "ER";
	public static final String AL = "AL";
	
	// Personal total
	public static final String PERSONAL_TOTAL = "個人計";
	public static final String WORKPLACE_TOTAL = "職場計";
	public static final String GROSS_TOTAL = "総合計";
	public static final String WORKPLACE_HIERARCHY_TOTAL = "職場累計";
	
	// DAY_COUNT
	public static final String DAY_COUNT = "日数計";
	public static final String PREDETERMINED = "所定日数";
	public static final String HOLIDAY = "休日日数";
	public static final String OFF = "休出日数";
	public static final String YEAR_OFF = "年休使用数";
	public static final String HEAVY = "所定日数";
	public static final String SPECIAL = "特休日数";
	public static final String ABSENCE = "欠勤日数";
	public static final String LATE_COME = "遅刻回数";
	public static final String EARLY_LEAVE = "早退回数";
	public static final String[] DAY_COUNT_TITLES =
		{
			PREDETERMINED, HOLIDAY, OFF, YEAR_OFF,
			HEAVY, SPECIAL, ABSENCE, LATE_COME, EARLY_LEAVE
		};
	
	// Range name, used to copy range from template to real sheet
	public static final String RANGE_EMPLOYEE_ROW = "EmployeeRow";
	public static final String RANGE_WORKPLACE_ROW = "WorkplaceRow";
	public static final String RANGE_WHITE_ROW = "White"; // Append number of row after this constants, eg. "White3" for 3 rows
	public static final String RANGE_LIGHTBLUE_ROW = "LBlue"; // Append number of row after this constants, eg. "LBlue3" for 3 rows
	public static final String RANGE_DAYCOUNT_ROW = "DayCount";
	public static final String RANGE_TOTAL_ROW = "Total"; // Append number of row after this constants, eg. "Total3" for 3 rows
	public static final String RANGE_GROSS_ROW = "Gross"; // Append number of row after this constants, eg. "Gross3" for 3 rows
	
	public static final String RANGE_DATE_ROW = "DateRow";
	public static final String RANGE_DAILY_WORKPLACE_ROW = "DailyWorkplaceRow";
}
