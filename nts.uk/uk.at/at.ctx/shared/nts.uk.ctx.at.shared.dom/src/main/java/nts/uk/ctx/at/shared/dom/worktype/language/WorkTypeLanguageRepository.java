package nts.uk.ctx.at.shared.dom.worktype.language;

import java.util.List;

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
	 * Insert Work Type Language
	 * 
	 * @param workTypeLanguage
	 */
	void insert(WorkTypeLanguage workTypeLanguage);
}
