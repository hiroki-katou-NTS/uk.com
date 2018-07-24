package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger.ReserveLeaveManagerImport;

@Stateless
public class EmploymentReserveLeaveInforFinder {
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	@Inject
	private ReserveLeaveManagerApdater reserveLeaveManagerApdater;
	
	public EmploymentReserveLeaveInfor getEmploymentReserveLeaveInfor(boolean mode, List<String> employeeIDs, String inputDate){
		EmploymentReserveLeaveInfor result = new EmploymentReserveLeaveInfor();
		//2.社員リスト情報作成
		List<EmployeeInfoImport> employeeInforImports = this.atEmployeeAdapter.getByListSID(employeeIDs);
		result.setEmployeeInfors(employeeInforImports);
		if(!CollectionUtil.isEmpty(employeeInforImports)){
			result.setEmployeeCode(employeeInforImports.get(0).getScd());
			result.setEmployeeName(employeeInforImports.get(0).getBussinessName());
		}
		if(inputDate != null){
			GeneralDate referDate = GeneralDate.fromString(inputDate, "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成
			ReserveLeaveManagerImport reserveLeaveManagerImport = this.reserveLeaveManagerApdater.getReserveLeaveManager(employeeIDs.get(0), referDate);
			result.setReserveLeaveManagerImport(reserveLeaveManagerImport);
		}
		return result;
	}
	public EmploymentReserveLeaveInfor getByEmloyee(boolean mode, List<String> employeeIDs, String inputDate){
		EmploymentReserveLeaveInfor result = new EmploymentReserveLeaveInfor();
		if(inputDate != null){
			GeneralDate referDate = GeneralDate.fromString(inputDate, "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成
			ReserveLeaveManagerImport reserveLeaveManagerImport = this.reserveLeaveManagerApdater.getReserveLeaveManager(employeeIDs.get(0), referDate);
			result.setReserveLeaveManagerImport(reserveLeaveManagerImport);
		}
		return result;
	}
}
