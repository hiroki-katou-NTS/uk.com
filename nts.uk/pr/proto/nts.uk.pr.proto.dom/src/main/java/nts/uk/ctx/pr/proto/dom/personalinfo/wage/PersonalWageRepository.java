package nts.uk.ctx.pr.proto.dom.personalinfo.wage;

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
	List<PersonalWage> find(String companyCode, String personId, Date baseYm);
}
