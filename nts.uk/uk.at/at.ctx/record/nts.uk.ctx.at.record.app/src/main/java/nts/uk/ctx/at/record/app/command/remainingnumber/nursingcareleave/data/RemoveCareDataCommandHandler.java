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
public class RemoveCareDataCommandHandler extends AsyncCommandHandler<RemoveCareDataCommand> {

	@Inject
	private NursCareLevRemainDataRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveCareDataCommand> context) {
		RemoveCareDataCommand data = context.getCommand();
		NursingCareLeaveRemainingData updateObj = new NursingCareLeaveRemainingData();
		updateObj.setLeaveType(EnumAdaptor.valueOf(1, LeaveType.class));
		updateObj.setSId(data.getSId());
		repo.remove(updateObj, AppContexts.user().companyId());
	}

}
