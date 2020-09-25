package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceRepository;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CopyTimeWorkplaceCommandHandler extends CommandHandler<CopyTimeWorkplaceCommand> {

    @Inject
    private AgreementTimeOfWorkPlaceRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeWorkplaceCommand> context) {

        CopyTimeWorkplaceCommand command = context.getCommand();

        //1: get(会社ID,雇用コード,３６協定労働制) : ３６協定基本設定
        Optional<AgreementTimeOfWorkPlace> timeOfWorkPlace =  repo.findAgreementTimeOfWorkPlace(command.getWorkplaceId(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));
        if(timeOfWorkPlace.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfWorkPlace.get().getSetting();

            //2: delete(会社ID,雇用コード)
            repo.remove(command.getWorkplaceId(),EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfWorkPlace agreementTimeOfEmployment = new AgreementTimeOfWorkPlace(command.getWorkplaceId(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),basicAgreementSetting);
            repo.add(agreementTimeOfEmployment);
        }

    }
}
