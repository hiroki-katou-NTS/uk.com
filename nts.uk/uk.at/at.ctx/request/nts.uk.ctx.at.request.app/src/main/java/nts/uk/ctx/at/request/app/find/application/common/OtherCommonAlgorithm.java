package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 
 * 16.その他
 *
 */
public class OtherCommonAlgorithm {
	
	/**
	 * 4.社員の当月の期間を算出する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param date 基準日
	 * @return List<String>: [0]: startDate, [1]: endDate <=> 締め期間(開始年月日,終了年月日) 
	 */
	public List<String> employeePeriodCurrentMonthCalculate(String companyID, String employeeID, String date){
		
		/*
		アルゴリズム「社員所属雇用履歴を取得」を実行する(thực hiện xử lý 「社員所属雇用履歴を取得」)
		String employeeCD = EmployeeEmploymentHistory.find(employeeID, date); // emloyeeCD <=> 雇用コード
		
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		Object<String: tightenID, String: currentMonth> obj1 = Tighten.find(companyID, employeeCD); // obj1 <=> (締めID,当月)
		
		当月の期間を算出する(tính period của tháng hiện tại)
		Object<String: startDate, String: endDate> obj2 = Period.find(obj1.tightenID, obj1.currentMonth); // obj2 <=> 締め期間(開始年月日,終了年月日) 
		*/
		return new ArrayList<String>(Arrays.asList("startDate", "endDate"));
	}
}
