package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

@Stateless
public class EmployeeUnregisterApprovalRootImpl implements  EmployeeUnregisterApprovalRoot{
	@Inject
	private EmployeeOfApprovalRoot employeeOfApprovalRoot;
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	
	@Override
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate) {
		List<EmployeeApproveDto> lstEmps = new ArrayList<>();
		//ドメインモデル「社員」を取得する(lấy dữ liệu domain「社員」)
		// TODO thuc hien khi co tra loi QA
		
		//データが０件(data = 0)
		if(CollectionUtil.isEmpty(lstEmps)) {
			return null;
		}
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain「会社別就業承認ルート」)
		List<CompanyApprovalRoot> comInfo = comRootRepository.findByBaseDateOfCommon(companyId, baseDate);
		
		List<CompanyApprovalRoot> comInfoCommon = comInfo.stream()
				.filter(x -> x.getEmploymentRootAtr().value == EmploymentRootAtr.COMMON.value)
				.collect(Collectors.toList());		
		if(!CollectionUtil.isEmpty(comInfoCommon)) {
			return null;
		}
		//就業ルート区分が共通の「会社別就業承認ルート」がない場合(không có thông tin 「会社別就業承認ルート」 của 就業ルート区分là common)
		//ドメインモデル「職場別就業承認ルート」を取得する(lấy thông tin domain 「職場別就業承認ルート」)
		//List<WorkplaceApprovalRoot> wpInfo = wpRootRepository.findByBaseDateOfCommon(companyId, workplaceId, baseDate);
		// TODO Auto-generated method stub
		return null;
	}

}
