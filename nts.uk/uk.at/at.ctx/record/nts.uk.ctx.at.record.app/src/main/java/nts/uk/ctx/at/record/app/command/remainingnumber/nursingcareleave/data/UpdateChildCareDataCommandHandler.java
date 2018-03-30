package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateChildCareDataCommandHandler extends AsyncCommandHandler<UpdateCareDataCommand>{

	@Inject
	private NursCareLevRemainDataRepository repo;

	@Override
	protected void handle(CommandHandlerContext<UpdateCareDataCommand> context) {
		UpdateCareDataCommand data = context.getCommand();
		repo.update(new NursingCareLeaveRemainingData(data.getSId(), EnumAdaptor.valueOf(2, LeaveType.class),
				data.getUserDay()), AppContexts.user().companyId());
	}

}
