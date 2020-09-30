package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 画面表示を行う
 */
@Stateless
public class AgreeOpeSetScreenProcessor {

    @Inject
    private AgreementOperationSettingRepository operationSettingRepository;

    public AgreementOperationSettingDto findDataAgreeOpeSet() {

        Optional<AgreementOperationSetting> data = operationSettingRepository.find(AppContexts.user().companyId());

        return AgreementOperationSettingDto.setData(data);
    }
}
