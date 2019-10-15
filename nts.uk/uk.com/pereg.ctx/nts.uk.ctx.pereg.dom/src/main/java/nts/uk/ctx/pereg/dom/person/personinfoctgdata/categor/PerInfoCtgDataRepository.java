/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor;

import java.util.List;
import java.util.Optional;

/**
 * @author danpv
 *
 */
public interface PerInfoCtgDataRepository {
	
	public Optional<PerInfoCtgData> getByRecordId(String recordId);
	
	public List<PerInfoCtgData> getByPerIdAndCtgId(String perId, String ctgId);
	
	/**
	 * Add person info category data ドメインモデル「個人情報カテゴリデータ」を新規登録する
	 * @param data
	 */
	void addCategoryData(PerInfoCtgData data);
	
	/**
	 * Update person info category data ドメインモデル「個人情報カテゴリデータ」を更新する
	 * @param data
	 */
	void updateCategoryData(PerInfoCtgData data);
	/**
	 * Delete person info category data
	 * @param data
	 */
	void deleteCategoryData(PerInfoCtgData data);
	
	/**
	 * @author lanlt
	 * get thông tin person data theo sids, ctgId
	 * @param pids
	 * @param ctgId
	 * @return
	 */
	public List<PerInfoCtgData> getAllByPidsAndCtgId(List<String> pids, String ctgId);
	
	/**
	 * @author lanlt
	 * Add persons info category data ドメインモデル「個人情報カテゴリデータ」を新規登録する
	 * @param domains
	 */
	void addAll(List<PerInfoCtgData> domains);
	
	/**
	 * @author lanlt
	 * Update persons info category data ドメインモデル「個人情報カテゴリデータ」を更新する
	 * @param domains
	 */
	void updateAll(List<PerInfoCtgData> domains);

}
