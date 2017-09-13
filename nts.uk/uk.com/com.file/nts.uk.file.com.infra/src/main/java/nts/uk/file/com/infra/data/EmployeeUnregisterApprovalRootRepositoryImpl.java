package nts.uk.file.com.infra.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeOfApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.EmployeeUnregisterApprovalRootImpl;
import nts.uk.file.com.app.EmployeeUnregisterApprovalRootRepository;
import nts.uk.file.com.app.EmployeeUnregisterOutputDataSoure;

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
	public EmployeeUnregisterOutputDataSoure getEmployeeUnregisterOutputLst(String companyId, GeneralDate date) {

		List<EmployeeUnregisterOutput> employLst = this.lstEmployeeUnregister(companyId, date);
		EmployeeUnregisterOutputDataSoure employeeUnregisOutput = new EmployeeUnregisterOutputDataSoure(employLst);
		return employeeUnregisOutput;
	}

	private List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate) {
		List<EmployeeApproveDto> lstEmps = new ArrayList<>();
		EmployeeApproveDto emp = new EmployeeApproveDto();
		emp.setPId("CEC90E5D-1910-4271-A1F5-2DC27B53E3E5");
		emp.setSId("90000000-0000-0000-0000-000000000012");
		emp.setSCd("000000000002");
		emp.setPName("日通システム　ベトナム　１");
		emp.setWpCode("C000000002");
		emp.setPName("Webメニューの設定");
		lstEmps.add(emp);
		emp.setPId("CE82367D-929C-4872-A51C-12BE4426EA6C");
		emp.setSId("90000000-0000-0000-0000-000000000016");
		emp.setSCd("000000000001");
		emp.setPName("日通システム　ベトナム　2");
		emp.setWpCode("C000000003");
		emp.setPName("Webメニューの設定１");
		lstEmps.add(emp);
		// ドメインモデル「社員」を取得する(lấy dữ liệu domain「社員」)
		// TODO thuc hien khi co tra loi QA

		// データが０件(data = 0)
		if (CollectionUtil.isEmpty(lstEmps)) {
			return null;
		}
		// ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain「会社別就業承認ルート」)
		List<CompanyApprovalRoot> comInfo = comRootRepository.findByBaseDateOfCommon(companyId, baseDate);
		if (CollectionUtil.isEmpty(comInfo)) {
			// TODO thuc hien khi co tra loi QA
			// return null;
		} else {
			List<CompanyApprovalRoot> comInfoCommon = comInfo.stream()
					.filter(x -> x.getEmploymentRootAtr().value == EmploymentRootAtr.COMMON.value)
					.collect(Collectors.toList());
			if (!CollectionUtil.isEmpty(comInfoCommon)) {
				return null;
			}
		}

		// 就業ルート区分が共通の「会社別就業承認ルート」がない場合(không có thông tin 「会社別就業承認ルート」 của 就業ルート区分là
		// common)
		// ドメインモデル「職場別就業承認ルート」を取得する(lấy thông tin domain 「職場別就業承認ルート」)
		List<WorkplaceApprovalRoot> wpInfo = wpRootRepository.findAllByBaseDate(companyId, baseDate);
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
		List<PersonApprovalRoot> psInfo = psRootRepository.findAllByBaseDate(companyId, baseDate);
		// 承認ルート未登録出力対象としてリスト
		List<EmployeeUnregisterOutput> lstUnRegister = new ArrayList<>();
		EmployeeUnregisterOutput empInfo = new EmployeeUnregisterOutput();
		for (EmployeeApproveDto empInfor : lstEmps) {
			List<String> appTypes = new ArrayList<>();
			for (ApplicationType appType : ApplicationType.values()) {
				// 社員の対象申請の承認ルートを取得する(lấy dữ liệu approve route của đối tượng đơn xin của nhân
				// viên)
				boolean isEmpRoot = employeeOfApprovalRoot.lstEmpApprovalRoot(companyId, comInfo, wpInfo, psInfo,
						empInfor, appType, baseDate);
				// 承認ルート未登録出力対象として追加する(thêm vào đối tượng chưa cài đặt approve route để output)
				if (!isEmpRoot) {
					appTypes.add(appType.nameId);
				}
			}

			if (!CollectionUtil.isEmpty(appTypes)) {
				empInfo.setAppType(appTypes);
				// TODO can phai them thong tin worplace cho employee
				empInfo.setEmpInfor(empInfor);
				lstUnRegister.add(empInfo);
			}
		}

		return lstUnRegister;
	}

}
