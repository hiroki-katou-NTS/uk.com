package nts.uk.ctx.at.function.dom.processexecution;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 更新処理実行種別
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public enum ProcessExecType {
	
	/**通常実行*/
	NORMAL_EXECUTION(0,"Enum_ProcessExecType_NORMAL_EXECUTION"),
	
	/**再作成*/
	RE_CREATE(1,"Enum_ProcessExecType_RE_CREATE");
	
public int value;
	
	public String nameId;
	
	private ProcessExecType (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
