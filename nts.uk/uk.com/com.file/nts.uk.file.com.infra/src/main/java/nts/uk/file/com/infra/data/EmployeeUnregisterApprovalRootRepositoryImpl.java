package nts.uk.file.com.infra.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeOfApprovalRoot;
import nts.uk.file.com.app.EmployeeUnregisterApprovalRootRepository;

@Stateless
public class EmployeeUnregisterApprovalRootRepositoryImpl implements EmployeeUnregisterApprovalRootRepository {

	@Inject
	private EmployeeOfApprovalRoot employeeOfApprovalRoot;
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;

	@Override
	public List<EmployeeUnregisterOutput> getEmployeeUnregisterOutputLst(String companyId, GeneralDate date) {

		List<EmployeeUnregisterOutput> employLst = this.lstEmployeeUnregister(companyId, date);
		return employLst;
	}

	private List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate) {
		List<EmployeeImport> lstEmps = new ArrayList<>();
		for (int i = 0; i < 15; i = i + 2) {
			EmployeeImport emp = new EmployeeImport();
			emp.setCompanyId(companyId);
			emp.setPId("CEC90E5D-1910-4271-A1F5-2DC27B53E3E5");
			emp.setSId("90000000-0000-0000-0000-000000000016");
			emp.setSCd("00000000000" + String.valueOf(i));
			emp.setPName("日通システム　ベトナム　" + String.valueOf(i));
			emp.setWpCode("C00000000" +  String.valueOf(i));
			emp.setWpName("Webメニューの設定");
			emp.setSMail("a@gmail.com");
			emp.setRetirementDate(GeneralDate.today());
			emp.setJoinDate(GeneralDate.today());
			lstEmps.add(emp);
			emp.setCompanyId(companyId);
			emp.setPId("CE82367D-929C-4872-A51C-12BE4426EA6C");
			emp.setSId("90000000-0000-0000-0000-000000000014");
			emp.setSCd("00000000000" + String.valueOf( i + 1));
			emp.setPName("日通システム　ベトナム　"+ String.valueOf( i + 1));
			emp.setWpCode("C00000000"+ String.valueOf( i + 1));
			emp.setWpName("Webメニューの設定" + String.valueOf( i + 1));
			emp.setSMail("b@gmail.com");
			emp.setRetirementDate(GeneralDate.today());
			emp.setJoinDate(GeneralDate.today());
			lstEmps.add(emp);

		}

		// ドメインモデル「社員」を取得する(lấy dữ liệu domain「社員」)
		// TODO thuc hien khi co tra loi QA

		// データが０件(data = 0)
		if (CollectionUtil.isEmpty(lstEmps)) {
			return null;
		}
		// ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain「会社別就業承認ルート」)
		List<CompanyApprovalRoot> comInfo = comRootRepository.findByBaseDateOfCommon(companyId, baseDate, 0).map(x -> Arrays.asList(x)).orElse(Collections.emptyList());
		if (CollectionUtil.isEmpty(comInfo)) {
			// TODO thuc hien khi co tra loi QA
			// return null;
		} else {
			List<CompanyApprovalRoot> comInfoCommon = comInfo.stream()
					.filter(x -> x.getApprRoot().getEmploymentRootAtr().value == EmploymentRootAtr.COMMON.value)
					.collect(Collectors.toList());
			if (!CollectionUtil.isEmpty(comInfoCommon)) {
				return null;
			}
		}

		// 就業ルート区分が共通の「会社別就業承認ルート」がない場合(không có thông tin 「会社別就業承認ルート」 của 就業ルート区分là
		// common)
		// ドメインモデル「職場別就業承認ルート」を取得する(lấy thông tin domain 「職場別就業承認ルート」)
//		List<WorkplaceApprovalRoot> wpInfo = wpRootRepository.findAllByBaseDate(companyId, baseDate);
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
//		List<PersonApprovalRoot> psInfo = psRootRepository.findAllByBaseDate(companyId, baseDate);
		// 承認ルート未登録出力対象としてリスト
		List<EmployeeUnregisterOutput> lstUnRegister = new ArrayList<>();
		for (EmployeeImport empInfor : lstEmps) {
			List<String> appTypes = new ArrayList<>();
			for (ApplicationType appType : ApplicationType.values()) {
				// 社員の対象申請の承認ルートを取得する(lấy dữ liệu approve route của đối tượng đơn xin của nhân
				// viên)
////				boolean isEmpRoot = employeeOfApprovalRoot.lstEmpApprovalRoot(companyId, comInfo, wpInfo, psInfo,
//						empInfor, appType, baseDate);
				// 承認ルート未登録出力対象として追加する(thêm vào đối tượng chưa cài đặt approve route để output)
//				if (!isEmpRoot) {
//					appTypes.add(appType.nameId);
//				}
			}

			if (!CollectionUtil.isEmpty(appTypes)) {
				// TODO can phai them thong tin worplace cho employee
				EmployeeUnregisterOutput empInfo = new EmployeeUnregisterOutput(empInfor, appTypes);
				lstUnRegister.add(empInfo);
			}
		}

		return lstUnRegister;
	}

}
