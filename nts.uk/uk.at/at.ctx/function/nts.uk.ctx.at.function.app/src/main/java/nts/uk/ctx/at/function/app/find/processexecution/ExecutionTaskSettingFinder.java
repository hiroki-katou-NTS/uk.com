package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionTaskSettingDto;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class Execution task setting finder.
 */
@Stateless
public class ExecutionTaskSettingFinder {

	/** The Execution task setting repository */
	@Inject
	private ExecutionTaskSettingRepository execTaskRepo;

	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.C:実行タスク設定.アルゴリズム.起動時処理.起動時処理
	 *
	 * @param execItemCd 取得した更新処理自動実行コード
	 * @return the Execution Task Setting Dto
	 */
	public ExecutionTaskSettingDto find(String execItemCd) {
		// ログイン社員の会社ID
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「実行タスク設定」を取得する
		Optional<ExecutionTaskSetting> taskSettingOpt = this.execTaskRepo.getByCidAndExecCd(companyId, execItemCd);
		if (taskSettingOpt.isPresent()) {
			// データが存在する
			return Optional.of(ExecutionTaskSettingDto.fromDomain(taskSettingOpt.get())).get();
		}
		// データが存在しない
		return null;
	}
}
