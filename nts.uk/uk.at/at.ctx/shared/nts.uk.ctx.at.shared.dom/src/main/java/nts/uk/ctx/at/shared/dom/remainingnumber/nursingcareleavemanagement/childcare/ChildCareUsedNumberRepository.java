package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare;

import java.util.List;
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
	 * 子の看護介護使用数データの検索
	 * @param employeeId 社員IDリスト
	 * @return 該当する子の看護休暇使用数データ
	 */
	List<ChildCareUsedNumberData> find(List<String> employeeIds);

	/**
	 * 登録および更新
	 * @param domain 子の看護使用数データ
	 */
	void persistAndUpdate(String employeeId, ChildCareUsedNumberData domain);

	/**
	 * 新規登録
	 * @param obj
	 */
	void add(String cId, ChildCareUsedNumberData obj);

	/**
	 * 新規登録
	 * @param cid　会社ID
	 * @param childCareDataInsert　子の看護休暇使用数データリスト
	 */
	void addAll(String cid, List<ChildCareUsedNumberData> childCareDataInsert);

	/**
	 * 更新
	 * @param cid　会社ID
	 * @param childCareDataUpdate　子の看護休暇使用数データ
	 */
	void update(String cid, ChildCareUsedNumberData childCareDataUpdate);

	/**
	 * 更新
	 * @param cid　会社ID
	 * @param childCareDataUpdate　子の看護休暇使用数データリスト
	 */
	void updateAll(String cid, List<ChildCareUsedNumberData> childCareDataUpdate);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 */
	void remove(String employeeId);
}
