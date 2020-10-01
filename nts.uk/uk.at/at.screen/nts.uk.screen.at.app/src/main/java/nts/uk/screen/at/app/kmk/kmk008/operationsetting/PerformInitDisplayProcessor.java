package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Screen G : 初期表示を行う
 */
@Stateless
public class PerformInitDisplayProcessor {

    @Inject
    private AgreementOperationSettingRepository operationSettingRepository;

    public AgreementOperationSettingDto find() {

        Optional<AgreementOperationSetting> data = operationSettingRepository.find(AppContexts.user().companyId());

        return AgreementOperationSettingDto.setData(data);
    }

}
