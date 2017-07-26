package nts.uk.ctx.at.shared.dom.dailypattern;

import java.util.List;

public interface DailyPatternValRepository {

	   /**
   	 * Find by pattern cd.
   	 *
   	 * @param patternCd the pattern cd
   	 * @return the list
   	 */
   	List<DailyPatternVal> findByPatternCd(String cid, String patternCd);
	
}
