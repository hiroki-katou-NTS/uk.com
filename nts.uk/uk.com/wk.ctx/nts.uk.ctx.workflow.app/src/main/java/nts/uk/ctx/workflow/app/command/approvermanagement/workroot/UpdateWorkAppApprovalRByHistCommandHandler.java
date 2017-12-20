package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class UpdateWorkAppApprovalRByHistCommandHandler extends CommandHandler<UpdateWorkAppApprovalRByHistCommand>{
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
	private final int COMPANY = 0;
	private final int WORKPLACE = 1;
	private final int EDIT = 1;
	@Override
	protected void handle(CommandHandlerContext<UpdateWorkAppApprovalRByHistCommand> context) {
		UpdateWorkAppApprovalRByHistCommand  objUpdateItem = context.getCommand();
		//TH: company - domain 会社別就業承認ルート
		if(objUpdateItem.getCheck() == COMPANY){
			this.updateHistoryCom(objUpdateItem);
		}
		//TH: work place - domain 職場別就業承認ルート
		else if(objUpdateItem.getCheck() == WORKPLACE){
			this.updateHistoryWorkplace(objUpdateItem);
		}
		//TH: person - domain 個人別就業承認ルート
		else{
			this.updateHistoryPerson(objUpdateItem);
		}
	}
	/**
	 * edit history
	 * domain 会社別就業承認ルート (Company)
	 * @param objUpdateItem
	 */
	private void updateHistoryCom(UpdateWorkAppApprovalRByHistCommand  objUpdateItem){
		String companyId = AppContexts.user().companyId();
		List<UpdateHistoryDto> lstHist = objUpdateItem.getLstUpdate();
		//history current
		String startDate = objUpdateItem.getStartDate();
		GeneralDate sDate = GeneralDate.fromString(startDate, "yyyy/MM/dd");
		GeneralDate eDate = sDate.addDays(-1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String endDateUpdate = eDate.localDate().format(formatter);//Edate: edit
		String endDateDelete = "9999-12-31";//Edate: delete
		//history previous
		String startDatePrevious = objUpdateItem.getStartDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious.replace("/","-")));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);//Edate to find history Previous
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and EmployRootAtr
			List<CompanyApprovalRoot> lstComByApp = repoCom.getComApprovalRootByType(companyId, updateItem.getApplicationType(), updateItem.getEmployRootAtr());
			Optional<CompanyApprovalRoot> comAppRootDb = repoCom.getComApprovalRoot(companyId, updateItem.getApprovalId(), updateItem.getHistoryId());
			if(!comAppRootDb.isPresent()){
				continue;
			}
			//item update (New)
			CompanyApprovalRoot comAppRoot = CompanyApprovalRoot.updateSdate(comAppRootDb.get(), startDate.replace("/","-"));
			if(lstComByApp.size() < 2){// history previous is not exist
				if(objUpdateItem.getEditOrDelete()==1){//TH: edit
					repoCom.updateComApprovalRoot(comAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, comAppRoot.getBranchId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
					}
					//delete All Approval Phase By Branch Id
					repoAppPhase.deleteAllAppPhaseByBranchId(companyId, comAppRoot.getBranchId());
					//delete ComApprovalRoot
					repoCom.deleteComApprovalRoot(companyId, updateItem.getApprovalId(), updateItem.getHistoryId());
				}
			}else{// history previous is exist
				if(objUpdateItem.getEditOrDelete( )== EDIT){//edit
					CompanyApprovalRoot com = lstComByApp.get(1);
					String sDatePre = com.getEmploymentAppHistoryItems().get(0).start().toString();
					//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
					if(startDate.compareTo(sDatePre) <= 0){
						//エラーメッセージ(Msg_156)(error message (Msg_156))
						throw new BusinessException("Msg_156");
					}
					//history previous 
					CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.updateEdate(com, endDateUpdate);
					//update history previous
					repoCom.updateComApprovalRoot(comAppRootUpdate);
					//update history current
					repoCom.updateComApprovalRoot(comAppRoot);
				}else{//delete
					//find history previous
				    List<CompanyApprovalRoot> lstOld= repoCom.getComApprovalRootByEdate(companyId, eDatePrevious, comAppRoot.getApplicationType()== null ? null : comAppRoot.getApplicationType().value, comAppRoot.getEmploymentRootAtr().value);
				    if(!lstOld.isEmpty()){
				    	CompanyApprovalRoot comold = lstOld.get(0);
					    CompanyApprovalRoot comAppRootUpdate = CompanyApprovalRoot.updateEdate(comold, endDateDelete);
						//update history previous
						repoCom.updateComApprovalRoot(comAppRootUpdate);
				    }
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, comAppRoot.getBranchId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
						}
						//delete All Approval Phase By Branch Id
						repoAppPhase.deleteAllAppPhaseByBranchId(companyId, comAppRoot.getBranchId());
					}
					//delete history current
					repoCom.deleteComApprovalRoot(companyId, updateItem.getApprovalId(), updateItem.getHistoryId());
				}
			}
		}
	}
	/**
	 * edit history
	 * domain 職場別就業承認ルート (Work place)
	 * @param objUpdateItem
	 */
	private void updateHistoryWorkplace(UpdateWorkAppApprovalRByHistCommand  objUpdateItem){
		String companyId = AppContexts.user().companyId();
		List<UpdateHistoryDto> lstHist = objUpdateItem.getLstUpdate();
		//history current
		String startDate = objUpdateItem.getStartDate();
		GeneralDate sDate = GeneralDate.fromString(startDate, "yyyy/MM/dd");
		GeneralDate eDate = sDate.addDays(-1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String endDateUpdate = eDate.localDate().format(formatter);//Edate: edit
		String endDateDelete = "9999-12-31";//Edate: delete
		//history previous
		String startDatePrevious = objUpdateItem.getStartDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious.replace("/","-")));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);//Edate to find history Previous
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			List<WorkplaceApprovalRoot> lstWpByApp = repoWorkplace.getWpApprovalRootByType(companyId, objUpdateItem.getWorkplaceId(), updateItem.getApplicationType(), updateItem.getEmployRootAtr());
			//find history current
			Optional<WorkplaceApprovalRoot> wpAppRootDb = repoWorkplace.getWpApprovalRoot(companyId, updateItem.getApprovalId(), objUpdateItem.getWorkplaceId(), updateItem.getHistoryId());
			if(!wpAppRootDb.isPresent()){
				continue;
			}
			WorkplaceApprovalRoot wpAppRoot = WorkplaceApprovalRoot.updateSdate(wpAppRootDb.get(), startDate.replace("/","-"));
			if(lstWpByApp.size() < 2){// history previous is not exist
				if(objUpdateItem.getEditOrDelete()==1){//TH: edit
					repoWorkplace.updateWpApprovalRoot(wpAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, wpAppRoot.getBranchId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
						}
						//delete All Approval Phase By Branch Id
						repoAppPhase.deleteAllAppPhaseByBranchId(companyId, wpAppRoot.getBranchId());
					}
					//delete WpApprovalRoot
					repoWorkplace.deleteWpApprovalRoot(companyId, updateItem.getApprovalId(), wpAppRoot.getWorkplaceId(), updateItem.getHistoryId());
				}
			}else{// history previous is exist
				if(objUpdateItem.getEditOrDelete() == EDIT){//edit
					//history previous
					WorkplaceApprovalRoot wp = lstWpByApp.get(1);
					String sDatePre = wp.getEmploymentAppHistoryItems().get(0).start().toString();
					//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
					if(startDate.compareTo(sDatePre) <= 0){
						//エラーメッセージ(Msg_156)(error message (Msg_156))
						throw new BusinessException("Msg_156");
					}
					//history previous 
					WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(wp, endDateUpdate);
					//update history previous
					repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
					//update history current
					repoWorkplace.updateWpApprovalRoot(wpAppRoot);
				}else{//delete 
					//find history previous (lien ke)
					List<WorkplaceApprovalRoot> lstOld= repoWorkplace.getWpApprovalRootByEdate(companyId, wpAppRoot.getWorkplaceId(), eDatePrevious, wpAppRoot.getApplicationType()== null ? null : wpAppRoot.getApplicationType().value, wpAppRoot.getEmploymentRootAtr().value);
					if(!lstOld.isEmpty()){
						//history previous
						WorkplaceApprovalRoot wpOld = lstOld.get(0);
						WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(wpOld, endDateDelete);
						//update history previous
						repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
					}
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, wpAppRoot.getBranchId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
						}
						//delete All Approval Phase By Branch Id
						repoAppPhase.deleteAllAppPhaseByBranchId(companyId, wpAppRoot.getBranchId());
					}
					//delete history current
					repoWorkplace.deleteWpApprovalRoot(companyId, updateItem.getApprovalId(), wpAppRoot.getWorkplaceId(), updateItem.getHistoryId());
				}
			}
		}
	}
	/**
	 * edit history
	 * domain 個人別就業承認ルート (Person)
	 * @param objUpdateItem
	 */
	private void updateHistoryPerson(UpdateWorkAppApprovalRByHistCommand  objUpdateItem){
		String companyId = AppContexts.user().companyId();
		List<UpdateHistoryDto> lstHist = objUpdateItem.getLstUpdate();
		//history current
		String startDate = objUpdateItem.getStartDate();
		GeneralDate sDate = GeneralDate.fromString(startDate, "yyyy/MM/dd");
		GeneralDate eDate = sDate.addDays(-1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String endDateUpdate = eDate.localDate().format(formatter);//Edate: edit
		String endDateDelete = "9999-12-31";//Edate: delete
		//history previous
		String startDatePrevious = objUpdateItem.getStartDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious.replace("/","-")));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);//Edate to find history Previous
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			List<PersonApprovalRoot> lstPsByApp = repoPerson.getPsApprovalRootByType(companyId, objUpdateItem.getEmployeeId(), updateItem.getApplicationType(), updateItem.getEmployRootAtr());
			//find history current
			Optional<PersonApprovalRoot> psAppRootDb = repoPerson.getPsApprovalRoot(companyId, updateItem.getApprovalId(), objUpdateItem.getEmployeeId(), updateItem.getHistoryId());
			if(!psAppRootDb.isPresent()){
				continue;
			}
			PersonApprovalRoot psAppRoot = PersonApprovalRoot.updateSdate(psAppRootDb.get(), startDate.replace("/","-"));
			if(lstPsByApp.size() < 2){// history previous is not exist
				if(objUpdateItem.getEditOrDelete()==1){//TH: edit
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, psAppRoot.getBranchId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
						}
						//delete All Approval Phase By Branch Id
						repoAppPhase.deleteAllAppPhaseByBranchId(companyId, psAppRoot.getBranchId());
					}
					//delete PsApprovalRoot
					repoPerson.deletePsApprovalRoot(companyId, updateItem.getApprovalId(), psAppRoot.getEmployeeId(), updateItem.getHistoryId());
				}
			}else{// history previous is exist
				if(objUpdateItem.getEditOrDelete()==1){//edit
					//history previous
					PersonApprovalRoot ps = lstPsByApp.get(1);
					String sDatePre = ps.getEmploymentAppHistoryItems().get(0).start().toString();
					//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
					if(startDate.compareTo(sDatePre) <= 0){
						//エラーメッセージ(Msg_156)(error message (Msg_156))
						throw new BusinessException("Msg_156");
					}
					//history previous 
					PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(ps,  endDateUpdate);
					//update history previous
					repoPerson.updatePsApprovalRoot(psAppRootUpdate);
					//update history current
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}else{//delete 
					//find history previous
					List<PersonApprovalRoot> lstOld= repoPerson.getPsApprovalRootByEdate(companyId, psAppRoot.getEmployeeId(), eDatePrevious, psAppRoot.getApplicationType()== null ? null : psAppRoot.getApplicationType().value, psAppRoot.getEmploymentRootAtr().value);
					if(!lstOld.isEmpty()){
						//history previous
						PersonApprovalRoot psOld = lstOld.get(0);
						PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(psOld, endDateDelete);
						//update history previous
						repoPerson.updatePsApprovalRoot(psAppRootUpdate);
					}
					//get all  ApprovalPhase by BranchId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(companyId, psAppRoot.getBranchId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(companyId, approvalPhase.getApprovalPhaseId());
						}
						//delete All Approval Phase By Branch Id
						repoAppPhase.deleteAllAppPhaseByBranchId(companyId, psAppRoot.getBranchId());
					}
					//delete history current
					repoPerson.deletePsApprovalRoot(companyId, updateItem.getApprovalId(), psAppRoot.getEmployeeId(),  psAppRoot.getEmploymentAppHistoryItems().get(0).getHistoryId());
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
