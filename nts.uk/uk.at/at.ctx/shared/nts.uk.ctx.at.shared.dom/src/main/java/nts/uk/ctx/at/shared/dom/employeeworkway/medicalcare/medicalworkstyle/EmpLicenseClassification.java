package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 社員免許区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.社員免許区分
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
