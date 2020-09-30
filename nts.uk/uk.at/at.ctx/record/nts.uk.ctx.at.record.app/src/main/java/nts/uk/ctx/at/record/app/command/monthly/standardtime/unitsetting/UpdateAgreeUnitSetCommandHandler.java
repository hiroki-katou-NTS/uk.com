package nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting.UpdateAgreeOperationSetCommand;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UpdateAgreeUnitSetCommandHandler extends CommandHandler<UpdateAgreeUnitSetCommand> {

    @Inject
    private AgreementUnitSettingRepository unitSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateAgreeUnitSetCommand> context) {
        UpdateAgreeUnitSetCommand command = context.getCommand();

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getClassificationUseAtr(), UseClassificationAtr.class),
                EnumAdaptor.valueOf(command.getEmploymentUseAtr(), UseClassificationAtr.class),
                EnumAdaptor.valueOf(command.getWorkPlaceUseAtr(), UseClassificationAtr.class));

        this.unitSettingRepository.update(agreementUnitSetting);
    }
}
