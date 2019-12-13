package nts.uk.ctx.at.shared.dom.scherec.totaltimes.language;

import java.util.List;
/**
 * 
 * @author phongtq
 *
 */
import java.util.Optional;
public interface TotalTimesLangRepository {

	void add (TotalTimesLang totalTimesLang);
	
	void update (TotalTimesLang totalTimesLang);
	
	public List<TotalTimesLang> findAll (String companyId, String langId);
	
	public Optional<TotalTimesLang> findById (String companyId, Integer totalTimesNo, String langId);
}
