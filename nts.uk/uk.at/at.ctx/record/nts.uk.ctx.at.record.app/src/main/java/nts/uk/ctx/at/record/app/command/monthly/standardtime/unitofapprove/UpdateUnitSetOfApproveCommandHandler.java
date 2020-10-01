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
public class UpdateUnitSetOfApproveCommandHandler extends CommandHandler<UpdateUnitSetOfApproveCommand> {

    @Inject
    private UnitOfApproverRepo unitOfApproverRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateUnitSetOfApproveCommand> context) {
        UpdateUnitSetOfApproveCommand command = context.getCommand();

        UnitOfApprover unitOfApprover = new UnitOfApprover(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(command.getUseWorkplace() ? 1 : 0, DoWork.class));

        this.unitOfApproverRepo.update(unitOfApprover);
    }
}
