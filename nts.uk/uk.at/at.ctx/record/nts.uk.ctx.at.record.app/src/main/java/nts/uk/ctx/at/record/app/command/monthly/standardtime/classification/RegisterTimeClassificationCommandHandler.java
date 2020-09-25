package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentDomainService;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.standardtime.*;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class RegisterTimeClassificationCommandHandler extends CommandHandlerWithResult<RegisterTimeClassificationCommand,List<String>> {

    @Inject
    private AgreementTimeOfClassificationRepository repo;

    @Inject
    private AgreementTimeOfClassificationDomainService timeOfClassificationDomainService;

    @Override
    protected List<String> handle(CommandHandlerContext<RegisterTimeClassificationCommand> context) {
        RegisterTimeClassificationCommand command = context.getCommand();

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


        Optional<AgreementTimeOfClassification> agreementTimeOfClassification = this.repo.find(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),command.getClassificationCode());

        if (agreementTimeOfClassification.isPresent()) {
            AgreementTimeOfClassification newAgreementTimeOfClassification= new AgreementTimeOfClassification(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class), new ClassificationCode(command.getClassificationCode()), basicAgreementSetting);
            return this.timeOfClassificationDomainService.update(basicAgreementSetting, newAgreementTimeOfClassification);
        } else {

            AgreementTimeOfClassification agreementTimeOfEmployment = new AgreementTimeOfClassification(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class), new ClassificationCode(command.getClassificationCode()), basicAgreementSetting);

            return this.timeOfClassificationDomainService.add(agreementTimeOfEmployment,basicAgreementSetting);
        }
    }
}
