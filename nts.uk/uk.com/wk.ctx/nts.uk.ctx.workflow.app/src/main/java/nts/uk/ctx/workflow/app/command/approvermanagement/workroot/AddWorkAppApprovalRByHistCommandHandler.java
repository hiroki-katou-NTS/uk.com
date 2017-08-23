package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
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
public class AddWorkAppApprovalRByHistCommandHandler extends CommandHandler<AddWorkAppApprovalRByHistCommand>{

	@Inject
	private WorkAppApprovalRootRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddWorkAppApprovalRByHistCommand> context) {
		String companyId = AppContexts.user().companyId();
		String historyId = UUID.randomUUID().toString();
		String branchId = UUID.randomUUID().toString();
		AddWorkAppApprovalRByHistCommand  addItem = context.getCommand();
		String startDate = addItem.getStartDate();
		GeneralDate sDate = GeneralDate.localDate(LocalDate.parse(startDate));
		GeneralDate eDate = sDate.addDays(-1);
		String endDateNew = eDate.toString();
		String endDate = "9999/12/31";
		if(addItem.getCheck()==1){//company
			CompanyApprovalRoot comAppRoot = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
					historyId,
					addItem.getApplicationType(),
					addItem.getStartDate(),
					addItem.getEndDate(),
					branchId,
					addItem.getAnyItemApplicationId(),
					addItem.getConfirmationRootType(),
					addItem.getEmploymentRootAtr());
			if(addItem.getCopyOrNew()==1){//copy
				List<CompanyApprovalRoot> lstOld= repo.getComApprovalRootByEdate(companyId, endDate);
				if(lstOld.isEmpty()){
					repo.addComApprovalRoot(comAppRoot);
				}else{
					CompanyApprovalRoot com = lstOld.get(0);
					CompanyApprovalRoot comAppRootNew = CompanyApprovalRoot.createSimpleFromJavaType(companyId,
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
			}else{//new
				repo.addComApprovalRoot(comAppRoot);
			}
		}
		if(addItem.getCheck()==2){//workplace
			WorkplaceApprovalRoot wpAppRoot = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
					addItem.getWorkplaceId(),
					historyId,
					addItem.getApplicationType(),
					addItem.getStartDate(),
					addItem.getEndDate(),
					branchId,
					addItem.getAnyItemApplicationId(),
					addItem.getConfirmationRootType(),
					addItem.getEmploymentRootAtr());
			if(addItem.getCopyOrNew()==1){//copy
				List<WorkplaceApprovalRoot> lstWp = repo.getWpApprovalRootByEdate(companyId, addItem.getWorkplaceId(), endDate);
				if(lstWp.isEmpty()){
					repo.addWpApprovalRoot(wpAppRoot);
				}else{
					WorkplaceApprovalRoot wp = lstWp.get(0);
					WorkplaceApprovalRoot wpAppRootNew = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
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
					WorkplaceApprovalRoot wpAppRootPre = WorkplaceApprovalRoot.createSimpleFromJavaType(companyId,
							addItem.getWorkplaceId(),
							historyId,
							addItem.getApplicationType(),
							addItem.getStartDate(),
							endDate,
							wp.getBranchId(),
							addItem.getAnyItemApplicationId(),
							addItem.getConfirmationRootType(),
							addItem.getEmploymentRootAtr());
					repo.updateWpApprovalRoot(wpAppRootPre);
				}
			}else{//new
				repo.addWpApprovalRoot(wpAppRoot);
			}
		}
		if(addItem.getCheck()==3){//person
			PersonApprovalRoot psAppRoot = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					addItem.getEmployeeId(),
					historyId,
					addItem.getApplicationType(),
					addItem.getStartDate(),
					addItem.getEndDate(),
					branchId,
					addItem.getAnyItemApplicationId(),
					addItem.getConfirmationRootType(),
					addItem.getEmploymentRootAtr());
			if(addItem.getCopyOrNew()==1){//copy
				List<PersonApprovalRoot> lstPs= repo.getPsApprovalRootByEdate(companyId, addItem.getEmployeeId(), endDate);
				if(lstPs.isEmpty()){
					repo.addPsApprovalRoot(psAppRoot);
				}else{
					PersonApprovalRoot ps = lstPs.get(0);
					PersonApprovalRoot psAppRootNew = PersonApprovalRoot.createSimpleFromJavaType(companyId,
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
					PersonApprovalRoot psAppRootPre = PersonApprovalRoot.createSimpleFromJavaType(companyId,
							addItem.getEmployeeId(),
							historyId,
							addItem.getApplicationType(),
							addItem.getStartDate(),
							endDate,
							ps.getBranchId(),
							addItem.getAnyItemApplicationId(),
							addItem.getConfirmationRootType(),
							addItem.getEmploymentRootAtr());
					repo.updatePsApprovalRoot(psAppRootPre);
				}
			}else{//new
				repo.addPsApprovalRoot(psAppRoot);
			}
		}
	}
}
