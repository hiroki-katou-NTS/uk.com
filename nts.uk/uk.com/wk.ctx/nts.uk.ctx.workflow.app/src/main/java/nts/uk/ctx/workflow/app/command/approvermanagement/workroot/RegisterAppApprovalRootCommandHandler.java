package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterAppApprovalRootCommandHandler  extends CommandHandler<RegisterAppApprovalRootCommand>{

	@Inject
	private PersonApprovalRootRepository repoPerson;
	@Inject
	private CompanyApprovalRootRepository repoCom;
	@Inject
	private WorkplaceApprovalRootRepository repoWorkplace;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	@Override
	protected void handle(CommandHandlerContext<RegisterAppApprovalRootCommand> context) {
		String workpplaceId = context.getCommand().getWorkpplaceId(); 
		String employeeId = context.getCommand().getEmployeeId();
		//TH: Delete root
		if(context.getCommand().isCheckDelete()){//delete
			List<DataDeleteDto> lstDelete = context.getCommand().getLstDelete();
			delete(lstDelete,context.getCommand().getRootType(), workpplaceId, employeeId);
		}
		//TH: add history
		if(context.getCommand().isCheckAddHist()){
			List<AddHistoryDto> lstHist = context.getCommand().getLstAddHist();
			addHistory(lstHist, workpplaceId, employeeId);
			//TH: update AppPhase + Edit Approver
			if(context.getCommand().isCheckEdit()){
				
			}
		}
		//TH: add root
		if(context.getCommand().isCheckAddRoot()){
			
			
		}
		
	}
	private void delete(List<DataDeleteDto> lstDelete, int rootType, String workpplaceId, String employeeId){
		String companyId = AppContexts.user().companyId();
		//TH: company - domain 会社別就業承認ルート
		if(rootType == 0){
			for (DataDeleteDto dataDeleteDto : lstDelete) {
				//get root from db
				Optional<CompanyApprovalRoot> com = repoCom.getComApprovalRoot(companyId, dataDeleteDto.getApprovalId(), dataDeleteDto.getHistoryId());
				if(com.isPresent()){//exist
					CompanyApprovalRoot comRoot = com.get();
					//get appPhase
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, comRoot.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repoAppPhase.deleteAllAppPhaseByBranchId(companyId, comRoot.getBranchId());
					//delete root
					repoCom.deleteComApprovalRoot(companyId, dataDeleteDto.getApprovalId(), dataDeleteDto.getHistoryId());
				}
			}
		}
		//TH: workplace - domain 職場別就業承認ルート
		else if(rootType == 1){
			for (DataDeleteDto dataDeleteDto : lstDelete) {
				//get Work place root from db
				Optional<WorkplaceApprovalRoot> wp = repoWorkplace.getWpApprovalRoot(companyId, dataDeleteDto.getApprovalId(), workpplaceId, dataDeleteDto.getHistoryId());
				if(wp.isPresent()){//exist
					WorkplaceApprovalRoot wpRoot = wp.get();
					//get appPhase
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, wpRoot.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repoAppPhase.deleteAllAppPhaseByBranchId(companyId, wpRoot.getBranchId());
					//delete Work place root
					repoWorkplace.deleteWpApprovalRoot(companyId, dataDeleteDto.getApprovalId(), workpplaceId, dataDeleteDto.getHistoryId());
				}
			}
		}
		//TH: person - domain 個人別就業承認ルート
		else{
			for (DataDeleteDto dataDeleteDto : lstDelete) {
				//get person root from db
				Optional<PersonApprovalRoot> ps = repoPerson.getPsApprovalRoot(companyId, dataDeleteDto.getApprovalId(), employeeId, dataDeleteDto.getHistoryId());
				if(ps.isPresent()){//exist
					PersonApprovalRoot psRoot = ps.get();
					//get appPhase
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, psRoot.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repoAppPhase.deleteAllAppPhaseByBranchId(companyId, psRoot.getBranchId());
					//delete person root 
					repoPerson.deletePsApprovalRoot(companyId, dataDeleteDto.getApprovalId(), employeeId, dataDeleteDto.getHistoryId());
				}
			}
		}
	}
	/**
	 * add history
	 * @param context
	 */
	private void addHistory(List<AddHistoryDto> context, String workpplaceId, String employeeId) {
		String companyId = AppContexts.user().companyId();
		List<AddHistoryDto>  lstAddItem = context;
		for (AddHistoryDto addItem : lstAddItem) {
			String historyId = UUID.randomUUID().toString();
			String approvalId = UUID.randomUUID().toString();
			String branchId = UUID.randomUUID().toString();
			String startDate = addItem.getStartDate();
			GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
			GeneralDate eDate = sDate.addDays(-1);
			String endDateNew = eDate.toString();
			String endDateS = "9999/12/31";
			GeneralDate endDate = GeneralDate.fromString(endDateS, "yyyy-MM-dd");
			//TH: company - domain 会社別就業承認ルート
			if(addItem.getMode() == 0){
				//com root new
				CompanyApprovalRoot comAppRootNew = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
						approvalId, historyId,
						addItem.getApplicationType(),
						addItem.getStartDate(),
						endDateS, branchId, null, null,
						addItem.getApplicationType() == null ? 0 : 1);
				//find history previous
				List<CompanyApprovalRoot> lstOld= repoCom.getComApprovalRootByEdate(companyId, endDate, addItem.getApplicationType());
				//TH: history previous is not exist
				if(lstOld.isEmpty()){
					//copy/new
					repoCom.addComApprovalRoot(comAppRootNew);
				}
				//TH: history previous is exist
				else{
					CompanyApprovalRoot com = lstOld.get(0);
					//追加する履歴を最新の履歴の開始年月日と比較する
					if(!checkStartDate(com.getPeriod().getStartDate().toString(),addItem.getStartDate())){
						throw new BusinessException("Msg_153");
					}
					//update history previous
					CompanyApprovalRoot comPre = CompanyApprovalRoot.updateSdateEdate(com, com.getPeriod().getStartDate().toString(), endDateNew);
					//TH: copy
					if(addItem.isCopyDataFlag()){
						repoCom.addComApprovalRoot(comAppRootNew);
						repoCom.updateComApprovalRoot(comPre);
						//copy data from history pre -> history new
						//get lst APhase of history pre
						List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, com.getBranchId());
						List<ApprovalPhase> lstAPhaseNew = new ArrayList<ApprovalPhase>();
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//get lst Approver
							List<Approver> lstApprover = repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
							List<Approver> lstApproverNew = new ArrayList<Approver>();
							for (Approver approver : lstApprover) {
								lstApproverNew.add(Approver.updateApprovalPhaseId(approver));
							}
							//update lst Approver New
							repoApprover.addAllApprover(lstApproverNew);
							//convert
							approvalPhase.updateBranchId(branchId);
							lstAPhaseNew.add(approvalPhase);
						}
						//update lst APhase
						repoAppPhase.addAllApprovalPhase(lstAPhaseNew);
					}
					//TH: new
					else{
						repoCom.addComApprovalRoot(comAppRootNew);
						repoCom.updateComApprovalRoot(comPre);
					}
				}
			}
			//TH: workplace - domain 職場別就業承認ルート
			if(addItem.getMode() == 1){
				WorkplaceApprovalRoot wpAppRootNew = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
						approvalId, workpplaceId, historyId,
						addItem.getApplicationType(),
						addItem.getStartDate(),
						endDateS, branchId, null, null,
						addItem.getApplicationType() == null ? 0 : 1);
				//find history previous
				List<WorkplaceApprovalRoot> lstOld= repoWorkplace.getWpApprovalRootByEdate(companyId, workpplaceId, endDate, addItem.getApplicationType());
				if(lstOld.isEmpty()){// history previous is not exist
					//copy/new
					repoWorkplace.addWpApprovalRoot(wpAppRootNew);
				}else{// history previous is exist
					WorkplaceApprovalRoot wp = lstOld.get(0);
					//追加する履歴を最新の履歴の開始年月日と比較する
					if(!checkStartDate(wp.getPeriod().getStartDate().toString(),addItem.getStartDate())){
						throw new BusinessException("Msg_153");
					}
					//update history previous
					WorkplaceApprovalRoot wpPre = WorkplaceApprovalRoot.updateSdateEdate(wp, wp.getPeriod().getStartDate().toString(), endDateNew);
					//TH: copy
					if(addItem.isCopyDataFlag()){
						repoWorkplace.addWpApprovalRoot(wpAppRootNew);
						repoWorkplace.updateWpApprovalRoot(wpPre);
						//copy data from history pre -> history new
						//get lst APhase of history pre
						List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, wp.getBranchId());
						List<ApprovalPhase> lstAPhaseNew = new ArrayList<ApprovalPhase>();
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//get lst Approver
							List<Approver> lstApprover = repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
							List<Approver> lstApproverNew = new ArrayList<Approver>();
							for (Approver approver : lstApprover) {
								lstApproverNew.add(Approver.updateApprovalPhaseId(approver));
							}
							//update lst Approver New
							repoApprover.addAllApprover(lstApproverNew);
							//convert
							approvalPhase.updateBranchId(branchId);
							lstAPhaseNew.add(approvalPhase);
						}
						//update lst APhase
						repoAppPhase.addAllApprovalPhase(lstAPhaseNew);
					}
					//TH: new
					else{
						repoWorkplace.addWpApprovalRoot(wpAppRootNew);
						repoWorkplace.updateWpApprovalRoot(wpPre);
					}
				}
			}
			//TH: person - domain 個人別就業承認ルート
			if(addItem.getMode() == 2){
				PersonApprovalRoot psAppRootNew = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						approvalId, employeeId, historyId,
						addItem.getApplicationType(),
						addItem.getStartDate(),
						endDateS, branchId, null, null,
						addItem.getApplicationType() == null ? 0 : 1);
				//find history previous
				List<PersonApprovalRoot> lstOld= repoPerson.getPsApprovalRootByEdate(companyId, employeeId, endDate, addItem.getApplicationType());
				if(lstOld.isEmpty()){// history previous is not exist
					//copy/new
					repoPerson.addPsApprovalRoot(psAppRootNew);
				}else{// history previous is exist
					PersonApprovalRoot ps = lstOld.get(0);
					//追加する履歴を最新の履歴の開始年月日と比較する
					if(!checkStartDate(ps.getPeriod().getStartDate().toString(),addItem.getStartDate())){
						throw new BusinessException("Msg_153");
					}
					//update history previous
					PersonApprovalRoot psPre = PersonApprovalRoot.updateSdateEdate(ps, ps.getPeriod().getStartDate().toString(), endDateNew);
					//TH: copy
					if(addItem.isCopyDataFlag()){
						repoPerson.addPsApprovalRoot(psAppRootNew);
						repoPerson.updatePsApprovalRoot(psPre);
						//copy data from history pre -> history new
						//get lst APhase of history pre
						List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, ps.getBranchId());
						List<ApprovalPhase> lstAPhaseNew = new ArrayList<ApprovalPhase>();
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//get lst Approver
							List<Approver> lstApprover = repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
							List<Approver> lstApproverNew = new ArrayList<Approver>();
							for (Approver approver : lstApprover) {
								lstApproverNew.add(Approver.updateApprovalPhaseId(approver));
							}
							//update lst Approver New
							repoApprover.addAllApprover(lstApproverNew);
							//convert
							approvalPhase.updateBranchId(branchId);
							lstAPhaseNew.add(approvalPhase);
						}
						//update lst APhase
						repoAppPhase.addAllApprovalPhase(lstAPhaseNew);
					}else{//new
						repoPerson.addPsApprovalRoot(psAppRootNew);
						repoPerson.updatePsApprovalRoot(psPre);
					}
				}
			}
		}
	}
	/**
	 * check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
	 * @param sDatePre
	 * @param sDateCur
	 * @return
	 */
	public boolean checkStartDate(String sDatePre, String sDateCur){
		if(sDateCur.compareTo(sDatePre)>0){
			return true;
		}
		return false;
	}
}
