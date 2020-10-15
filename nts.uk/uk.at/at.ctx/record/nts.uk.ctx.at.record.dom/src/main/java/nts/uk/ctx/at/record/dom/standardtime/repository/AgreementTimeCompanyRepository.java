package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

public interface AgreementTimeCompanyRepository {

	void add(AgreementTimeOfCompany agreementTimeOfCompany);
	
	Optional<AgreementTimeOfCompany> find(String companyId, LaborSystemtAtr laborSystemAtr);
	
	void update(AgreementTimeOfCompany agreementTimeOfCompany);
	
	List<AgreementTimeOfCompany> find(String companyId);
	
}
