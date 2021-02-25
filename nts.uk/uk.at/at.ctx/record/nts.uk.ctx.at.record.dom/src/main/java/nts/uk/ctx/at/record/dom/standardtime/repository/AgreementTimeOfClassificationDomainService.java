package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

public interface AgreementTimeOfClassificationDomainService {
	
	List<String> add(AgreementTimeOfClassification agreementTimeOfClassification, BasicAgreementSetting basicAgreementSetting);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfClassification agreementTimeOfClassification);
	
	void remove(String companyId, int laborSystemAtr, String classificationCode, String basicSettingId);
}
