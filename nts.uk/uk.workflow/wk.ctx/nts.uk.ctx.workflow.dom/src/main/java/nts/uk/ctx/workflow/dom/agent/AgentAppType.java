package nts.uk.ctx.workflow.dom.agent;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(1)
public enum AgentAppType {
	// 0:代行者指定
	SUBSTITUTE_DESIGNATION(0),
	// 1:パス
	PATH(1),
	// 2:設定なし
	NO_SETTINGS(2);
	public final int value;
	
	private AgentAppType(int value){
		this.value = value;
	}
}
	