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
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;

@Stateless
public class EmploymentReserveLeaveInforFinder {
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	@Inject
	private ReserveLeaveManagerApdater rsvLeaManaApdater;
	@Inject
	private HdAppSetRepository repoHdAppSet;
	/**
	 * 1.積休ダイアログ作成（起動用）
	 * @param param
	 * @return
	 */
	public EmpRsvLeaveInforDto getEmploymentReserveLeaveInfor(ParamEmpRsvLeave param){
		//2.社員リスト情報作成 - RequestList228
		List<EmployeeInfoImport> lstEmpInfor = this.atEmployeeAdapter.getByListSID(param.getListSID());
		String employeeCode = "";
		String employeeName = "";
		String yearResigName = "";
		EmployeeInfoImport firstEmp = lstEmpInfor.get(0);
		RsvLeaManagerImport rsvLeaManaImp = null;
		if(!CollectionUtil.isEmpty(lstEmpInfor)){
			employeeCode = firstEmp.getScd();
			employeeName = firstEmp.getBussinessName();
		}
		if(param.getInputDate() != null){
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成 - RequestList201
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(firstEmp.getSid(), referDate);
			if(rsvLeaManaImport.isPresent()){
				rsvLeaManaImp = rsvLeaManaImport.get();
			}
		}
		//ドメインモデル「休暇申請設定」を取得する (Vacation application setting)
		Optional<HdAppSet> hdAppSet = repoHdAppSet.getAll();
		if(hdAppSet.isPresent()){
			yearResigName = hdAppSet.get().getYearResig().v();
		}
		return new EmpRsvLeaveInforDto(lstEmpInfor, rsvLeaManaImp, employeeCode, employeeName, yearResigName);
	}
	public EmpRsvLeaveInforDto getByEmloyee(ParamEmpRsvLeave param){
		RsvLeaManagerImport rsvLeaManaImp = null;
		if(param.getInputDate() != null){
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(param.getListSID().get(0), referDate);
			if(rsvLeaManaImport.isPresent()){
				rsvLeaManaImp = rsvLeaManaImport.get();
			}
		}
		return new EmpRsvLeaveInforDto(new ArrayList<>(), rsvLeaManaImp, "", "", "");
	}
}
