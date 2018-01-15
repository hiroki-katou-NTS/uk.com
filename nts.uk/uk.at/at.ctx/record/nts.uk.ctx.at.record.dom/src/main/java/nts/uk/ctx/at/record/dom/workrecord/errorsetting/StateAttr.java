package nts.uk.ctx.at.record.dom.workrecord.errorsetting;

//状態区分
public enum StateAttr {

	// 重複なし
	NO_DUPLICATION(0),

	// 重複あり
	DUPLICATION(1);
	
	public int value;

	private StateAttr(int value) {
		this.value = value;
	}
}
