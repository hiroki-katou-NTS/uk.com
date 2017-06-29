package nts.uk.ctx.at.record.app.command.standardtime.yearsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;

/**
 * 
 * @author nampt 特例設定 screen remove year
 *
 */
@Stateless
public class RemoveAgreementYearSettingCommandHandler extends CommandHandler<RemoveAgreementYearSettingCommand> {

	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveAgreementYearSettingCommand> context) {
		RemoveAgreementYearSettingCommand command = context.getCommand();
		
		this.agreementYearSettingRepository.delete(command.getEmployeeId(), command.getYearValue());
	}

}
