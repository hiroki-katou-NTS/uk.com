package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;

public class AddSocialInsurAcquisiInforCommandHandler  extends CommandHandler<SocialInsurAcquisiInforCommand> {

    @Inject
    SocialInsurAcquisiInforRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SocialInsurAcquisiInforCommand> context) {
        SocialInsurAcquisiInforCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        SocialInsurAcquisiInfor domain = new SocialInsurAcquisiInfor(cid,
                command.getEmployeeId(),
                command.getPercentOrMore(),
                command.getRemarksOther(),
                command.getRemarksAndOtherContents(),
                command.getRemunMonthlyAmountKind(),
                command.getRemunMonthlyAmount(),
                command.getTotalMonthlyRemun(),
                command.getLivingAbroad(),
                command.getReasonOther(),
                command.getReasonAndOtherContents(),
                command.getShortTimeWorkers(),
                command.getShortStay(),
                command.getDepenAppoint(),
                command.getQualifiDistin(),
                command.getContinReemAfterRetirement()
        );

        if(command.getMode() == 0){
            repository.add(domain);
        }else{
            repository.update(domain);
        }
    }
}
