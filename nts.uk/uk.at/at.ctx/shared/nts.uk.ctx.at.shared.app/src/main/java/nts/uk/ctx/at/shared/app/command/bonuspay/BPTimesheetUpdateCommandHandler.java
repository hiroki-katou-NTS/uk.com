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
public class BPTimesheetUpdateCommandHandler  extends CommandHandler<List<BPTimesheetUpdateCommand>> {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;
	@Override
	protected void handle(CommandHandlerContext<List<BPTimesheetUpdateCommand>> context) {
		 List<BPTimesheetUpdateCommand> lstBPTimesheetUpdateCommand = context.getCommand();
			String companyId = AppContexts.user().companyId();
			List<BonusPayTimesheet> listTimesheet = bpTimesheetRepository.getListTimesheet(companyId, new BonusPaySettingCode(lstBPTimesheetUpdateCommand.get(0).bonusPaySettingCode));
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
		bpTimesheetRepository.updateListTimesheet(companyId,
			new BonusPaySettingCode(lstBPTimesheetUpdateCommand.get(0).bonusPaySettingCode),
				lstBPTimesheetUpdateCommand.stream().map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}
	
	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetUpdateCommand bpTimesheetUpdateCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetUpdateCommand.timeSheetNO, bpTimesheetUpdateCommand.useAtr,
				bpTimesheetUpdateCommand.timeItemID, bpTimesheetUpdateCommand.startTime,
				bpTimesheetUpdateCommand.endTime, bpTimesheetUpdateCommand.roundingTimeAtr,
				bpTimesheetUpdateCommand.roundingAtr);
	}

}
