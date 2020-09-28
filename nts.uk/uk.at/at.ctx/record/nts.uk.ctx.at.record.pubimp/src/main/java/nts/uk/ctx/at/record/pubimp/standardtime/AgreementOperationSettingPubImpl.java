package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.pub.standardtime.AgreementOperationSettingPub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;

@Stateless
public class AgreementOperationSettingPubImpl implements AgreementOperationSettingPub {
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	@Override
	public Optional<AgreementOperationSetting> find(String cid) {
		return this.agreementOperationSettingRepository.find(cid);
	}
}
