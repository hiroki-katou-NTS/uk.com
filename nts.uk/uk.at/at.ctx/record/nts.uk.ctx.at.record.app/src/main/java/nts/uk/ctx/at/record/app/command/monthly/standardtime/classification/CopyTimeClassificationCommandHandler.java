package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CopyTimeClassificationCommandHandler extends CommandHandler<CopyTimeClassificationCommand> {

    @Inject
    private Classification36AgreementTimeRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeClassificationCommand> context) {

        CopyTimeClassificationCommand command = context.getCommand();

        //1: get(会社ID,雇用コード) : ３６協定基本設定
        Optional<AgreementTimeOfClassification> timeOfClassification =  repo.getByCidAndClassificationCode(AppContexts.user().companyId(),
                command.getClassificationCdSource(),EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));

        if(timeOfClassification.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfClassification.get().getSetting();

            Optional<AgreementTimeOfClassification> timeOfClassificationCoppy =  repo.getByCidAndClassificationCode(AppContexts.user().companyId(),
                    command.getClassificationCdTarget(),EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));

            //2: delete(会社ID,雇用コード)
            timeOfClassificationCoppy.ifPresent(x -> repo.delete(x));

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfClassification agreementTimeOfClassification = new AgreementTimeOfClassification(AppContexts.user().companyId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),new ClassificationCode(command.getClassificationCdTarget()),basicAgreementSetting);
            repo.insert(agreementTimeOfClassification);
        }

    }
}
