package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionScopeDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionSettingDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ReExecutionConditionDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.shr.com.context.AppContexts;

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
	private boolean cloudCreationFlag;

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

	@Override
	public void setContractCode(String contractCode) {
		//not use
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

	@Override
	public void setCloudCreFlag(boolean cloudCreFlag) {
		this.cloudCreationFlag = cloudCreFlag;
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
		domain.setMemento(AppContexts.user().contractCode(), dto);
		return dto;
	}

}
