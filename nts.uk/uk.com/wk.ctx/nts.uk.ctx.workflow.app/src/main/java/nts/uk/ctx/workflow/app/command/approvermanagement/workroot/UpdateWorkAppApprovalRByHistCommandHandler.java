package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
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
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			List<CompanyApprovalRoot> lstComByApp = repoCom.getComApprovalRootByType(companyId, updateItem.getApplicationType(), updateItem.getEmployRootAtr());
			Optional<CompanyApprovalRoot> comAppRootDb = repoCom.getComApprovalRoot(companyId, updateItem.getApprovalId(), updateItem.getHistoryId());
			if(!comAppRootDb.isPresent()){
				continue;
			}
			//item update (New)
			CompanyApprovalRoot comAppRoot = CompanyApprovalRoot.updateSdate(comAppRootDb.get(), startDate.replace("/","-"));
			if(lstComByApp.isEmpty()){// history previous is not exist
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
				List<CompanyApprovalRoot> lstSortBy = this.sortCompany(lstComByApp);
				int length = lstSortBy.size()-1;
				String sDatePre = lstSortBy.get(length).getPeriod().getStartDate().toString();
				//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
				if(startDate.compareTo(sDatePre) <= 0){
					//エラーメッセージ(Msg_156)(error message (Msg_156))
					throw new BusinessException("Msg_156");
				}
				CompanyApprovalRoot com = lstSortBy.get(length);
				if(objUpdateItem.getEditOrDelete()==1){//edit
					//history previous 
					CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.updateEdate(com, endDateUpdate);
					//update history previous
					repoCom.updateComApprovalRoot(comAppRootUpdate);
					//update history current
					repoCom.updateComApprovalRoot(comAppRoot);
				}else{//delete 
					CompanyApprovalRoot comAppRootUpdate = CompanyApprovalRoot.updateEdate(com, endDateDelete);
					//update history previous
					repoCom.updateComApprovalRoot(comAppRootUpdate);
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
	 * domain 職場別就業承認ルート (Workplace)
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
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			List<WorkplaceApprovalRoot> lstWpByApp = repoWorkplace.getWpApprovalRootByType(companyId, objUpdateItem.getWorkplaceId(), updateItem.getApplicationType(), updateItem.getEmployRootAtr());
			//find history current
			Optional<WorkplaceApprovalRoot> wpAppRootDb = repoWorkplace.getWpApprovalRoot(companyId, updateItem.getApprovalId(), objUpdateItem.getWorkplaceId(), updateItem.getHistoryId());
			if(!wpAppRootDb.isPresent()){
				continue;
			}
			WorkplaceApprovalRoot wpAppRoot = WorkplaceApprovalRoot.updateSdate(wpAppRootDb.get(), startDate.replace("/","-"));
			if(lstWpByApp.isEmpty()){// history previous is not exist
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
				List<WorkplaceApprovalRoot> lstSortBy = this.sortWorkplace(lstWpByApp);
				int length = lstSortBy.size()-1;
				String sDatePre = lstSortBy.get(length).getPeriod().getStartDate().toString();
				//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
				if(startDate.compareTo(sDatePre) <= 0){
					//エラーメッセージ(Msg_156)(error message (Msg_156))
					throw new BusinessException("Msg_156");
				}
				//history previous
				WorkplaceApprovalRoot wp = lstWpByApp.get(length);
				if(objUpdateItem.getEditOrDelete()==1){//edit
					//history previous 
					WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(wp, endDateUpdate);
					//update history previous
					repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
					//update history current
					repoWorkplace.updateWpApprovalRoot(wpAppRoot);
				}else{//delete 
					WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(wp, endDateDelete);
					//update history previous
					repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
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
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			List<PersonApprovalRoot> lstPsByApp = repoPerson.getPsApprovalRootByType(companyId, objUpdateItem.getEmployeeId(), updateItem.getApplicationType(), updateItem.getEmployRootAtr());
			//find history current
			Optional<PersonApprovalRoot> psAppRootDb = repoPerson.getPsApprovalRoot(companyId, updateItem.getApprovalId(), objUpdateItem.getEmployeeId(), updateItem.getHistoryId());
			if(!psAppRootDb.isPresent()){
				continue;
			}
			PersonApprovalRoot psAppRoot = PersonApprovalRoot.updateSdate(psAppRootDb.get(), startDate.replace("/","-"));
			if(lstPsByApp.isEmpty()){// history previous is not exist
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
				List<PersonApprovalRoot> lstSortBy = this.sortPerson(lstPsByApp);
				int length = lstSortBy.size()-1;
				String sDatePre = lstSortBy.get(length).getPeriod().getStartDate().toString();
				//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
				if(startDate.compareTo(sDatePre) <= 0){
					//エラーメッセージ(Msg_156)(error message (Msg_156))
					throw new BusinessException("Msg_156");
				}
				//history previous
				PersonApprovalRoot ps = lstPsByApp.get(length);
				if(objUpdateItem.getEditOrDelete()==1){//edit
					//history previous 
					PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(ps,  endDateUpdate);
					//update history previous
					repoPerson.updatePsApprovalRoot(psAppRootUpdate);
					//update history current
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}else{//delete 
					PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(ps, endDateDelete);
					//update history previous
					repoPerson.updatePsApprovalRoot(psAppRootUpdate);
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
					repoPerson.deletePsApprovalRoot(companyId, updateItem.getApprovalId(), psAppRoot.getEmployeeId(),  psAppRoot.getHistoryId());
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
	/**
	 * sort list company root by start date (asc)
	 * @param lstCom
	 * @return
	 */
	private List<CompanyApprovalRoot> sortCompany(List<CompanyApprovalRoot> lstCom){
		Collections.sort(lstCom, new Comparator<CompanyApprovalRoot>(){
		  public int compare(CompanyApprovalRoot p1, CompanyApprovalRoot p2){
		    return p1.getPeriod().getStartDate().compareTo(p2.getPeriod().getStartDate());
		  }
		});
		return lstCom;
	}
	/**
	 * sort list company root by start date (asc)
	 * @param lstCom
	 * @return
	 */
	private List<WorkplaceApprovalRoot> sortWorkplace(List<WorkplaceApprovalRoot> lstWorkplace){
		Collections.sort(lstWorkplace, new Comparator<WorkplaceApprovalRoot>(){
		  public int compare(WorkplaceApprovalRoot p1, WorkplaceApprovalRoot p2){
		    return p1.getPeriod().getStartDate().compareTo(p2.getPeriod().getStartDate());
		  }
		});
		return lstWorkplace;
	}
	/**
	 * sort list person root by start date (asc)
	 * @param lstCom
	 * @return
	 */
	private List<PersonApprovalRoot> sortPerson(List<PersonApprovalRoot> lstPerson){
		Collections.sort(lstPerson, new Comparator<PersonApprovalRoot>(){
		  public int compare(PersonApprovalRoot p1, PersonApprovalRoot p2){
		    return p1.getPeriod().getStartDate().compareTo(p2.getPeriod().getStartDate());
		  }
		});
		return lstPerson;
	}
}
