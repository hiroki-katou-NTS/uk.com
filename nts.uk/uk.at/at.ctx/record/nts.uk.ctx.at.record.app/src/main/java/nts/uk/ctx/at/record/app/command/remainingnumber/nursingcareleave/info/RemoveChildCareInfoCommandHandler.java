package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.RemoveCareDataCommand;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveChildCareInfoCommandHandler extends AsyncCommandHandler<RemoveCareDataCommand> {

	@Inject
	private NursCareLevRemainDataRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RemoveCareDataCommand> context) {
		RemoveCareDataCommand data = context.getCommand();
		NursingCareLeaveRemainingData removeData = new NursingCareLeaveRemainingData();
		removeData.setLeaveType(EnumAdaptor.valueOf(2, LeaveType.class));
		removeData.setSId(data.getSId());
		repo.remove(removeData, AppContexts.user().companyId());
	}

}