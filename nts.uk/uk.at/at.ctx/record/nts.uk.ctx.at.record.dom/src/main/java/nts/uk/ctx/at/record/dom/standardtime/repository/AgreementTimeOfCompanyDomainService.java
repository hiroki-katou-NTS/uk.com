package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

public interface AgreementTimeOfCompanyDomainService {
	
	List<String> add(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfCompany agreementTimeOfCompany);
	
	List<String> update(BasicAgreementSetting basicAgreementSetting, AgreementTimeOfCompany agreementTimeOfCompany);

}
