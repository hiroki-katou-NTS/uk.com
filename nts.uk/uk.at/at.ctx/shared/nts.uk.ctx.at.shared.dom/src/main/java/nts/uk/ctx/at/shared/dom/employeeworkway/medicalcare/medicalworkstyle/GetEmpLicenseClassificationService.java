package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;

/**
 * 社員の免許区分を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.社員の免許区分を取得する
 * @author HieuLt
 *
 */
public class GetEmpLicenseClassificationService {
	
	public static List<EmpLicenseClassification> get(Require require, GeneralDate referenceDate, List<String> listEmp){
		// $社員の看護区分Map = require.社員の医療勤務形態履歴項目を取得する(基準日, 社員リスト)
		Map<String, NurseClassifiCode> mapNurseClassifiCode = require.getEmpMedicalWorkStyleHistoryItem(listEmp, referenceDate)
				.stream()
				.collect(Collectors.toMap(	EmpMedicalWorkStyleHistoryItem::getEmpID,
											EmpMedicalWorkStyleHistoryItem::getNurseClassifiCode));
		// $看護区分マスタMap
		Map<NurseClassifiCode, NurseClassification> nurseClsMasterMaps = require.getListCompanyNurseCategory()
				.stream()
				.collect(Collectors.toMap(NurseClassification::getNurseClassifiCode, nurseCls -> nurseCls));
		
		return listEmp.stream().map(empId-> {
			// 	$社員の看護区分コード = $社員の看護区分Map.get($)
			NurseClassifiCode classifiCode = mapNurseClassifiCode.get(empId);
			if(classifiCode == null){
				return EmpLicenseClassification.createEmpLicenseClassification(empId);
			}
			
			// $看護区分 = $看護区分マスタMap.get($社員の看護区分コード)											
			NurseClassification nurseClassification = nurseClsMasterMaps.get(classifiCode);
			
			if(nurseClassification == null){
				return EmpLicenseClassification.createEmpLicenseClassification(empId);
			}
			
			return EmpLicenseClassification.createEmpLicenseClassification(empId, nurseClassification);
			 
		}).collect(Collectors.toList());
	}
	
	
	public static interface Require{
		/**
		 * [R-1] 社員の医療勤務形態履歴項目を取得する
		 * @param listEmp
		 * @param referenceDate
		 * @return
		 */
		List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp , GeneralDate referenceDate);
		
		/**
		 * [R-1] 会社の看護区分リストを取得する
		 * @param companyId
		 * @return
		 */
		List<NurseClassification> getListCompanyNurseCategory();
	}

}
