package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
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
    private Employment36HoursRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeEmploymentCommand> context) {

        CopyTimeEmploymentCommand command = context.getCommand();
        val cid = AppContexts.user().companyId();

        //1: get(会社ID,雇用コード) : ３６協定基本設定
        Optional<AgreementTimeOfEmployment> timeOfEmployment =  repo.getByCidAndEmployCode(cid,command.getEmpCdSource(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));
        if(timeOfEmployment.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfEmployment.get().getSetting();

            Optional<AgreementTimeOfEmployment> timeOfEmploymentCoppy =  repo.getByCidAndEmployCode(cid,command.getEmpCdTarget(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));

            //2: delete
            timeOfEmploymentCoppy.ifPresent(x -> repo.delete(x));

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfEmployment agreementTimeOfEmployment = new AgreementTimeOfEmployment(cid, EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
                    new EmploymentCode(command.getEmpCdTarget()),basicAgreementSetting);
            repo.insert(agreementTimeOfEmployment);
        }
    }
}
