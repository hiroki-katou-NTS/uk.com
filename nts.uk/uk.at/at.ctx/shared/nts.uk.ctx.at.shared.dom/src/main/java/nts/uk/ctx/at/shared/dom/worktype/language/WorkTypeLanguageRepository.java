package nts.uk.ctx.at.shared.dom.worktype.language;

import java.util.List;
import java.util.Optional;

/**
 * Interface Work Type Repository
 * 
 * @author sonnh1
 *
 */
public interface WorkTypeLanguageRepository {

	/**
	 * 
	 * @param companyId
	 * @param langId
	 * @return List WorkTypeLanguage
	 */
	public List<WorkTypeLanguage> findByCIdAndLangId(String companyId, String langId);

	/**
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param langId
	 * @return
	 */
	public Optional<WorkTypeLanguage> findById(String companyId, String workTypeCode, String langId);

	/**
	 * Add Work Type Language
	 * 
	 * @param workTypeLanguage
	 */
	void add(WorkTypeLanguage workTypeLanguage);

	/**
	 * Update Work Type Language
	 * 
	 * @param workTypeLanguage
	 */
	void update(WorkTypeLanguage workTypeLanguage);
}
