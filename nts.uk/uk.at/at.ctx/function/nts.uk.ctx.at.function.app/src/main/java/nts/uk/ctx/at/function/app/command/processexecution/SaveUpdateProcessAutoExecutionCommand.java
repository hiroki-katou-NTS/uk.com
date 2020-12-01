package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Data;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionScopeDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionSettingDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ReExecutionConditionDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

@Data
public class SaveUpdateProcessAutoExecutionCommand implements UpdateProcessAutoExecution.MementoGetter {
	
	private boolean newMode;
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * コード
	 */
	private String execItemCode;

	/**
	 * 名称
	 */
	private String execItemName;

	/**
	 * 実行範囲
	 */
	private ProcessExecutionScopeDto execScope;

	/**
	 * 実行設定
	 */
	private ProcessExecutionSettingDto execSetting;

	/**
	 * 実行種別
	 */
	private int executionType;

	/**
	 * 再実行条件
	 */
	private ReExecutionConditionDto reExecCondition;

	/**
	 * クラウド作成フラグ
	 */
	private boolean cloudCreationFlag;
	
	/**
	 * タスク有効設定
	 */
	private boolean enableSetting;

	@Override
	public ProcessExecutionScope getExecScope() {
		return this.execScope.toDomain();
	}

	@Override
	public ProcessExecutionSetting getExecSetting() {
		return this.execSetting.toDomain();
	}

	@Override
	public ReExecutionCondition getReExecCondition() {
		return this.reExecCondition.toDomain();
	}

	@Override
	public boolean getCloudCreFlag() {
		return this.cloudCreationFlag;
	}
}
