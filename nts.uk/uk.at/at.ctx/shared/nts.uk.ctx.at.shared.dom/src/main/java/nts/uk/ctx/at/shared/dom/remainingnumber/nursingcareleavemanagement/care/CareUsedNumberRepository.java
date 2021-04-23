package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;


/**
 * リポジトリ：介護休暇使用数データ
 * @author masaaki_jinno
 *
 */
public interface CareUsedNumberRepository {

	/**
	 * 介護使用数データの検索
	 * @param employeeId 社員ID
	 * @return 該当する介護休暇使用数データ
	 */
	Optional<CareUsedNumberData> find(String employeeId);

	/**
	 * 介護使用数データの検索
	 * @param employeeIds 社員IDリスト
	 * @return 該当する介護休暇使用数データ
	 */
	List<CareUsedNumberData> find(List<String> employeeIds);

	/**
	 * 登録および更新
	 * @param domain 介護使用数データ
	 */
	void persistAndUpdate(String employeeId, CareUsedNumberData domain);

	/**
	 * 新規登録
	 * @param obj
	 */
	void add(String cId, CareUsedNumberData obj);

	/**
	 * 追加登録
	 * @param cid　会社ID
	 * @param childCareDataInsert 介護休暇使用数データリスト
	 */
	void addAll(String cid, List<CareUsedNumberData> careDataInsert);

	/**
	 * 更新
	 * @param cid　会社ID
	 * @param childCareDataUpdate　介護休暇使用数データ
	 */
	void update(String cid, CareUsedNumberData careDataUpdate);

	/**
	 * 更新
	 * @param cid　会社ID
	 * @param careDataUpdate　介護使用数データリスト
	 */
	void updateAll(String cid, List<CareUsedNumberData> careDataUpdate);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 */
	void remove(String employeeId);

}
