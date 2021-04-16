package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare;

import java.util.Optional;


/**
 *　リポジトリ：子の看護休暇使用数データ
 */
public interface ChildCareUsedNumberRepository{

	/**
	 * 子の看護介護使用数データの検索
	 * @param employeeId 社員ID
	 * @return 該当する子の看護休暇使用数データ
	 */
	Optional<ChildCareUsedNumberData> find(String employeeId);

	/**
	 * 登録および更新
	 * @param domain 子の看護使用数データ
	 */
	void persistAndUpdate(String employeeId, ChildCareUsedNumberData domain);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 */
	void remove(String employeeId);
}
