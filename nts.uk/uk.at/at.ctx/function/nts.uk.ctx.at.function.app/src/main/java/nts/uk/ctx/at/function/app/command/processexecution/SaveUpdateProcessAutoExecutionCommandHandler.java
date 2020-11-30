package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
/**
 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.B:実行設定.アルゴリズム.登録ボタン押下時処理.登録ボタン押下時処理
 */
public class SaveUpdateProcessAutoExecutionCommandHandler extends CommandHandler<SaveUpdateProcessAutoExecutionCommand> {

	@Inject
	private ProcessExecutionRepository processExecutionRepository;

	@Inject
	private ProcessExecutionLogManageRepository processExecutionLogManageRepository;
	
	@Inject
	private LastExecDateTimeRepository lastExecDateTimeRepository;

	@Override
	protected void handle(CommandHandlerContext<SaveUpdateProcessAutoExecutionCommand> context) {
		SaveUpdateProcessAutoExecutionCommand command = context.getCommand();
		UpdateProcessAutoExecution domain = UpdateProcessAutoExecution.createFromMemento(AppContexts.user().companyId(),
				command);
		// アルゴリズム「登録チェック処理」を実行する
		domain.validate();
		// 画面モードチェック
		if (command.isNewMode()) {
			// 新規モード
			// 更新処理自動実行項目コードは重複してはならない
			// #Msg_3
			if (this.processExecutionRepository
					.getProcessExecutionByCidAndExecCd(AppContexts.user().companyId(), domain.getExecItemCode().v())
					.isPresent()) {
				throw new BusinessException("Msg_3");
			}
			// アルゴリズム「新規登録処理」を実行する
			this.processRegister(domain);
		} else {
			// 更新モード
			this.processUpdate(domain);
		}
	}

	private void processRegister(UpdateProcessAutoExecution domain) {
		String cid = AppContexts.user().companyId();
		// ドメインモデル「更新処理自動実行」に新規登録する
		this.processExecutionRepository.insert(domain);
		// ドメインモデル「更新処理自動実行管理」に新規登録する
		this.processExecutionLogManageRepository.insert(ProcessExecutionLogManage.builder()
				.companyId(cid)
				.currentStatus(Optional.of(CurrentExecutionStatus.INVALID))
				.execItemCd(domain.getExecItemCode())
				.overallStatus(Optional.of(EndStatus.NOT_IMPLEMENT))
				.build());
		// ドメインモデル「更新処理前回実行日時」に新規登録する
		this.lastExecDateTimeRepository.insert(new LastExecDateTime(cid, domain.getExecItemCode(), null));
	}

	private void processUpdate(UpdateProcessAutoExecution domain) {
		// ドメインモデル「更新処理自動実行管理」を取得し、現在の実行状態を判断する
		Optional<ProcessExecutionLogManage> optLogMng = this.processExecutionLogManageRepository
				.getLogByCIdAndExecCd(AppContexts.user().companyId(), domain.getExecItemCode().v());
		optLogMng.ifPresent(logManage -> {
			// 更新処理自動実行管理.現在の実行状態　＝　実行中
			if (logManage.getCurrentStatus().isPresent() &&
					logManage.getCurrentStatus().get().equals(CurrentExecutionStatus.RUNNING)) {
				// エラーメッセージ「#Msg_1318」を表示する
				throw new BusinessException("Msg_1318");
			}
			// ドメインモデル「更新処理自動実行」に更新登録する
			// ドメインモデル「実行タスク設定」を更新する
			this.processExecutionRepository.update(domain);
		});
	}
}
