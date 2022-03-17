package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;

/**
 * 日別勤怠の所属情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.所属情報.日別勤怠の所属情報
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationInforOfDailyAttd implements DomainObject  {
	
	/**
	 * 雇用コード
	 */
	private EmploymentCode employmentCode;
	
	/**
	 * 職位ID
	 */
	private String jobTitleID;
	
	/**
	 * 職場ID
	 */
	private String wplID;
	
	/**
	 * 分類コード
	 */
	private ClassificationCode clsCode;
	
	/**
	 * 勤務種別コード
	 */
	private Optional<BusinessTypeCode> businessTypeCode;
	
	/**
	 * 加給コード
	 */
	private Optional<BonusPaySettingCode> bonusPaySettingCode;
	
	/**
	 * 職場グループID
	 */
	private Optional<String> workplaceGroupId;
	
	/**
	 * 看護免許区分
	 */
	private Optional<LicenseClassification> nursingLicenseClass;
	
	/**
	 * 看護管理者か
	 */
	private Optional<Boolean> isNursingManager;
	
	
	/**
	 * [C-1] 作る
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	public static AffiliationInforOfDailyAttd create(Require require, String employeeId, GeneralDate standardDate) {
		
		EmpOrganizationImport empOrganization = getEmpOrganization(require, employeeId, standardDate);
		
		EmpLicenseClassification  empLicenseClass = getNursingLicenseClass(require, employeeId, standardDate);
		
		return new AffiliationInforOfDailyAttd(
				getEmploymentCode(require, employeeId, standardDate), 
				getJobTitleId(require, employeeId, standardDate), 
				empOrganization.getWorkplaceId(), 
				getClassificationCode(require, employeeId, standardDate), 
				getBusinessTypeCode(require, employeeId, standardDate), 
				getBonusPaySettingCode(require, employeeId, standardDate),
				empOrganization.getWorkplaceGroupId(),
				empLicenseClass.getOptLicenseClassification(),
				empLicenseClass.getIsNursingManager() );
	}
	
	/**
	 * 	[S-1] 雇用コードを取得する
	 * @param require
	 * @param employeeId 社員ID
	 * @param standardDate 基準日
	 * @return
	 */
	private static EmploymentCode getEmploymentCode(Require require, String employeeId, GeneralDate standardDate) {
		SharedSyEmploymentImport employmentHisoty = require.getAffEmploymentHistory(employeeId, standardDate);
		
		return new EmploymentCode( employmentHisoty.getEmploymentCode() );
	}
	
	/**
	 * 	[S-2] 職位IDを取得する
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	private static String getJobTitleId(Require require, String employeeId, GeneralDate standardDate) {
		SharedAffJobTitleHisImport jobTitlehistory = require.getAffJobTitleHistory(employeeId, standardDate);
		
		return jobTitlehistory.getJobTitleId();
	}
	
	/**
	 * [S-3] 社員の所属組織を取得する
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	private static EmpOrganizationImport getEmpOrganization(Require require, String employeeId, GeneralDate standardDate) {
		return require.getEmpOrganization(employeeId, standardDate);
	}
	
	/**
	 * [S-4] 分類コードを取得する
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	private static ClassificationCode getClassificationCode(Require require, String employeeId, GeneralDate standardDate) {
		SClsHistImport classificationHistory = require.getClassificationHistory(employeeId, standardDate);
		
		return new ClassificationCode(classificationHistory.getClassificationCode());
	}
	
	/**
	 * [S-5] 勤務種別コードを取得する
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	private static Optional<BusinessTypeCode> getBusinessTypeCode(Require require, String employeeId, GeneralDate standardDate) {
		Optional<BusinessTypeOfEmployee> businessTypeOfEmployee =  require.getBusinessType(employeeId, standardDate);
		if ( businessTypeOfEmployee.isPresent() ) {
			return Optional.of( businessTypeOfEmployee.get().getBusinessTypeCode() );
		}
		
		return Optional.empty();	
	}
	
	/**
	 * [S-6] 加給コードを取得する
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	private static Optional<BonusPaySettingCode> getBonusPaySettingCode(Require require, String employeeId, GeneralDate standardDate) {
		Optional<WorkingConditionItem> workingCondition = require.getWorkingConditionHistory(employeeId, standardDate);
		if ( !workingCondition.isPresent()) {
			throw new BusinessException("Msg_430");
		}
		
		return workingCondition.get().getTimeApply();
	}
	
	/**
	 * [S-8] 看護免許区分を取得する
	 * @param require
	 * @param employeeId 社員ID
	 * @param standardDate 基準日
	 * @return
	 */
	private static EmpLicenseClassification getNursingLicenseClass(Require require, String employeeId, GeneralDate standardDate) {
		
		return GetEmpLicenseClassificationService
				.get( require, standardDate, Arrays.asList(employeeId ))
				.get( 0 );
	}
	
	public static interface Require extends GetEmpLicenseClassificationService.Require {
		
		/**
		 * [R-1] 所属雇用履歴を取得する (社員ID, 年月日) : Single
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		SharedSyEmploymentImport getAffEmploymentHistory(String employeeId, GeneralDate standardDate);
		
		/**
		 * [R-2] 所属職位履歴を取得する (社員ID, 年月日) : Single
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		SharedAffJobTitleHisImport getAffJobTitleHistory(String employeeId, GeneralDate standardDate);		
			
		/**
		 * [R-4] 所属分類履歴を取得する (社員ID, 年月日) : Single
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		SClsHistImport getClassificationHistory(String employeeId, GeneralDate standardDate);
		
		/**
		 * [R-5] 勤務種別履歴を取得する
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		Optional<BusinessTypeOfEmployee> getBusinessType(String employeeId, GeneralDate standardDate);
		
		/**
		 * [R-6] 労働条件履歴を取得する
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		Optional<WorkingConditionItem> getWorkingConditionHistory(String employeeId, GeneralDate standardDate);
		
		/**
		 * [R-7] 所属組織を取得する
		 * @param employeeId
		 * @param standardDate
		 * @return
		 */
		EmpOrganizationImport getEmpOrganization(String employeeId, GeneralDate standardDate);
		
	}
		
}