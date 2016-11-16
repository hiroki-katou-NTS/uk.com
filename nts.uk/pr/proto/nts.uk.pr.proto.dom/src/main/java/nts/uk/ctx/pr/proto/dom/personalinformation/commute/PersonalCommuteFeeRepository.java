package nts.uk.ctx.pr.proto.dom.personalinformation.commute;

import java.util.Date;
import java.util.List;

public interface PersonalCommuteFeeRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYM
	 * @return list personal commutes
	 */
	List<PersonalCommuteFee> find(String companyCode, List<String> personIdList, Date baseYM);
}
