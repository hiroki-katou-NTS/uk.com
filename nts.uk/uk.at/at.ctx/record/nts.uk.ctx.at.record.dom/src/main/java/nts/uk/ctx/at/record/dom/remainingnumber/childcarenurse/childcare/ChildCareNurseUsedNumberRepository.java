package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.Optional;


/**
 *　リポジトリ：子の看護休暇使用数データ
  * @author yuri_tamakoshi
 */
public interface ChildCareNurseUsedNumberRepository {

	/**
	 * 子の看護介護使用数データの検索
	 * @param employeeId 社員ID
	 * @param nursingCategory 介護看護区分
	 * @return 該当する子の看護休暇使用数データ
	 */
	Optional<ChildCareNurseUsedNumber> find(String employeeId);

	/**
	 * 登録および更新
	 * @param domain 子の看護介護使用数データ
	 */
	void persistAndUpdate(String employeeId, ChildCareNurseUsedNumber domain);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param nursingType 介護看護区分
	 */
	void remove(String employeeId);

}
