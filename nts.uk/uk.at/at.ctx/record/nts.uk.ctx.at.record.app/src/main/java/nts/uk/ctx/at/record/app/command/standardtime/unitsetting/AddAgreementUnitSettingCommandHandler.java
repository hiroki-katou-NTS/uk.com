package nts.uk.ctx.at.record.app.command.standardtime.unitsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 単位設定 screen, add
 *
 */
@Stateless
public class AddAgreementUnitSettingCommandHandler extends CommandHandler<AddAgreementUnitSettingCommand> {

	@Inject
	private AgreementUnitSettingRepository agreementUnitSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAgreementUnitSettingCommand> context) {
		AddAgreementUnitSettingCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting(companyId,
				EnumAdaptor.valueOf(command.getClassificationUseAtr(), UseClassificationAtr.class),
				EnumAdaptor.valueOf(command.getEmploymentUseAtr(), UseClassificationAtr.class),
				EnumAdaptor.valueOf(command.getWorkPlaceUseAtr(), UseClassificationAtr.class));
		
		this.agreementUnitSettingRepository.add(agreementUnitSetting);
	}

}
