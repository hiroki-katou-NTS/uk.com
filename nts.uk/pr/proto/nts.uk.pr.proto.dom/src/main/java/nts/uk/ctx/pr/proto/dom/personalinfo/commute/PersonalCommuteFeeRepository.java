package nts.uk.ctx.pr.proto.dom.personalinfo.commute;

import java.util.List;
import java.util.Optional;

public interface PersonalCommuteFeeRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYM
	 * @return list personal commutes
	 */
	List<PersonalCommuteFee> findAll(String companyCode, List<String> personIdList, int baseYM);

	/**
	 * Get personal commute
	 * 
	 * @param companyCode
	 * @param personId
	 * @param startYearMonth
	 * @return
	 */
	Optional<PersonalCommuteFee> find(String companyCode, String personId, int startYearMonth);
}
