package nts.uk.ctx.at.shared.dom.worktime.language;

import java.util.List;
import java.util.Optional;

/**
 * Interface Work Time Repository
 * 
 * @author sonnh1
 *
 */
public interface WorkTimeLanguageRepository {

	/**
	 * 表示中勤務時間帯の変更
	 * 
	 * @param companyId
	 * @param langId
	 * @return
	 */
	public List<WorkTimeLanguage> findByCIdAndLangId(String companyId, String langId);
	
	/**
	 * 
	 * @param companyId
	 * @param langId
	 * @param workTimeCode
	 * @return
	 */
	public Optional<WorkTimeLanguage> findById(String companyId, String langId, String workTimeCode);
	
	/**
	 * 
	 * @param workTimeLanguage
	 */
	public void insert(WorkTimeLanguage workTimeLanguage);
	
	/**
	 * 
	 * @param workTimeLanguage
	 */
	public void update(WorkTimeLanguage workTimeLanguage);
	
	/**
	 * 対応するドメインモデル「就業時間帯の他言語表示名」を削除する
	 * 
	 * @param companyId
	 * @param langId
	 * @param workTimeCode
	 */
	public void delete(String companyId, String langId, String workTimeCode);

}
