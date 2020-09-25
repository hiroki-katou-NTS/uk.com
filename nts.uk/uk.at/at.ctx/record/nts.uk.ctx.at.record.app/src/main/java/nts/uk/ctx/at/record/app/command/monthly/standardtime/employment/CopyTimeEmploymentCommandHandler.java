package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CopyTimeEmploymentCommandHandler extends CommandHandler<CopyTimeEmploymentCommand> {

    @Inject
    private AgreementTimeOfEmploymentRepostitory repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeEmploymentCommand> context) {

        CopyTimeEmploymentCommand command = context.getCommand();

        //1: get(会社ID,雇用コード,３６協定労働制) : ３６協定基本設定
        Optional<AgreementTimeOfEmployment> timeOfEmployment =  repo.find(command.getCompanyId(),command.getEmploymentCD(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));
        if(timeOfEmployment.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfEmployment.get().getSetting();

            //2: delete(会社ID,雇用コード)
            repo.remove(command.getCompanyId(),command.getEmploymentCD(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfEmployment agreementTimeOfEmployment = new AgreementTimeOfEmployment(command.getCompanyId(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
                    new EmploymentCode(command.getEmploymentCD()),basicAgreementSetting);
            repo.add(agreementTimeOfEmployment);
        }

    }
}
