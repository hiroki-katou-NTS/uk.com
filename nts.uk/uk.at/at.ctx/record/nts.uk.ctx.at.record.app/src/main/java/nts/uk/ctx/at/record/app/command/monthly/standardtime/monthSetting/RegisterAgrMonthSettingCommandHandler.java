package nts.uk.ctx.at.record.app.command.monthly.standardtime.monthSetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.yearSetting.RegisterAgrYearSettingCommand;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 特別条項設定を更新登録する（年月）
 */
@Stateless
public class RegisterAgrMonthSettingCommandHandler extends CommandHandler<RegisterAgrMonthSettingCommand> {

    @Inject
    private AgreementMonthSettingRepository repo;

    @Override
    protected void handle(CommandHandlerContext<RegisterAgrMonthSettingCommand> context) {

        RegisterAgrMonthSettingCommand command = context.getCommand();

        //1: set(会社ID,年月,1年間のエラーアラーム時間)
        AgreementMonthSetting setting = new AgreementMonthSetting(command.getEmployeeId(),new YearMonth(command.getYearMonth()),
                OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(command.getErrorTime()),new AgreementOneMonthTime(command.getAlarmTime())));
        repo.add(setting);
    }
}
