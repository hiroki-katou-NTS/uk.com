package nts.uk.file.at.infra.dailyschedule;

public class WorkScheOutputConstants {
	// Remark text
	public static final String ERAL = "ER/AL";
	public static final String DATE = "日付";
	public static final String DAYMONTH = "月日";
	public static final String DAY = "曜";
	public static final String REMARK = "備考";
	
	// Calculation text
	public static final String TOTAL_EMPLOYEE = "個人計";
	public static final String TOTAL_DAY = "日数計";
	
	// Record part
	public static final String WORKPLACE = "【職場】";
	public static final String EMPLOYEE = "【個人】";
	public static final String EMPLOYMENT = "【雇用】";
	public static final String POSITION = "【職位】";
	
	// ER / AL
	public static final String ER = "ER";
	public static final String AL = "AL";
	
	// Personal total
	public static final String PERSONAL_TOTAL = "個人計";
	
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
}
