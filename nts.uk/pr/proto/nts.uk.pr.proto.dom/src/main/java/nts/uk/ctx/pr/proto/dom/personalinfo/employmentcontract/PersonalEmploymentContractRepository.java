package nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonalEmploymentContractRepository {
	
	Optional<PersonalEmploymentContract> find(String companyCode, String personId, LocalDate baseYmd);
	
	/**
	 * 
	 * @param companyCode
	 * @param personIdList
	 * @param baseYmd
	 * @return list employment contract of list person with base times.
	 */
	List<PersonalEmploymentContract> findAll(String companyCode, List<String> personIdList, LocalDate baseYmd);
}
