package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor;

import java.util.Optional;

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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 日別勤怠の所属情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.所属情報.日別勤怠の所属情報
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AffiliationInforOfDailyAttd implements DomainObject  {
	
	//雇用コード
	private EmploymentCode employmentCode;
	
	//職位ID
	private String jobTitleID;
	
	//職場ID
	private String wplID;
	
	//分類コード
	private ClassificationCode clsCode;
	
	//勤務種別コード
	private Optional<BusinessTypeCode> businessTypeCode;
	
	//加給コード : optional
	private Optional<BonusPaySettingCode> bonusPaySettingCode;

	/**
	 * @param employmentCode 雇用コード
	 * @param jobTitleID 職位ID
	 * @param wplID 職場ID
	 * @param clsCode 分類コード
	 * @param businessTypeCode 勤務種別コード
	 * @param bonusPaySettingCode 加給コード
	 */
	public AffiliationInforOfDailyAttd(
			EmploymentCode employmentCode, 
			String jobTitleID, 
			String wplID,
			ClassificationCode clsCode, 
			Optional<BusinessTypeCode> businessTypeCode,
			Optional<BonusPaySettingCode> bonusPaySettingCode) {
		super();
		this.employmentCode = employmentCode;
		this.jobTitleID = jobTitleID;
		this.wplID = wplID;
		this.clsCode = clsCode;
		this.businessTypeCode = businessTypeCode;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}
	
	/**
	 * [C-1] 作る
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	public static AffiliationInforOfDailyAttd create(Require require, String employeeId, GeneralDate standardDate) {
		
		return new AffiliationInforOfDailyAttd(
				AffiliationInforOfDailyAttd.getEmploymentCode(require, employeeId, standardDate), 
				AffiliationInforOfDailyAttd.getJobTitleId(require, employeeId, standardDate), 
				AffiliationInforOfDailyAttd.getWorkplaceId(require, employeeId, standardDate), 
				AffiliationInforOfDailyAttd.getClassificationCode(require, employeeId, standardDate), 
				AffiliationInforOfDailyAttd.getBusinessTypeCode(require, employeeId, standardDate), 
				AffiliationInforOfDailyAttd.getBonusPaySettingCode(require, employeeId, standardDate));
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
	 * [S-3] 職場IDを取得する
	 * @param require
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	private static String getWorkplaceId(Require require, String employeeId, GeneralDate standardDate) {
		SharedAffWorkPlaceHisImport workplaceHistory = require.getAffWorkplaceHistory(employeeId, standardDate);
		
		return workplaceHistory.getWorkplaceId();
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
		// TODO edit with R-5
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
	
	public static interface Require {
		
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
		 * [R-3] 所属職場履歴を取得する (社員ID, 年月日) : Single
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		SharedAffWorkPlaceHisImport getAffWorkplaceHistory(String employeeId, GeneralDate standardDate);
		
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
		// TODO
		
		
		/**
		 * [R-6] 労働条件履歴を取得する
		 * @param employeeId 社員ID
		 * @param standardDate 基準日
		 * @return
		 */
		Optional<WorkingConditionItem> getWorkingConditionHistory(String employeeId, GeneralDate standardDate);
	}
		
}
