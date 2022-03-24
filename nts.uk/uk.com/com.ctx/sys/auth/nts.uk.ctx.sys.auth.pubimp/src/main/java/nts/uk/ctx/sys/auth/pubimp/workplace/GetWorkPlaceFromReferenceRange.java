package nts.uk.ctx.sys.auth.pubimp.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.pub.wkpmanager.ReferenceableWorkplace;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;

/**
 * 
 * @author sonnlb
 * 
 *         参照範囲から参照できる職場・社員を取得する
 */
@Stateless
public class GetWorkPlaceFromReferenceRange {

	@Inject
	private WorkplaceListPub workplaceListPub;

	/**
	 * [1] 取得する
	 * @param require
	 * @param employeeID 社員ID
	 * @param date 年月日
	 * @param range 社員参照範囲
	 * @return
	 */
	public ReferenceableWorkplace get(Require require, String employeeID, GeneralDate date, Integer range) {

		// if 社員参照範囲 == 自分のみ
		if (range == EmployeeReferenceRangeExport.ONLY_MYSELF.value) {
			// return [prv-1] 自分の所属職場取得する(require,社員ID,年月日)
			return this.getWorkPlace(require, employeeID, date);
		}

		// return [prv-2] 参照可能職場を取得する(require,社員参照範囲,年月日)
		return this.GetReferenceableWorkplace(require, range, date, employeeID);

	}

	/**
	 * [prv-1] 自分の所属職場取得する(require,社員ID,年月日)
	 * @param require
	 * @param employeeID 社員ID
	 * @param date 年月日
	 * @return 参照可能職場
	 */
	private ReferenceableWorkplace getWorkPlace(Require require, String employeeID, GeneralDate date) {

		// $所属情報 = require.所属職場を取得する(社員ID,基準日)
		Map<String, String> infos = require.getAWorkplace(employeeID, date);
		// if not $所属情報.isPresent
		if (infos.size() == 0) {
			// BusinessException Msg_427
			throw new BusinessException("Msg_427");
		}

		// return 参照可能職場#参照可能職場($所属情報.value,$所属情報)

		return new ReferenceableWorkplace(new ArrayList<>(infos.values()), infos);

	}

	
	/**
	 * // [prv-2] 参照可能職場を取得する
	 * 
	 * @param require
	 * @param range 社員参照範囲
	 * @param date 年月日
	 * @param employeeID 社員ID
	 * @return 参照可能職場
	 */
	private ReferenceableWorkplace GetReferenceableWorkplace(Require require, Integer range, GeneralDate date,
			String employeeID) {

		// $職場リスト = 指定社員が参照可能な職場リストを取得する(基準日,社員参照範囲,社員ID)
		List<String> listWpkIds = this.workplaceListPub.getListWorkPlaceIDNoWkpAdmin(employeeID, range, date);

		// $所属情報 = require.所属職場リストを取得する(基準日,$職場リスト)
		Map<String, String> infos = require.getByListIds(listWpkIds, date);

		// return 参照可能職場#参照可能職場($職場リスト,$所属情報)

		return new ReferenceableWorkplace(listWpkIds, infos);

	}

	public static interface Require {

		/**
		 * [R-1] 所属職場を取得する
		 * 
		 * @param employeeID
		 *            社員
		 * @param date
		 *            年月日
		 * @return
		 */
		public Map<String, String> getAWorkplace(String employeeID, GeneralDate date);

		/**
		 * [R-2] 所属職場リストを取得する
		 * 
		 * @param sIds
		 *            List＜社員ID＞
		 * @param baseDate
		 *            基準日
		 * @return
		 */
		public Map<String, String> getByListIds(List<String> workPlaceIds, GeneralDate baseDate);

	}

}
