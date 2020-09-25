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
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;
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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.ApprovalRootCommonService;
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
	private ApprovalPhaseRepository phaseRepository;
	@Inject
	private PersonAdapter psInfor;
	@Inject
	private ApprovalRootCommonService appRootCm;
	
	@Override
	public MasterApproverRootOutput masterInfors(String companyID, int sysAtr, GeneralDate baseDate, 
			boolean isCompany, boolean isWorkplace, boolean isPerson, List<AppTypeName> lstName) {
		CompanyApprovalInfor comMasterInfor = null;		
		MasterWkpOutput wkpRootOutput = new MasterWkpOutput(new HashMap<>(), new ArrayList<>());
		MasterEmployeeOutput empRootOutput = new MasterEmployeeOutput(new HashMap<>(), new ArrayList<>());
		List<Integer> lstNotice = new ArrayList<>();
		List<String> lstEvent = new ArrayList<>();
		if(sysAtr == SystemAtr.HUMAN_RESOURCES.value) {
			lstNotice = this.lstNotice(lstName);
			lstEvent = this.lstEvent(lstName);
		}
		//出力対象に会社別がある(có 会社別 trong đối tượng output)
		if(isCompany) {//Lay data COMPANY
			comMasterInfor = this.getComApprovalInfor(companyID, baseDate, sysAtr, lstName, lstNotice, lstEvent);
		}
		//出力対象に職場別がある(có 職場別 trong đối tượng output)
		if(isWorkplace) {//Lay data WORKPLACE
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
			List<WorkplaceApprovalRoot> lstWps = new ArrayList<>();
			if(sysAtr == SystemAtr.WORK.value) {
				lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate, sysAtr);
			}else {
				lstWps = wpRootRepository.findByBaseDateJinji(companyID, baseDate, lstNotice, lstEvent);
			}
			
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstWps)) {				
				wkpRootOutput = this.getWpApproverInfor(lstWps, companyID, baseDate, sysAtr, lstName);				
			}
		}		
		//出力対象に個人別がある(có 個人別 trong đối tượng output)
		if(isPerson) {//Lay data PERSON
			//ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
			List<PersonApprovalRoot> lstPss = new ArrayList<>();
			if(sysAtr == SystemAtr.WORK.value) {
				lstPss = psRootRepository.findAllByBaseDate(companyID, baseDate, sysAtr);
			}else {
				lstPss = psRootRepository.findByBaseDateJinji(companyID, baseDate, lstNotice, lstEvent);
			}
			//データが１件以上取得した場合(có 1 data trở lên)
			if(!CollectionUtil.isEmpty(lstPss)) {
				empRootOutput = this.getPsRootInfor(lstPss, companyID, lstName);
			}
		}
		return new MasterApproverRootOutput(comMasterInfor, wkpRootOutput, empRootOutput);
	}
	
	private List<Integer> lstNotice(List<AppTypeName> lstName){
		List<Integer> lstResult = new ArrayList<>();
		for(AppTypeName app : lstName) {
			if(app.getEmpRAtr() == EmploymentRootAtr.NOTICE.value) {
				lstResult.add(Integer.valueOf(app.getValue()));
			}
		}
		return lstResult;
	}
	private List<String> lstEvent(List<AppTypeName> lstName){
		List<String> lstResult = new ArrayList<>();
		for(AppTypeName app : lstName) {
			if(app.getEmpRAtr() == EmploymentRootAtr.BUS_EVENT.value) {
				lstResult.add(app.getValue());
			}
		}
		return lstResult;
	}
	/**
	 *  get all approval of employee
	 * @param lstPss
	 * @param companyID
	 * @return
	 */
	private MasterEmployeeOutput getPsRootInfor(List<PersonApprovalRoot> lstPss, String companyID, List<AppTypeName> lstName){
		Map<String, PersonApproverOutput> mapPsRootInfor = new HashMap<>();
		List<EmployeeApproverOutput> lstEmployeeInfo = new ArrayList<>();
		for(PersonApprovalRoot root: lstPss) {
			List<ApprovalForApplication> psWootInfor = new ArrayList<>();
			ApprovalRootCommonOutput psRoot = new ApprovalRootCommonOutput(root.getCompanyId(),
					root.getApprovalId(), root.getEmployeeId(),
					"", 
					root.getApprRoot().getHistoryItems().get(0).getHistoryId(),
					root.getApprRoot().getApplicationType() == null ? 0: root.getApprRoot().getApplicationType().value,
					root.getApprRoot().getHistoryItems().get(0).start(), 
					root.getApprRoot().getHistoryItems().get(0).end(), 
					root.getApprRoot().getBranchId(), 
					root.getApprRoot().getAnyItemApplicationId(),
					root.getApprRoot().getConfirmationRootType() == null ? 0: root.getApprRoot().getConfirmationRootType().value,
					root.getApprRoot().getEmploymentRootAtr().value,
					root.getApprRoot().getNoticeId(), 
					root.getApprRoot().getBusEventId()); 
			//Neu da co person roi
			if(!mapPsRootInfor.isEmpty() && mapPsRootInfor.containsKey(root.getEmployeeId())) {
				PersonApproverOutput psApp = mapPsRootInfor.get(root.getEmployeeId());
				psWootInfor = psApp.getPsRootInfo();
				psWootInfor = this.getAppInfors(psRoot, psWootInfor, companyID, lstName);
				continue;
			}
			//ドメインモデル「社員」を取得する(lấy dữ liệu domain「社員」)		
			PersonImport psInfos = psInfor.getPersonInfo(root.getEmployeeId());
			EmployeeApproverOutput empInfor = new EmployeeApproverOutput(psInfos.getSID(), psInfos.getEmployeeCode(), psInfos.getEmployeeName()); 
			psWootInfor = this.getAppInfors(psRoot, psWootInfor, companyID, lstName);
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
	private MasterWkpOutput getWpApproverInfor(List<WorkplaceApprovalRoot> lstWps, String companyID,
			GeneralDate baseDate, int sysAtr, List<AppTypeName> lstName){
		Map<String, WorkplaceApproverOutput> mapWpRootInfor =  new HashMap<>();
		List<WkpDepInfo> lstWpInfor = new ArrayList<>();
		for(WorkplaceApprovalRoot root: lstWps) {//loop theo wkp
			List<ApprovalForApplication> wpRootInfor = new ArrayList<>();
			ApprovalRootCommonOutput wpRoot = new ApprovalRootCommonOutput(root.getCompanyId(),
					root.getApprovalId(), 
					"",
					root.getWorkplaceId(), 
					root.getApprRoot().getHistoryItems().get(0).getHistoryId(),
					root.getApprRoot().getApplicationType() == null ? 0:  root.getApprRoot().getApplicationType().value,
					root.getApprRoot().getHistoryItems().get(0).start(), 
					root.getApprRoot().getHistoryItems().get(0).end(), 
					root.getApprRoot().getBranchId(), 
					root.getApprRoot().getAnyItemApplicationId(),
					root.getApprRoot().getConfirmationRootType()  == null ? 0:  root.getApprRoot().getConfirmationRootType().value,
					root.getApprRoot().getEmploymentRootAtr().value,
					root.getApprRoot().getNoticeId(), 
					root.getApprRoot().getBusEventId());
			//Neu da co workplace roi
			if(!mapWpRootInfor.isEmpty() && mapWpRootInfor.containsKey(root.getWorkplaceId())) {
				WorkplaceApproverOutput wpApp = mapWpRootInfor.get(root.getWorkplaceId());						
				wpRootInfor = wpApp.getWpRootInfor();
				wpRootInfor = this.getAppInfors(wpRoot, wpRootInfor, companyID, lstName);
				continue;
			}
			//Lay thong tin detail cua workplace
			//ドメインモデル「職場」を取得する(lấy dữ liệu domain 「職場NEW」)
			WkpDepInfo wkpInf = appRootCm.getWkpDepInfo(root.getWorkplaceId(), sysAtr);
			wpRootInfor = this.getAppInfors(wpRoot, wpRootInfor, companyID, lstName);
			
			// fix data
			WorkplaceApproverOutput wpOutput = new WorkplaceApproverOutput(wkpInf, wpRootInfor);
			mapWpRootInfor.put(root.getWorkplaceId(), wpOutput);
			lstWpInfor.add(wkpInf);
		}
		//QA#100181
		for (Map.Entry<String, WorkplaceApproverOutput> entry : mapWpRootInfor.entrySet()){
			WorkplaceApproverOutput wkp = entry.getValue();
			List<ApprovalForApplication> wpRootSort = this.sortByAppTypeConfirm(wkp.getWpRootInfor());
			wkp.setWpRootInfor(wpRootSort);
			entry.setValue(wkp);
		}
		Collections.sort(lstWpInfor, Comparator.comparing(WkpDepInfo:: getCode));
		return new MasterWkpOutput(mapWpRootInfor, lstWpInfor);
	}
	
	private List<ApprovalForApplication> getAppInfors(ApprovalRootCommonOutput root,
			List<ApprovalForApplication> wpRootInfor, String companyID, List<AppTypeName> lstName){
		String typeV = this.convertType(root.getEmploymentRootAtr(), root.getAppType(), root.getConfType(), 
				root.getNoticeId(), root.getEventId());
		String appName = this.findNameRoot(root.getEmploymentRootAtr(), root.getAppType(), root.getConfType(), 
				root.getNoticeId(), root.getEventId(), lstName);
				
		List<ApprovalRootMaster> lstAppInfo = new ArrayList<>();
		//承認フェーズ, 承認者
		lstAppInfo = getPhaseApprover(companyID, root.getApprovalId(), root.getStartDate());
		ApprovalForApplication wpAppInfo = new ApprovalForApplication(root.getEmploymentRootAtr(), typeV, appName, root.getStartDate(), root.getEndDate(), lstAppInfo);
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
		List<ApprovalForApplication> lstNotice = wpRootInfor.stream()
				.filter(c -> c.getEmpRootAtr() == 4).collect(Collectors.toList());
		List<ApprovalForApplication> lstEvent = wpRootInfor.stream()
				.filter(c -> c.getEmpRootAtr() == 5).collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(lstApp)) {
			lstApp.sort((a, b) -> {
				return Integer.valueOf(a.getAppType()).compareTo(Integer.valueOf(b.getAppType()));
			});
		}
		if(!CollectionUtil.isEmpty(lstConfirm)) {
			Collections.sort(lstConfirm, Comparator.comparing(ApprovalForApplication:: getAppType));
		}
		if(!CollectionUtil.isEmpty(lstNotice)) {
			lstNotice.sort((a, b) -> {
				return Integer.valueOf(a.getAppType()).compareTo(Integer.valueOf(b.getAppType()));
			});
		}
		if(!CollectionUtil.isEmpty(lstEvent)) {
			Collections.sort(lstEvent, Comparator.comparing(ApprovalForApplication:: getAppType));
		}
		lstWpRootSort.addAll(lstCommon);
		lstWpRootSort.addAll(lstApp);
		lstWpRootSort.addAll(lstConfirm);
		lstWpRootSort.addAll(lstNotice);
		lstWpRootSort.addAll(lstEvent);
		return lstWpRootSort;
	}
	/**
	 * get phase, approver of company
	 * @param approvalForApplication
	 * @param comRoot
	 * @param companyID
	 * @return
	 */
	private ApprovalForApplication getApproval(CompanyApprovalRoot comRoot, String companyID, List<AppTypeName> lstName) {
		//find name
		
		int empR = comRoot.getApprRoot().getEmploymentRootAtr().value;
		Integer appType = empR == 1 ? comRoot.getApprRoot().getApplicationType().value : null;
		Integer confType = empR == 2 ? comRoot.getApprRoot().getConfirmationRootType().value : null;
		String typeV = this.convertType(empR, appType, confType, comRoot.getApprRoot().getNoticeId(), 
				comRoot.getApprRoot().getBusEventId());
		String nameRoot = this.findNameRoot(empR, appType, confType, comRoot.getApprRoot().getNoticeId(), 
				comRoot.getApprRoot().getBusEventId(), lstName);
		//khoi tao
		ApprovalForApplication approvalForApp = new ApprovalForApplication(empR, 
				typeV, nameRoot , null, null, null);
		approvalForApp.setStartDate(comRoot.getApprRoot().getHistoryItems().get(0).start());
		approvalForApp.setEndDate(comRoot.getApprRoot().getHistoryItems().get(0).end());
		
		List<ApprovalRootMaster> lstMatter = new ArrayList<>();
		//承認フェーズ, 承認者
		lstMatter = getPhaseApprover(companyID, comRoot.getApprovalId(), comRoot.getApprRoot().getHistoryItems().get(0).start());		
		approvalForApp.setLstApproval(lstMatter);
		return approvalForApp;
	}
	private String convertType(int empR, Integer appType, Integer confirm, Integer notice, String event) {
            switch(empR){
                case 0:
                    return null;
                case 1:
                    return appType.toString();
                case 2:
                    return confirm.toString();
                case 4:
                    return notice.toString();
                default://BUS_EVENT 5
                    return event;
            }
	}
	
	private String findNameRoot(int empR, Integer appType, Integer confirm, Integer notice, String event, 
			List<AppTypeName> lstName){
		switch(empR){
	        case 0:
	        	return "共通ルート";
	        case 1:
	        	return EnumAdaptor.valueOf(appType, ApplicationType.class).nameId;
	        case 2:
	        	return EnumAdaptor.valueOf(confirm, ConfirmationRootType.class).nameId;
	        case 4:
	            return this.findName(notice.toString(), empR, lstName);
	        default://BUS_EVENT 5
	            return this.findName(event, empR, lstName);
	    }
	}
	private String findName(String value, int empRAtr, List<AppTypeName> lstName) {
			for(AppTypeName amp: lstName) {
				if(amp.getEmpRAtr() == empRAtr && amp.getValue().equals(value)) {
					return amp.getName();
				}
			}
			return "コード削除済";
	}
	/**
	 * フェーズ承認者取得
	 * @return
	 */
	private List<ApprovalRootMaster> getPhaseApprover(String companyID, String approvalId, GeneralDate baseDate){
		List<ApprovalRootMaster> lstMatter = new ArrayList<>();
		//承認フェーズ, 承認者
		List<ApprovalPhase> getAllIncludeApprovers = phaseRepository.getAllIncludeApprovers(approvalId);
		if(CollectionUtil.isEmpty(getAllIncludeApprovers)){
			return lstMatter;
		}
		for(ApprovalPhase phase: getAllIncludeApprovers) {
			List<String> lstApprovers = new ArrayList<>();
			List<Approver> lstApprover  = phase.getApprovers();
			if(!CollectionUtil.isEmpty(lstApprover)) {
				Collections.sort(lstApprover, Comparator.comparing(Approver:: getApproverOrder));
			}
			ApprovalAtr appAtr = phase.getApprovalAtr();
			for(Approver approver: lstApprover) {
				//lstApprovers.add(psInfor.personName(approver.getEmployeeId()));
				if(appAtr.equals(ApprovalAtr.PERSON)){
					lstApprovers.add(psInfor.getPersonInfo(approver.getEmployeeId()).getEmployeeName());
				}else{
					lstApprovers.add(appRootCm.getJobGInfo(approver.getJobGCD()).getName());
				}
			}
			ApprovalRootMaster appRoot = new ApprovalRootMaster(phase.getPhaseOrder(), phase.getApprovalForm().name, lstApprovers);
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
	private CompanyApprovalInfor getComApprovalInfor(String companyID, GeneralDate baseDate, int sysAtr,
			List<AppTypeName> lstName, List<Integer> lstNotice, List<String> lstEvent) {
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy thông tin domain 「会社別就業承認ルート」)
		List<CompanyApprovalRoot> lstComs = new ArrayList<>();
		if(sysAtr == SystemAtr.WORK.value) {
			lstComs = comRootRepository.findByBaseDate(companyID, baseDate, sysAtr);
		}else {
			lstComs = comRootRepository.findByBaseDateJinji(companyID, baseDate, lstNotice, lstEvent);
		}
		//Thong tin company
		Optional<CompanyInfor> comInfo = comAdapter.getCurrentCompany();
		//list root by COM
		List<ApprovalForApplication> comApproverRoots =  new ArrayList<>();
		if(CollectionUtil.isEmpty(lstComs)) {
			return null;
		}
		for (CompanyApprovalRoot comRoot : lstComs) {
			//convert data
			ApprovalForApplication comApprover = this.getApproval(comRoot, companyID, lstName);
			comApproverRoots.add(comApprover);
		}
		CompanyApprovalInfor comMasterInfor = new CompanyApprovalInfor(comInfo, this.sortByAppTypeConfirm(comApproverRoots));
		return comMasterInfor;
	}
}
