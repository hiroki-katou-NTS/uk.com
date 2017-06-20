package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

public interface AgreementTimeCompanyRepository {

	void add(AgreementTimeOfCompany agreementTimeOfCompany);
	
	List<AgreementTimeOfCompany> find(String companyId, LaborSystemtAtr laborSystemAtr);
	
}
