package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;

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
		
		//$社員の看護区分Map = require.社員の医療勤務形態履歴項目を取得する(基準日, 社員リスト)
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem  = require.get(listEmp, referenceDate);
		
		mapNurseClassifiCode = listEmpMedicalWorkFormHisItem.stream().filter(x-> x.getOptMedicalWorkFormInfor().isPresent()).collect(()-> new HashMap<String,NurseClassifiCode>(), 
		            (r,s) -> r.put(s.getEmpID(),s.getOptMedicalWorkFormInfor().get().getNurseClassifiCode()),(r,s) -> r.putAll(s));
		// Test cho Phong ---
		//listEmpMedicalWorkFormHisItem.stream().filter(x-> x.getOptMedicalWorkFormInfor().isPresent()).collect(Collectors.toMap(EmpMedicalWorkFormHisItem::getEmpID, item -> item));
		//$看護区分Map = require.会社の看護区分リストを取得する()		
		List<NurseClassification>  listNurseClassification = require.getListCompanyNurseCategory();
		mapNurseClassifiCode.entrySet().forEach(action->{
			Optional<NurseClassification> classfica = listNurseClassification.stream().filter(x-> x.getNurseClassifiCode().v() == action.getValue().v()).findFirst();
			map.put(action.getValue(), classfica.get());
		});
		//$社員の看護区分コード = $社員の看護区分Map.get($)	
	//	listEmpMedicalWorkFormHisItem.
		if(mapNurseClassifiCode.isEmpty()){
			
		}
		
		return null;
		
		
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
