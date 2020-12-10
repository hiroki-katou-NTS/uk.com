/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.ItemSelectionType;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkScheduleCommandSaveHandler.
 * @author HoangDD
 */
@Stateless
public class OutputItemDailyWorkScheduleSaveHandler extends CommandHandler<OutputItemDailyWorkScheduleCommand>{

	/** The repository. */
	@Inject
	private OutputItemDailyWorkScheduleRepository repository;
	/** The repository. */
	@Inject
	private OutputStandardSettingRepository standardSettingRepository;
	/** The repository. */
	@Inject
	private FreeSettingOfOutputItemRepository freeSettingRepository;
	
	/* (non-Javadoc)WorkScheduleOutputCondition
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutputItemDailyWorkScheduleCommand> context) {
		OutputItemDailyWorkScheduleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		OutputItemDailyWorkSchedule domain = new OutputItemDailyWorkSchedule(command);

		// Step. 画面モードをチェックする(Check screen mode)
		// IF.新規モードの場合(Th new mode)
		if (command.isNewMode()) {
			// Input．項目選択種類をチェック (Check select type Input.item)
			// 定型選択の場合
			if (command.getSelectionType() == ItemSelectionType.STANDARD_SELECTION.value) {
				// 定型設定のコードから出力項目を取得 (Get the output item from the code of the fixed form setting)
				Optional<OutputItemDailyWorkSchedule> standardDomain = this.standardSettingRepository
						.findByCompanyIdAndCode(companyId, command.getItemCode().v());
				
				// エラーメッセージ（ID:Msg_3）を表示する(Display error message (ID: Msg_3))
				if (standardDomain.isPresent()) {
					throw new BusinessException("Msg_3");
				}
				
				// ドメインモデル「日別勤務表の出力項目定型設定」を追加する (Add domain model「日別勤務表の出力項目定型設定」)
				OutputStandardSettingOfDailyWorkScheduleCommand standardCommand = new OutputStandardSettingOfDailyWorkScheduleCommand(
						command.getSelectionType()
						, companyId
						, Arrays.asList(command));
				this.standardSettingRepository.add(OutputStandardSettingOfDailyWorkSchedule.createFromMemento(standardCommand));
				
			}
			
			// 自由設定の場合
			if (command.getSelectionType() == ItemSelectionType.FREE_SETTING.value) {
				// 自由設定のコードから出力項目を取得 (Get the output item from free setup code)
				Optional<OutputItemDailyWorkSchedule> freeSettingDomain = this.freeSettingRepository
						.findByCompanyIdAndEmployeeIdAndCode(companyId, command.getEmployeeId(), command.getItemCode().v());
				
				// エラーメッセージ（ID:Msg_3）を表示する(Display error message (ID: Msg_3))
				if (freeSettingDomain.isPresent()) {
					throw new BusinessException("Msg_3");
				}
				
				// ドメインモデル「日別勤務表の出力項目自由設定」を追加 (Add domain model 「日別勤務表の出力項目自由設定」)
				FreeSettingOfOutputItemForDailyWorkScheduleCommand freeSettingCommand = new FreeSettingOfOutputItemForDailyWorkScheduleCommand(
						command.getSelectionType()
						, companyId
						, command.getEmployeeId()
						, Arrays.asList(command));
				this.freeSettingRepository.add(FreeSettingOfOutputItemForDailyWorkSchedule.createFromMemento(freeSettingCommand));
			}
		// IF 更新モード(Update mode)
		} else {
			this.repository.update(domain, command.getSelectionType(), companyId, command.getEmployeeId());
		}
	}
}
