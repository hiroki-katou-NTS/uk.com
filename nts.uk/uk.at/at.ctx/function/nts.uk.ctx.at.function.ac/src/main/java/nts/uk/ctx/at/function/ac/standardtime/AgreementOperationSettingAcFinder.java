package nts.uk.ctx.at.function.ac.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.pub.standardtime.AgreementOperationSettingPub;

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
			AgreementOperationSetting data = dataOpt.get();
			return Optional.of(new AgreementOperationSettingImport(data.getStartingMonth().value,
					data.getNumberTimesOverLimitType().value, data.getClosingDateType().value,
					data.getClosingDateAtr().value, data.getYearlyWorkTableAtr().value, data.getAlarmListAtr().value));
		}
		return Optional.empty();
	}
}
