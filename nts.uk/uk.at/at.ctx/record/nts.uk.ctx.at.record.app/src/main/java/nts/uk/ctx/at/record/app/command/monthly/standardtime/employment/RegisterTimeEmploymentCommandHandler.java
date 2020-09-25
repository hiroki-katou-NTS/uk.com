package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.standardtime.*;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class RegisterTimeEmploymentCommandHandler extends CommandHandlerWithResult<RegisterTimeEmploymentCommand,List<String>> {

    @Inject
    private AgreementTimeOfEmploymentRepostitory repo;

    @Inject
    private AgreementTimeOfEmploymentDomainService agreementTimeOfEmploymentDomainService;

    @Override
    protected List<String> handle(CommandHandlerContext<RegisterTimeEmploymentCommand> context) {
        RegisterTimeEmploymentCommand command = context.getCommand();

        val errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(command.getErrorTimeMonth1())
                , new AgreementOneMonthTime(command.getAlarmTimeMonth1()));
        AgreementOneMonthTime upperLimitTime = new AgreementOneMonthTime(command.getUpperLimitTimeMonth1());

        val basicSettingMonth = new OneMonthTime(errorTimeInMonth, upperLimitTime);

        val errorTimeInMonthUpper = new ErrorTimeInMonth(new AgreementOneMonthTime(command.getErrorTimeMonth2())
                , new AgreementOneMonthTime(command.getAlarmTimeMonth2()));
        val upperLimitTimeMonthUpper = new AgreementOneMonthTime(command.getUpperLimitTimeMonth2());
        val upperLimitDueToSpecialProvisionsMonth = new OneMonthTime(errorTimeInMonthUpper, upperLimitTimeMonthUpper);

        val errorTimeInYear = new ErrorTimeInYear(new AgreementOneYearTime(command.getErrorTimeYear1())
                , new AgreementOneYearTime(command.getAlarmTimeYear1()));
        val upperLimitYear = new AgreementOneYearTime(command.getUpperLimitTimeYear1());
        val basicSettingYear = new OneYearTime(errorTimeInYear, upperLimitYear);

        val errorTimeInYearUpper = new ErrorTimeInYear(new AgreementOneYearTime(command.getErrorTimeYear2())
                , new AgreementOneYearTime(command.getAlarmTimeYear2()));
        val upperLimitTimeYearUpper = new AgreementOneYearTime(command.getUpperLimitTimeYear2());
        val upperLimitDueToSpecialProvisionsYear = new OneYearTime(errorTimeInYearUpper, upperLimitTimeYearUpper);

        BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(
                new AgreementsOneMonth(basicSettingMonth, upperLimitDueToSpecialProvisionsMonth),
                new AgreementsOneYear(basicSettingYear, upperLimitDueToSpecialProvisionsYear),
                new AgreementsMultipleMonthsAverage(errorTimeInMonth), EnumAdaptor.valueOf(command.getNumberTimesOverLimitType(), TimeOverLimitType.class));

        Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment = this.repo.find(AppContexts.user().companyId(),command.getEmploymentCD(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

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
