package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionScopeDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionSettingDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ReExecutionConditionDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProcessExecutionCommand implements UpdateProcessAutoExecution.MementoGetter {

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
	 * Gets execution scope.
	 *
	 * @return the domain execution scope
	 */
	public ProcessExecutionScope getExecScope() {
		return this.execScope.toDomain();
	}

	/**
	 * Gets execution setting.
	 *
	 * @return the domain execution setting
	 */
	public ProcessExecutionSetting getExecSetting() {
		return this.execSetting.toDomain();
	}

	/**
	 * Gets re-execution condition.
	 *
	 * @return the domain re-execution condition
	 */
	public ReExecutionCondition getReExecCondition() {
		return this.reExecCondition.toDomain();
	}

	/**
	 * Gets cloud creation flag.
	 *
	 * @return the cloud creation flag
	 */
	@Override
	public boolean getCloudCreFlag() {
		return this.cloudCreationFlag;
	}

}
