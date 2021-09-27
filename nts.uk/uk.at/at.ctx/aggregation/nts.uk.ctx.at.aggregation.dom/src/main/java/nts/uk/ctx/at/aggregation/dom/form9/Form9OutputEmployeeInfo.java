package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;
/**
 * 様式９の出力社員情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の出力社員情報を取得する.様式９の出力社員情報
 * @author lan_lt
 *
 */
@Value
public class Form9OutputEmployeeInfo {

	/** 社員ID **/
	private final String employeeId;
	
	/** 種別 **/
	private final LicenseClassification license;
	
	/** 氏名 **/
	private final String fullName;
	
	/** 常勤 **/
	private final boolean fullTime;
	
	/** 非常勤 **/
	private final boolean partTime;
	
	/** 短時間勤務 **/
	private final boolean shortTime;
	
	/** 他部署兼務 **/
	private final boolean concurrentPost;
	
	/** 夜勤専従 **/
	private final boolean nightShiftOnly;
	
	/** 事務的業務従事者 **/
	private final boolean officeWorker;
	
	/**
	 * 作成する
	 * @param require
	 * @param baseDate 基準日
	 * @param employeeId 社員ID
	 * @return
	 */
	public static Optional<Form9OutputEmployeeInfo> create(Require require, GeneralDate baseDate, String employeeId) {
		
		Optional<EmpMedicalWorkStyleHistoryItem> empMedicalHistItem = require
				.getEmpMedicalWorkStyleHistoryItem(employeeId, baseDate);
		if (!empMedicalHistItem.isPresent()) {
			return Optional.empty();
		}

		Optional<NurseClassification> nurseClassification = require
				.getNurseClassification(empMedicalHistItem.get().getNurseClassifiCode());
		if (!nurseClassification.isPresent() || nurseClassification.get().isNursingManager()) {
			return Optional.empty();
		}

		boolean fullTime = empMedicalHistItem.get().getMedicalWorkStyle() == MedicalCareWorkStyle.FULLTIME;
		Optional<ShortWorkTimeHistoryItem> shortTimeItem = require.getShortWorkTimeHistoryItem(employeeId, baseDate);
		String fullName = require.getPersonEmployeeBasicInfo(employeeId).get().getBusinessName();
		
		return Optional.of(
				new Form9OutputEmployeeInfo(employeeId
						,	nurseClassification.get().getLicense()
						,	fullName
						,	fullTime
						,	!fullTime
						,	shortTimeItem.isPresent()
						,	empMedicalHistItem.get().isConcurrently()
						,	empMedicalHistItem.get().isOnlyNightShift()
						,	nurseClassification.get().isOfficeWorker()
							));
	}
	
	public static interface Require{
		
		/**
		 * 個人社員基本情報を取得する( 社員ID )
		 * @param employeeId 社員ID
		 * @return
		 */
		Optional<EmployeeCodeAndDisplayNameImport> getPersonEmployeeBasicInfo(String employeeId);
		
		/**
		 * 社員の医療勤務形態履歴項目を取得する
		 * @param employeeId 社員ID
		 * @param baseDate 基準日
		 * @return
		 */
		Optional<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(String employeeId, GeneralDate baseDate);
		
		/**
		 * 社員の短時間勤務履歴項目を取得する
		 * @param employeeId 社員ID
		 * @param baseDate 基準日
		 * @return
		 */
		Optional<ShortWorkTimeHistoryItem> getShortWorkTimeHistoryItem(String employeeId, GeneralDate baseDate);
		
		/**
		 * 看護区分を取得する
		 * @param nurseClassifiCode
		 * @return
		 */
		Optional<NurseClassification> getNurseClassification(NurseClassifiCode nurseClassifiCode);
		
	}
	
}
