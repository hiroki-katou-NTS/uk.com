package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
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
	// 抽出条件_設定
	private boolean extCondition;
	
	// 期間
	private GeneralDate period ;
	// 改ページ区分
	private int pageBreakClassification;
	
	// 指定年月
	
	GeneralDateTime exportTime;
	
	
	
	// 抽出条件
	
	// old
	// 指定月
	Integer printDate;
	// 参照の選択
	int pageBreakSelected;
	//selectedEmployees
	List<EmployeeInfo>  selectedEmployees;
	
	List<ClosurePrintDto> closureData;
	
	String programName;
}
