package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class RemoveCareInfoCommandHandler extends AsyncCommandHandler<RemoveCareInfoCommand> {

	@Inject
	private NursCareLevRemainInfoRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RemoveCareInfoCommand> context) {
		RemoveCareInfoCommand data = context.getCommand();
		NursingCareLeaveRemainingInfo removeData = new NursingCareLeaveRemainingInfo();
		removeData.setLeaveType(EnumAdaptor.valueOf(1, LeaveType.class));
		removeData.setSId(data.getSId());
		repo.remove(removeData, AppContexts.user().companyId());
	}

}
