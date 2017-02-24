package nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn;

import java.util.List;
import java.util.Optional;

/**
 * The Interface PensionAvgearnRepository.
 */
public interface PensionAvgearnRepository {

	/**
	 * Update.
	 *
	 * @param pensionAvgearns the pension avgearns
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 */
	void update(List<PensionAvgearn> pensionAvgearns, String ccd, String officeCd);

	/**
	 * Removes the.
	 *
	 * @param histId the hist id
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 * @param pensionGrade the pension grade
	 */
	void remove(String histId, String ccd, String officeCd, Integer pensionGrade);

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
	 * @param histId the hist id
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 * @param pensionGrade the pension grade
	 * @return the optional
	 */
	Optional<PensionAvgearn> find(String histId, String ccd, String officeCd, Integer pensionGrade);

}
