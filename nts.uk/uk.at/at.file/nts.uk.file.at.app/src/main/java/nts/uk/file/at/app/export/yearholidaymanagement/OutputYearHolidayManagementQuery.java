package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.sys.auth.dom.user.User;

@Data
@NoArgsConstructor
//年休管理表の出力条件
public class OutputYearHolidayManagementQuery {
	// ダブルトラック時の期間拡張
	private boolean doubleTrack = false;
	// ユーザID
	private User userId;
	// 会社ID
	private Company companyId;
	// 参照区分
	private int selectedReferenceType;
	// 対象期間
	private PeriodToOutput selectedDateType;
	// 年休取得日の印字方法
	private AnnualLeaveAcquisitionDate printAnnualLeaveDate;
	// 抽出条件 A5_7
	private boolean extCondition;
	// 改ページ区分
	private int pageBreakClassification;
	// 抽出条件_設定
	private Optional<ExtractionConditionSetting> extractionCondtionSetting;
	// 期間
	private DatePeriod period ;
	
	GeneralDateTime exportTime;
	// old
	// 指定月
	Integer printDate;
	// 参照の選択
	int pageBreakSelected;
	//selectedEmployees
	List<EmployeeInfo>  selectedEmployees;
	
	List<ClosurePrintDto> closureData;
	
	String programName;
	
	private int mode;
	
	public static OutputYearHolidayManagementQuery createFromJavaType(OutputYearHolidayManagementQueryDto dto) {
		OutputYearHolidayManagementQuery query = new OutputYearHolidayManagementQuery();
		query.setDoubleTrack(dto.isDoubleTrack());
		query.setSelectedReferenceType(dto.getSelectedReferenceType());
		query.setSelectedDateType(PeriodToOutput.valueOf(dto.getSelectedDateType()));
		query.setPrintAnnualLeaveDate(AnnualLeaveAcquisitionDate.valueOf(dto.getPrintAnnualLeaveDate()));
		query.setExtCondition(dto.isExtCondition());
		query.setPageBreakClassification(dto.getPageBreakSelected());
		query.setPageBreakSelected(dto.getPageBreakSelected());
		query.setPrintDate(dto.getPrintDate());
		query.setExtractionCondtionSetting(Optional.of(new ExtractionConditionSetting(dto.getExtConditionSettingDay(),ComparisonConditions.valueOf(dto.getExtConditionSettingCoparison()))));
		query.setPeriod(new DatePeriod(dto.getStartDate(), dto.getEndDate()));
		query.setSelectedEmployees(dto.getSelectedEmployees());
		query.setClosureData(dto.getClosureData());
		query.setMode(dto.getMode());
		query.setExportTime(dto.getExportTime());
		query.setProgramName(dto.getProgramName());
		return query;
	}
}
