package nts.uk.ctx.pr.proto.dom.personalinformation.wage;

import java.util.Date;
import java.util.List;

public interface PersonalWageRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYm
	 * @return list of personal wage
	 */
	List<PersonalWage> find(String companyCode, List<String> personIdList, Date baseYm);
}
