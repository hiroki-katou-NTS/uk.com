package nts.uk.ctx.at.shared.app.command.workingconditionitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * 
 * @author quytb
 *
 */

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class UpadateWorkingConditonItemCommandHandler extends CommandHandler<WorkingConditionItemSaveCommand> {
	@Inject
	private WorkingConditionItemRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<WorkingConditionItemSaveCommand> context) {
		WorkingConditionItemSaveCommand command = context.getCommand();
		
		Optional<WorkingConditionItem> optional = repository.getByHistoryId(command.getHistId());
		/** 設定対象が選択されているかチェック */
		if(optional.isPresent()) {	
			WorkingConditionItem workingConditionItem = new WorkingConditionItem(
					command.getHistId(), optional.get().getScheduleManagementAtr(), 
					optional.get().getWorkCategory(), 
					optional.get().getAutoIntervalSetAtr(), optional.get().getAutoIntervalSetAtr(),
					command.getEmployeeId(), optional.get().getVacationAddedTimeAtr(), 
					optional.get().getContractTime(), optional.get().getLaborSystem(), 
					optional.get().getHolidayAddTimeSet().isPresent() ? optional.get().getHolidayAddTimeSet().get(): null, 
					optional.get().getScheduleMethod().isPresent() ? optional.get().getScheduleMethod().get() : null, 
					optional.get().getHourlyPaymentAtr().value, 
					command.getBonusPaySettingCode() != null ? new BonusPaySettingCode(command.getBonusPaySettingCode()): null, 
					optional.get().getMonthlyPattern().isPresent() ? optional.get().getMonthlyPattern().get(): null);
			
			/**persist()*/
			repository.update(workingConditionItem);
			
		/** 選択されてない場合*/
		} else {
			throw new BusinessException("Msg_339");
		}		
	}
}
