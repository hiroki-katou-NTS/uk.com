package nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UpdateAgreeOperationSetCommandHandler extends CommandHandler<UpdateAgreeOperationSetCommand> {

    @Inject
    private AgreementOperationSettingRepository agreementOperationSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateAgreeOperationSetCommand> context) {
        UpdateAgreeOperationSetCommand command = context.getCommand();

        AgreementOperationSetting agreementOperationSetting = new AgreementOperationSetting(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getStartingMonth(), StartingMonthType.class) ,
                new ClosureDate(command.getClosureDay(),command.getLastDayOfMonth()),
                command.getSpecicalConditionApplicationUse(),
                command.getYearSpecicalConditionApplicationUse()
        );
        this.agreementOperationSettingRepository.update(agreementOperationSetting);
    }
}
