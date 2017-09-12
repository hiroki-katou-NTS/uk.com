package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.approverrootmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WorkplaceApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverDto;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;

public class ApproverRootMasterImpl implements ApproverRootMaster{
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Inject
	private CompanyAdapter comAdapter;
	@Inject
	private WorkplaceApproverAdaptor wpAdapter;
	@Override
	public List<EmployeeApproveDto> employees(String companyID,
			GeneralDate baseDate, 
			boolean isCompany, 
			boolean isWorkplace,
			boolean isPerson) {
		//出力対象に会社別がある(có 会社別 trong đối tượng output)
		if(isCompany) {
			//ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain 「会社別就業承認ルート」)
			List<CompanyApprovalRoot> lstComs = comRootRepository.findByBaseDateOfCommon(companyID, baseDate);
			Optional<CompanyInfor> comInfo = comAdapter.getCurrentCompany();
		}
		//出力対象に職場別がある(có 職場別 trong đối tượng output)
		if(isWorkplace) {
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
			List<WorkplaceApprovalRoot> lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate);			
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstWps)) {
				List<WorkplaceApproverOutput> lstInfors = new ArrayList<>();	
				//Map<String, List<WorkplaceApproverDto>, List<WorkplaceApprovalRoot>> infor = new HashMap<String, List<WorkplaceApproverDto>, List<WorkplaceApprovalRoot>>();
				//Map<String, List<WorkplaceApproverDto>>  = new HashMap<String, List<WorkplaceApproverDto>>();
				for(WorkplaceApprovalRoot root: lstWps) {
					
					
					
					/*if(lstInfors.contains(root.getWorkplaceId())) {
						lstInfors
						continue;
					}
					//ドメインモデル「職場」を取得する(lấy dữ liệu domain 「職場」)
					List<WorkplaceApproverDto> wpInfors = wpAdapter.findByWkpId(companyID, root.getWorkplaceId(), baseDate);
					WorkplaceApproverOutput infor = new WorkplaceApproverOutput();
					List<WorkplaceApprovalRoot> rootInfors = new ArrayList<>();
					rootInfors.add(root);
					infor.setWorplaceId(root.getWorkplaceId());
					infor.setWpInfor(wpInfors);
					lstInfors.add(infor);*/
				}
				
			}
		}		
		//出力対象に個人別がある(có 個人別 trong đối tượng output)
		if(isPerson) {
			//ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
			List<PersonApprovalRoot> lstPss = psRootRepository.findAllByBaseDate(companyID, baseDate);
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstPss)) {
				//ドメインモデル「社員」を取得する(lấy dữ liệu domain「社員」)
				// TODO Viet sau khi QA duoc tra loi
			}
		}
		
		return null;
	}

}
