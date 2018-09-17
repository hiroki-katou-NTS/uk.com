package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalForApplication;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.CompanyApprovalInfor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterEmployeeOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterWkpOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.PersonApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WorkplaceApproverOutput;
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
	private WorkplaceApproverAdapter wpAdapter;
	@Inject
	private ApprovalPhaseRepository phaseRepository;
	@Inject
	private PersonAdapter psInfor;
	@Inject
	private SyJobTitleAdapter jobTitle;
	private static final String ROOT_COMMON = "共通ルート";
	@Override
	public MasterApproverRootOutput masterInfors(String companyID,
			GeneralDate baseDate, 
			boolean isCompany, 
			boolean isWorkplace,
			boolean isPerson) {
		CompanyApprovalInfor comMasterInfor = null;		
		MasterWkpOutput wkpRootOutput = new MasterWkpOutput(new HashMap<>(), new ArrayList<>());
		MasterEmployeeOutput empRootOutput = new MasterEmployeeOutput(new HashMap<>(), new ArrayList<>());
		//出力対象に会社別がある(có 会社別 trong đối tượng output)
		if(isCompany) {//Lay data COMPANY
			comMasterInfor = this.getComApprovalInfor(companyID, baseDate);
		}
		//出力対象に職場別がある(có 職場別 trong đối tượng output)
		if(isWorkplace) {//Lay data WORKPLACE
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
			List<WorkplaceApprovalRoot> lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate);
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstWps)) {				
				wkpRootOutput = this.getWpApproverInfor(lstWps, companyID, baseDate);				
			}
		}		
		//出力対象に個人別がある(có 個人別 trong đối tượng output)
		if(isPerson) {//Lay data PERSON
			//ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
			List<PersonApprovalRoot> lstPss = psRootRepository.findAllByBaseDate(companyID, baseDate);
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstPss)) {
				empRootOutput = this.getPsRootInfor(lstPss, companyID);
			}
		}
		return new MasterApproverRootOutput(comMasterInfor, wkpRootOutput, empRootOutput);
	}
	/**
	 *  get all approval of employee
	 * @param lstPss
	 * @param companyID
	 * @return
	 */
	private MasterEmployeeOutput getPsRootInfor(List<PersonApprovalRoot> lstPss, String companyID){
		Map<String, PersonApproverOutput> mapPsRootInfor = new HashMap<>();
		List<EmployeeApproverOutput> lstEmployeeInfo = new ArrayList<>();
		for(PersonApprovalRoot root: lstPss) {
			List<ApprovalForApplication> psWootInfor = new ArrayList<>();
			ApprovalRootCommonOutput psRoot = new ApprovalRootCommonOutput(root.getCompanyId(),
					root.getApprovalId(), root.getEmployeeId(),
					"", 
					root.getEmploymentAppHistoryItems().get(0).getHistoryId(),
					root.getApplicationType() == null ? 0: root.getApplicationType().value,
					root.getEmploymentAppHistoryItems().get(0).start(), 
					root.getEmploymentAppHistoryItems().get(0).end(), 
					root.getBranchId(), 
					root.getAnyItemApplicationId(),
					root.getConfirmationRootType() == null ? 0: root.getConfirmationRootType().value,
					root.getEmploymentRootAtr() == null ? 0: root.getEmploymentRootAtr().value); 
			//Neu da co person roi
			if(!mapPsRootInfor.isEmpty() && mapPsRootInfor.containsKey(root.getEmployeeId())) {
				PersonApproverOutput psApp = mapPsRootInfor.get(root.getEmployeeId());
				psWootInfor = psApp.getPsRootInfo();
				psWootInfor = this.getAppInfors(psRoot, psWootInfor, companyID);
				continue;
			}
			//ドメインモデル「社員」を取得する(lấy dữ liệu domain「社員」)		
			PersonImport psInfos = psInfor.getPersonInfo(root.getEmployeeId());
			EmployeeApproverOutput empInfor = new EmployeeApproverOutput(psInfos.getSID(), psInfos.getEmployeeCode(), psInfos.getEmployeeName()); 
			psWootInfor = this.getAppInfors(psRoot, psWootInfor, companyID);
			PersonApproverOutput psOutput = new PersonApproverOutput(empInfor, psWootInfor);
			mapPsRootInfor.put(root.getEmployeeId(), psOutput);
			lstEmployeeInfo.add(empInfor);
		}
		//QA#100181
		for (Map.Entry<String, PersonApproverOutput> entry : mapPsRootInfor.entrySet()){
			PersonApproverOutput ps = entry.getValue();
			List<ApprovalForApplication> psRootSort = this.sortByAppTypeConfirm(ps.getPsRootInfo());
			ps.setPsRootInfo(psRootSort);
			entry.setValue(ps);
		}
		Collections.sort(lstEmployeeInfo, Comparator.comparing(EmployeeApproverOutput:: getEmpCD));
		return new MasterEmployeeOutput(mapPsRootInfor, lstEmployeeInfo);
	}
	
	/**
	 * get all approval of workplace
	 * @param lstWps
	 * @param companyID
	 * @param baseDate
	 * @return
	 */
	private MasterWkpOutput getWpApproverInfor(List<WorkplaceApprovalRoot> lstWps, String companyID, GeneralDate baseDate){
		Map<String, WorkplaceApproverOutput> mapWpRootInfor =  new HashMap<>();
		List<WorkplaceImport> lstWpInfor = new ArrayList<>();
		for(WorkplaceApprovalRoot root: lstWps) {//loop theo wkp
			List<ApprovalForApplication> wpRootInfor = new ArrayList<>();
			ApprovalRootCommonOutput wpRoot = new ApprovalRootCommonOutput(root.getCompanyId(),
					root.getApprovalId(), 
					"",
					root.getWorkplaceId(), 
					root.getEmploymentAppHistoryItems().get(0).getHistoryId(),
					root.getApplicationType() == null ? 0:  root.getApplicationType().value,
					root.getEmploymentAppHistoryItems().get(0).start(), 
					root.getEmploymentAppHistoryItems().get(0).end(), 
					root.getBranchId(), 
					root.getAnyItemApplicationId(),
					root.getConfirmationRootType()  == null ? 0:  root.getConfirmationRootType().value,
					root.getEmploymentRootAtr() == null ? 0: root.getEmploymentRootAtr().value);
			//Neu da co workplace roi
			if(!mapWpRootInfor.isEmpty() && mapWpRootInfor.containsKey(root.getWorkplaceId())) {
				WorkplaceApproverOutput wpApp = mapWpRootInfor.get(root.getWorkplaceId());						
				wpRootInfor = wpApp.getWpRootInfor();
				wpRootInfor = this.getAppInfors(wpRoot, wpRootInfor, companyID);
				continue;
			}
			//Lay thong tin detail cua workplace
			//ドメインモデル「職場」を取得する(lấy dữ liệu domain 「職場」)
			Optional<WorkplaceImport> wpOp = wpAdapter.findByWkpId( root.getWorkplaceId(), baseDate);
			WorkplaceImport wpDto = wpOp.isPresent() ? wpOp.get() : new WorkplaceImport(root.getWorkplaceId(), "", "マスタ未登録");
			wpRootInfor = this.getAppInfors(wpRoot, wpRootInfor, companyID);
			
			// fix data
			WorkplaceApproverOutput wpOutput = new WorkplaceApproverOutput(wpDto, wpRootInfor);
			mapWpRootInfor.put(root.getWorkplaceId(), wpOutput);
			lstWpInfor.add(wpDto);
		}
		//QA#100181
		for (Map.Entry<String, WorkplaceApproverOutput> entry : mapWpRootInfor.entrySet()){
			WorkplaceApproverOutput wkp = entry.getValue();
			List<ApprovalForApplication> wpRootSort = this.sortByAppTypeConfirm(wkp.getWpRootInfor());
			wkp.setWpRootInfor(wpRootSort);
			entry.setValue(wkp);
		}
		Collections.sort(lstWpInfor, Comparator.comparing(WorkplaceImport:: getWkpCode));
		return new MasterWkpOutput(mapWpRootInfor, lstWpInfor);
	}
	
	private List<ApprovalForApplication> getAppInfors(ApprovalRootCommonOutput root, List<ApprovalForApplication> wpRootInfor, String companyID){
		//ApprovalForApplication wpAppInfo = null;
		//neu la 就業ルート区分 la 共通(common)
		String appName = "";
		int appType = 0;
		if(root.getEmploymentRootAtr() == EmploymentRootAtr.COMMON.value) {
			appName = ROOT_COMMON;
		}else if(root.getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION.value) {
			appType = root.getApplicationType();
			appName = EnumAdaptor.valueOf(root.getApplicationType(), ApplicationType.class).nameId;
		}else if(root.getEmploymentRootAtr()== EmploymentRootAtr.CONFIRMATION.value){
			appType = root.getConfirmationRootType();
			appName = EnumAdaptor.valueOf(root.getConfirmationRootType(), ConfirmationRootType.class).nameId;
		}
		List<ApprovalRootMaster> lstAppInfo = new ArrayList<>();
		//承認フェーズ, 承認者
		lstAppInfo = getPhaseApprover(companyID, root.getBranchId(), root.getStartDate());
		ApprovalForApplication wpAppInfo = new ApprovalForApplication(root.getEmploymentRootAtr(), appType, appName, root.getStartDate(), root.getEndDate(), lstAppInfo);
		wpRootInfor.add(wpAppInfo);
		return wpRootInfor;
	}
	/**
	 * ソート順： 申請種類（昇順）、確認ルート種類（昇順）
	 * @param wpRootInfor
	 * @return
	 */
	private List<ApprovalForApplication> sortByAppTypeConfirm(List<ApprovalForApplication> wpRootInfor){
		List<ApprovalForApplication>  lstWpRootSort = new ArrayList<>();
		List<ApprovalForApplication> lstCommon = wpRootInfor.stream()
					.filter(c -> c.getEmpRootAtr() == 0).collect(Collectors.toList());
		List<ApprovalForApplication> lstApp = wpRootInfor.stream()
				.filter(c -> c.getEmpRootAtr() == 1).collect(Collectors.toList());
		List<ApprovalForApplication> lstConfirm = wpRootInfor.stream()
				.filter(c -> c.getEmpRootAtr() == 2).collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(lstApp)) {
			Collections.sort(lstApp, Comparator.comparing(ApprovalForApplication:: getAppType));
		}
		if(!CollectionUtil.isEmpty(lstConfirm)) {
			Collections.sort(lstConfirm, Comparator.comparing(ApprovalForApplication:: getAppType));
		}
		lstWpRootSort.addAll(lstCommon);
		lstWpRootSort.addAll(lstApp);
		lstWpRootSort.addAll(lstConfirm);
		return lstWpRootSort;
	}
	/**
	 * get phase, approver of company
	 * @param approvalForApplication
	 * @param comRoot
	 * @param companyID
	 * @return
	 */
	private ApprovalForApplication getApproval(CompanyApprovalRoot comRoot, String companyID) {
		//find name
		String nameRoot = this.findNameRoot(comRoot.getEmploymentRootAtr(), comRoot.getApplicationType(), comRoot.getConfirmationRootType());
		//khoi tao
		ApprovalForApplication approvalForApp = new ApprovalForApplication(comRoot.getEmploymentRootAtr().value, comRoot.getEmploymentRootAtr().value == 0 ? null : 
			comRoot.getEmploymentRootAtr().value == 1 ? comRoot.getApplicationType().value : comRoot.getConfirmationRootType().value,
					nameRoot , null, null, null);
		approvalForApp.setStartDate(comRoot.getEmploymentAppHistoryItems().get(0).start());
		approvalForApp.setEndDate(comRoot.getEmploymentAppHistoryItems().get(0).end());
		
		List<ApprovalRootMaster> lstMatter = new ArrayList<>();
		//承認フェーズ, 承認者
		lstMatter = getPhaseApprover(companyID, comRoot.getBranchId(), comRoot.getEmploymentAppHistoryItems().get(0).start());		
		approvalForApp.setLstApproval(lstMatter);
		return approvalForApp;
	}
	private String findNameRoot(EmploymentRootAtr empRootAtr, ApplicationType appType, ConfirmationRootType confirmType){
		if(empRootAtr.equals(EmploymentRootAtr.COMMON)){
			return "共通ルート";
		}
		if(empRootAtr.equals(EmploymentRootAtr.APPLICATION)){
			return EnumAdaptor.valueOf(appType.value, ApplicationType.class).nameId;
		}
		if(empRootAtr.equals(EmploymentRootAtr.CONFIRMATION)){
			return EnumAdaptor.valueOf(confirmType.value, ConfirmationRootType.class).nameId;
		}
		return "";
	}
	
	private List<ApprovalRootMaster> getPhaseApprover(String companyID, String branchId, GeneralDate baseDate){
		List<ApprovalRootMaster> lstMatter = new ArrayList<>();
		//承認フェーズ, 承認者
		List<ApprovalPhase> getAllIncludeApprovers = phaseRepository.getAllIncludeApprovers(companyID, branchId);
		if(CollectionUtil.isEmpty(getAllIncludeApprovers)){
			return lstMatter;
		}
		for(ApprovalPhase phase: getAllIncludeApprovers) {
			List<String> lstApprovers = new ArrayList<>();
			List<Approver> lstApprover  = phase.getApprovers();
			if(!CollectionUtil.isEmpty(lstApprover)) {
				Collections.sort(lstApprover, Comparator.comparing(Approver:: getOrderNumber));
			}
			for(Approver approver: lstApprover) {
				//lstApprovers.add(psInfor.personName(approver.getEmployeeId()));
				if(approver.getApprovalAtr() == ApprovalAtr.PERSON){
					lstApprovers.add(psInfor.getPersonInfo(approver.getEmployeeId()).getEmployeeName());
				}else{
					lstApprovers.add(jobTitle.findJobTitleByPositionId(companyID, approver.getJobTitleId(), baseDate).getPositionName());
				}
			}
			ApprovalRootMaster appRoot = new ApprovalRootMaster(phase.getOrderNumber(), phase.getApprovalForm().name, lstApprovers);
			lstMatter.add(appRoot);
		}
		if(!CollectionUtil.isEmpty(lstMatter)) {
			Collections.sort(lstMatter, Comparator.comparing(ApprovalRootMaster:: getPhaseNumber));
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
		//CompanyApprovalInfor comMasterInfor = null;
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain 「会社別就業承認ルート」)
		List<CompanyApprovalRoot> lstComs = comRootRepository.findByBaseDate(companyID, baseDate);
		//Thong tin company
		Optional<CompanyInfor> comInfo = comAdapter.getCurrentCompany();
		//list root by COM
		List<ApprovalForApplication> comApproverRoots =  new ArrayList<>();
		if(CollectionUtil.isEmpty(lstComs)) {
			return null;
		}
		for (CompanyApprovalRoot comRoot : lstComs) {
			//TH don 36 bo qua
			if(comRoot.isApplication() && comRoot.getApplicationType().equals(ApplicationType.APPLICATION_36)){
				continue;
			}
			//convert data
			ApprovalForApplication comApprover = this.getApproval(comRoot, companyID);
			comApproverRoots.add(comApprover);
		}
		CompanyApprovalInfor comMasterInfor = new CompanyApprovalInfor(comInfo, this.sortByAppTypeConfirm(comApproverRoots));
		return comMasterInfor;
	}
}
