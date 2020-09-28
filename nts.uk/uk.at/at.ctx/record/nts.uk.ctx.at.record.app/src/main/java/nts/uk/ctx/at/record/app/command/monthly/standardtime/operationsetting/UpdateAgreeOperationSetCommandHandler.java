package nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.company.RegisterTimeCompanyCommand;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfCompanyDomainService;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.standardtime.*;
import nts.uk.ctx.at.shared.dom.standardtime.enums.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class UpdateAgreeOperationSetCommandHandler extends CommandHandlerWithResult<UpdateAgreeOperationSetCommand, List<String>> {

    @Inject
    private AgreementOperationSettingRepository agreementOperationSettingRepository;

    @Override
    protected List<String> handle(CommandHandlerContext<UpdateAgreeOperationSetCommand> context) {
        UpdateAgreeOperationSetCommand command = context.getCommand();

        AgreementOperationSetting agreementOperationSetting = new AgreementOperationSetting(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getStartingMonth(), StartingMonthType.class) ,
                new ClosureDate()

                );

        this.agreementOperationSettingRepository.update(agreementOperationSetting);
    }
}
