package nts.uk.ctx.at.auth.dom.employmentrole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.RollInformation;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.WorkplaceManagerDto;
import nts.uk.ctx.at.auth.dom.kmk013.WorkPlaceAuthority;

/**
 * 指定社員の基準日に就業確定できる職場一覧を取得する
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.権限管理.就業ロール.指定社員の基準日に就業確定できる職場一覧を取得する
 * @author Laitv
 */


public class GetListOfWorkplacesService {
	
	/**
	 * [1] 取得する
	 * @param require
	 * @param companyId  会社ID
	 * @param closureId  締めID
	 * @param employeeId 社員ID
	 * @param baseDate   基準日
	 * @return List<職場ID> List<Workplace ID>
	 */
	public static List<String> get(Require require, String companyId, Integer closureId, String employeeId, GeneralDate baseDate) {
		
		// Set<職場ID> $職場リスト
		Set<String> workplaceIds = new HashSet<>();
		
		// $参照職場リスト = [prv-1] ロールから就業確定できる職場を取得する(require, 会社ID, 社員ID, 基準日)	
		List<String> workplaceIdsOfPrv1 = getWorkplaceWhereConfirmWorkFromRole(require, companyId, employeeId, baseDate);
		
		// $職場リスト.add($参照職場リスト)
		workplaceIds.addAll(workplaceIdsOfPrv1);
		
		// $管理職場リスト = [prv-2] 管理職場を取得する(require, 社員ID, 基準日)
		List<String> workplaceIdsOfPrv2 = getManagedWorkplace(require, employeeId, baseDate);
		
		// $職場リスト.add($管理職場リスト)	
		workplaceIds.addAll(workplaceIdsOfPrv2);
		
		return GetWorkplacesService.get(require, closureId, workplaceIds.stream().collect(Collectors.toList()), baseDate);
	}
	
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * @param require
	 * @param companyId  会社ID
	 * @param employeeId 社員ID
	 * @param baseDate   基準日
	 * @return List<職場ID>
	 */
	private static List<String> getWorkplaceWhereConfirmWorkFromRole(Require require, String companyId, String employeeId, GeneralDate baseDate) {
		
		// $ユーザID = require.ユーザIDを取得する(社員ID)	
		Optional<String> userId =  require.getUserID( employeeId );
		if (!userId.isPresent()) {
			return new ArrayList<>();  
		}
		
		//$ロール種類 = 就業 
		int rollType = RoleType.EMPLOYMENT.value;
		
		// $ロール情報 = require.ロールを取得する($ユーザID, $ロール種類, 基準日, 会社ID)	
		Optional<RollInformation> rollInformation = require.getRole(userId.get(), rollType, baseDate, companyId);
		if (!rollInformation.isPresent()) {
			return new ArrayList<>();
		}
		
		// 	$機能NO = 2
		int functionNo = 2;
		
		// $所属職場権限 = require.所属職場権限を取得する($ロール情報.ロールID, 会社ID, $機能NO)					
		Optional<WorkPlaceAuthority> workPlaceAuthority = require.getWorkAuthority(rollInformation.get().getRoleId(), companyId, functionNo);
		
		// 	if $所属職場権限.isEmpty OR (not $所属職場権限.利用できる／できない権限の設定.利用できる)				
		//return Optional.empty	
		
		if ((!workPlaceAuthority.isPresent())) { 
			return new ArrayList<>();
		}
		
		if (workPlaceAuthority.get().isAvailability() == false) { 
			return new ArrayList<>();  
		} 
		
		// 	<社員参照範囲>$社員参照範囲 = 部門・職場（配下含む）
		int empRange = EmployeeReferenceRange.DEPARTMENT_AND_CHILD.value; 
		
		// 	if not $ロール情報.担当ロールか																	
		//$社員参照範囲 = require.社員参照範囲を取得する($ロール情報.ロールID)
		if (rollInformation.get().getRoleCharge() == false) {
			OptionalInt optEmpRange = require.getEmployeeReferenceRange(rollInformation.get().getRoleId());
			if (optEmpRange.isPresent()) {
				empRange = optEmpRange.getAsInt();
			}
		}
		
		List<String> workplaceIds = require.getListWorkPlaceIDNoWkpAdmin(  baseDate,  empRange, employeeId );
		
		return workplaceIds;
	}


	/**
	 * [prv-2] 管理職場を取得する
	 * @param require
	 * @param employeeID  社員ID
	 * @param baseDate    基準日
	 * @return List<職場ID>
	 */
	private static List<String> getManagedWorkplace(Require require, String employeeId, GeneralDate baseDate) {
		
		List<WorkplaceManagerDto> listWorkplaceManager  = require.getWorkplaceManager(employeeId, baseDate);
		
		if (listWorkplaceManager.isEmpty()) {
			return new ArrayList<String>();
		} 
		
		List<String> workplaceIds = listWorkplaceManager.stream().distinct().map(i -> i.getWorkplaceId()).collect(Collectors.toList());
		
		return workplaceIds;
	}
	
	
	public static interface Require extends GetWorkplacesService.Require{
		
		/**
		 * [R-1] ユーザIDを取得する
		 * @param employeeId
		 * @return userId
		 * imp search --> 社員IDからユーザIDを取得する
		 */
		Optional<String> getUserID(String employeeId);

		
		/**
		 * [R-2] ロールを取得する
		 * @param userID
		 * @param roleType
		 * @param referenceDate
		 * @param companyId
		 * @return  RollInformation
		 */
		Optional<RollInformation> getRole(String userID, int roleType, GeneralDate referenceDate, String companyId);
		
		
		/**
		 * [R-3] 所属職場権限を取得する
		 * @param roleId
		 * @param companyId
		 * @param functionNo
		 * @return WorkPlaceAuthority
		 */
		Optional<WorkPlaceAuthority> getWorkAuthority(String roleId,  String companyId, Integer functionNo);
		
		
		/**
		 * [R-4] 社員参照範囲を取得する
		 * @param roleId
		 * @return OptionalInt
		 */
		OptionalInt getEmployeeReferenceRange(String roleId);
		
		/**
		 * [R-5] 参照可能な職場リストを取得する
		 * @param referenceDate
		 * @param empRange
		 * @param employeeID
		 * @return ListWorkPlaceID
		 */
		List<String> getListWorkPlaceIDNoWkpAdmin( GeneralDate referenceDate, int empRange,String employeeID );
		
		/**
		 * [R-6] 職場管理者を取得する
		 * @param employeeID
		 * @param baseDate
		 * @return List<WorkplaceManagerDto>
		 */
		List<WorkplaceManagerDto> getWorkplaceManager(String employeeID, GeneralDate baseDate );
	}
	
//	@RequiredArgsConstructor
//	private static class RequireImpl implements GetWorkplacesService.Require {
//		
//		@Inject
//		private AuthWorkPlaceAdapter workPlaceAdapter;
//		
//		@Inject
//		private ClosureService closureService;
//		
//		@Override
//		public List<AffWorkplaceHistoryItemImport> getAffiliatedEmployees(String workplaceId, GeneralDate baseDate) {
//			// 職場と基準日から所属職場履歴項目を取得する
//			List<AffWorkplaceHistoryItemImport> listWkpItem =  workPlaceAdapter.getWorkHisItemfromWkpIdAndBaseDate(workplaceId, baseDate);
//			return listWkpItem;
//		}
//
//		@Override
//		public List<ClosureInformation> getProcessCloseCorrespondToEmps(List<String> sIds, GeneralDate baseDate) {
//			
//			// 社員(list)に対応する処理締めを取得する (Lấy closure xử lý ứng với employee(list))
//			List<ClosureInformation> result = new ArrayList<>();
//			for (String employeeId : sIds) {
//				Closure closure = closureService.getClosureDataByEmployee(employeeId, baseDate);
//				ClosureInformation closureInformation = new ClosureInformation(employeeId, closure!=null ? closure.getClosureId().value : null);
//				result.add(closureInformation);
//			}
//			return result;
//		}
//	}
}
