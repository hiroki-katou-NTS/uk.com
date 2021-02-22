package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;
import nts.uk.ctx.at.function.dom.employmentfunction.commonform.SettingClassification;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SetOutItemsWoScCommandSaveHandler extends CommandHandler<SetOutItemsWoScCommand> {
	
	@Inject
	private SetOutputItemOfAnnualWorkSchRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
		String companyId = AppContexts.user().companyId();
		SetOutItemsWoScCommand command = context.getCommand();
		Optional<String> employeeId = command.getSettingType() == SettingClassification.FREE_SETTING.value
									? Optional.of(AppContexts.user().employeeId())
									: Optional.empty();
		command.setCid(companyId);
		if (command.getSettingType() == SettingClassification.FREE_SETTING.value) {
			command.setSid(AppContexts.user().employeeId());
		}
		
		if (command.isNewMode()) {
			Optional<SettingOutputItemOfAnnualWorkSchedule> domain = this.repository.findByCode(command.getCd()
					  , employeeId
					  , companyId
					  , command.getSettingType());
			// コードは重複してはならない(khong trung code)
			if (domain.isPresent()) {
				// #Msg_3
				throw new BusinessException("Msg_3");
			}
			command.setLayoutId(UUID.randomUUID().toString());
			repository.add(SettingOutputItemOfAnnualWorkSchedule.createFromMemento(command));
		} else {
			repository.update(SettingOutputItemOfAnnualWorkSchedule.createFromMemento(command));
		}
		
	}

}
