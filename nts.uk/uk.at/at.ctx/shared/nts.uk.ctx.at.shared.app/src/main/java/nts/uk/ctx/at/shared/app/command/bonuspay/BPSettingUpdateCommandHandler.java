package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.services.BonusPaySettingService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPSettingUpdateCommandHandler extends CommandHandlerWithResult<BPSettingUpdateCommand, List<RegisterErrorList>> {
	@Inject
	private BonusPaySettingService bonusPaySettingService;

	@Override
	protected List<RegisterErrorList> handle(CommandHandlerContext<BPSettingUpdateCommand> context) {
		String companyId = AppContexts.user().companyId();
		BPSettingUpdateCommand bpSettingUpdateCommand = context.getCommand();
		List<BPTimesheetUpdateCommand> lstBPTimesheetAddCommand = bpSettingUpdateCommand.getLstBonusPayTimesheet();
		List<SpecBPTimesheetUpdateCommand> lstSpecBPTimesheetAddCommand = bpSettingUpdateCommand
				.getLstSpecBonusPayTimesheet();
		if(lstBPTimesheetAddCommand.stream().allMatch(item -> item.useAtr == 0) 
				&& lstSpecBPTimesheetAddCommand.stream().allMatch(item -> item.useAtr == 0)) {
			throw new BusinessException("Msg_34");
		}
		List<RegisterErrorList> errorLists = new ArrayList<>();
		lstBPTimesheetAddCommand.forEach(item ->{
			if(item.getUseAtr() == 1 && item.getStartTime() > item.getEndTime()) {
				errorLists.add(new RegisterErrorList(true, item.getTimeSheetNO(), "Msg_28"));
			} else if (item.getUseAtr() == 1 && item.getStartTime() == item.getEndTime()) {
				errorLists.add(new RegisterErrorList(true, item.getTimeSheetNO(), "Msg_33"));				
			}
		});
		lstSpecBPTimesheetAddCommand.forEach(item ->{
			if(item.getUseAtr() == 1 && item.getStartTime() > item.getEndTime()) {
				errorLists.add(new RegisterErrorList(false, item.getTimeSheetNO(), "Msg_28"));
			} else if (item.getUseAtr() == 1 && item.getStartTime() == item.getEndTime()) {
				errorLists.add(new RegisterErrorList(false, item.getTimeSheetNO(), "Msg_33"));				
			}
		});
		if(errorLists.isEmpty()) {
			bonusPaySettingService.updateBonusPaySetting(this.toBonusPaySettingDomain(bpSettingUpdateCommand,companyId));			
		}
		return errorLists;
	}

	private BonusPaySetting toBonusPaySettingDomain(BPSettingUpdateCommand bpSettingUpdateCommand,String companyId) {
		List<BPTimesheetUpdateCommand> lstBPTimesheetUpdateCommand = bpSettingUpdateCommand.getLstBonusPayTimesheet();
		List<BonusPayTimesheet> lstBonusPayTimesheet = lstBPTimesheetUpdateCommand.stream()
				.map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		List<SpecBPTimesheetUpdateCommand> lstSpecBPTimesheetUpdateCommand = bpSettingUpdateCommand
				.getLstSpecBonusPayTimesheet();
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = lstSpecBPTimesheetUpdateCommand.stream()
				.map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		
		return BonusPaySetting.createFromJavaType(companyId,
				bpSettingUpdateCommand.code, bpSettingUpdateCommand.name,lstBonusPayTimesheet,lstSpecBonusPayTimesheet);
//		bonusPaySetting.setListTimesheet(lstBonusPayTimesheet);
//		bonusPaySetting.setListSpecialTimesheet(lstSpecBonusPayTimesheet);
	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetUpdateCommand bpTimesheetUpdateCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetUpdateCommand.timeSheetNO,
				bpTimesheetUpdateCommand.useAtr, bpTimesheetUpdateCommand.timeItemID,
				bpTimesheetUpdateCommand.startTime, bpTimesheetUpdateCommand.endTime,
				bpTimesheetUpdateCommand.roundingTimeAtr, bpTimesheetUpdateCommand.roundingAtr);
	}

	private SpecBonusPayTimesheet toSpecBonusPayTimesheetDomain(
			SpecBPTimesheetUpdateCommand specBPTimesheetUpdateCommand) {
		return SpecBonusPayTimesheet.createFromJavaType(specBPTimesheetUpdateCommand.timeSheetNO,
				specBPTimesheetUpdateCommand.useAtr, specBPTimesheetUpdateCommand.timeItemID,
				specBPTimesheetUpdateCommand.startTime,
				specBPTimesheetUpdateCommand.endTime, specBPTimesheetUpdateCommand.roundingTimeAtr,
				specBPTimesheetUpdateCommand.roundingAtr, specBPTimesheetUpdateCommand.specialDateItemNO);

	}

}
