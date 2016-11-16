package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.pr.proto.dom.personalinformation.commute.PersonalCommuteFee;

public interface PersonalCommuteFeeRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param baseYM
	 * @return list personal commutes
	 */
	List<PersonalCommuteFee> find(String companyCode, String personId, Date baseYM);
}
