package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;

public interface AgreementUnitSettingRepository {
	
	Optional<AgreementUnitSetting> find(String companyId);
	
	void add(AgreementUnitSetting agreementUnitSetting);
	
	void update(AgreementUnitSetting agreementUnitSetting);
	
}
