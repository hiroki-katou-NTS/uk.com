package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 運用 screen
 */
@Stateless
public class AgreementOperationSettingFinder {

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	public AgreementOperationSettingDto find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementOperationSettingDto agreementOperationSettingDto = new AgreementOperationSettingDto();

		Optional<AgreementOperationSetting> agreementOperationSetting = this.agreementOperationSettingRepository
				.find(companyId);

		if (agreementOperationSetting.isPresent()) {
			agreementOperationSettingDto.setAlarmListAtr(agreementOperationSetting.get().getAlarmListAtr().value);
			agreementOperationSettingDto.setClosingDateAtr(agreementOperationSetting.get().getClosingDateAtr().value);
			agreementOperationSettingDto.setClosingDateType(agreementOperationSetting.get().getClosingDateType().value);
			agreementOperationSettingDto
					.setNumberTimesOverLimitType(agreementOperationSetting.get().getNumberTimesOverLimitType().value);
			agreementOperationSettingDto.setStartingMonth(agreementOperationSetting.get().getStartingMonth().value);
			agreementOperationSettingDto
					.setYearlyWorkTableAtr(agreementOperationSetting.get().getYearlyWorkTableAtr().value);
		} else {
			return null;
		}

		return agreementOperationSettingDto;
	}
}
