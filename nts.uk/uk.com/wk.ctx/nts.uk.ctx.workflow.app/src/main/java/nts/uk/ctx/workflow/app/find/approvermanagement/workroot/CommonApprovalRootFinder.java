package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class CommonApprovalRootFinder {
	@Inject
	private CompanyAdapter comAdapter;
	@Inject
	private PersonApprovalRootRepository repo;
	@Inject
	private CompanyApprovalRootRepository repoCom;
	@Inject
	private WorkplaceApprovalRootRepository repoWorkplace;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	
	public CommonApprovalRootDto getAllCommonApprovalRoot(ParamDto param){
		return getAllDataApprovalRoot(param);
	}
	public CommonApprovalRootDto getPrivate(ParamDto param){
//		CommonApprovalRootDto root = getAllDataApprovalRoot(param);
//		//TH: company - domain 会社別就業承認ルート
//		if(param.getRootType()==0){
//			
//		}
		return getAllDataApprovalRoot(param);
	}
	private CommonApprovalRootDto getAllDataApprovalRoot(ParamDto param){
		//user contexts
		String companyId = AppContexts.user().companyId();
		//get name company
		Optional<CompanyInfor> companyCurrent = comAdapter.getCurrentCompany();
		String companyName = companyCurrent == null ? "" : companyCurrent.get().getCompanyName();
		//TH: company - domain 会社別就業承認ルート
		if(param.getRootType() == 0){
			List<CompanyAppRootDto> lstComRoot = new ArrayList<>();
			//get all data from ComApprovalRoot (会社別就業承認ルート)
			List<ComApprovalRootDto> lstCom = this.repoCom.getAllComApprovalRoot(companyId)
								.stream()
								.map(c->ComApprovalRootDto.fromDomain(c))
								.collect(Collectors.toList());
			for (ComApprovalRootDto companyApprovalRoot : lstCom) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<ApproverDto> lstApprover = new ArrayList<ApproverDto>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, companyApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
								.stream()
								.map(c->ApproverDto.fromDomain(c))
								.collect(Collectors.toList());
					//lst (ApprovalPhase + lst Approver)
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
				//add in lstAppRoot
				lstComRoot.add(new CompanyAppRootDto(companyApprovalRoot,lstApprovalPhase));
			}
			List<ObjDate> result = this.groupingMapping(lstComRoot);
			if(result.isEmpty()){
				return null;
			}
			return new CommonApprovalRootDto(companyName,lstComRoot, null, null,result);
		}
		//TH: workplace - domain 職場別就業承認ルート
		if(param.getRootType() == 1){
			List<WorkPlaceAppRootDto> lstWpRoot = new ArrayList<>();
			List<ObjDate> result = new ArrayList<>();
			//get all data from WorkplaceApprovalRoot (職場別就業承認ルート)
			List<WpApprovalRootDto> lstWp = this.repoWorkplace.getAllWpApprovalRoot(companyId, param.getWorkplaceId())
					.stream()
					.map(c->WpApprovalRootDto.fromDomain(c))
					.collect(Collectors.toList());
			for (WpApprovalRootDto workplaceApprovalRoot : lstWp) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<ApproverDto> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, workplaceApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
							.stream()
							.map(c->ApproverDto.fromDomain(c))
							.collect(Collectors.toList());
					//lst (ApprovalPhase + lst Approver)
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
				//add in lstAppRoot
				lstWpRoot.add(new WorkPlaceAppRootDto(workplaceApprovalRoot,lstApprovalPhase));
			}
			return new CommonApprovalRootDto(companyName, null, lstWpRoot, null, result);
		}
		//TH: person - domain 個人別就業承認ルート
		else{
			List<PersonAppRootDto> lstPsRoot = new ArrayList<>();
			List<ObjDate> result = new ArrayList<>();
			//get all data from PersonApprovalRoot (個人別就業承認ルート)
			List<PsApprovalRootDto> lstPs = this.repo.getAllPsApprovalRoot(companyId, param.getEmployeeId())
					.stream()
					.map(c->PsApprovalRootDto.fromDomain(c))
					.collect(Collectors.toList());
			for (PsApprovalRootDto personApprovalRoot : lstPs) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<ApproverDto> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, personApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
							.stream()
							.map(c->ApproverDto.fromDomain(c))
							.collect(Collectors.toList());
					//lst (ApprovalPhase + lst Approver)
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
				//add in lstAppRoot
				lstPsRoot.add(new PersonAppRootDto(personApprovalRoot,lstApprovalPhase));
			}
			return new CommonApprovalRootDto(companyName, null, null, lstPsRoot, result);
		}
	}
	/**
	 * grouping history
	 * @param lstRoot
	 * @return
	 */
	private List<ObjDate> groupingMapping(List<CompanyAppRootDto> lstRoot){
		List<ComApprovalRootDto> origin = new ArrayList<ComApprovalRootDto>();
		List<ObjDate> result = new ArrayList<ObjDate>();
		lstRoot.forEach(item ->{
			origin.add(item.getCompany());
		});
		origin.forEach(date1 -> {
			origin.forEach(date2 -> {
				//ktra date2 co de len date1 k?
				//neu dung thi add va nhay den phan tu tiep theo
				//neu khong thi nhay xuong else
				if (date1.getApprovalId() != date2.getApprovalId() && isOverlap(date1,date2)) {
					result.add(new ObjDate(date1.getStartDate(), date1.getEndDate()));
					System.out.println("them "+date1);
				}
				//ktra list result co date1 chua?
				//chua co thi add, co roi thi thoi
				else if (!result.contains(date1)) {
					result.add(new ObjDate(date1.getStartDate(), date1.getEndDate()));
					System.out.println("co vao ko");
				}
			});
		});
		return result;
		
	}
	
	/**
	 * ktra date2 co nam trong date1 k? 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public boolean isOverlap(ComApprovalRootDto date1, ComApprovalRootDto date2){
		//date1=date2 (sDate1 = sDate2 && eDate1 = eDate2)
//		if(date1.getStartDate().compareTo(date2.getStartDate()) == 0 && date1.getEndDate().compareTo(date2.getEndDate()) == 0){
//			return false;
//		}
//		//date2 nam ngoai date1(sDate1 > eDate2 or eDate1 < sDate2)
//		if(date1.getStartDate().compareTo(date2.getEndDate()) < 0 || date1.getEndDate().compareTo(date2.getStartDate())<0){
//			return false;
//		}
		/**
		 * date 1.........|..............|..........
		 * date 2............|......................
		 */
		if (date2.getStartDate().compareTo(date1.getStartDate()) >= 0
				&& date2.getStartDate().compareTo(date1.getEndDate()) <= 0) {
			//System.out.println(date2+" de len "+date1);
			return true;
		}
		/**
		 * date 1.........|..............|..........
		 * date 2.....|........|....................
		 */
		if (date2.getStartDate().compareTo(date1.getStartDate()) <= 0
				&& date2.getEndDate().compareTo(date1.getStartDate()) >= 0) {
			//System.out.println(date2+" de len "+date1);
			return true;
		}
		//System.out.println(date2+" zxczxcv khong de len "+date1);
		return false;
	}

	/**
	 * Checks if is same date.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if is same date
	 */
	public boolean isSameDate(ComApprovalRootDto date1, ComApprovalRootDto date2){
		if (date2.getStartDate().compareTo(date1.getStartDate()) == 0
				&& date2.getEndDate().compareTo(date1.getEndDate()) == 0) {
			//System.out.println(date2+" trung lap "+date1);
			return true;
		}
		//System.out.println(date2+" khong trung lap "+date1);
		return false;
	}
}
