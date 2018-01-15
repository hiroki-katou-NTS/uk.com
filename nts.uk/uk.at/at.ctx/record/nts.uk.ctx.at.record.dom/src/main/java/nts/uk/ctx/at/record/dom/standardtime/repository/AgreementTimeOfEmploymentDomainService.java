package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfEmployment;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

public interface AgreementTimeOfEmploymentDomainService {
	
	List<String> add(BasicAgreementSetting setting, AgreementTimeOfEmployment agreementTimeOfEmployment);
	
	void remove(String companyId, String employmentCategoryCode, int laborSystemtAtr, String basicSettingId);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting);
}
