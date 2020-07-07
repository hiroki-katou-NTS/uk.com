package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import nts.arc.time.GeneralDate;

/**
 * 社員の免許区分を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.医療勤務形態
 * @author HieuLt
 *
 */
public class GetEmpLicenseClassificationService {
	
	public static List<EmpLicenseClassification> get(Require reuiqre, GeneralDate referenceDate, List<String> listEmp){
		
		Map<String , NurseClassifiCode> map = new HashMap<>();
	/**	$社員の看護区分Map = require.社員の医療勤務形態履歴項目を取得する(基準日, 社員リスト)			
				:filter $.医療情報.isPresent									
				:map	key		社員ID									
				value	看護区分コード	 **/		
		
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
		List<NurseClassification> getListCompanyNurseCategory(String companyId);
	}

}
