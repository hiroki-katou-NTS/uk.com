package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

public interface AgreementTimeOfEmploymentRepostitory {
	
	void add(AgreementTimeOfEmployment agreementTimeOfEmployment);
	
	void update(AgreementTimeOfEmployment agreementTimeOfEmployment);
	
	void remove(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr);
	
	Optional<String> findEmploymentBasicSettingId(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr);

	List<String> findEmploymentSetting(String companyId, LaborSystemtAtr laborSystemAtr);

	Optional<AgreementTimeOfEmployment> find(String companyId, String employmentCategoryCode,
			LaborSystemtAtr laborSystemAtr);
	
	List<AgreementTimeOfEmployment> findEmploymentSetting(String comId, List<String> employmentCategoryCode);
}
