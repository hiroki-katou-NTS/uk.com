package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.pr.proto.dom.personalinformation.employmentcontract.PersonalEmploymentContract;

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
