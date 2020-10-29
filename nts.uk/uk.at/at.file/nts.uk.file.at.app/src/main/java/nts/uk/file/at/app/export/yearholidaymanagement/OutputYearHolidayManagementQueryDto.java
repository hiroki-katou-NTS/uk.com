package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Data
public class OutputYearHolidayManagementQueryDto {
	private String programName;
	
	private GeneralDateTime exportTime;
	
	//ダブルトラック時の期間拡張 - Extended period for double track - A7_2
	private boolean doubleTrack;
	
	private String userId;
	
	private String companyId;
	
	// 参照区分
	private int selectedReferenceType;
	
	// 対象期間
	private int selectedDateType;
	
	// 年休取得日の印字方法
	private int printAnnualLeaveDate;
	
	// 抽出条件
	private boolean extCondition;
	
	// 抽出条件_設定
	private int extConditionSettingDay;
	
	//抽出条件_比較条件
	private int extConditionSettingCoparison;
	
	private int printDate;
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	// 改ページ区分
	private int pageBreakSelected;
	
	//selectedEmployees
	private	List<EmployeeInfo>  selectedEmployees;
		
	private	List<ClosurePrintDto> closureData;
	
	
	private int mode;
	
	
}
