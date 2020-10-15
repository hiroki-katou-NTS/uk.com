package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

public interface AgreementTimeOfClassificationRepository {

	void add(AgreementTimeOfClassification agreementTimeOfClassification);
	
	void update(AgreementTimeOfClassification agreementTimeOfClassification);
	
	void remove(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode);
	
	List<AgreementTimeOfClassification> find(String companyId, LaborSystemtAtr laborSystemAtr);
	
	List<String> findClassificationSetting(String companyId, LaborSystemtAtr laborSystemAtr);
	
	Optional<String> findEmploymentBasicSettingID(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode);
	
	Optional<AgreementTimeOfClassification> find(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode);
	
	List<AgreementTimeOfClassification> find(String companyId, List<String> classificationCode);
}
