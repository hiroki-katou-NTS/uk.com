package nts.uk.screen.at.app.query.kdl.kdl006.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleInformationImport;
import nts.uk.ctx.at.auth.dom.adapter.user.UserAuthAdapter;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.auth.dom.adapter.workplace.WorkplaceManagerImport;
import nts.uk.ctx.at.auth.dom.employmentrole.GetListWorkplacesByEmpsService;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.ClosureInformation;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.ClosureTime;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.RollInformation;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.WorkplaceManagerDto;
import nts.uk.ctx.at.auth.dom.kmk013.WorkPlaceAuthority;
import nts.uk.ctx.at.auth.dom.kmk013.WorkPlaceAuthorityRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**UKDesign.UniversalK.就業.KDL_ダイアログ.KDL014_打刻参照ダイアログ.メニュー別OCD.打刻実績を参照する.打刻実績の取得処理*/
@Stateless
public class WorkConfirmationFinder {

	@Inject
	private ClosureRepository closureRepository;

	public void DisplayOfWorkConfirmationDialog() {
		String cid = AppContexts.user().companyId();
		List<Closure> listClosure = closureRepository.findAllUse(cid);
	}
	
	public void get(int closureId) {
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().companyId();
		List<String> workplace = GetListWorkplacesByEmpsService.get(new RequireImpl(), cid, employeeId, Optional.of(closureId));
	}
	
	private class RequireImpl implements GetListWorkplacesByEmpsService.Require {
		
		@Inject
		private ClosureService closureService;
		
		@Inject
		private ClosureRepository closureRepository;
		
		@Inject
		private UserAuthAdapter userAuthAdapter;
		
		@Inject
		private RoleAdaptor roleAdaptor;
		
		@Inject
		private WorkPlaceAuthorityRepository workPlaceAuthRepo;
		
		@Inject
		private AuthWorkPlaceAdapter authWorkPlaceAdapter;
		
		@Inject
		private AuthWorkPlaceAdapter workPlaceAdapter;
		
		@Override
		public Optional<String> getUserID(String employeeId) {
			return userAuthAdapter.getUserIDByEmpID(employeeId);
		}
		
		@Override
		public Optional<RollInformation> getRole(String userID, int roleType, GeneralDate referenceDate, String companyId) {
			RoleInformationImport dataImport =  roleAdaptor.getRoleIncludCategoryFromUserID(userID, roleType, referenceDate, companyId);
			if (dataImport == null) {
				return Optional.empty();
			}
			return Optional.of(new RollInformation(dataImport.getRoleCharge(), dataImport.getRoleId()));
		}
		
		@Override
		public Optional<WorkPlaceAuthority> getWorkAuthority(String roleId, String companyId, Integer functionNo) {
			Optional<WorkPlaceAuthority> result = workPlaceAuthRepo.getWorkPlaceAuthorityById(companyId, roleId, functionNo);
			return result;
		}
		
		@Override
		public OptionalInt getEmployeeReferenceRange(String roleId) {
			OptionalInt result = roleAdaptor.findEmpRangeByRoleID(roleId);
			return result;
		}
		@Override
		public List<String> getListWorkPlaceIDNoWkpAdmin(GeneralDate referenceDate, int empRange, String employeeID) {
			List<String> result = authWorkPlaceAdapter.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange, referenceDate);
			return result;
		}
		
		@Override
		public List<WorkplaceManagerDto> getWorkplaceManager(String employeeID, GeneralDate baseDate) {
			List<WorkplaceManagerImport> dataImport = authWorkPlaceAdapter.findListWkpManagerByEmpIdAndBaseDate(employeeID, baseDate);
			if (dataImport.isEmpty()) {
				return new ArrayList<>();
			}
			
			List<WorkplaceManagerDto> result = dataImport.stream().map(i -> {
				WorkplaceManagerDto export = new WorkplaceManagerDto(i.getWorkplaceManagerId(), i.getEmployeeId(), i.getWorkplaceId(), i.getHistoryPeriod());
				return export;
			}).collect(Collectors.toList());
			
			return result;
		}
		
		@Override
		public List<AffWorkplaceHistoryItemImport> getAffiliatedEmployees(String workplaceId, GeneralDate baseDate) {
			List<AffWorkplaceHistoryItemImport> listWkpItem =  workPlaceAdapter.getWorkHisItemfromWkpIdAndBaseDate(workplaceId, baseDate);
			return listWkpItem;
		}
		
		@Override
		public List<ClosureInformation> getProcessCloseCorrespondToEmps(List<String> sIds, GeneralDate baseDate) {
			List<ClosureInformation> result = new ArrayList<>();
			for (String employeeId : sIds) {
				Closure closure = closureService.getClosureDataByEmployee(employeeId, baseDate);
				ClosureInformation closureInformation = new ClosureInformation(employeeId, closure!=null ? closure.getClosureId().value : null);
				result.add(closureInformation);
			}
			return result;
		}
		
		@Override
		public List<ClosureInfo> getCurrentMonthForAllClosure() {
			return closureService.getAllClosureInfo();
		}
		
		//指定した締めの当月期間を算出する
		@Override
		public Optional<DatePeriod> getCurrentMonthPeriod(Integer closureId) {
			String companyId = AppContexts.user().companyId();
			Optional<Closure> closure = closureRepository.findById(companyId, closureId);
			if(closure.isPresent()) {
				CurrentMonth currentMonth = closure.get().getClosureMonth();
				return Optional.of(closureService.getClosurePeriod(closureId, currentMonth.getProcessingYm()));
			}
			return Optional.empty();
		}

	}
}
