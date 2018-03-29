package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateCareInfoCommandHandler extends AsyncCommandHandler<UpdateCareInfoCommand> {

	@Inject
	private NursCareLevRemainInfoRepository repo;

	@Override
	protected void handle(CommandHandlerContext<UpdateCareInfoCommand> context) {
		UpdateCareInfoCommand data = context.getCommand();
		repo.update(new NursingCareLeaveRemainingInfo(data.getSId(), EnumAdaptor.valueOf(2, LeaveType.class), 
				data.isUseClassification(), EnumAdaptor.valueOf(data.getUpperlimitSetting(), UpperLimitSetting.class), 
				data.getMaxDayForThisFiscalYear() == null ? Optional.empty() : Optional.of(data.getMaxDayForThisFiscalYear()), 
						data.getMaxDayForNextFiscalYear() == null ? Optional.empty() : Optional.of(data.getMaxDayForNextFiscalYear())), AppContexts.user().companyId());
	}

}