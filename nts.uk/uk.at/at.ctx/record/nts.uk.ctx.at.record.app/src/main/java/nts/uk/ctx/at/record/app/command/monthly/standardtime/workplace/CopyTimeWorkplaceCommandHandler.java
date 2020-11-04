package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CopyTimeWorkplaceCommandHandler extends CommandHandler<CopyTimeWorkplaceCommand> {

    @Inject
    private Workplace36AgreedHoursRepository repo;

    @Override
    protected void handle(CommandHandlerContext<CopyTimeWorkplaceCommand> context) {

        CopyTimeWorkplaceCommand command = context.getCommand();

		if (command.getWorkplaceIdTarget().equals(command.getWorkplaceIdSource())) {
			return;
		}

        //1: get(会社ID,雇用コード) : ３６協定基本設定
        Optional<AgreementTimeOfWorkPlace> timeOfWorkPlace =  repo.getByWorkplaceId(command.getWorkplaceIdSource(),EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));
        if(timeOfWorkPlace.isPresent()){
            BasicAgreementSetting basicAgreementSetting = timeOfWorkPlace.get().getSetting();

            Optional<AgreementTimeOfWorkPlace> timeOfWorkPlaceCoppy =  repo.getByWorkplaceId(command.getWorkplaceIdTarget(),EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));

            //2: delete(会社ID,雇用コード)
            timeOfWorkPlaceCoppy.ifPresent(x -> repo.delete(x));

            //3: insert(会社ID,雇用コード,３６協定労働制,３６協定基本設定)
            AgreementTimeOfWorkPlace agreementTimeOfEmployment = new AgreementTimeOfWorkPlace(command.getWorkplaceIdTarget(),
                    EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),basicAgreementSetting);
            repo.insert(agreementTimeOfEmployment);
        }

    }
}
