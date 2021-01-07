package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

@Data
@NoArgsConstructor
public class SelectedProcessExecutionDto {
	
	/**
	 * 実行タスク設定
	 */
	private ExecutionTaskSettingDto taskSetting;
	
	/**
	 * 更新処理自動実行
	 */
	private UpdateProcessAutoExecutionDto processExecution;
	
	/**
	 * 職場情報一覧
	 */
	private List<WorkplaceInfor> workplaceInfos;
}
