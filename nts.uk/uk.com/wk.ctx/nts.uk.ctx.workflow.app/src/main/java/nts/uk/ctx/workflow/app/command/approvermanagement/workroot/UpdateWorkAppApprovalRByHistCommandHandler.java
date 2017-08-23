package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
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
public class UpdateWorkAppApprovalRByHistCommandHandler extends CommandHandler<UpdateWorkAppApprovalRByHistCommand>{
	@Inject
	private WorkAppApprovalRootRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateWorkAppApprovalRByHistCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateWorkAppApprovalRByHistCommand  updateItem = context.getCommand();
		//history current
		String startDate = updateItem.getStartDate();
		GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
		GeneralDate eDate = sDate.addDays(-1);
		String endDateUpdate = eDate.toString();//Edate: edit
		//history previous
		String startDatePrevious = updateItem.getSDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);
		String endDatePrevious  = eDatePrevious.toString();//Edate to find history Previous 
		String endDateDelete = "9999/12/31";//Edate: delete
		//TH: company - domain 会社別就業承認ルート
		if(updateItem.getCheck()==1){
			CompanyApprovalRoot comAppRoot = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
					updateItem.getHistoryId(),
					updateItem.getApplicationType(),
					updateItem.getStartDate(),
					updateItem.getEndDate(),
					updateItem.getBranchId(),
					updateItem.getAnyItemApplicationId(),
					updateItem.getConfirmationRootType(),
					updateItem.getEmploymentRootAtr());
			//find history previous
			List<CompanyApprovalRoot> lstOld= repo.getComApprovalRootByEdate(companyId, endDatePrevious);
			if(lstOld.isEmpty()){// history previous is not exist
				if(updateItem.getEditOrDelete()==1){//TH: edit
					repo.updateComApprovalRoot(comAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, updateItem.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repo.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repo.deleteAllAppPhaseByBranchId(companyId, updateItem.getBranchId());
					//delete ComApprovalRoot
					repo.deleteComApprovalRoot(companyId, updateItem.getHistoryId());
				}
			}else{// history previous is exist
				CompanyApprovalRoot com = lstOld.get(0);
				//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
				if(!checkStartDate(com.getPeriod().getStartDate().toString(),updateItem.getStartDate())){
					throw new BusinessException("Msg_156");
				}
				if(updateItem.getEditOrDelete()==1){//edit
					//history previous 
					CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.createSimpleFromJavaType(companyId,
							com.getHistoryId(),
							com.getApplicationType().value.intValue(),
							com.getPeriod().getStartDate().toString(),
							endDateUpdate,
							com.getBranchId(),
							com.getAnyItemApplicationId(),
							com.getConfirmationRootType().value.intValue(),
							com.getEmploymentRootAtr().value);
					//update history previous
					repo.updateComApprovalRoot(comAppRootUpdate);
					//update history current
					repo.updateComApprovalRoot(comAppRoot);
				}else{//delete 
					CompanyApprovalRoot comAppRootUpdate = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
							com.getHistoryId(),
							com.getApplicationType().value.intValue(),
							com.getPeriod().getStartDate().toString(),
							endDateDelete,
							com.getBranchId(),
							com.getAnyItemApplicationId(),
							com.getConfirmationRootType().value.intValue(),
							com.getEmploymentRootAtr().value);
					//update history previous
					repo.updateComApprovalRoot(comAppRootUpdate);
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, updateItem.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repo.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repo.deleteAllAppPhaseByBranchId(companyId, updateItem.getBranchId());
					//delete history current
					repo.deleteComApprovalRoot(companyId, updateItem.getHistoryId());
				}
			}
		}
		//TH: workplace - domain 職場別就業承認ルート
		if(updateItem.getCheck()==2){
			WorkplaceApprovalRoot wpAppRoot = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
					updateItem.getWorkplaceId(),
					updateItem.getHistoryId(),
					updateItem.getApplicationType(),
					updateItem.getStartDate(),
					updateItem.getEndDate(),
					updateItem.getBranchId(),
					updateItem.getAnyItemApplicationId(),
					updateItem.getConfirmationRootType(),
					updateItem.getEmploymentRootAtr());
			//find history previous
			List<WorkplaceApprovalRoot> lstOld= repo.getWpApprovalRootByEdate(companyId, updateItem.getWorkplaceId(), endDatePrevious);
			if(lstOld.isEmpty()){// history previous is not exist
				if(updateItem.getEditOrDelete()==1){//TH: edit
					repo.updateWpApprovalRoot(wpAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, updateItem.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repo.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repo.deleteAllAppPhaseByBranchId(companyId, updateItem.getBranchId());
					//delete WpApprovalRoot
					repo.deleteWpApprovalRoot(companyId, updateItem.getWorkplaceId(), updateItem.getHistoryId());
				}
			}else{// history previous is exist
				WorkplaceApprovalRoot wp = lstOld.get(0);
				//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
				if(!checkStartDate(wp.getPeriod().getStartDate().toString(),updateItem.getStartDate())){
					throw new BusinessException("Msg_156");
				}
				if(updateItem.getEditOrDelete()==1){//edit
					//history previous 
					WorkplaceApprovalRoot wpAppRootUpdate= WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
							wp.getWorkplaceId(),
							wp.getHistoryId(),
							wp.getApplicationType().value.intValue(),
							wp.getPeriod().getStartDate().toString(),
							endDateUpdate,
							wp.getBranchId(),
							wp.getAnyItemApplicationId(),
							wp.getConfirmationRootType().value.intValue(),
							wp.getEmploymentRootAtr().value);
					//update history previous
					repo.updateWpApprovalRoot(wpAppRootUpdate);
					//update history current
					repo.updateWpApprovalRoot(wpAppRoot);
				}else{//delete 
					WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
							wp.getWorkplaceId(),
							wp.getHistoryId(),
							wp.getApplicationType().value.intValue(),
							wp.getPeriod().getStartDate().toString(),
							endDateDelete,
							wp.getBranchId(),
							wp.getAnyItemApplicationId(),
							wp.getConfirmationRootType().value.intValue(),
							wp.getEmploymentRootAtr().value);
					//update history previous
					repo.updateWpApprovalRoot(wpAppRootUpdate);
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, updateItem.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repo.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repo.deleteAllAppPhaseByBranchId(companyId, updateItem.getBranchId());
					//delete history current
					repo.deleteWpApprovalRoot(companyId, updateItem.getWorkplaceId(), updateItem.getHistoryId());
				}
			}
		}
		//TH: person - domain 個人別就業承認ルート
		if(updateItem.getCheck()==3){
			PersonApprovalRoot psAppRoot = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					updateItem.getEmployeeId(),
					updateItem.getHistoryId(),
					updateItem.getApplicationType(),
					updateItem.getStartDate(),
					updateItem.getEndDate(),
					updateItem.getBranchId(),
					updateItem.getAnyItemApplicationId(),
					updateItem.getConfirmationRootType(),
					updateItem.getEmploymentRootAtr());
			//find history previous
			List<PersonApprovalRoot> lstOld= repo.getPsApprovalRootByEdate(companyId, updateItem.getEmployeeId(),  endDatePrevious);
			if(lstOld.isEmpty()){// history previous is not exist
				if(updateItem.getEditOrDelete()==1){//TH: edit
					repo.updatePsApprovalRoot(psAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, updateItem.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repo.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repo.deleteAllAppPhaseByBranchId(companyId, updateItem.getBranchId());
					//delete PsApprovalRoot
					repo.deletePsApprovalRoot(companyId, updateItem.getEmployeeId(), updateItem.getHistoryId());
				}
			}else{// history previous is exist
				PersonApprovalRoot ps = lstOld.get(0);
				//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
				if(!checkStartDate(ps.getPeriod().getStartDate().toString(),updateItem.getStartDate())){
					throw new BusinessException("Msg_156");
				}
				if(updateItem.getEditOrDelete()==1){//edit
					//history previous 
					PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.createSimpleFromJavaType(companyId,
							ps.getEmployeeId(),
							ps.getHistoryId(),
							ps.getApplicationType().value.intValue(),
							ps.getPeriod().getStartDate().toString(),
							endDateUpdate,
							ps.getBranchId(),
							ps.getAnyItemApplicationId(),
							ps.getConfirmationRootType().value.intValue(),
							ps.getEmploymentRootAtr().value);
					//update history previous
					repo.updatePsApprovalRoot(psAppRootUpdate);
					//update history current
					repo.updatePsApprovalRoot(psAppRoot);
				}else{//delete 
					PersonApprovalRoot psAppRootUpdate = PersonApprovalRoot.createSimpleFromJavaType(companyId,
							ps.getEmployeeId(),
							ps.getHistoryId(),
							ps.getApplicationType().value.intValue(),
							ps.getPeriod().getStartDate().toString(),
							endDateDelete,
							ps.getBranchId(),
							ps.getAnyItemApplicationId(),
							ps.getConfirmationRootType().value.intValue(),
							ps.getEmploymentRootAtr().value);
					//update history previous
					repo.updatePsApprovalRoot(psAppRootUpdate);
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repo.getAllApprovalPhasebyCode(companyId, updateItem.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repo.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repo.deleteAllAppPhaseByBranchId(companyId, updateItem.getBranchId());
					//delete history current
					repo.deletePsApprovalRoot(companyId, updateItem.getEmployeeId(),  updateItem.getHistoryId());
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
