package nts.uk.ctx.at.request.dom.application.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.ClosureAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor;

@Stateless
public class OtherCommonAlgorithmDefault implements OtherCommonAlgorithmService {
	
	@Inject
	private EmployeeAdaptor employeeAdaptor;
	
	/*@Inject
	private ClosureAdaptor closureAdaptor;*/
	
	public List<GeneralDate> employeePeriodCurrentMonthCalculate(String companyID, String employeeID, GeneralDate date){
		/*
		アルゴリズム「社員所属雇用履歴を取得」を実行する(thực hiện xử lý 「社員所属雇用履歴を取得」)
		String employeeCD = EmployeeEmploymentHistory.find(employeeID, date); // emloyeeCD <=> 雇用コード
		*/
		String employeeCD = employeeAdaptor.getEmploymentCode(companyID, employeeID, date);
		
		/*
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		Object<String: tightenID, String: currentMonth> obj1 = Tighten.find(companyID, employeeCD); // obj1 <=> (締めID,当月)
		*/
		//String closure = closureAdaptor.findById(companyID, employeeCD);
		
		/*
		当月の期間を算出する(tính period của tháng hiện tại)
		Object<String: startDate, String: endDate> obj2 = Period.find(obj1.tightenID, obj1.currentMonth); // obj2 <=> 締め期間(開始年月日,終了年月日) 
		*/
		return new ArrayList<GeneralDate>();
	}

	@Override
	public void getWorkingHoursByWorkplace(String companyID, String employeeID, GeneralDate referenceDate) {
		// TODO Auto-generated method stub
		
	}
}
