package nts.uk.ctx.bs.employee.app.find.employeeinfo.workplacegroup;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupGettingService;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupRespository;
import nts.uk.shr.com.context.AppContexts;


/**
 * <<Query>> 社員の所属職場グループを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.App.社員の所属職場グループを取得する
 * @author quytb
 *
 */
@Stateless
public class AffWorkplaceGroupEmployeeQuery {
	
	@Inject
	private WorkplaceGroupRespository workplaceGroupRespository;
	
	@Inject
	private AffWorkplaceHistoryItemRepository affWkpHistoryItemRepo;
	
	/** 職場グループ所属情報Repository **/
	@Inject
	private AffWorkplaceGroupRespository repoAffWorkplaceGroup;
	
	/** 社員の所属職場グループを取得する */
	public AffWorkplaceGroupDto getWorkplaceGroupOfEmployee(GeneralDate date){		
		String employeeId = AppContexts.user().employeeId();
		List<String> employeeIds = new ArrayList<String>();
		employeeIds.add(employeeId);	
		
		WorkplaceGroupGettingImpl require = new WorkplaceGroupGettingImpl(affWkpHistoryItemRepo, repoAffWorkplaceGroup);		
		List<EmployeeAffiliation> employeeAffiliations = WorkplaceGroupGettingService.get(require, date, employeeIds);	
		
		if(!CollectionUtil.isEmpty(employeeAffiliations)) {
			if(employeeAffiliations.get(0).getWorkplaceGroupID().isPresent()) {
				String WKPGRPID = employeeAffiliations.get(0).getWorkplaceGroupID().get();	
				String companyId = AppContexts.user().companyId();				
				return workplaceGroupRespository.getById(companyId, WKPGRPID).map(x -> new AffWorkplaceGroupDto(x.getWKPGRPName().v(),
						x.getWKPGRPID())).orElse(null);
			} else {
				return new AffWorkplaceGroupDto();
			}			
		} else {
			return new AffWorkplaceGroupDto();
		}
	}	
	
	@AllArgsConstructor
	private static class WorkplaceGroupGettingImpl implements WorkplaceGroupGettingService.Require{		
		@Inject
		private AffWorkplaceHistoryItemRepository affWkpHistoryItemRepo;
		
		/** 職場グループ所属情報Repository **/
		@Inject
		private AffWorkplaceGroupRespository repoAffWorkplaceGroup;		
		
		@Override
		public String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
			List<AffWorkplaceHistoryItem> itemLst = affWkpHistoryItemRepo.getAffWrkplaHistItemByEmpIdAndDate(date, employeeID);
			if(CollectionUtil.isEmpty(itemLst)) {
				return null;
			} else {
				return itemLst.get(0).getWorkplaceId();
			}			
		}

		@Override
		public List<AffWorkplaceGroup> getWGInfo(List<String> WKPID) {
			String companyId = AppContexts.user().companyId();
			List<AffWorkplaceGroup> affWorkplaceGroups = repoAffWorkplaceGroup.getByListWKPID(companyId, WKPID);
			return affWorkplaceGroups;
		}		
	}
}

