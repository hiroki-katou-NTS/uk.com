package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CopyTimeClassificationCommandHandler extends CommandHandler<CopyTimeClassificationCommand> {

    @Inject
    private AgreementTimeOfClassificationRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeClassificationCommand> context) {

        CopyTimeClassificationCommand command = context.getCommand();

        //1: get(会社ID,雇用コード,３６協定労働制) : ３６協定基本設定
        Optional<AgreementTimeOfClassification> timeOfClassification =  repo.find(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),command.getClassificationCode());

        if(timeOfClassification.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfClassification.get().getSetting();

            //2: delete(会社ID,雇用コード)
            repo.remove(AppContexts.user().companyId(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),command.getClassificationCode());

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfClassification agreementTimeOfEmployment = new AgreementTimeOfClassification(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),new ClassificationCode(command.getClassificationCode()),basicAgreementSetting);
            repo.add(agreementTimeOfEmployment);
        }

    }
}
