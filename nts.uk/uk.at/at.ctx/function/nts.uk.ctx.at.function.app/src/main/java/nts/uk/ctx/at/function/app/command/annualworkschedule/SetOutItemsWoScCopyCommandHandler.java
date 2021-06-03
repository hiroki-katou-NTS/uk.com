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
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SetOutItemsWoScCopyCommandHandler.
 * 
 * @author LienPTK
 *
 */
@Stateless
@Transactional
public class SetOutItemsWoScCopyCommandHandler extends CommandHandler<SetOutItemsWoScCopyCommand> {

	@Inject
	private SetOutputItemOfAnnualWorkSchRepository repository;

	/**
	 * アルゴリズム「レイアウト情報を複製する」を実行する
	 *
	 */
	@Override
	protected void handle(CommandHandlerContext<SetOutItemsWoScCopyCommand> context) {
		String companyId = AppContexts.user().companyId();
		SetOutItemsWoScCopyCommand command = context.getCommand();
		Optional<String> employeeId = command.getSelectedType() == 0
				? employeeId = Optional.empty()
				: Optional.of(AppContexts.user().employeeId());
		
		// ドメインモデル「年間勤務表の出力項目設定」で コード重複チェックを行う
		Optional<SettingOutputItemOfAnnualWorkSchedule> duplicateItem = this.repository.findByCode(
				command.getDuplicateCode(),
				employeeId,
				companyId,
				command.getSelectedType(),
				command.getPrintFormat()
		);
		
		// 重複する場合
		if (duplicateItem.isPresent()) {
			throw new BusinessException("Msg_1776");
		}

		Optional<SettingOutputItemOfAnnualWorkSchedule> outputItem = this.repository.findByLayoutId(command.getLayoutId());

		//複製元の存在チェックを行う
		if (!outputItem.isPresent()) {
			// 複製元出力項目が存在しない場合
			throw new BusinessException("Msg_1946");
		} else {
			String duplicateId = UUID.randomUUID().toString();
			outputItem.get().setCd(new OutItemsWoScCode(command.getDuplicateCode()));
			outputItem.get().setName(new OutItemsWoScName(command.getDuplicateName()));
			outputItem.get().setLayoutId(duplicateId);
			// 複製元出力項目が存在する場合
			this.repository.add(outputItem.get());
		}	
	}

}
