package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaGrantRemainingImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.TmpRsvLeaveMngImport;

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
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(employeeInforImports.get(0).getSid(), referDate);
			if(rsvLeaManaImport.isPresent()){
				result.setRsvLeaManaImport(rsvLeaManaImport.get());
			}else{
//				List<RsvLeaGrantRemainingImport> lstRsv = new ArrayList<>();
//				lstRsv.add(new RsvLeaGrantRemainingImport("2019/01/01","2019/05/05",3.1, 3.2,3.3));
//				lstRsv.add(new RsvLeaGrantRemainingImport("2017/01/01","2017/05/05",1.1, 1.2,1.3));
//				lstRsv.add(new RsvLeaGrantRemainingImport("2018/01/01","2018/05/05",2.1, 2.2,2.3));
//				List<TmpRsvLeaveMngImport> lstTmp = new ArrayList<>();
//				lstTmp.add(new TmpRsvLeaveMngImport("2018/03/03", "Loai2", 0.7));
//				lstTmp.add(new TmpRsvLeaveMngImport("2017/03/03", "Loai1", 0.5));
//				lstTmp.add(new TmpRsvLeaveMngImport("2019/03/03", "Loai3", 0.9));
//				RsvLeaManagerImport dataF = new RsvLeaManagerImport(null, lstRsv,lstTmp);
//				result.setRsvLeaManaImport(dataF);
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
