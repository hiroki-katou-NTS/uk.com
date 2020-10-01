package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

public interface AgreementTimeOfEmploymentDomainService {
	
	List<String> add(BasicAgreementSetting setting, AgreementTimeOfEmployment agreementTimeOfEmployment);
	
	void remove(String companyId, String employmentCategoryCode, int laborSystemtAtr, String basicSettingId);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfEmployment agreementTimeOfEmployment);
}
