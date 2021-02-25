package nts.uk.screen.at.app.kmk.kmk008.unitsetting;

import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen kmk008 H : 初期表示を行う
 */
@Stateless
public class PerformInitDisplayProcessor {

    @Inject
    private AgreementUnitSettingRepository unitSettingRepository;

    public AgreementUnitSettingDto find() {

        Optional<AgreementUnitSetting> data = unitSettingRepository.find(AppContexts.user().companyId());

        return AgreementUnitSettingDto.setData(data);
    }

}
