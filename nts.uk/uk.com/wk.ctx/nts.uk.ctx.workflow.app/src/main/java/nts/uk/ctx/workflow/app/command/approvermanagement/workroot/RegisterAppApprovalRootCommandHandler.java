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
import nts.gul.text.StringUtil;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApprovalPhaseDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApproverDto;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;
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
//	@Inject
//	private ApprovalBranchRepository repoBranch;
	@Inject
	private WorkplaceApproverAdapter adapterWp;
	@Inject
	private CreateDailyApprover creDailyAppr;
	private static final int COMPANY = 0;
	private static final int WORKPLACE = 1;
	private static final int SHUUGYOU = 0;
	@Override
	protected void handle(CommandHandlerContext<RegisterAppApprovalRootCommand> context) {
		//____________New__________
		String historyId = UUID.randomUUID().toString();
		RegisterAppApprovalRootCommand data = context.getCommand();
		int rootType = data.getRootType();
		//TH: company
		if(rootType == COMPANY){
			this.registerCom(data, historyId);
		}
		//TH: work place
		else if(rootType == WORKPLACE){
			this.registerWp(data, historyId);
		}
		//TH: person
		else{
			this.registerPs(data, historyId);
		}
	}
	/**
	 * register domain Company
	 * @param data
	 * @param historyId
	 */
	private void registerCom(RegisterAppApprovalRootCommand data,String historyId){
		String companyId = AppContexts.user().companyId();
		List<CompanyAppRootADto> root = data.getRoot();
		List<CompanyAppRootADto> rootInsert = new ArrayList<>();
		boolean checkAddHist = data.isCheckAddHist();
		String startDate = data.getStartDate().replace("/", "-");
		String endDateOld = data.getEndDate().replace("/", "-");
		GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
		GeneralDate eDate = sDate.addDays(-1);
		String endDateNew = eDate.toString().replace("/", "-");
		String endDateS = "9999-12-31";
		GeneralDate endDateUpdate = GeneralDate.fromString(endDateOld, "yyyy-MM-dd");
		//loai bo nhung root chua duoc setting
		for (CompanyAppRootADto commonRoot : root) {
			if(commonRoot.isColor()){
				rootInsert.add(commonRoot);
			}
		}
		//TH: create history new
		if(checkAddHist){
			//Tạo root có ls mới với appType ở dữ liệu bên phải.
			//Update root có ls trước đó của những root mới được tạo ở trên.
			List<CompanyApprovalRoot> listCom = new ArrayList<>();
			List<CompanyApprovalRoot> listComPre = new ArrayList<>();
			// List<ApprovalBranch> lstBranch = new ArrayList<>();
			for (CompanyAppRootADto commonRoot : rootInsert) {
				int employRootAtr = commonRoot.getEmployRootAtr();
				String type = commonRoot.getAppTypeValue();
				Integer typeApp = employRootAtr == 1 ? Integer.valueOf(type) : null;
				Integer typeConf = employRootAtr == 2 ? Integer.valueOf(type) : null;
				Integer typeNt = employRootAtr == 4 ? Integer.valueOf(type) : null;
				String typeEv = employRootAtr == 5 ? type : null;
				String approvalId = UUID.randomUUID().toString();
				//root right
				CompanyApprovalRoot com = CompanyApprovalRoot.createSimpleFromJavaType(companyId, 
						approvalId, historyId, typeApp, startDate, endDateS, 
						typeConf, employRootAtr, data.getSystemAtr(), typeNt, typeEv);
				if(!ApprovalRoot.checkValidate(startDate.toString(), endDateS)){
					throw new BusinessException("Msg_156");
				}
				listCom.add(com);
				//Add approval
				this.addApproval(commonRoot, approvalId
						);
			}
			//find history by type and EmployRootAtr
			if(data.getCheckMode() == 1){
				// xử lí update phần riêng
				if(!rootInsert.isEmpty()){
					String type = rootInsert.get(0).getAppTypeValue();
					int empRoot = rootInsert.get(0).getEmployRootAtr();
					Integer typeI = empRoot != 0 && empRoot != 5 ? Integer.valueOf(type) : null;
					String typeS = empRoot == 5 ? type : null;
					List<CompanyApprovalRoot> comOld = repoCom.getComApprovalRootByType(companyId, typeI, empRoot, typeS, data.getSystemAtr());
					if(!comOld.isEmpty()){
						//update ls cu
						CompanyApprovalRoot comPre = CompanyApprovalRoot.updateEdate(comOld.get(0), endDateNew);
						listComPre.add(comPre);
					}
				}
			}else{
				// xử lí update phần chung
				List<CompanyApprovalRoot> comOlds = repoCom.getComAppRootLast(companyId, 
						GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), data.getSystemAtr());
					for(CompanyApprovalRoot comAppRoot : comOlds){
						CompanyApprovalRoot comPre = CompanyApprovalRoot.updateEdate(comAppRoot, endDateNew);
						listComPre.add(comPre);
					}
				
			}
			//Add ls new, update ls old, add branch
			repoCom.addAllComApprovalRoot(listCom);
			repoCom.updateAllComApprovalRoot(listComPre);
			// repoBranch.addAllBranch(lstBranch);
		}
		//TH: update history old
		else{
			List<AppType> lstAppTypeDb = data.getLstAppType();
			List<AppType> lstAppTypeUi = new ArrayList<>();
			for (CompanyAppRootADto commonRoot : rootInsert) {
				lstAppTypeUi.add(new AppType(commonRoot.getAppTypeValue(), commonRoot.getEmployRootAtr()));
			}
			//delete root not display in screen
			for (AppType type : lstAppTypeDb) {
				if(!this.checkContain(lstAppTypeUi, type)){
					Integer value = type.getEmployRootAtr() != 5 && type.getEmployRootAtr() != 0 ? Integer.valueOf(type.getValue()) : 0;
					String id = type.getEmployRootAtr() == 5 ? type.getValue() : "";
					List<CompanyApprovalRoot> lstCom = repoCom.getComApprovalRootByEdate(companyId, endDateUpdate, value,
							type.getEmployRootAtr(), id, data.getSystemAtr());
					if(!lstCom.isEmpty()){
						CompanyApprovalRoot com = lstCom.get(0);
						//==========
						this.deleteAppPh(com.getApprovalId());
						//=======
						//delete root
						repoCom.deleteComApprovalRoot(companyId, com.getApprovalId(), com.getApprRoot().getHistoryItems().get(0).getHistoryId());
						//delete branch
						// repoBranch.deleteBranch(companyId, com.getApprRoot().getBranchId());
					}
				}
			}
			List<CompanyApprovalRoot> listCom = new ArrayList<>();
			// List<ApprovalBranch> lstBranch = new ArrayList<>();
			for (AppType type : lstAppTypeUi) {
				CompanyAppRootADto commonRoot = this.findRoot(root, type);
				String approvalId = commonRoot.getApprovalId();
				//root create new
				if(StringUtil.isNullOrEmpty(commonRoot.getHistoryId(), true)){
					approvalId = UUID.randomUUID().toString();
					String typeCom = commonRoot.getAppTypeValue();
					int employRootAtr = commonRoot.getEmployRootAtr();
					Integer typeApp = employRootAtr == 1 ? Integer.valueOf(typeCom) : null;
					Integer typeConf = employRootAtr == 2 ? Integer.valueOf(typeCom) : null;
					Integer typeNt = employRootAtr == 4 ? Integer.valueOf(typeCom) : null;
					String typeEv = employRootAtr == 5 ? typeCom : null;
					
					//root right
					CompanyApprovalRoot com = CompanyApprovalRoot.createSimpleFromJavaType(companyId, 
										approvalId, historyId, typeApp, startDate, endDateOld,
										typeConf,
										employRootAtr, data.getSystemAtr(), typeNt, typeEv);

					if(!ApprovalRoot.checkValidate(startDate.toString(), endDateOld)){
						throw new BusinessException("Msg_156");
					}
					listCom.add(com);
					this.addApproval(commonRoot, approvalId);
				}
			}
			//Add ls new
			repoCom.addAllComApprovalRoot(listCom);
			//add branch
			// repoBranch.addAllBranch(lstBranch);
			//update root display in screen
			this.updateRoot(lstAppTypeUi, rootInsert);
		}
	}
	/**
	 * register domain work place
	 * @param data
	 * @param historyId
	 */
	private void registerWp(RegisterAppApprovalRootCommand data,String historyId){
		String companyId = AppContexts.user().companyId();
		//list display in screen
		List<CompanyAppRootADto> root = data.getRoot();
		//list insert in db
		List<CompanyAppRootADto> rootInsert = new ArrayList<>();
		String workplaceId = data.getWorkpplaceId();
		boolean checkAddHist = data.isCheckAddHist();
		String startDate = data.getStartDate().replace("/", "-");
		String endDateOld = data.getEndDate().replace("/", "-");
		GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
		GeneralDate eDate = sDate.addDays(-1);
		String endDateNew = eDate.toString().replace("/", "-");
		String endDateS = "9999-12-31";
		GeneralDate endDateUpdate = GeneralDate.fromString(endDateOld, "yyyy-MM-dd");
		//loai bo nhung root chua duoc setting
		for (CompanyAppRootADto commonRoot : root) {
			if(commonRoot.isColor()){
				rootInsert.add(commonRoot);
			}
		}
		if(workplaceId.compareTo("") == 0){
			GeneralDate baseDate = GeneralDate.today();
			WorkplaceImport workplace = adapterWp.findBySid(AppContexts.user().employeeId(), baseDate);
			workplaceId = workplace.getWkpId();
		}
		//TH: create history new
		if(checkAddHist){
			if(rootInsert.size() == 0){//TH tao moi ls nhung k co don nao duoc setting
				return;
			}
			//Tạo root có ls mới với appType ở dữ liệu bên phải.
			//Update root có ls trước đó của những root mới được tạo ở trên.
			List<WorkplaceApprovalRoot> listWp = new ArrayList<>();
			List<WorkplaceApprovalRoot> listWpPre = new ArrayList<>();
			// List<ApprovalBranch> lstBranch = new ArrayList<>();
			for (CompanyAppRootADto commonRoot : rootInsert) {
				String type = commonRoot.getAppTypeValue();
				int employRootAtr = commonRoot.getEmployRootAtr();
				Integer typeApp = employRootAtr == 1 ? Integer.valueOf(type) : null;
				Integer typeConf = employRootAtr == 2 ? Integer.valueOf(type) : null;
				Integer typeNt = employRootAtr == 4 ? Integer.valueOf(type) : null;
				String typeEv = employRootAtr == 5 ? type : null;
				
				String approvalId = UUID.randomUUID().toString();
				WorkplaceApprovalRoot com = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId, 
									approvalId, workplaceId, historyId, typeApp, startDate, endDateS,
									typeConf, employRootAtr, data.getSystemAtr(), typeNt, typeEv);
				if(!ApprovalRoot.checkValidate(startDate.toString(), endDateS)){
					throw new BusinessException("Msg_156");
				}
				listWp.add(com);
				
				//Add approval
				this.addApproval(commonRoot, approvalId);
			}
			//find history by type and 
			if(data.getCheckMode() == 1){
				Integer employRootAtr = rootInsert.get(0).getEmployRootAtr();
				String type = rootInsert.get(0).getAppTypeValue();
				Integer value = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(type) : 0;
				String id = employRootAtr == 5 ? type : "";
				List<WorkplaceApprovalRoot> psOld = repoWorkplace.getWpApprovalRootByType(companyId, workplaceId,
						value, employRootAtr, id, data.getSystemAtr());
				if(!psOld.isEmpty()){
					//update ls cu
					WorkplaceApprovalRoot psPre = WorkplaceApprovalRoot.updateEdate(psOld.get(0), endDateNew);
					listWpPre.add(psPre);
				}
			}else{
				// xu li update phan chung
				List<WorkplaceApprovalRoot> psOlds = repoWorkplace.getWpAppRootLast(companyId, workplaceId,
						GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), data.getSystemAtr());
				for(WorkplaceApprovalRoot wpAppRoot : psOlds){
					WorkplaceApprovalRoot psPre = WorkplaceApprovalRoot.updateEdate(wpAppRoot, endDateNew);
					listWpPre.add(psPre);
				}
			}
			
			//Add ls new, update ls old, add branch
			repoWorkplace.addAllWpApprovalRoot(listWp);
			repoWorkplace.updateAllWpApprovalRoot(listWpPre);
		}
		//TH: update history old
		else{
			List<AppType> lstAppTypeDb = data.getLstAppType();
			List<AppType> lstAppTypeUi = new ArrayList<>();
			for (CompanyAppRootADto commonRoot : rootInsert) {
				lstAppTypeUi.add(new AppType(commonRoot.getAppTypeValue(), commonRoot.getEmployRootAtr()));
			}
			//delete root not display in screen
			for (AppType type : lstAppTypeDb) {
				if(!this.checkContain(lstAppTypeUi, type)){
					Integer employRootAtr = type.getEmployRootAtr();
					String value = type.getValue();
					Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
					String id = employRootAtr == 5 || employRootAtr == 4 ? value : "";
					List<WorkplaceApprovalRoot> lstWp = repoWorkplace.getWpApprovalRootByEdate(companyId, workplaceId,
							endDateUpdate, valueI, employRootAtr, id, data.getSystemAtr());
					if(!lstWp.isEmpty()){
						WorkplaceApprovalRoot wp = lstWp.get(0);
						//==========
						this.deleteAppPh(wp.getApprovalId());
						//=======
						repoWorkplace.deleteWpApprovalRoot(companyId, wp.getApprovalId(), workplaceId, wp.getApprRoot().getHistoryItems().get(0).getHistoryId());
					}
				}
			}
			List<WorkplaceApprovalRoot> listWp = new ArrayList<>();
			// List<ApprovalBranch> lstBranch = new ArrayList<>();
			for (AppType type : lstAppTypeUi) {
				CompanyAppRootADto commonRoot = this.findRoot(root, type);
				String approvalId = commonRoot.getApprovalId();
				if(StringUtil.isNullOrEmpty(commonRoot.getHistoryId(), true)){
					approvalId = UUID.randomUUID().toString();
					int employRootAtr = commonRoot.getEmployRootAtr();
					String typeV = type.getValue();
					Integer typeApp = employRootAtr == 1 ? Integer.valueOf(typeV) : null;
					Integer typeConf = employRootAtr == 2 ? Integer.valueOf(typeV) : null;
					Integer typeNt = employRootAtr == 4 ? Integer.valueOf(typeV) : null;
					String typeEv = employRootAtr == 5 ? typeV : null;
					//root right
					WorkplaceApprovalRoot wp = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId, approvalId, workplaceId,
							historyId, typeApp, startDate, endDateOld, 
							typeConf, employRootAtr,
							data.getSystemAtr(), typeNt, typeEv);
					if(!ApprovalRoot.checkValidate(startDate.toString(), endDateOld)){
						throw new BusinessException("Msg_156");
					}
					listWp.add(wp);
					this.addApproval(commonRoot, approvalId);
				}
			}
			//Add ls new
			repoWorkplace.addAllWpApprovalRoot(listWp);
			//update root display in screen
			this.updateRoot(lstAppTypeUi, root);
		}
	}
	/**
	 * register domain person
	 * @param data
	 * @param historyId
	 */
	private void registerPs(RegisterAppApprovalRootCommand data,String historyId){
		String companyId = AppContexts.user().companyId();
		//list display in screen
		List<CompanyAppRootADto> root = data.getRoot();
		//list insert in db
		List<CompanyAppRootADto> rootInsert = new ArrayList<>();
		String employeeId = data.getEmployeeId(); 
		boolean checkAddHist = data.isCheckAddHist();
		String startDate = data.getStartDate().replace("/", "-");
		String endDateOld = data.getEndDate().replace("/", "-");
		GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
		GeneralDate eDate = sDate.addDays(-1);
		String endDateNew = eDate.toString().replace("/", "-");
		String endDateS = "9999-12-31";
		GeneralDate endDateUpdate = GeneralDate.fromString(endDateOld, "yyyy-MM-dd");
		//loai bo nhung root chua duoc setting
		for (CompanyAppRootADto commonRoot : root) {
			if(commonRoot.isColor()){
				rootInsert.add(commonRoot);
			}
		}
		//TH: create history new
		//履歴追加を実行する
		if(checkAddHist){
			//Tạo root có ls mới với appType ở dữ liệu bên phải.
			//Update root có ls trước đó của những root mới được tạo ở trên.
			List<PersonApprovalRoot> listPs = new ArrayList<>();
			List<PersonApprovalRoot> listPsPre = new ArrayList<>();
			// List<ApprovalBranch> lstBranch = new ArrayList<>();
			for (CompanyAppRootADto commonRoot : rootInsert) {
				String type = commonRoot.getAppTypeValue();
				int employRootAtr = commonRoot.getEmployRootAtr();
				Integer typeApp = employRootAtr == 1 ? Integer.valueOf(type) : null;
				Integer typeConf = employRootAtr == 2 ? Integer.valueOf(type) : null;
				Integer typeNt = employRootAtr == 4 ? Integer.valueOf(type) : null;
				String typeEv = employRootAtr == 5 ? type : null;
				
				String approvalId = UUID.randomUUID().toString();
				PersonApprovalRoot com = PersonApprovalRoot.createSimpleFromJavaType(companyId, 
									approvalId, employeeId, historyId, typeApp, startDate, endDateS,
									typeConf,
									employRootAtr, data.getSystemAtr(), typeNt, typeEv);
				if(!ApprovalRoot.checkValidate(startDate.toString(), endDateS)){
					throw new BusinessException("Msg_156");
				}
				listPs.add(com);
				
				//Add approval
				this.addApproval(commonRoot, approvalId);
			}
			//find history by type and
			if(data.getCheckMode() == 1){
				Integer employRootAtr = rootInsert.get(0).getEmployRootAtr();
				String value = rootInsert.get(0).getAppTypeValue();
				Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
				String id = employRootAtr == 5 ? value : "";
				List<PersonApprovalRoot> psOld = repoPerson.getPsApprovalRootByType(companyId, employeeId, valueI,
						employRootAtr, id, data.getSystemAtr());
				if(!psOld.isEmpty()){
					//update ls cu
					PersonApprovalRoot psPre = PersonApprovalRoot.updateEdate(psOld.get(0), endDateNew);
					listPsPre.add(psPre);
				}
			}else{
				// xu li phan chung 
				List<PersonApprovalRoot> psOlds = repoPerson.getPsAppRootLastest(companyId, employeeId,
						GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), data.getSystemAtr());
				for(PersonApprovalRoot psAppRoot : psOlds){
					PersonApprovalRoot psPre = PersonApprovalRoot.updateEdate(psAppRoot, endDateNew);
					listPsPre.add(psPre);
				}
			}
			
			//Add ls new, update ls old, add branch
			repoPerson.addAllPsApprovalRoot(listPs);
			repoPerson.updateAllPsApprovalRoot(listPsPre);
		}
		//TH: update history old
		//承認ルートの更新を実行する
		else{
			List<AppType> lstAppTypeDb = data.getLstAppType();
			List<AppType> lstAppTypeUi = new ArrayList<>();
			for (CompanyAppRootADto commonRoot : rootInsert) {
				lstAppTypeUi.add(new AppType(commonRoot.getAppTypeValue(),commonRoot.getEmployRootAtr()));
			}
			//delete root not display in screen
			for (AppType type : lstAppTypeDb) {
				if(!this.checkContain(lstAppTypeUi, type)){
					Integer employRootAtr = type.getEmployRootAtr();
					String value = type.getValue();
					Integer valueI = employRootAtr != 5 && employRootAtr != 0 ? Integer.valueOf(value) : 0;
					String id = employRootAtr == 5 ? value : "";
					List<PersonApprovalRoot> lstPs = repoPerson.getPsApprovalRootByEdate(companyId, employeeId,
							endDateUpdate, valueI, employRootAtr, id, data.getSystemAtr());
					if(!lstPs.isEmpty()){
						PersonApprovalRoot ps = lstPs.get(0);
						//==========
						this.deleteAppPh(ps.getApprovalId());
						//=======
						repoPerson.deletePsApprovalRoot(companyId, ps.getApprovalId(),employeeId, ps.getApprRoot().getHistoryItems().get(0).getHistoryId());
					}
				}
			}
			List<PersonApprovalRoot> listPs = new ArrayList<>();
			// List<ApprovalBranch> lstBranch = new ArrayList<>();
			for (AppType type : lstAppTypeUi) {
				CompanyAppRootADto commonRoot = this.findRoot(root, type);
				String approvalId = commonRoot.getApprovalId();
				if(StringUtil.isNullOrEmpty(commonRoot.getHistoryId(), true)){
					approvalId = UUID.randomUUID().toString();
					int employRootAtr = commonRoot.getEmployRootAtr();
					String value = type.getValue();
					Integer typeApp = employRootAtr == EmploymentRootAtr.APPLICATION.value ? Integer.valueOf(value) : null;
					Integer typeConf = employRootAtr == EmploymentRootAtr.CONFIRMATION.value ? Integer.valueOf(value) : null;
					Integer typeNt = employRootAtr == EmploymentRootAtr.NOTICE.value ? Integer.valueOf(value) : null;
					String typeEv = employRootAtr == EmploymentRootAtr.BUS_EVENT.value ? value : null;
					//root right
					PersonApprovalRoot ps = PersonApprovalRoot.createSimpleFromJavaType(companyId, approvalId, employeeId,
							historyId, typeApp, startDate, endDateOld,
							typeConf, employRootAtr, data.getSystemAtr(), typeNt, typeEv);
					if(!ApprovalRoot.checkValidate(startDate.toString(), endDateOld)){
						throw new BusinessException("Msg_156");
					}
					listPs.add(ps);
					this.addApproval(commonRoot, approvalId);
				}
			}
			//Add ls new
			repoPerson.addAllPsApprovalRoot(listPs);
			//update root display in screen
			this.updateRoot(lstAppTypeUi, root);
		}
		//EA修正履歴 No.3267
		//EA修正履歴 No.3269
		//EA修正履歴 No.3270
		//EA修正履歴 No.3272
		//追加する履歴の開始日とシステム日付をチェックする
		if(sDate.after(GeneralDate.today())){//履歴の開始日＞システム日付
			return;
		}
		if(data.getSystemAtr() == SHUUGYOU){
			//指定社員の中間データを作成する（日別）
			creDailyAppr.createDailyApprover(data.getEmployeeId(), RecordRootType.CONFIRM_WORK_BY_DAY, sDate, sDate);
			//指定社員の中間データを作成する（月別）
			creDailyAppr.createDailyApprover(data.getEmployeeId(), RecordRootType.CONFIRM_WORK_BY_MONTH, sDate, sDate);
		}

	}
	/**
	 * Add new history
	 * @param root
	 * @param lstAddHist
	 * @param rootType
	 * @param startDate
	 * @param endDate
	 * @param approvalId
	 */
	private void addApproval(CompanyAppRootADto commonRoot, String approvalId
			//, String branchId
			){
		if(commonRoot == null){
			return;
		}
		List<ApprovalPhase> listAppPhase = new ArrayList<>();
		this.deleteAppPh(approvalId);
		ApprovalPhase appPhaseN1 = this.checkAppPh(commonRoot.getAppPhase1(), approvalId);
		ApprovalPhase appPhaseN2 = this.checkAppPh(commonRoot.getAppPhase2(), approvalId);
		ApprovalPhase appPhaseN3 = this.checkAppPh(commonRoot.getAppPhase3(), approvalId);
		ApprovalPhase appPhaseN4 = this.checkAppPh(commonRoot.getAppPhase4(), approvalId);
		ApprovalPhase appPhaseN5 = this.checkAppPh(commonRoot.getAppPhase5(), approvalId);
		if(appPhaseN1 != null){
			listAppPhase.add(appPhaseN1);
		}
		if(appPhaseN2 != null){
			listAppPhase.add(appPhaseN2);
		}
		if(appPhaseN3 != null){
			listAppPhase.add(appPhaseN3);
		}
		if(appPhaseN4 != null){
			listAppPhase.add(appPhaseN4);
		}
		if(appPhaseN5 != null){
			listAppPhase.add(appPhaseN5);
		}
		for (ApprovalPhase phase : listAppPhase) {
			repoApprover.addAllApprover(phase.getApprovalId(), phase.getPhaseOrder(), phase.getApprovers());
		}
		repoAppPhase.addAllApprovalPhase(listAppPhase);
	}

	/**
	 * TH: update
	 * update root display in screen
	 */
	private void updateRoot(List<AppType> lstAppTypeUi, List<CompanyAppRootADto> root){
		//update root display in screen
		for (AppType type : lstAppTypeUi) {
			CompanyAppRootADto commonRoot = this.findRoot(root, type);
			String approvalId = commonRoot.getApprovalId();
			if(StringUtil.isNullOrEmpty(commonRoot.getHistoryId(), true)){
				continue;
			}
			//xoa app Phase
			this.deleteAppPh(approvalId);
			ApprovalPhase appPhaseN1 = this.checkAppPh(commonRoot.getAppPhase1(), approvalId);
			ApprovalPhase appPhaseN2 = this.checkAppPh(commonRoot.getAppPhase2(), approvalId);
			ApprovalPhase appPhaseN3 = this.checkAppPh(commonRoot.getAppPhase3(), approvalId);
			ApprovalPhase appPhaseN4 = this.checkAppPh(commonRoot.getAppPhase4(), approvalId);
			ApprovalPhase appPhaseN5 = this.checkAppPh(commonRoot.getAppPhase5(), approvalId);
			//Xu ly them,sua,xoa appPh and approver
			if(appPhaseN1 != null){
				this.addAppPhase(appPhaseN1, approvalId);
			}
			if(appPhaseN2 != null && appPhaseN2.getApprovalForm().value != 0){
				this.addAppPhase(appPhaseN2, approvalId);
				this.addAppPhase(appPhaseN2, approvalId);
			}
			if(appPhaseN3 != null && appPhaseN3.getApprovalForm().value != 0){
				this.addAppPhase(appPhaseN3, approvalId);
			}
			if(appPhaseN4 != null && appPhaseN4.getApprovalForm().value != 0){
				this.addAppPhase(appPhaseN4, approvalId);
			}
			if(appPhaseN5 != null && appPhaseN5.getApprovalForm().value != 0){
				this.addAppPhase(appPhaseN5, approvalId);
			}
		}
	}
	/**
	 * add appPhase
	 * @param appPhaseN1
	 * @param approvalId
	 */
	private void addAppPhase(ApprovalPhase appPhaseN1, String approvalId){
		if(appPhaseN1 == null){
			return;
		}
		Optional<ApprovalPhase> appPh1 = repoAppPhase.getApprovalPhase(approvalId, appPhaseN1.getPhaseOrder());
		if(!appPh1.isPresent()){//add new appPh and Approver
			List<Approver>  approvers = appPhaseN1.getApprovers();
			repoApprover.addAllApprover(appPhaseN1.getApprovalId(), appPhaseN1.getPhaseOrder(), approvers);
			repoAppPhase.addApprovalPhase(appPhaseN1);
		}
	}
	/**
	 * check AppPhase(add or not add)
	 * @param appPhase
	 * @param approvalId
	 * @return
	 */
	private ApprovalPhase checkAppPh(ApprovalPhaseDto appPhase, String approvalId){
		if(appPhase.getApprovalForm() == null || appPhase.getApprovalForm().intValue() == 0){//khong co
			return null;
		}
		List<Approver> lstApp = new ArrayList<>();
		List<ApproverDto> approver = appPhase.getApprover();
		for (ApproverDto approverDto : approver) {
			lstApp.add(Approver.createSimpleFromJavaType(approverDto.getApproverOrder(), approverDto.getJobGCD(), 
					approverDto.getEmployeeId(), approverDto.getConfirmPerson(), approverDto.getSpecWkpId()));
		}
		return ApprovalPhase.createSimpleFromJavaType(approvalId,
				appPhase.getPhaseOrder(), 
				// branchId, 
				appPhase.getApprovalForm(),
				appPhase.getBrowsingPhase(), appPhase.getApprovalAtr(), lstApp);
	}
	/**
	 * find root
	 * @param root
	 * @param appTypeValue
	 * @return
	 */
	private CompanyAppRootADto findRoot(List<CompanyAppRootADto> root, AppType appTypeValue){
		for (CompanyAppRootADto comRoot : root) {
			if(comRoot.getEmployRootAtr() == EmploymentRootAtr.COMMON.value && comRoot.getEmployRootAtr() == appTypeValue.getEmployRootAtr()) {
				return comRoot;
			}
			if(comRoot.getAppTypeValue() == appTypeValue.getValue() && comRoot.getEmployRootAtr() == appTypeValue.getEmployRootAtr()){
				return comRoot;
			}
		}
		return null;
	}
	/**
	 * delete
	 * @param approvalId
	 */
	private void deleteAppPh(String approvalId){
		List<ApprovalPhase> lstAppPhase = repoAppPhase.getAllApprovalPhasebyCode(approvalId);
		if(!lstAppPhase.isEmpty()){
			for (ApprovalPhase phase : lstAppPhase) {
				//delete All Approver By Approval Phase Id
				repoApprover.deleteAllApproverByAppPhId(approvalId, phase.getPhaseOrder());
			}
			//delete All Approval Phase By approvalId
			repoAppPhase.deleteAllAppPhaseByApprovalId(approvalId);
		}
	}
	private boolean checkContain(List<AppType> lstType, AppType type) {
		if(type.getEmployRootAtr() == 0) {
			for(AppType appType : lstType) {
				if(appType.getEmployRootAtr() == 0) {
					return true;
				}
			}
			return false;
		}
		return lstType.contains(type);
	}
}
