package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAcc;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAccRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddInforOnWelfPenInsurAccCommandHandler extends CommandHandler<InforOnWelfPenInsurAccCommand>
{
    
    @Inject
    private InforOnWelfPenInsurAccRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<InforOnWelfPenInsurAccCommand> context) {
        InforOnWelfPenInsurAccCommand command = context.getCommand();
        repository.add(new InforOnWelfPenInsurAcc(
                command.getEmployeeId(),
                command.getHealInsurDis(),
                command.getReasonAndOContent(),
                command.getYearsOldOrOlder(),
                command.getRemarksOther(),
                command.getReOtherContents(),
                command.getRemuMonthlyKind(),
                command.getRemuMonthlyAmount(),
                command.getTotalMonthlyRemun(),
                command.getLivingAbroad(),
                command.getReasonOther(),
                command.getShortTimeWorker(),
                command.getShortTermResidence(),
                command.getDependentNotiClass(),
                command.getQuaAcquiRemarks(),
                command.getQualifiClass(),
                command.getContiReemAfRetirement()
        ));
    
    }
}
