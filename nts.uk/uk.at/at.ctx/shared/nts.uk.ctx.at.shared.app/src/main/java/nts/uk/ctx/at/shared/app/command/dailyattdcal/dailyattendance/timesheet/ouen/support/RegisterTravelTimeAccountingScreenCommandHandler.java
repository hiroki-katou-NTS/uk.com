package nts.uk.ctx.at.shared.app.command.dailyattdcal.dailyattendance.timesheet.ouen.support;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.AccountingOfMoveTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * <<Command>>応援・作業設定の移動時間計上先設定を登録する
 * 
 * @author NWS
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterTravelTimeAccountingScreenCommandHandler
		extends CommandHandler<RegisterTravelTimeAccountingScreenCommand> {

	@Inject
	private SupportWorkSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegisterTravelTimeAccountingScreenCommand> context) {
		RegisterTravelTimeAccountingScreenCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// get
		SupportWorkSetting domain = repository.get(companyId);
		if (domain != null) {
			domain = new SupportWorkSetting(companyId, EnumAdaptor.valueOf(command.getIsUse(), NotUseAtr.class),
					AccountingOfMoveTime.of(command.getAccountingOfMoveTime()));
			repository.update(domain);
		} else {
			domain = new SupportWorkSetting(companyId, EnumAdaptor.valueOf(command.getIsUse(), NotUseAtr.class),
					AccountingOfMoveTime.of(command.getAccountingOfMoveTime()));
			repository.insert(domain);
		}
	}

}
