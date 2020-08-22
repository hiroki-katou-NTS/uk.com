package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentReserveLeaveInforFinder {
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	@Inject
	private ReserveLeaveManagerApdater rsvLeaManaApdater;
	@Inject
	private HdAppSetRepository repoHdAppSet;
	@Inject
	private AbsenceTenProcess absenceTenProcess;
	@Inject
	private ClosureService closureService;
	/**
	 * 1.積休ダイアログ作成（起動用）
	 * @param param
	 * @return
	 */
	public EmpRsvLeaveInforDto getEmploymentReserveLeaveInfor(ParamEmpRsvLeave param) {
		//2.社員リスト情報作成 - RequestList228
		List<EmployeeInfoImport> lstEmpInfor = this.atEmployeeAdapter.getByListSID(param.getListSID());
		String employeeCode = "";
		String employeeName = "";
		String yearResigName = "";
		// 積立年休管理区分
		boolean isRetentionManage = false;
		EmployeeInfoImport firstEmp = lstEmpInfor.get(0);
		RsvLeaManagerImport rsvLeaManaImp = null;
		if(!CollectionUtil.isEmpty(lstEmpInfor)) {
			employeeCode = firstEmp.getScd();
			employeeName = firstEmp.getBussinessName();
		}
		if(param.getInputDate() != null) {
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成 - RequestList201
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(firstEmp.getSid(), referDate);
			if(rsvLeaManaImport.isPresent()) {
				rsvLeaManaImp = rsvLeaManaImport.get();
			}
		}
		//ドメインモデル「休暇申請設定」を取得する (Vacation application setting)
		Optional<HdAppSet> hdAppSet = repoHdAppSet.getAll();
		if(hdAppSet.isPresent()) {
			yearResigName = hdAppSet.get().getYearResig().v();
		}
		return new EmpRsvLeaveInforDto(lstEmpInfor, rsvLeaManaImp, employeeCode, employeeName, yearResigName, isRetentionManage);
	}
	
	public EmpRsvLeaveInforDto getByEmloyee(ParamEmpRsvLeave param) {
		RsvLeaManagerImport rsvLeaManaImp = null;
		// 積立年休管理区分
		boolean isRetentionManage = false;
		if(param.getInputDate() != null) {
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			String cId = AppContexts.user().companyId();
			String sId = param.getListSID().get(0);
			//10-4.積立年休の設定を取得する
			isRetentionManage = absenceTenProcess.getSetForYearlyReserved(cId, sId, referDate);
			if (isRetentionManage) {
				//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成
				Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(param.getListSID().get(0), referDate);
				if(rsvLeaManaImport.isPresent()) {
					rsvLeaManaImp = rsvLeaManaImport.get();
				}
				// 社員に対応する締め期間を取得する
				DatePeriod closingPeriod = this.closureService.findClosurePeriod(sId, referDate);
				rsvLeaManaImp.getGrantRemainingList().stream().map(item-> {
					GeneralDate deadLine = GeneralDate.fromString(item.getDeadline(), "yyyy/MM/dd");
					return new RsvLeaGrantRemainingImportDto(
						item.getGrantDate(),
						item.getDeadline(),
						item.getGrantNumber(),
						item.getUsedNumber(),
						item.getRemainingNumber(),
						closingPeriod.contains(deadLine)
					);
				}).collect(Collectors.toList());
			}
		}
		return new EmpRsvLeaveInforDto(new ArrayList<>(), rsvLeaManaImp, "", "", "", isRetentionManage);
	}
}