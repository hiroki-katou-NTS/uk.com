package nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn;

import java.util.List;
import java.util.Optional;

/**
 * The Interface PensionAvgearnRepository.
 */
public interface PensionAvgearnRepository {

	/**
	 * Adds the.
	 *
	 * @param pensionAvgearn the pension avgearn
	 */
	void add(PensionAvgearn pensionAvgearn);

	/**
	 * Update.
	 *
	 * @param pensionAvgearn the pension avgearn
	 */
	void update(PensionAvgearn pensionAvgearn);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 */
	void remove(String id);

	/**
	 * Find.
	 *
	 * @param historyId the history id
	 * @return the list
	 */
	List<PensionAvgearn> find(String historyId);

	/**
	 * Find.
	 *
	 * @param historyId the history id
	 * @param levelCode the level code
	 * @return the pension avgearn
	 */
	Optional<PensionAvgearn> find(String historyId, Integer levelCode);

}
