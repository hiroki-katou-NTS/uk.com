package nts.uk.ctx.at.function.ac.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.record.pub.standardtime.AgreementOperationSettingPub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;

/**
 * @author dat.lh
 *
 */
@Stateless
public class AgreementOperationSettingAcFinder implements AgreementOperationSettingAdapter {

	@Inject
	private AgreementOperationSettingPub agreementOperationSettingPub;

	@Override
	public Optional<AgreementOperationSettingImport> find(String cid) {
		Optional<AgreementOperationSetting> dataOpt = agreementOperationSettingPub.find(cid);
		if (dataOpt.isPresent()) {
			/** TODO: 36協定時間対応により、コメントアウトされた */
//			AgreementOperationSetting data = dataOpt.get();
//			return Optional.of(new AgreementOperationSettingImport(data.getStartingMonth().value,
//					data.getNumberTimesOverLimitType().value, data.getClosingDateType().value,
//					data.getClosingDateAtr().value, data.getYearlyWorkTableAtr().value, data.getAlarmListAtr().value));
		}
		return Optional.empty();
	}
}
