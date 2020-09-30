package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

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
        val cid = AppContexts.user().companyId();

        //1: get(会社ID,雇用コード,３６協定労働制) : ３６協定基本設定
        Optional<AgreementTimeOfEmployment> timeOfEmployment =  repo.find(cid,command.getEmploymentCD(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));
        if(timeOfEmployment.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfEmployment.get().getSetting();

            //2: delete(会社ID,雇用コード)
            repo.remove(cid,command.getEmploymentCD(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfEmployment agreementTimeOfEmployment = new AgreementTimeOfEmployment(cid, EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
                    new EmploymentCode(command.getEmploymentCD()),basicAgreementSetting);
            repo.add(agreementTimeOfEmployment);
        }

    }
}
