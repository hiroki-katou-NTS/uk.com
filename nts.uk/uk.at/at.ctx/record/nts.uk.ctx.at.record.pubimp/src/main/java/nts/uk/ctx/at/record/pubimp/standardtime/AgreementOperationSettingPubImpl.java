package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.AgreementOperationSettingDto;
import nts.uk.ctx.at.record.pub.standardtime.AgreementOperationSettingPub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AgreementOperationSettingPubImpl implements AgreementOperationSettingPub {

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	public Optional<AgreementOperationSettingDto> find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		AgreementOperationSettingDto agreementOperationSettingDto = new AgreementOperationSettingDto();
		Optional<AgreementOperationSetting> agreementOperationSetting = this.agreementOperationSettingRepository
				.find(companyId);

		agreementOperationSettingDto.setStartingMonth(agreementOperationSetting.get().getStartingMonth().value);
		return Optional.ofNullable(agreementOperationSettingDto);
	}
}
