package nts.uk.ctx.at.record.app.command.monthly.standardtime.monthSetting;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

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

        //1: get(社員ID,年度) : ３６協定年月設定
        Optional<AgreementMonthSetting> agreementMonthSetting = repo.findByKey(command.getEmployeeId(), new YearMonth(command.getYearMonth()));

        //2:[Not 年月.empty]
        if (agreementMonthSetting.isPresent()){
            throw new BusinessException("Msg_61");
        }

        //3: create(会社ID,年月,1年間のエラーアラーム時間)
        AgreementMonthSetting setting = new AgreementMonthSetting(command.getEmployeeId(),new YearMonth(command.getYearMonth()),
                OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(command.getErrorTime()),new AgreementOneMonthTime(command.getAlarmTime())));
        repo.add(setting);
    }
}
