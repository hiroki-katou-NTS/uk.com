package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPTimesheetAddCommandHandler extends CommandHandler<List<BPTimesheetAddCommand>> {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimesheetAddCommand>> context) {
		List<BPTimesheetAddCommand> lstBPTimesheetAddCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<BonusPayTimesheet> listTimesheet = bpTimesheetRepository.getListTimesheet(companyId, new BonusPaySettingCode(lstBPTimesheetAddCommand.get(0).bonusPaySettingCode));
		if(listTimesheet!=null &&!listTimesheet.isEmpty()){
			throw new BusinessException("Msg_3");
		}
		listTimesheet.forEach(c->{
			if(c.getStartTime().minute()>c.getEndTime().minute()){
				throw new BusinessException("Msg_28");
			}
			if(c.getStartTime().minute()==c.getEndTime().minute()){
				throw new BusinessException("Msg_33");
			}
		});
		bpTimesheetRepository.addListTimesheet(companyId,
			 new BonusPaySettingCode(lstBPTimesheetAddCommand.get(0).bonusPaySettingCode),
				lstBPTimesheetAddCommand.stream().map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetAddCommand bpTimesheetAddCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetAddCommand.timeSheetNO, bpTimesheetAddCommand.useAtr,
				bpTimesheetAddCommand.timeItemID, bpTimesheetAddCommand.startTime,
				bpTimesheetAddCommand.endTime, bpTimesheetAddCommand.roundingTimeAtr,
				bpTimesheetAddCommand.roundingAtr);
	}

}
