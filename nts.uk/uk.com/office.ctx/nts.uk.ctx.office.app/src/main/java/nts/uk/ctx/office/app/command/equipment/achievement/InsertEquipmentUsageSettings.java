package nts.uk.ctx.office.app.command.equipment.achievement;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.app.command.equipment.achievement.dto.EquipmentFormSettingDto;
import nts.uk.ctx.office.app.command.equipment.achievement.dto.EquipmentPerformInputFormatSettingDto;
import nts.uk.ctx.office.app.command.equipment.achievement.dto.EquipmentUsageRecordItemSettingDto;
import nts.uk.ctx.office.dom.equipment.achievement.domainservice.DefaultRequireImpl;
import nts.uk.ctx.office.dom.equipment.achievement.domainservice.PersistenceProcess;
import nts.uk.ctx.office.dom.equipment.achievement.domainservice.RegisterEquipmentItemSettingMaster;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentFormSettingRepository;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentFormatSettingRepository;
import nts.uk.ctx.office.dom.equipment.achievement.repo.EquipmentRecordItemSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.オフィス.OEM_設備マスタ.OEM003_設備利用実績の設定.A：設備利用実績の設定.メニュー別OCD.設備利用実績設定を登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class InsertEquipmentUsageSettings extends CommandHandler<InsertEquipmentUsageSettingsCommand> {

	@Inject
	private EquipmentFormatSettingRepository formatSettingRepository;

	@Inject
	private EquipmentFormSettingRepository formSettingRepository;

	@Inject
	private EquipmentRecordItemSettingRepository itemSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertEquipmentUsageSettingsCommand> context) {
		InsertEquipmentUsageSettingsCommand command = context.getCommand();
		DefaultRequireImpl require = new DefaultRequireImpl(formatSettingRepository, formSettingRepository,
				itemSettingRepository);
		// 1. 登録する(require, ログイン会社ID, 設備利用実績の項目設定List,設備の実績入力フォーマット設定, 設備帳票設定)
		PersistenceProcess persistenceProcess = RegisterEquipmentItemSettingMaster.register(require,
				AppContexts.user().companyId(),
				command.getItemSettings().stream().map(EquipmentUsageRecordItemSettingDto::toDomain)
						.collect(Collectors.toList()),
				Optional.ofNullable(command.getFormatSetting()).map(EquipmentPerformInputFormatSettingDto::toDomain),
				Optional.ofNullable(command.getFormSetting()).map(EquipmentFormSettingDto::toDomain));
		// 2.1. persist 設備の実績入力フォーマット設定の永続化処理
		transaction.allInOneTransaction(persistenceProcess.getInputFormatSettingTasks());
		// 2.2. persist 設備利用実績の項目設定の永続化処理
		transaction.allInOneTransaction(persistenceProcess.getItemSettingTasks());
		// 2.3. [設備帳票設定isPresent]
		if (command.getFormSetting() != null) {
			// persist 設備帳票設定の永続化処理
			transaction.allInOneTransaction(persistenceProcess.getFormSettingTasks());
		}
	}

}
