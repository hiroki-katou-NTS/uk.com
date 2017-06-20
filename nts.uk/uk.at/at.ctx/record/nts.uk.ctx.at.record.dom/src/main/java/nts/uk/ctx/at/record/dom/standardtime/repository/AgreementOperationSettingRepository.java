package nts.uk.ctx.at.record.dom.standardtime.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;

public interface AgreementOperationSettingRepository {

	Optional<AgreementOperationSetting> find(String companyId);

	void add(AgreementOperationSetting agreementOperationSetting);

	void update(String companyId, int startingMonth, int numberTimesOverLimitType, int closingDateType,
			int closingDateAtr, int yearlyWorkTableAtr, int alarmListAtr);
}
