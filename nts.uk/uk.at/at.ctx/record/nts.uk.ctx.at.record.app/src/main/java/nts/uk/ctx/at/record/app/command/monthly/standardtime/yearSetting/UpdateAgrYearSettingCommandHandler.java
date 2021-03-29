package nts.uk.ctx.at.record.app.command.monthly.standardtime.yearSetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 特別条項設定を更新登録する（年度）
 */
@Stateless
public class UpdateAgrYearSettingCommandHandler extends CommandHandler<RegisterAgrYearSettingCommand> {

    @Inject
    private AgreementYearSettingRepository repo;

    @Override
    protected void handle(CommandHandlerContext<RegisterAgrYearSettingCommand> context) {

        RegisterAgrYearSettingCommand command = context.getCommand();

        //2: set(会社ID,年月,１ヶ月時間)
        AgreementYearSetting setting = new AgreementYearSetting(command.getEmployeeId(),command.getYear(),
                OneYearErrorAlarmTime.of(new AgreementOneYearTime(command.getErrorTime()),new AgreementOneYearTime(command.getAlarmTime())));
        repo.update(setting);
    }
}
