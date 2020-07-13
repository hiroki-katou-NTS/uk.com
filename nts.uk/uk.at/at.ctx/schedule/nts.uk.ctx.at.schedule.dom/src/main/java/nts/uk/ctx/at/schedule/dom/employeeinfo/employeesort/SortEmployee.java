package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;


/**
 * «Temporary» 並び替え社員
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class SortEmployee {
	
	/** 社員ID **/ 
	private String empID;
	/**	職位ID **/
	private String jobtitleID;
	/**	チーム (team)	 **/
	private String scheduleTeamID;
	/**	ランク (rank) **/
	private String rankCode;
	/** 免許区分 (phân loại giấy phép) **/
	private LicenseClassification licenseClassification;
	
}
