package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

/**
 * The class Update process auto execution dto.<br>
 * Dto 更新処理自動実行
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class UpdateProcessAutoExecutionDto implements UpdateProcessAutoExecution.MementoSetter {

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
	private boolean cloudCreFlag;

	/**
	 * No args constructor.
	 */
	private UpdateProcessAutoExecutionDto() {
	}

	/**
	 * Sets execution scope.
	 *
	 * @param execScope the domain execution scope
	 */
	@Override
	public void setExecScope(ProcessExecutionScope execScope) {
		this.execScope = ProcessExecutionScopeDto.fromDomain(execScope);
	}

	/**
	 * Sets execution setting.
	 *
	 * @param execSetting  the domain execution setting
	 */
	@Override
	public void setExecSetting(ProcessExecutionSetting execSetting) {
		this.execSetting = ProcessExecutionSettingDto.createFromDomain(execSetting);
	}

	/**
	 * Sets re-execution condition.
	 *
	 * @param reExecCondition the domain re-execution condition
	 */
	@Override
	public void setReExecCondition(ReExecutionCondition reExecCondition) {
		this.reExecCondition = ReExecutionConditionDto.createFromDomain(reExecCondition);
	}

	/**
	 * Sets cloud creation flag.
	 *
	 * @param cloudCreFlag the cloud creation flag
	 */
	@Override
	public void setCloudCreFlag(boolean cloudCreFlag) {
		this.cloudCreFlag = cloudCreFlag;
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Update process auto execution dto.
	 */
	public static UpdateProcessAutoExecutionDto createFromDomain(UpdateProcessAutoExecution domain) {
		if (domain == null) {
			return null;
		}
		UpdateProcessAutoExecutionDto dto = new UpdateProcessAutoExecutionDto();
		domain.setMemento(dto);
		return dto;
	}

}
