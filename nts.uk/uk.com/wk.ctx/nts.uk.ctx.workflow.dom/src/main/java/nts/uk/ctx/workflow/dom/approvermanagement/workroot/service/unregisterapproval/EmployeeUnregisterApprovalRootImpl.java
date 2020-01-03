package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

@Stateless
public class EmployeeUnregisterApprovalRootImpl implements EmployeeUnregisterApprovalRoot {
	@Inject
	private EmployeeOfApprovalRoot employeeOfApprovalRoot;
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Inject
	private PersonAdapter psInfor;
	@Inject
	private EmployeeAdapter empInfor;
	@Inject
	private WorkplaceApproverAdapter wpNameInfor;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;

	@Override
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate) {
		List<EmployeeImport> lstEmps = empInfor.getEmployeesAtWorkByBaseDate(companyId, baseDate);
		// 承認ルート未登録出力対象としてリスト
		List<EmployeeUnregisterOutput> lstUnRegister = new ArrayList<>();
		// データが０件(data = 0)
		if (CollectionUtil.isEmpty(lstEmps)) {
			return lstUnRegister;
		}
		// ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain「会社別就業承認ルート」)
		List<CompanyApprovalRoot> comInfo = comRootRepository.findByBaseDate(companyId, baseDate);
		List<CompanyApprovalRoot> comInfoCommon = comInfo.stream()
				.filter(x -> x.getApprRoot().getEmploymentRootAtr().value == EmploymentRootAtr.COMMON.value)
				.collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(comInfoCommon)) {
			for (CompanyApprovalRoot companyApprovalRoot : comInfoCommon) {
				List<ApprovalPhase> lstAppPhase = repoAppPhase.getAllApprovalPhasebyCode(companyApprovalRoot.getApprovalId());
				if(!lstAppPhase.isEmpty()){
					return lstUnRegister;
				}
			}
		}

		// 就業ルート区分が共通の「会社別就業承認ルート」がない場合(không có thông tin 「会社別就業承認ルート」 của 就業ルート区分là
		// common)
		// ドメインモデル「職場別就業承認ルート」を取得する(lấy thông tin domain 「職場別就業承認ルート」)
		List<WorkplaceApprovalRoot> wpInfoByCid = wpRootRepository.findAllByBaseDate(companyId, baseDate);
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
		List<PersonApprovalRoot> psInfo = psRootRepository.findAllByBaseDate(companyId, baseDate);

		for (EmployeeImport empImport : lstEmps) {
			List<String> appTypes = new ArrayList<>();
			for (ApplicationType appType : ApplicationType.values()) {
				if(appType == ApplicationType.APPLICATION_36) {
					continue;
				}
				// 社員の対象申請の承認ルートを取得する(lấy dữ liệu approve route của đối tượng đơn xin của nhân viên)
				boolean isEmpRoot =false;
				isEmpRoot = employeeOfApprovalRoot.lstEmpApprovalRoot(companyId, comInfo, wpInfoByCid, psInfo, empImport,
						appType, baseDate);
				// 承認ルート未登録出力対象として追加する(thêm vào đối tượng chưa cài đặt approve route để output)
				if (!isEmpRoot) {
					appTypes.add(appType.nameId);
				}
			}
			//set workplace infor
			WorkplaceImport wpInforBySid = wpNameInfor.findBySid(empImport.getSId(), baseDate);
			empImport.setWpCode(wpInforBySid.getWkpCode());
			empImport.setWpName(wpInforBySid.getWkpName());
			if (!CollectionUtil.isEmpty(appTypes)) {
				empImport.setPName(psInfor.getPersonInfo(empImport.getSId()).getEmployeeName());
				EmployeeUnregisterOutput dataOuput = new EmployeeUnregisterOutput(empImport, appTypes);
				lstUnRegister.add(dataOuput);
			}
		}

		return lstUnRegister;
	}

}
