package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

public interface AgreementTimeOfCompanyDomainService {
	
	List<String> add(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfCompany agreementTimeOfCompany);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting);

}
