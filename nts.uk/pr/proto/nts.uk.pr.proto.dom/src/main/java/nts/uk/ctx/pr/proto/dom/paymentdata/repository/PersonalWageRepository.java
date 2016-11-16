package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.pr.proto.dom.personalinformation.wage.PersonalWage;

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
