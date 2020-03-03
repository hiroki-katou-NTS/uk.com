package nts.uk.ctx.at.shared.dom.scherec.totaltimes.language;

import java.util.List;
/**
 * 
 * @author phongtq
 *
 */
import java.util.Optional;
public interface TotalTimesLangRepository {

	/**
	 * add Total Times Lang
	 * @param totalTimesLang
	 */
	void add (TotalTimesLang totalTimesLang);
	
	/**
	 * update Total Times Lang
	 * @param totalTimesLang
	 */
	void update (TotalTimesLang totalTimesLang);
	
	/**
	 * findAll Total Times Lang
	 * @param companyId
	 * @param langId
	 * @return
	 */
	public List<TotalTimesLang> findAll (String companyId, String langId);
	
	/**
	 * findById Total Times Lang
	 * @param companyId
	 * @param totalTimesNo
	 * @param langId
	 * @return
	 */
	public Optional<TotalTimesLang> findById (String companyId, Integer totalTimesNo, String langId);
}
