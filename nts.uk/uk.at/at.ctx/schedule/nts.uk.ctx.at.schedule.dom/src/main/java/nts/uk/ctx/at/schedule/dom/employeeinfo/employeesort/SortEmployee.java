package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;


/**
 * «Temporary» 並び替え社員
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * @author HieuLt
 *
 */
@Getter
@AllArgsConstructor
public class SortEmployee {
	
	/** 社員ID **/ 
	private final String empID;
	/**	職位ID **/
	private String jobtitleID;
	/**	チーム (team)	 **/
	private ScheduleTeamCd scheduleTeamCd;
	/**	ランク (rank) **/
	private RankCode rankCode;
	/** 免許区分 (phân loại giấy phép) **/
	private LicenseClassification licenseClassification;
	
}
