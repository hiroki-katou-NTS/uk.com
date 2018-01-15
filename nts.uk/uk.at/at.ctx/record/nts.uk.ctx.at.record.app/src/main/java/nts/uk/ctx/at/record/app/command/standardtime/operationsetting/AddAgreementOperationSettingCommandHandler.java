package nts.uk.ctx.at.record.app.command.standardtime.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 運用 screen add
 *
 */
@Stateless
public class AddAgreementOperationSettingCommandHandler extends CommandHandler<AddAgreementOperationSettingCommand> {

	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAgreementOperationSettingCommand> context) {
		AddAgreementOperationSettingCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AgreementOperationSetting agreementOperationSetting = new AgreementOperationSetting(companyId,
				EnumAdaptor.valueOf(command.getStartingMonth(), StartingMonthType.class),
				EnumAdaptor.valueOf(command.getNumberTimesOverLimitType(),TimeOverLimitType.class),
				EnumAdaptor.valueOf(command.getClosingDateType(), ClosingDateType.class),
				EnumAdaptor.valueOf(command.getClosingDateAtr(),ClosingDateAtr.class),
				EnumAdaptor.valueOf(command.getYearlyWorkTableAtr(), TargetSettingAtr.class),
				EnumAdaptor.valueOf(command.getAlarmListAtr(), TargetSettingAtr.class));
		
		this.agreementOperationSettingRepository.add(agreementOperationSetting);
	}

}
