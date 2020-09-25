package nts.uk.ctx.at.record.app.command.monthly.standardtime.company;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfCompanyDomainService;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.standardtime.*;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class RegisterTimeCompanyCommandHandler extends CommandHandlerWithResult<RegisterTimeCompanyCommand, List<String>> {

    @Inject
    private AgreementTimeCompanyRepository repo;

    @Inject
    private AgreementTimeOfCompanyDomainService agreementTimeOfCompanyDomainService;

    @Override
    protected List<String> handle(CommandHandlerContext<RegisterTimeCompanyCommand> context) {
        RegisterTimeCompanyCommand command = context.getCommand();

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

        Optional<AgreementTimeOfCompany> agreementTimeOfCompanyOpt = this.repo.find(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

        if (agreementTimeOfCompanyOpt.isPresent()) {
            AgreementTimeOfCompany newAgreementTimeOfCompany= new AgreementTimeOfCompany(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class), basicAgreementSetting);
            return this.agreementTimeOfCompanyDomainService.update(basicAgreementSetting, newAgreementTimeOfCompany);
        } else {
            AgreementTimeOfCompany agreementTimeOfCompany = new AgreementTimeOfCompany(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class), basicAgreementSetting);
            return this.agreementTimeOfCompanyDomainService.add(basicAgreementSetting, agreementTimeOfCompany);
        }
    }
}
