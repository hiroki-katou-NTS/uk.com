package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalForApplication;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.CompanyApprovalInfor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WorkplaceApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.WorkplaceApproverDto;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
@Stateless
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
	@Inject
	private ApprovalPhaseRepository phaseRepository;
	
	private final String rootCommon = "共通ルート";
	@Override
	public MasterApproverRootOutput masterInfors(String companyID,
			GeneralDate baseDate, 
			boolean isCompany, 
			boolean isWorkplace,
			boolean isPerson) {
		MasterApproverRootOutput masterInfor = null;
		CompanyApprovalInfor comMasterInfor = null;		
		Map<String, WorkplaceApproverOutput> mapWpRootInfor = new HashMap<>();
		//出力対象に会社別がある(có 会社別 trong đối tượng output)
		if(isCompany) {
			comMasterInfor = getComApprovalInfor(companyID, baseDate);
			masterInfor.setCompanyRootInfor(comMasterInfor);
		}
		//出力対象に職場別がある(có 職場別 trong đối tượng output)
		if(isWorkplace) {
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
			List<WorkplaceApprovalRoot> lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate);
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstWps)) {				
				mapWpRootInfor = getWpApproverInfor(lstWps, companyID, baseDate);				
				masterInfor.setWorplaceRootInfor(mapWpRootInfor);
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
		
		return masterInfor;
	}
	
	private Map<String, WorkplaceApproverOutput> getWpApproverInfor(List<WorkplaceApprovalRoot> lstWps, String companyID, GeneralDate baseDate){
		Map<String, WorkplaceApproverOutput> mapWpRootInfor =  new HashMap<>();
		for(WorkplaceApprovalRoot root: lstWps) {
			List<ApprovalForApplication> wpRootInfor = new ArrayList<>();
			//Neu da co workplace roi
			if(mapWpRootInfor.containsKey(root.getWorkplaceId())) {
				WorkplaceApproverOutput wpApp = mapWpRootInfor.get(root.getWorkplaceId());						
				wpRootInfor = wpApp.getWpRootInfor();
				wpRootInfor = getAppInfors(root, wpRootInfor, companyID);
				continue;
			}
			
			//ドメインモデル「職場」を取得する(lấy dữ liệu domain 「職場」)
			List<WorkplaceApproverDto> wpInfors = wpAdapter.findByWkpId(companyID, root.getWorkplaceId(), baseDate);
			WorkplaceApproverOutput wpOutput = null;
			wpOutput.setWpInfor(wpInfors);
			wpRootInfor = getAppInfors(root, wpRootInfor, companyID);
			wpOutput.setWpRootInfor(wpRootInfor);
			mapWpRootInfor.put(root.getWorkplaceId(), wpOutput);
		}
		return mapWpRootInfor;
	}
	
	private List<ApprovalForApplication> getAppInfors(WorkplaceApprovalRoot root, List<ApprovalForApplication> wpRootInfor, String companyID){
		ApprovalForApplication wpAppInfo = null;
		//neu la 就業ルート区分 la 共通(common)
		if(root.getEmploymentRootAtr() == EmploymentRootAtr.COMMON) {
			wpAppInfo.setAppType(rootCommon);
		}else if(root.getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION) {
			wpAppInfo.setAppType(root.getApplicationType().nameId);
		}else {
			wpAppInfo.setAppType("");
		}
		wpAppInfo.setStartDate(root.getPeriod().getStartDate());
		wpAppInfo.setEndDate(root.getPeriod().getEndDate());
		List<ApprovalRootMaster> lstAppInfo = new ArrayList<>();
		//承認フェーズ, 承認者
		lstAppInfo = getPhaseApprover(companyID, root.getBranchId());
		wpAppInfo.setLstApproval(lstAppInfo);
		wpRootInfor.add(wpAppInfo);
		return wpRootInfor;
	}

	
	/**
	 * get phase, approver of company
	 * @param approvalForApplication
	 * @param comRoot
	 * @param companyID
	 * @return
	 */
	private ApprovalForApplication getApproval(ApprovalForApplication approvalForApplication,CompanyApprovalRoot comRoot, String companyID) {
		approvalForApplication.setStartDate(comRoot.getPeriod().getStartDate());
		approvalForApplication.setEndDate(comRoot.getPeriod().getEndDate());
		
		List<ApprovalRootMaster> lstMatter = new ArrayList<>();
		//承認フェーズ, 承認者
		lstMatter = getPhaseApprover(companyID, comRoot.getBranchId());		
		approvalForApplication.setLstApproval(lstMatter);
		return approvalForApplication;
	}
	
	
	private List<ApprovalRootMaster> getPhaseApprover(String companyID, String branchId){
		List<ApprovalRootMaster> lstMatter = new ArrayList<>();
		ApprovalRootMaster appRoot = null;
		//承認フェーズ, 承認者
		List<ApprovalPhase> getAllIncludeApprovers = phaseRepository.getAllIncludeApprovers(companyID, branchId);
		for(ApprovalPhase phase: getAllIncludeApprovers) {
			appRoot.setPhaseNumber(phase.getOrderNumber());
			appRoot.setApprovalForm(phase.getApprovalForm().name);
			List<String> lstApprovers = new ArrayList<>();
			for(Approver approver: phase.getApprovers()) {
				// TODO chuyen sang ten khi co request
				lstApprovers.add(approver.getEmployeeId());
			}
			appRoot.setPersonName(lstApprovers);
			lstMatter.add(appRoot);
		}
		return lstMatter;
	}
	
	/**
	 * get all approval of company
	 * @param companyID
	 * @param baseDate
	 * @return
	 */
	private CompanyApprovalInfor getComApprovalInfor(String companyID, GeneralDate baseDate) {
		CompanyApprovalInfor comMasterInfor = null;
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain 「会社別就業承認ルート」)
		List<CompanyApprovalRoot> lstComs = comRootRepository.findByBaseDateOfCommon(companyID, baseDate);
		Optional<CompanyInfor> comInfo = comAdapter.getCurrentCompany();
		List<ApprovalForApplication> comApproverRoot =  new ArrayList<>();
		ApprovalForApplication approvalForApplication = null;
		//Lap de lay du lieu theo app type
		for(ApplicationType appType: ApplicationType.values()) {
			approvalForApplication = null;
			approvalForApplication.setAppType(appType.nameId);
			//「会社別就業承認ルート」	
			if(!lstComs.isEmpty()) {
				Optional<CompanyApprovalRoot> comApps = lstComs
						.stream()
						.filter(x -> x.getApplicationType() == appType)
						.findAny();
				// TODO CHI TRA RA 1 DU LIEU -> XEM LAI
				if(comApps.isPresent()) {						
					approvalForApplication = getApproval(approvalForApplication, comApps.get(), companyID);
				}
			}					
			//thong tin các application da duoc set approver
			comApproverRoot.add(approvalForApplication);
		}
		approvalForApplication = null;
		approvalForApplication.setAppType(rootCommon);
		if(!lstComs.isEmpty()) {
			//lay du lieu voi truong hop 0: 共通(common)
			Optional<CompanyApprovalRoot> opComCommon = lstComs.stream()
					.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
					.findAny();
			if(opComCommon.isPresent()) {					
				approvalForApplication = getApproval(approvalForApplication, opComCommon.get(), companyID);					
			}	
		}
		comApproverRoot.add(approvalForApplication);
		comMasterInfor.setComInfo(comInfo);
		comMasterInfor.setLstComs(comApproverRoot);
		return comMasterInfor;
	}
}
