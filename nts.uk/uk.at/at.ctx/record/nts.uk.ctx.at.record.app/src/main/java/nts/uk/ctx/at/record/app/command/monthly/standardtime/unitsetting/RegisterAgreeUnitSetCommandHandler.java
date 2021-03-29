package nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class RegisterAgreeUnitSetCommandHandler extends CommandHandler<RegisterAgreeUnitSetCommand> {

    @Inject
    private AgreementUnitSettingRepository unitSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<RegisterAgreeUnitSetCommand> context) {
        RegisterAgreeUnitSetCommand command = context.getCommand();
        Optional<AgreementUnitSetting> agreementUnitSettingOld = unitSettingRepository.find(AppContexts.user().companyId());

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getClassificationUseAtr(), UseClassificationAtr.class),
                EnumAdaptor.valueOf(command.getEmploymentUseAtr(), UseClassificationAtr.class),
                EnumAdaptor.valueOf(command.getWorkPlaceUseAtr(), UseClassificationAtr.class));

        if (agreementUnitSettingOld.isPresent()){
            this.unitSettingRepository.update(agreementUnitSetting);
        }else {
            this.unitSettingRepository.add(agreementUnitSetting);
        }
    }
}
