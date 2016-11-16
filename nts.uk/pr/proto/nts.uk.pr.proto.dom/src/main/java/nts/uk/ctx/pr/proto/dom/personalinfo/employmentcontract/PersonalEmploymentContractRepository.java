package nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract;

import java.util.Date;
import java.util.List;

public interface PersonalEmploymentContractRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personIdList
	 * @param baseYmd
	 * @return list employment contract of list person with base times.
	 */
	List<PersonalEmploymentContract> find(String companyCode, List<String> personIdList, Date baseYmd);
}
