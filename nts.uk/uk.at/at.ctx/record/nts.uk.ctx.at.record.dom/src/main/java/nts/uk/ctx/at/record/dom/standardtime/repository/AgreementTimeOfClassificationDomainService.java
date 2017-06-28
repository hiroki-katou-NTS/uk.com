package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfClassification;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

public interface AgreementTimeOfClassificationDomainService {
	
	List<String> add(AgreementTimeOfClassification agreementTimeOfClassification, BasicAgreementSetting basicAgreementSetting);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting);
	
	void remove(String companyId, int laborSystemAtr, String classificationCode, String basicSettingId);
}
