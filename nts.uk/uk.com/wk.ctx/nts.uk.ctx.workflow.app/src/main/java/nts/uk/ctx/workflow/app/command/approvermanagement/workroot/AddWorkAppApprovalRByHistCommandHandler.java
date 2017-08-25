package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class AddWorkAppApprovalRByHistCommandHandler extends CommandHandler<List<AddWorkAppApprovalRByHistCommand>>{

	@Inject
	private WorkAppApprovalRootRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<List<AddWorkAppApprovalRByHistCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<AddWorkAppApprovalRByHistCommand>  lstAddItem = context.getCommand();
		for (AddWorkAppApprovalRByHistCommand addItem : lstAddItem) {
			String historyId = UUID.randomUUID().toString();
			String approvalId = UUID.randomUUID().toString();
			String branchId = UUID.randomUUID().toString();
			String startDate = addItem.getStartDate();
			GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
			GeneralDate eDate = sDate.addDays(-1);
			String endDateNew = eDate.toString();
			String endDateS = "9999/12/31";
			GeneralDate endDate = GeneralDate.fromString(endDateS, "yyyy-MM-dd");
			//TH: company - doamin 会社別就業承認ルート
			if(addItem.getCheck()==1){
				CompanyApprovalRoot comAppRoot = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
						approvalId,
						historyId,
						addItem.getApplicationType(),
						addItem.getStartDate(),
						addItem.getEndDate(),
						branchId,
						addItem.getAnyItemApplicationId(),
						addItem.getConfirmationRootType(),
						addItem.getEmploymentRootAtr());
				//find history previous
				List<CompanyApprovalRoot> lstOld= repo.getComApprovalRootByEdate(companyId, endDate, addItem.getApplicationType());
				if(lstOld.isEmpty()){// history previous is not exist
					//copy/new
					repo.addComApprovalRoot(comAppRoot);
				}else{// history previous is exist
					CompanyApprovalRoot com = lstOld.get(0);
					//追加する履歴を最新の履歴の開始年月日と比較する
					if(!checkStartDate(com.getPeriod().getStartDate().toString(),addItem.getStartDate())){
						throw new BusinessException("Msg_153");
					}
					if(addItem.getCopyOrNew()==1){//copy
						CompanyApprovalRoot comAppRootNew = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								addItem.getEndDate(),
								com.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.addComApprovalRoot(comAppRootNew);
						//update history previous
						CompanyApprovalRoot comPre = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								endDateNew,
								com.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.updateComApprovalRoot(comPre);
						//copy data from history pre -> history new
						//get lst APhase of history pre
						List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, com.getBranchId());
						List<ApprovalPhase> lstAPhaseNew = new ArrayList<ApprovalPhase>();
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//get lst Approver
							List<Approver> lstApprover = repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
							List<Approver> lstApproverNew = new ArrayList<Approver>();
							for (Approver approver : lstApprover) {
								lstApproverNew.add(Approver.updateApprovalPhaseId(approver));
							}
							//update lst Approver New
							repo.addAllApprover(lstApproverNew);
							//convert
							lstAPhaseNew.add(ApprovalPhase.updateBranchId(approvalPhase, branchId));
						}
						//update lst APhase
						repo.addAllApprovalPhase(lstAPhaseNew);
					}else{//new
						repo.addComApprovalRoot(comAppRoot);
						//update history previous
						CompanyApprovalRoot comPre = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								endDateNew,
								com.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.updateComApprovalRoot(comPre);
					}
				}
			}
			//TH: workplace - domain 職場別就業承認ルート
			if(addItem.getCheck()==2){
				WorkplaceApprovalRoot wpAppRoot = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
						approvalId,
						addItem.getWorkplaceId(),
						historyId,
						addItem.getApplicationType(),
						addItem.getStartDate(),
						addItem.getEndDate(),
						branchId,
						addItem.getAnyItemApplicationId(),
						addItem.getConfirmationRootType(),
						addItem.getEmploymentRootAtr());
				//find history previous
				List<WorkplaceApprovalRoot> lstOld= repo.getWpApprovalRootByEdate(companyId, addItem.getWorkplaceId(), endDate, addItem.getApplicationType());
				if(lstOld.isEmpty()){// history previous is not exist
					//copy/new
					repo.addWpApprovalRoot(wpAppRoot);
				}else{// history previous is exist
					WorkplaceApprovalRoot wp = lstOld.get(0);
					//追加する履歴を最新の履歴の開始年月日と比較する
					if(!checkStartDate(wp.getPeriod().getStartDate().toString(),addItem.getStartDate())){
						throw new BusinessException("Msg_153");
					}
					if(addItem.getCopyOrNew()==1){//copy
						WorkplaceApprovalRoot wpAppRootNew = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								addItem.getWorkplaceId(),
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								addItem.getEndDate(),
								wp.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.addWpApprovalRoot(wpAppRootNew);
						//update history previous
						WorkplaceApprovalRoot wpPre = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								addItem.getWorkplaceId(),
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								endDateNew,
								wp.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.updateWpApprovalRoot(wpPre);
						//copy data from history pre -> history new
						//get lst APhase of history pre
						List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, wp.getBranchId());
						List<ApprovalPhase> lstAPhaseNew = new ArrayList<ApprovalPhase>();
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//get lst Approver
							List<Approver> lstApprover = repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
							List<Approver> lstApproverNew = new ArrayList<Approver>();
							for (Approver approver : lstApprover) {
								lstApproverNew.add(Approver.updateApprovalPhaseId(approver));
							}
							//update lst Approver New
							repo.addAllApprover(lstApproverNew);
							//convert
							lstAPhaseNew.add(ApprovalPhase.updateBranchId(approvalPhase, branchId));
						}
						//update lst APhase
						repo.addAllApprovalPhase(lstAPhaseNew);
					}else{//new
						repo.addWpApprovalRoot(wpAppRoot);
						//update history previous
						WorkplaceApprovalRoot wpPre = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								addItem.getWorkplaceId(),
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								endDateNew,
								wp.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.updateWpApprovalRoot(wpPre);
					}
				}
			}
			//TH: person - domain 個人別就業承認ルート
			if(addItem.getCheck()==3){
				PersonApprovalRoot psAppRoot = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						approvalId,
						addItem.getEmployeeId(),
						historyId,
						addItem.getApplicationType(),
						addItem.getStartDate(),
						addItem.getEndDate(),
						branchId,
						addItem.getAnyItemApplicationId(),
						addItem.getConfirmationRootType(),
						addItem.getEmploymentRootAtr());
				//find history previous
				List<PersonApprovalRoot> lstOld= repo.getPsApprovalRootByEdate(companyId, addItem.getEmployeeId(), endDate, addItem.getApplicationType());
				if(lstOld.isEmpty()){// history previous is not exist
					//copy/new
					repo.addPsApprovalRoot(psAppRoot);
				}else{// history previous is exist
					PersonApprovalRoot ps = lstOld.get(0);
					//追加する履歴を最新の履歴の開始年月日と比較する
					if(!checkStartDate(ps.getPeriod().getStartDate().toString(),addItem.getStartDate())){
						throw new BusinessException("Msg_153");
					}
					if(addItem.getCopyOrNew()==1){//copy
						PersonApprovalRoot psAppRootNew = PersonApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								addItem.getEmployeeId(),
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								addItem.getEndDate(),
								ps.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.addPsApprovalRoot(psAppRootNew);
						//update history previous
						PersonApprovalRoot psPre = PersonApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								addItem.getEmployeeId(),
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								endDateNew,
								ps.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.updatePsApprovalRoot(psPre);
						//copy data from history pre -> history new
						//get lst APhase of history pre
						List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, ps.getBranchId());
						List<ApprovalPhase> lstAPhaseNew = new ArrayList<ApprovalPhase>();
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//get lst Approver
							List<Approver> lstApprover = repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
							List<Approver> lstApproverNew = new ArrayList<Approver>();
							for (Approver approver : lstApprover) {
								lstApproverNew.add(Approver.updateApprovalPhaseId(approver));
							}
							//update lst Approver New
							repo.addAllApprover(lstApproverNew);
							//convert
							lstAPhaseNew.add(ApprovalPhase.updateBranchId(approvalPhase, branchId));
						}
						//update lst APhase
						repo.addAllApprovalPhase(lstAPhaseNew);
					}else{//new
						repo.addPsApprovalRoot(psAppRoot);
						//update history previous
						PersonApprovalRoot psPre = PersonApprovalRoot.createSimpleFromJavaType(companyId,
								approvalId,
								addItem.getEmployeeId(),
								historyId,
								addItem.getApplicationType(),
								addItem.getStartDate(),
								endDateNew,
								ps.getBranchId(),
								addItem.getAnyItemApplicationId(),
								addItem.getConfirmationRootType(),
								addItem.getEmploymentRootAtr());
						repo.updatePsApprovalRoot(psPre);
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
