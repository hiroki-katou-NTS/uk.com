package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

@Data
@AllArgsConstructor
public class SelectedProcessExecutionDto {
	
	/**
	 * 実行タスク設定
	 */
	private UpdateProcessAutoExecutionDto processExecution;
	
	/**
	 * 職場情報一覧
	 */
	private List<WorkplaceInfor> workplaceInfos;
}
