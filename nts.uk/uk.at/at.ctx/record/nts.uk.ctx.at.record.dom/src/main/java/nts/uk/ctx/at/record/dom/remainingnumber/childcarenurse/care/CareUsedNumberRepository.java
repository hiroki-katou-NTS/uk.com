package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

/**
 *　リポジトリ：介護休暇使用数データ
  * @author yuri_tamakoshi
 */
public interface CareUsedNumberRepository {

	/**
	 * 子の看護介護使用数データの検索
	 * @param employeeId 社員ID
	 * @return 該当する介護休暇使用数データ
	 */
	Optional<ChildCareNurseUsedNumber> find(String employeeId);

	/**
	 * 登録および更新
	 * @param domain 介護休暇使用数データ
	 */
	void persistAndUpdate(String employeeId, ChildCareNurseUsedNumber domain);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 */
	void remove(String employeeId);

}