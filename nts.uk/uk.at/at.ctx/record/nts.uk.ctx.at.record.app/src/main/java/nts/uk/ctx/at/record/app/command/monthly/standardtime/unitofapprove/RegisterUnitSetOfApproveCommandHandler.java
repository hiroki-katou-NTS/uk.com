package nts.uk.ctx.at.record.app.command.monthly.standardtime.unitofapprove;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApproverRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterUnitSetOfApproveCommandHandler extends CommandHandler<RegisterUnitSetOfApproveCommand> {

    @Inject
    private UnitOfApproverRepo unitOfApproverRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterUnitSetOfApproveCommand> context) {
        RegisterUnitSetOfApproveCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        UnitOfApprover unitOfApproverOld = unitOfApproverRepo.getByCompanyId(cid);
        UnitOfApprover unitOfApprover = new UnitOfApprover(cid,
                EnumAdaptor.valueOf(command.getUseWorkplace() ? 1 : 0, DoWork.class));

        if (unitOfApproverOld != null){
            this.unitOfApproverRepo.update(unitOfApprover);
        }else {
            this.unitOfApproverRepo.insert(unitOfApprover);
        }

    }
}
