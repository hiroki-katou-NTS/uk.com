package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.sql.Date;
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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.person.PersonInforExportAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverAdaptor;

@Stateless
public class EmployeeUnregisterApprovalRootImpl implements  EmployeeUnregisterApprovalRoot{
	@Inject
	private EmployeeOfApprovalRoot employeeOfApprovalRoot;
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Inject
	private PersonInforExportAdapter psInfor;
	@Inject
	private EmployeeApproveAdapter empInfor;
	@Inject
	private WorkplaceApproverAdaptor wpNameInfor;
	@Override
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate) {
		List<EmployeeApproveDto> lstEmps = empInfor.getEmployeesAtWorkByBaseDate(companyId, baseDate);
		
		//データが０件(data = 0)
		if(CollectionUtil.isEmpty(lstEmps)) {
			return null;
		}
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain「会社別就業承認ルート」)
		List<CompanyApprovalRoot> comInfo = comRootRepository.findByBaseDateOfCommon(companyId, baseDate);		
		if(CollectionUtil.isEmpty(comInfo)){
			// TODO thuc hien khi co tra loi QA
			//return null;
		}else {
			List<CompanyApprovalRoot> comInfoCommon = comInfo.stream()
					.filter(x -> x.getEmploymentRootAtr().value == EmploymentRootAtr.COMMON.value)
					.collect(Collectors.toList());		
			if(!CollectionUtil.isEmpty(comInfoCommon)) {
				return null;
			}	
		}
		
		//就業ルート区分が共通の「会社別就業承認ルート」がない場合(không có thông tin 「会社別就業承認ルート」 của 就業ルート区分là common)
		//ドメインモデル「職場別就業承認ルート」を取得する(lấy thông tin domain 「職場別就業承認ルート」)
		List<WorkplaceApprovalRoot> wpInfo = wpRootRepository.findAllByBaseDate(companyId, baseDate);
		//ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
		List<PersonApprovalRoot> psInfo = psRootRepository.findAllByBaseDate(companyId, baseDate);
		//承認ルート未登録出力対象としてリスト
		List<EmployeeUnregisterOutput> lstUnRegister = new ArrayList<>();
		for(EmployeeApproveDto empInfor: lstEmps) {
			EmployeeUnregisterOutput empInfo = new EmployeeUnregisterOutput();
			List<String> appTypes = new ArrayList<>();
			for(ApplicationType appType: ApplicationType.values()) {
				//社員の対象申請の承認ルートを取得する(lấy dữ liệu approve route của đối tượng đơn xin của nhân viên)
				boolean isEmpRoot = employeeOfApprovalRoot.lstEmpApprovalRoot(companyId,
						comInfo,
						wpInfo,
						psInfo,
						empInfor,
						appType,
						baseDate);
				//承認ルート未登録出力対象として追加する(thêm vào đối tượng chưa cài đặt approve route để output)
				if(!isEmpRoot) {
					appTypes.add(appType.nameId);					
				}
			}
			
			if(!CollectionUtil.isEmpty(appTypes)) {
				
				empInfo.setAppType(appTypes);
				empInfor.setPName(psInfor.personName(empInfor.getSId()));
				empInfo.setEmpInfor(empInfor);
				lstUnRegister.add(empInfo);
			}
		}
		
		return lstUnRegister;
	}

}
