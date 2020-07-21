package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;

/**
 * 社員の免許区分を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.医療勤務形態
 * @author HieuLt
 *
 */
public class GetEmpLicenseClassificationService {
	
	public static List<EmpLicenseClassification> get(Require require, GeneralDate referenceDate, List<String> listEmp){
		
		Map<String , NurseClassifiCode> mapNurseClassifiCode = new HashMap<>();
		Map<NurseClassifiCode ,NurseClassification> map = new HashMap<>();

		// $社員の看護区分Map = require.社員の医療勤務形態履歴項目を取得する(基準日, 社員リスト)
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem  = require.get(listEmp, referenceDate);
		
		mapNurseClassifiCode = listEmpMedicalWorkFormHisItem.stream().filter(x-> x.getOptMedicalWorkFormInfor().isPresent()).collect(Collectors
				.toMap(EmpMedicalWorkFormHisItem::getEmpID, item -> item.getOptMedicalWorkFormInfor().get().getNurseClassifiCode()));
		
		// $看護区分Map = require.会社の看護区分リストを取得する()
		List<NurseClassification>  listNurseClassification = require.getListCompanyNurseCategory();
		// $看護区分Map
		map = listNurseClassification.stream().collect(Collectors.toMap(x-> x.getNurseClassifiCode(), x-> x));
		
		// $社員の看護区分コード = $社員の看護区分Map.get($)	
		// listEmpMedicalWorkFormHisItem.
		Map<String ,NurseClassifiCode> mapNurse = mapNurseClassifiCode;
		Map<NurseClassifiCode ,NurseClassification> mapNurseClass = map;
		return listEmp.stream().map(empId-> {
			// 	$社員の看護区分コード = $社員の看護区分Map.get($)
			NurseClassifiCode classifiCode = mapNurse.get(empId);
			if(classifiCode == null){
				return new EmpLicenseClassification(empId, Optional.empty());
			}
			
			// $免許区分 = $免許区分Map.get($社員の看護区分コード)											
			NurseClassification nurseClassification = mapNurseClass.get(classifiCode);
			
			if(nurseClassification == null){
				return new EmpLicenseClassification(empId, Optional.empty());
			}
			
			return new EmpLicenseClassification(empId, Optional.of(LicenseClassification.valueOf(nurseClassification.getLicense().value)));
			 
		}).collect(Collectors.toList());
	}
	
	
	public static interface Require{

		/**
		 * EmpMedicalWorkStyleHistoryRepository
		 * [R-1] 社員の医療勤務形態履歴項目を取得する
		 * @param listEmp
		 * @param referenceDate
		 * @return
		 */
		List<EmpMedicalWorkFormHisItem> get(List<String> listEmp , GeneralDate referenceDate);
		//		看護区分Repository.会社の看護区分リストを取得する(会社ID)		
		/**
		 * [R-1] 会社の看護区分リストを取得する
		 * @param companyId
		 * @return
		 */
		List<NurseClassification> getListCompanyNurseCategory();
	}

}
