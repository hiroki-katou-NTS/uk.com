package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;

/**
 * 個人条件
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLT
 *
 */
@Getter
public class PersonalCondition {
	/** 社員ID **/
	private final String empId;
	/** チーム名**/
	private final Optional<String> teamName;
	/** ランク記号Rank symbol**/
	private final Optional<String> optRankSymbol; 
	/**免許区分 **/
	private final Optional<LicenseClassification> optLicenseClassification;
	
	//	[C-0] 個人条件( 社員ID, チーム名, ランク記号, 免許区分)
	public PersonalCondition(String empId, Optional<String> teamName, Optional<String> optRankSymbol,
			Optional<LicenseClassification> optLicenseClassification) {
		super();
		this.empId = empId;
		this.teamName = teamName;
		this.optRankSymbol = optRankSymbol;
		this.optLicenseClassification = optLicenseClassification;
	}
	
	
	
}
