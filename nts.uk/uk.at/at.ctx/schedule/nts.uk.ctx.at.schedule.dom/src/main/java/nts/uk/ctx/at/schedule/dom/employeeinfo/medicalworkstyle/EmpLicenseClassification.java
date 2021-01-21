package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 社員免許区分
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.医療勤務形態
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class EmpLicenseClassification {

	/** 社員ID**/
	private final String empID;
	
	/**	免許区分 **/
	
	private final Optional<LicenseClassification> optLicenseClassification;
	
	
	/**
	 * [C-1] 社員免許区分
	 * @param empID
	 * @return
	 */
	public static EmpLicenseClassification empLicenseClassification( String empID){
		return new EmpLicenseClassification(empID, 	Optional.empty());
	}
	/**
	 * [C-2] 社員免許区分
	 * @param empID
	 * @param licenseClassification
	 * @return
	 */
	public static EmpLicenseClassification get(String empID , LicenseClassification licenseClassification ){
		return new EmpLicenseClassification(empID, Optional.ofNullable(licenseClassification));
	}
}
