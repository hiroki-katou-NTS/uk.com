package nts.uk.ctx.pr.proto.dom.personalinfo.wage;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersonalWageRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYm
	 * @return list of personal wage
	 */
	List<PersonalWage> findAll(String companyCode, List<String> personIdList, Date baseYm);
	
	Optional<PersonalWage> find(String companyCode, String personId, int categoryAttribute, String wageCode, int startYearMonth);
}
