package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.services.BonusPaySettingService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPSettingAddCommandHandler extends CommandHandlerWithResult<BPSettingAddCommand, List<RegisterErrorList>> {
	@Inject
	private BonusPaySettingService bonusPaySettingService;

	@Override
	protected List<RegisterErrorList> handle(CommandHandlerContext<BPSettingAddCommand> context) {
		String companyId = AppContexts.user().companyId();
		BPSettingAddCommand bpSettingAddCommand = context.getCommand();
		if(bonusPaySettingService.isExisted(companyId, new BonusPaySettingCode(bpSettingAddCommand.getCode()))) {
			throw new BusinessException("Msg_3");
		}
		List<BPTimesheetAddCommand> lstBPTimesheetAddCommand = bpSettingAddCommand.getLstBonusPayTimesheet();
		List<SpecBPTimesheetAddCommand> lstSpecBPTimesheetAddCommand = bpSettingAddCommand
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
			bpSettingAddCommand.setLstBonusPayTimesheet(bpSettingAddCommand.getLstBonusPayTimesheet().stream()
				.map(x -> new BPTimesheetAddCommand(x.timeSheetNO, x.useAtr, x.bonusPaySettingCode, 
						x.timeItemID, x.startTime, x.endTime, x.roundingTimeAtr, x.roundingAtr)).collect(Collectors.toList()));
			bpSettingAddCommand.setLstSpecBonusPayTimesheet(bpSettingAddCommand.getLstSpecBonusPayTimesheet().stream()
					.map(x -> new SpecBPTimesheetAddCommand(x.timeSheetNO, x.useAtr, x.bonusPaySettingCode, 
							x.timeItemID, x.startTime, x.endTime, x.roundingTimeAtr, x.roundingAtr, x.specialDateItemNO)).collect(Collectors.toList()));
			bonusPaySettingService.addBonusPaySetting(this.ToBonusPaySettingDomain(bpSettingAddCommand,companyId));			
		}
		return errorLists;
	}

	private BonusPaySetting ToBonusPaySettingDomain(BPSettingAddCommand bpSettingAddCommand,String companyId) {
		List<BPTimesheetAddCommand> lstBPTimesheetAddCommand = bpSettingAddCommand.getLstBonusPayTimesheet();
		List<BonusPayTimesheet> lstBonusPayTimesheet = lstBPTimesheetAddCommand.stream()
				.map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		List<SpecBPTimesheetAddCommand> lstSpecBPTimesheetAddCommand = bpSettingAddCommand
				.getLstSpecBonusPayTimesheet();
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = lstSpecBPTimesheetAddCommand.stream()
				.map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		return BonusPaySetting.createFromJavaType(companyId, bpSettingAddCommand.getCode(),
				bpSettingAddCommand.getName(), lstBonusPayTimesheet, lstSpecBonusPayTimesheet);
		// bonusPaySetting.setListTimesheet(lstBonusPayTimesheet);
		// bonusPaySetting.setListSpecialTimesheet(lstSpecBonusPayTimesheet);

	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetAddCommand bpTimesheetAddCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetAddCommand.timeSheetNO, bpTimesheetAddCommand.useAtr,
				bpTimesheetAddCommand.timeItemID, bpTimesheetAddCommand.startTime,
				bpTimesheetAddCommand.endTime, bpTimesheetAddCommand.roundingTimeAtr,
				bpTimesheetAddCommand.roundingAtr);
	}

	private SpecBonusPayTimesheet toSpecBonusPayTimesheetDomain(SpecBPTimesheetAddCommand specBPTimesheetAddCommand) {
		return SpecBonusPayTimesheet.createFromJavaType(specBPTimesheetAddCommand.timeSheetNO,
				specBPTimesheetAddCommand.useAtr, specBPTimesheetAddCommand.timeItemID,
				specBPTimesheetAddCommand.startTime, specBPTimesheetAddCommand.endTime,
				specBPTimesheetAddCommand.roundingTimeAtr, specBPTimesheetAddCommand.roundingAtr,
				specBPTimesheetAddCommand.specialDateItemNO);

	}

}
