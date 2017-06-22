package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfEmployment;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

public interface AgreementTimeOfEmploymentRepostitory {
	
	void add(AgreementTimeOfEmployment agreementTimeOfEmployment);
	
	void remove(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr);
	
	List<AgreementTimeOfEmployment> find(String companyId, LaborSystemtAtr laborSystemAtr);

}
