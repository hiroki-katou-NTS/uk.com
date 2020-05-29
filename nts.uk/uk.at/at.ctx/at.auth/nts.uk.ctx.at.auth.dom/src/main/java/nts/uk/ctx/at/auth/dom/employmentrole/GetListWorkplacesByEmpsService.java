package nts.uk.ctx.at.auth.dom.employmentrole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.ClosureTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;

/**
 * 指定社員の指定締めに就業確定できる職場一覧を取得する	
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.権限管理.就業ロール.指定社員の指定締めに就業確定できる職場一覧を取得する
 * @author Laitv
 */
public class GetListWorkplacesByEmpsService {
	
	/**
	 * [1] 取得する
	 * @param  require
	 * @param  cid 会社ID
	 * @param  sid 社員ID
	 * @param  closureId Optional<締めID>
	 * @return List<職場ID>
	 */
	public static List<String> get(Require require,String companyId, String employeeId, Optional<Integer> closureId ) {
		
		// if 締めID.isEmpty return [prv-1] 全締から職場確定できる職場を取得する(require, 会社ID, 社員ID)
		if (!closureId.isPresent()) {
			return getWorkplacefromAllParties(require, companyId, employeeId);
					
		}
		
		// return [prv-2] 指定締めから職場確定できる職場を取得する(require, 会社ID, 社員ID, 締めID)
		return getWorkplace(require, companyId, employeeId, closureId.get());
	}
	
	/**
	 * [prv-1] 全締から職場確定できる職場を取得する
	 * @param require
	 * @param cid
	 * @param sid
	 * @return List<職場ID>
	 */
	public static List<String> getWorkplacefromAllParties(Require require, String companyId, String employeeId) {
		
		List<String> result = new ArrayList<>();
		
		// 	$締め情報リスト = require.全締めの当月期間を取得する()
		List<ClosureInfo> listClosureInfo =  require.getCurrentMonthForAllClosure();
		if (listClosureInfo.isEmpty()) {
			return new ArrayList<>();
		}
		
		listClosureInfo.forEach(closure -> {
			List<String> listWplId  = GetListOfWorkplacesService.get(require, companyId, closure.getClosureId().value, employeeId, closure.getPeriod().end());
			result.addAll(listWplId); 
		});
		
		return result;
	}
	
	
	/**
	 * [prv-2] 指定締めから職場確定できる職場を取得する
	 * @param require
	 * @param cid
	 * @param sid
	 * @param  closureId 締めID
	 * @return List<年月日>
	 */
	public static List<String> getWorkplace(Require require, String companyId, String employeeId, Integer closureId) {
		
		List<String> listWplId = new ArrayList<>();
		
		// 	$基準日 = require.当月期間を取得する(締めID) map $.終了年月日
		ClosureTime closureTime = require.getCurrentMonthPeriod(closureId);
		if (closureTime != null) {
			GeneralDate endDate = closureTime.getEnd();
			listWplId = GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, endDate);
		}
		return listWplId;
	}
	
	public static interface Require extends  GetListOfWorkplacesService.Require{
		
		/**
		 * [R-1] 全締めの当月期間を取得する
		 */
		List<ClosureInfo> getCurrentMonthForAllClosure();
		
		/**
		 * [R-2] 当月期間を取得する
		 */
		ClosureTime getCurrentMonthPeriod(Integer closureId);
		
	}
	
	
//	@RequiredArgsConstructor
//	private static class RequireImpl implements GetListOfWorkplacesService.Require {
//		
//		@Inject
//		private UserAuthAdapter userAuthAdapter;
//		
//		@Inject
//		private RoleAdaptor roleAdaptor;
//		
//		@Inject
//		private WorkPlaceAuthorityRepository workPlaceAuthRepo;
//		
//		@Inject
//		private AuthWorkPlaceAdapter authWorkPlaceAdapter;
//		
//		@Override
//		public Optional<String> getUserID(String employeeId) {
//			Optional<String> result = userAuthAdapter.getUserIDByEmpID(employeeId);
//			return result;
//		}
//
//		@Override
//		public Optional<RollInformation> getRole(String userID, int roleType, GeneralDate referenceDate, String companyId) {
//			
//			RoleInformationImport dataImport =  roleAdaptor.getRoleIncludCategoryFromUserID(userID, roleType, referenceDate, companyId);
//			if (dataImport == null) {
//				return Optional.empty();
//			}
//			
//			return Optional.of(new RollInformation(dataImport.getRoleCharge(), dataImport.getRoleId()));
//		}
//
//		@Override
//		public Optional<WorkPlaceAuthority> getWorkAuthority(String roleId, String companyId, Integer functionNo) {
//			
//			Optional<WorkPlaceAuthority> result = workPlaceAuthRepo.getWorkPlaceAuthorityById(companyId, roleId, functionNo);
//			return result;
//		}
//
//		@Override
//		public OptionalInt getEmployeeReferenceRange(String roleId) {
//			
//			OptionalInt result = roleAdaptor.findEmpRangeByRoleID(roleId);
//			return result;
//		}
//
//		@Override
//		public List<String> getListWorkPlaceIDNoWkpAdmin(GeneralDate referenceDate, int empRange, String employeeID) {
//			
//			List<String> result = authWorkPlaceAdapter.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange, referenceDate);
//			return result;
//		}
//
//		@Override
//		public List<WorkplaceManagerDto> getWorkplaceManager(String employeeId, GeneralDate baseDate) {
//			
//			List<WorkplaceManagerImport> dataImport = authWorkPlaceAdapter.findListWkpManagerByEmpIdAndBaseDate(employeeId, baseDate);
//			if (dataImport.isEmpty()) {
//				return new ArrayList<>();
//			}
//			
//			List<WorkplaceManagerDto> result = dataImport.stream().map(i -> {
//				WorkplaceManagerDto export = new WorkplaceManagerDto(i.getWorkplaceManagerId(), i.getEmployeeId(), i.getWorkplaceId(), i.getHistoryPeriod());
//				return export;
//			}).collect(Collectors.toList());
//			
//			return result;
//		}
//	}
}
