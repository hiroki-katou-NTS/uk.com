package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;


/**
 *　リポジトリ：子の看護休暇使用数データ
  * @author yuri_tamakoshi
 */
public interface ChildCareNurseUsedNumberRepository {

	/**
	 * 子の看護介護使用数データの検索
	 * @param employeeId 社員ID
	 * @return 該当する子の看護休暇使用数データ
	 */
	Optional<ChildCareNurseUsedNumber> find(String employeeId);

	/**
	 * 登録および更新
	 * @param domain 子の看護使用数データ
	 */
	void persistAndUpdate(String employeeId, ChildCareNurseUsedNumber domain);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 */
	void remove(String employeeId);

}
