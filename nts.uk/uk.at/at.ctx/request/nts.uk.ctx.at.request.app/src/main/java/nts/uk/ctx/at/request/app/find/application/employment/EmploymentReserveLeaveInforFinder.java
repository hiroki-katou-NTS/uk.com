package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;

@Stateless
public class EmploymentReserveLeaveInforFinder {
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	@Inject
	private ReserveLeaveManagerApdater rsvLeaManaApdater;
	/**
	 * 1.積休ダイアログ作成（起動用）
	 * @param param
	 * @return
	 */
	public EmpRsvLeaveInforDto getEmploymentReserveLeaveInfor(ParamEmpRsvLeave param){
		EmpRsvLeaveInforDto result = new EmpRsvLeaveInforDto();
		//2.社員リスト情報作成 - RequestList228
		List<EmployeeInfoImport> employeeInforImports = this.atEmployeeAdapter.getByListSID(param.getListSID());
		result.setEmployeeInfors(employeeInforImports);
		if(!CollectionUtil.isEmpty(employeeInforImports)){
			result.setEmployeeCode(employeeInforImports.get(0).getScd());
			result.setEmployeeName(employeeInforImports.get(0).getBussinessName());
		}
		if(param.getInputDate() != null){
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成 - RequestList201
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(param.getListSID().get(0), referDate);
			if(rsvLeaManaImport.isPresent()){
				result.setRsvLeaManaImport(rsvLeaManaImport.get());
			}
		}
		return result;
	}
	public EmpRsvLeaveInforDto getByEmloyee(ParamEmpRsvLeave param){
		EmpRsvLeaveInforDto result = new EmpRsvLeaveInforDto();
		if(param.getInputDate() != null){
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(param.getListSID().get(0), referDate);
			if(rsvLeaManaImport.isPresent()){
				result.setRsvLeaManaImport(rsvLeaManaImport.get());
			}
		}
		return result;
	}
}
