package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpecBPTimesheetAddCommandHandler extends CommandHandler<List<SpecBPTimesheetAddCommand>> {
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;

	@Override
	protected void handle(CommandHandlerContext<List<SpecBPTimesheetAddCommand>> context) {
	 List<SpecBPTimesheetAddCommand> lstSpecBPTimesheetAddCommand = context.getCommand();
	 String companyId = AppContexts.user().companyId();
		 List<SpecBonusPayTimesheet> listTimesheet = specBPTimesheetRepository.getListTimesheet(companyId, new BonusPaySettingCode(lstSpecBPTimesheetAddCommand.get(0).bonusPaySettingCode));
		if(listTimesheet!=null &&!listTimesheet.isEmpty()){
			throw new BusinessException("msg_3");
		}
		listTimesheet.forEach(c->{
			if(c.getStartTime().minute()>c.getEndTime().minute()){
				throw new BusinessException("msg_28");
			}
			if(c.getStartTime().minute()==c.getEndTime().minute()){
				throw new BusinessException("msg_33");
			}
		});
	 
	 specBPTimesheetRepository.addListTimesheet(companyId,
			new BonusPaySettingCode(lstSpecBPTimesheetAddCommand.get(0).bonusPaySettingCode),
				lstSpecBPTimesheetAddCommand.stream().map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}

	private SpecBonusPayTimesheet toSpecBonusPayTimesheetDomain(SpecBPTimesheetAddCommand specBPTimesheetAddCommand) {
		return SpecBonusPayTimesheet.createFromJavaType(specBPTimesheetAddCommand.timeSheetNO,
				specBPTimesheetAddCommand.useAtr, specBPTimesheetAddCommand.timeItemID,
				specBPTimesheetAddCommand.startTime, specBPTimesheetAddCommand.endTime,
				specBPTimesheetAddCommand.roundingTimeAtr, specBPTimesheetAddCommand.roundingAtr,
				specBPTimesheetAddCommand.specialDateItemNO);

	}

}
