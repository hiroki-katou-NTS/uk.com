package nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class RegisterAgreeOperationSetCommandHandler extends CommandHandler<RegisterAgreeOperationSetCommand> {

    @Inject
    private AgreementOperationSettingRepository agreementOperationSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<RegisterAgreeOperationSetCommand> context) {
        RegisterAgreeOperationSetCommand command = context.getCommand();
        Optional<AgreementOperationSetting> agreementOperationSettingOld = agreementOperationSettingRepository.find(AppContexts.user().companyId());
        AgreementOperationSetting agreementOperationSetting = new AgreementOperationSetting(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getStartingMonth(), StartingMonthType.class) ,
                new ClosureDate(command.getClosureDay(),command.getLastDayOfMonth()),
                command.getSpecialConditionApplicationUse(),
                command.getYearSpecicalConditionApplicationUse()
        );
        if (agreementOperationSettingOld.isPresent()){
            this.agreementOperationSettingRepository.update(agreementOperationSetting);
        }else {
            this.agreementOperationSettingRepository.add(agreementOperationSetting);
        }
    }
}
