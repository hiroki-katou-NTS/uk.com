package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.limitrule.AgreementMultiMonthAvg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class RegisterTimeEmploymentCommandHandler extends CommandHandlerWithResult<RegisterTimeEmploymentCommand,List<String>> {

    @Inject
    private Employment36HoursRepository repo;

    @Inject
    private AgreementTimeOfEmploymentDomainService agreementTimeOfEmploymentDomainService;

    @Override
    protected List<String> handle(CommandHandlerContext<RegisterTimeEmploymentCommand> context) {
        RegisterTimeEmploymentCommand command = context.getCommand();

        val errorTimeInMonth = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(command.getErrorOneMonth())
                , new AgreementOneMonthTime(command.getAlarmOneMonth()));
        AgreementOneMonthTime upperLimitTime = new AgreementOneMonthTime(command.getLimitOneMonth());

        val basicSettingMonth = OneMonthTime.of(errorTimeInMonth, upperLimitTime);

        val errorTimeInMonthUpper = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(command.getErrorTwoMonths())
                , new AgreementOneMonthTime(command.getAlarmTwoMonths()));
        val upperLimitTimeMonthUpper = new AgreementOneMonthTime(command.getLimitTwoMonths());
        val upperLimitDueToSpecialProvisionsMonth = OneMonthTime.of(errorTimeInMonthUpper, upperLimitTimeMonthUpper);

        val errorTimeInYear = OneYearErrorAlarmTime.of(new AgreementOneYearTime(command.getErrorOneYear())
                , new AgreementOneYearTime(command.getAlarmOneYear()));
        val upperLimitYear = new AgreementOneYearTime(command.getLimitOneYear());
        val basicSettingYear = OneYearTime.of(errorTimeInYear, upperLimitYear);

        val errorTimeInYearUpper = OneYearErrorAlarmTime.of(new AgreementOneYearTime(command.getErrorTwoYear())
                , new AgreementOneYearTime(command.getAlarmTwoYear()));
        BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(
                new AgreementOneMonth(basicSettingMonth, upperLimitDueToSpecialProvisionsMonth),
                new AgreementOneYear(errorTimeInYearUpper,basicSettingYear),
                new AgreementMultiMonthAvg(errorTimeInMonth),
                EnumAdaptor.valueOf(command.getOverMaxTimes(), AgreementOverMaxTimes.class));

        Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment = this.repo.getByCidAndEmployCode(AppContexts.user().companyId(),
                command.getEmploymentCD(),EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));

        if (agreementTimeOfEmployment.isPresent()) {
            AgreementTimeOfEmployment agreementTimeOfEmployment1 = new AgreementTimeOfEmployment(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class), new EmploymentCode(command.getEmploymentCD()), basicAgreementSetting);
            return this.agreementTimeOfEmploymentDomainService.update(basicAgreementSetting, agreementTimeOfEmployment1);
        } else {

            AgreementTimeOfEmployment agreementTimeOfEmployment1 = new AgreementTimeOfEmployment(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class), new EmploymentCode(command.getEmploymentCD()), basicAgreementSetting);

            return this.agreementTimeOfEmploymentDomainService.add(basicAgreementSetting,agreementTimeOfEmployment1);
        }
    }
}
