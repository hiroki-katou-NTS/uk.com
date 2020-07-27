package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;

/**
 * 社員が参照可能な職場グループと社員参照範囲を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".基幹.社員.職場.職場グループ
 * 
 * @author HieuLt
 *
 */
public class GetWorkplaceGroupsAndEmp {

	/**
	 * [1] 取得する
	 * 
	 * @param require
	 * @param baseDate
	 * @param empId
	 * @return
	 */
	public static Map<String, ScopeReferWorkplaceGroup> getWorkplaceGroup(Require require, GeneralDate baseDate,
			String empId) {
		return null;

	}

	/**
	 * [prv-1] 管理対象職場から取得する
	 * 
	 * @param require
	 * @param baseDate
	 * @param empId
	 * @return
	 */
	private Map<String, ScopeReferWorkplaceGroup> getFromManagedWorkplace(Require require, GeneralDate baseDate,
			String empId) {
		return null;
	}

	public static interface Require {
		// JpaWorkplaceGroupRespositorygetAll
		/**
		 * [R-1] 職場グループをすべて取得する 職場グループRepository.getAll( 会社ID )
		 * 
		 * @return
		 */
		List<WorkplaceGroup> getAll();

		/**
		 * [R-2] 指定職場の職場グループ所属情報を取得する 職場グループ所属情報Repository.*get( 会社ID,
		 * List<職場ID> )
		 * 
		 * @param WKPID
		 * @return
		 */
		List<AffWorkplaceGroup> getByListWKPID(List<String> WKPID);
		/**
		 * [R-3] 担当者かどうか ※ログイン社員が担当者かどうか
		 */

		/**
		 * [R-4] 社員参照範囲を取得する ※ログイン社員の社員参照範囲
		 */
		/**
		 * [R-5] 指定社員の管理職場をすべて取得する アルゴリズム.指定社員の管理職場をすべて取得する( 社員ID, 年月日 )
		 */

	}
}
