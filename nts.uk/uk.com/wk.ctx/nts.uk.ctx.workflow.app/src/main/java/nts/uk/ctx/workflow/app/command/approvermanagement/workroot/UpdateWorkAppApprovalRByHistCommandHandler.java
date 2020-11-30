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
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;
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
//	@Inject
//	private ApprovalBranchRepository repoBranch;
	@Inject
	private CreateDailyApprover creDailyAppr;
	private static final int COMPANY = 0;
	private static final int WORKPLACE = 1;
	private static final int EDIT = 1;
	private static final int COMMON = 0;
	private static final int PRIVATE = 1;
	private static final String EndDateDelete = "9999-12-31";
	@Override
	protected void handle(CommandHandlerContext<UpdateWorkAppApprovalRByHistCommand> context) {
		UpdateWorkAppApprovalRByHistCommand  objUpdateItem = context.getCommand();
		// 03.履歴の削除を実行する(まとめて設定モード)
		// Refactor5: UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.J:履歴の編集.アルゴリズム."03.履歴の削除を実行する(まとめて設定モード)"
		//TH: company - domain 会社別就業承認ルート
		if(objUpdateItem.getCheck() == COMPANY){
			this.updateHistoryCom(objUpdateItem);
		}
		//TH: work place - domain 職場別就業承認ルート
		else if(objUpdateItem.getCheck() == WORKPLACE){
			this.updateHistoryWorkplace(objUpdateItem);
		}
		//TH: person - domain 個人別就業承認ルート
		// 06.履歴の削除を実行する(申請個別設定モード)
		// Refactor5: UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.J:履歴の編集.アルゴリズム."06.履歴の削除を実行する(申請個別設定モード)"
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
		//history previous
		String startDatePrevious = objUpdateItem.getStartDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious.replace("/","-")));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);//Edate to find history Previous
		int sysAtr = objUpdateItem.getSysAtr();
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and EmployRootAtr
			Integer employRootAtr = updateItem.getEmployRootAtr();
			String value = updateItem.getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 || employRootAtr == 4 ? value : "";
			
			List<CompanyApprovalRoot> lstComByApp = repoCom.getComApprovalRootByType(companyId, valueI, employRootAtr, id, sysAtr);
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
					//get all  ApprovalPhase by approvalId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(comAppRoot.getApprovalId());
					for (ApprovalPhase approvalPhase : lstAPhase) {
						//delete All Approver By Approval Phase Id
						repoApprover.deleteAllApproverByAppPhId(approvalPhase.getApprovalId(), approvalPhase.getPhaseOrder());
					}
					//delete All Approval Phase By approvalId
					repoAppPhase.deleteAllAppPhaseByApprovalId(comAppRoot.getApprovalId());
					//delete ComApprovalRoot
					repoCom.deleteComApprovalRoot(companyId, updateItem.getApprovalId(), updateItem.getHistoryId());
					//delete branch
					// repoBranch.deleteBranch(companyId, comAppRoot.getApprRoot().getBranchId());
				}
			}else{// history previous is exist
				if(objUpdateItem.getEditOrDelete( )== EDIT){//edit
					CompanyApprovalRoot com = lstComByApp.get(1);
					String sDatePre = com.getApprRoot().getHistoryItems().get(0).start().toString();
					//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
					if(startDate.compareTo(sDatePre) <= 0){
						//エラーメッセージ(Msg_156)(error message (Msg_156))
						throw new BusinessException("Msg_156",sDatePre);
					}
					repoCom.updateComApprovalRoot(comAppRoot);
				}else{//delete
					//get all  ApprovalPhase by approvalId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(comAppRoot.getApprovalId());
					//check: if data(lstAPhase) > 0: delete
					// 「承認フェーズ」を削除する 
					// 
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(approvalPhase.getApprovalId(), approvalPhase.getPhaseOrder());
						}
						//delete All Approval Phase By approvalId
						repoAppPhase.deleteAllAppPhaseByApprovalId(comAppRoot.getApprovalId());
					}
					//delete history current
					// 「会社別承認ルート」を削除する
					repoCom.deleteComApprovalRoot(companyId, updateItem.getApprovalId(), updateItem.getHistoryId());
					//delete branch
					// remove in ver10
					// repoBranch.deleteBranch(companyId, comAppRoot.getApprRoot().getBranchId());
				}
			}
		}
		if(objUpdateItem.getCheckMode() == 0){
			// xu li mode chung
			List<CompanyApprovalRoot> lstComByApp = repoCom.getComAppRootLast(companyId, eDatePrevious, sysAtr);
			
				if(objUpdateItem.getEditOrDelete( )== EDIT){
					for(CompanyApprovalRoot appRoot : lstComByApp){
						//history previous 
						CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.updateEdate(appRoot, endDateUpdate);
						//update history previous
						repoCom.updateComApprovalRoot(comAppRootUpdate);
					}
				}else{
					for(CompanyApprovalRoot appRoot : lstComByApp){
						//history previous 
						CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.updateEdate(appRoot, EndDateDelete);
						//update history previous
						repoCom.updateComApprovalRoot(comAppRootUpdate);
					}
				}
		}else{
			// xu li mode rieng
			Integer employRootAtr = lstHist.get(0).getEmployRootAtr();
			String value = lstHist.get(0).getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 || employRootAtr == 4 ? value : "";
			List<CompanyApprovalRoot> lstComByApp = repoCom.getComApprovalRootByType(companyId, valueI, employRootAtr, id, sysAtr);
			if(objUpdateItem.getEditOrDelete( )== EDIT){
				if(!lstComByApp.isEmpty() && lstComByApp.size() > 1){
					//history previous 
					CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.updateEdate(lstComByApp.get(1), endDateUpdate);
					//update history previous
					repoCom.updateComApprovalRoot(comAppRootUpdate);
				}
			}else{
				//delete
				if(!lstComByApp.isEmpty()){
					//history previous 
					CompanyApprovalRoot comAppRootUpdate= CompanyApprovalRoot.updateEdate(lstComByApp.get(0), EndDateDelete);
					//update history previous
					repoCom.updateComApprovalRoot(comAppRootUpdate);
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
		//history previous
		String startDatePrevious = objUpdateItem.getStartDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious.replace("/","-")));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);//Edate to find history Previous
		int sysAtr = objUpdateItem.getSysAtr();
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			Integer employRootAtr = updateItem.getEmployRootAtr();
			String value = updateItem.getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 ? value : "";
			List<WorkplaceApprovalRoot> lstWpByApp = repoWorkplace.getWpApprovalRootByType(companyId, 
					objUpdateItem.getWorkplaceId(), valueI, employRootAtr, id, sysAtr);
			//find history current
			Optional<WorkplaceApprovalRoot> wpAppRootDb = repoWorkplace.getWpApprovalRoot(companyId, updateItem.getApprovalId(), objUpdateItem.getWorkplaceId(), updateItem.getHistoryId());
			if(!wpAppRootDb.isPresent()){
				continue;
			}
			WorkplaceApprovalRoot wpAppRoot = WorkplaceApprovalRoot.updateSdate(wpAppRootDb.get(), startDate.replace("/","-"));
			if(lstWpByApp.size() < 2){// history previous is not exist
				if(objUpdateItem.getEditOrDelete() == EDIT){//TH: edit
					repoWorkplace.updateWpApprovalRoot(wpAppRoot);
				}else{//TH: delete
					//get all  ApprovalPhase by approvalId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(wpAppRoot.getApprovalId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(approvalPhase.getApprovalId(), approvalPhase.getPhaseOrder());
						}
						//delete All Approval Phase By approvalId
						repoAppPhase.deleteAllAppPhaseByApprovalId(wpAppRoot.getApprovalId());
					}
					//delete WpApprovalRoot
					repoWorkplace.deleteWpApprovalRoot(companyId, updateItem.getApprovalId(), wpAppRoot.getWorkplaceId(), updateItem.getHistoryId());
					//delete branch
					// repoBranch.deleteBranch(companyId, wpAppRoot.getApprRoot().getBranchId());
				}
			}else{// history previous is exist
				if(objUpdateItem.getEditOrDelete() == EDIT){//edit
					//history previous
					WorkplaceApprovalRoot wp = lstWpByApp.get(1);
					String sDatePre = wp.getApprRoot().getHistoryItems().get(0).start().toString();
					//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
					if(startDate.compareTo(sDatePre) <= 0){
						//エラーメッセージ(Msg_156)(error message (Msg_156))
						throw new BusinessException("Msg_156",sDatePre);
					}
					repoWorkplace.updateWpApprovalRoot(wpAppRoot);
				}else{//delete 
					//get all  ApprovalPhase by approvalId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(wpAppRoot.getApprovalId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(approvalPhase.getApprovalId(), approvalPhase.getPhaseOrder());
						}
						//delete All Approval Phase By approvalId
						repoAppPhase.deleteAllAppPhaseByApprovalId(wpAppRoot.getApprovalId());
					}
					//delete history current
					repoWorkplace.deleteWpApprovalRoot(companyId, updateItem.getApprovalId(), wpAppRoot.getWorkplaceId(), updateItem.getHistoryId());
					//delete branch
					// repoBranch.deleteBranch(companyId, wpAppRoot.getApprRoot().getBranchId());
				}
			}
		}
		if(objUpdateItem.getCheckMode() == 0){
			// xu li mode chung
			List<WorkplaceApprovalRoot> lstWpByApp = repoWorkplace.getWpAppRootLast(companyId,
					objUpdateItem.getWorkplaceId(), eDatePrevious, sysAtr);
			
				if(objUpdateItem.getEditOrDelete( )== EDIT){
					for(WorkplaceApprovalRoot appRoot : lstWpByApp){
						//history previous 
						WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(appRoot, endDateUpdate);
						//update history previous
						repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
					}
				}else{
					for(WorkplaceApprovalRoot appRoot : lstWpByApp){
						//history previous 
						WorkplaceApprovalRoot wpAppRootUpdate= WorkplaceApprovalRoot.updateEdate(appRoot, EndDateDelete);
						//update history previous
						repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
					}
				}
		}else{
			//find history by type and 
			Integer employRootAtr = lstHist.get(0).getEmployRootAtr();
			String value = lstHist.get(0).getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 || employRootAtr == 4 ? value : "";
			List<WorkplaceApprovalRoot> lstWpByApp = repoWorkplace.getWpApprovalRootByType(companyId,
					objUpdateItem.getWorkplaceId(), valueI, employRootAtr, id, sysAtr);
			if(objUpdateItem.getEditOrDelete( )== EDIT){
				if(!lstWpByApp.isEmpty() && lstWpByApp.size() > 1){
					//history previous 
					WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(lstWpByApp.get(1), endDateUpdate);
					//update history previous
					repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
				}
			}else{
				//delete
				if(!lstWpByApp.isEmpty()){
					//history previous 
					WorkplaceApprovalRoot wpAppRootUpdate = WorkplaceApprovalRoot.updateEdate(lstWpByApp.get(0), EndDateDelete);
					//update history previous
					repoWorkplace.updateWpApprovalRoot(wpAppRootUpdate);
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
		//history previous
		String startDatePrevious = objUpdateItem.getStartDatePrevious();
		GeneralDate sDatePrevious = GeneralDate.localDate(LocalDate.parse(startDatePrevious.replace("/","-")));
		GeneralDate eDatePrevious = sDatePrevious.addDays(-1);//Edate to find history Previous
		String employeeId = objUpdateItem.getEmployeeId();
		GeneralDate dateLastest = null;
		int sysAtr = objUpdateItem.getSysAtr();
		//ドメインモデル「就業承認ルート履歴」を取得する
		if(objUpdateItem.getEditOrDelete() == EDIT && objUpdateItem.getCheckMode() == COMMON){//07.履歴編集を実行する(まとめて設定モード)
			Optional<PersonApprovalRoot> histL = repoPerson.getHistLastestCom(companyId, employeeId);
			if(histL.isPresent()) dateLastest = histL.get().getApprRoot().getHistoryItems().get(0).getDatePeriod().start();
		}
		if(objUpdateItem.getEditOrDelete() == EDIT && objUpdateItem.getCheckMode() == PRIVATE){//10.履歴編集を実行する(申請個別設定モード)
			Integer employRootAtr = objUpdateItem.getLstUpdate().get(0).getEmployRootAtr();
			String value = objUpdateItem.getLstUpdate().get(0).getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 ? value : "";
			Optional<PersonApprovalRoot> histL = repoPerson.getHistLastestPri(companyId, employeeId, employRootAtr, valueI, id, sysAtr);
			if(histL.isPresent()) dateLastest = histL.get().getApprRoot().getHistoryItems().get(0).getDatePeriod().start();
		}
		for (UpdateHistoryDto updateItem : lstHist) {
			//find history by type and 
			Integer employRootAtr = updateItem.getEmployRootAtr();
			String value = updateItem.getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 ? value : "";
			List<PersonApprovalRoot> lstPsByApp = repoPerson.getPsApprovalRootByType(companyId, objUpdateItem.getEmployeeId(),
					valueI, employRootAtr, id, sysAtr);
			//find history current
			Optional<PersonApprovalRoot> psAppRootDb = repoPerson.getPsApprovalRoot(companyId, updateItem.getApprovalId(), objUpdateItem.getEmployeeId(), updateItem.getHistoryId());
			if(!psAppRootDb.isPresent()){
				continue;
			}
			PersonApprovalRoot psAppRoot = PersonApprovalRoot.updateSdate(psAppRootDb.get(), startDate.replace("/","-"));
			if(lstPsByApp.size() < 2){// history previous is not exist
				if(objUpdateItem.getEditOrDelete() == EDIT){//TH: edit hist (J)
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}else{//TH: delete hist (J)
					//get all  ApprovalPhase by approvalId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(psAppRoot.getApprovalId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(approvalPhase.getApprovalId(), approvalPhase.getPhaseOrder());
						}
						//delete All Approval Phase By approvalId
						repoAppPhase.deleteAllAppPhaseByApprovalId(psAppRoot.getApprovalId());
					}
					//delete PsApprovalRoot
					repoPerson.deletePsApprovalRoot(companyId, updateItem.getApprovalId(), psAppRoot.getEmployeeId(), updateItem.getHistoryId());
					//delete branch
					// repoBranch.deleteBranch(companyId, psAppRoot.getApprRoot().getBranchId());
				}
			}else{// history previous is exist
				if(objUpdateItem.getEditOrDelete() == EDIT){//edit
					//history previous
					PersonApprovalRoot ps = lstPsByApp.get(1);
					String sDatePre = ps.getApprRoot().getHistoryItems().get(0).start().toString();
					//check 編集後の履歴の開始年月日 > 取得した履歴の開始年月日 が falseの場合
					if(startDate.compareTo(sDatePre) <= 0){
						//エラーメッセージ(Msg_156)(error message (Msg_156))
						throw new BusinessException("Msg_156",sDatePre);
					}
					//update history current
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}else{//delete 
					//get all  ApprovalPhase by approvalId
					List<ApprovalPhase> lstAPhase = repoAppPhase.getAllApprovalPhasebyCode(psAppRoot.getApprovalId());
					//check: if data(lstAPhase) > 0: delete
					if(!lstAPhase.isEmpty()){
						for (ApprovalPhase approvalPhase : lstAPhase) {
							//delete All Approver By Approval Phase Id
							repoApprover.deleteAllApproverByAppPhId(approvalPhase.getApprovalId(), approvalPhase.getPhaseOrder());
						}
						//delete All Approval Phase By approvalId
						repoAppPhase.deleteAllAppPhaseByApprovalId(psAppRoot.getApprovalId());
					}
					//delete history current
					repoPerson.deletePsApprovalRoot(companyId, updateItem.getApprovalId(), psAppRoot.getEmployeeId(),  psAppRoot.getApprRoot().getHistoryItems().get(0).getHistoryId());
					//delete branch
					// repoBranch.deleteBranch(companyId, psAppRoot.getApprRoot().getBranchId());
				}
			}
		}
		//xu li update hist old
		if(objUpdateItem.getCheckMode() == 0){
			// xu li mode chung
			List<PersonApprovalRoot> lstPsByApp = repoPerson.getPsAppRootLastest(companyId,
					objUpdateItem.getEmployeeId(), eDatePrevious, sysAtr);
			
				if(objUpdateItem.getEditOrDelete( )== EDIT){
					for(PersonApprovalRoot appRoot : lstPsByApp){
						//history previous 
						PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(appRoot,  endDateUpdate);
						//update history previous
						repoPerson.updatePsApprovalRoot(psAppRootUpdate);
					}
				}else{
					for(PersonApprovalRoot appRoot : lstPsByApp){
						PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(appRoot, EndDateDelete);
						//update history previous
						repoPerson.updatePsApprovalRoot(psAppRootUpdate);
					}
				}
		}else{
			//find history by type and 
			Integer employRootAtr = lstHist.get(0).getEmployRootAtr();
			String value = lstHist.get(0).getApplicationType();
			Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
			String id = employRootAtr == 5 || employRootAtr == 4 ? value : "";
			List<PersonApprovalRoot> lstPsByApp = repoPerson.getPsApprovalRootByType(companyId, objUpdateItem.getEmployeeId(),
					valueI, employRootAtr, id, sysAtr);
			if(objUpdateItem.getEditOrDelete( )== EDIT){
				if(!lstPsByApp.isEmpty() && lstPsByApp.size() > 1){
					//history previous 
					PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(lstPsByApp.get(1), endDateUpdate);
					//update history previous
					repoPerson.updatePsApprovalRoot(psAppRootUpdate);
				}
			}else{
				//delete
				if(!lstPsByApp.isEmpty()){
					//history previous 
					PersonApprovalRoot psAppRootUpdate= PersonApprovalRoot.updateEdate(lstPsByApp.get(0), EndDateDelete);
					//update history previous
					repoPerson.updatePsApprovalRoot(psAppRootUpdate);
				}
				
			}
			
		}
		//EA修正履歴 No.3268
		//EA修正履歴 No.3271
		//EA修正履歴 No.3273
		//EA修正履歴 No.3274
		//EA修正履歴 No.3290
		if(objUpdateItem.getSysAtr() == 0) {
			if(objUpdateItem.getEditOrDelete() == EDIT){//Edit
				//履歴の開始日とシステム日付をチェックする
				if(sDate.after(GeneralDate.today()) || dateLastest == null){//履歴の開始日＞システム日付
					return;
				}
				if(dateLastest.before(sDate)){//取得した開始日(1)＜新しい履歴の開始日
					//指定社員の中間データを作成する（日別）
					creDailyAppr.createDailyApprover(objUpdateItem.getEmployeeId(), RecordRootType.CONFIRM_WORK_BY_DAY, dateLastest, dateLastest);
					//指定社員の中間データを作成する（月別）
					creDailyAppr.createDailyApprover(objUpdateItem.getEmployeeId(), RecordRootType.CONFIRM_WORK_BY_MONTH, dateLastest, dateLastest);
				}
			}
			//指定社員の中間データを作成する（日別）
			creDailyAppr.createDailyApprover(objUpdateItem.getEmployeeId(), RecordRootType.CONFIRM_WORK_BY_DAY, sDate, sDate);
			//指定社員の中間データを作成する（月別）
			creDailyAppr.createDailyApprover(objUpdateItem.getEmployeeId(), RecordRootType.CONFIRM_WORK_BY_MONTH, sDate, sDate);
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
