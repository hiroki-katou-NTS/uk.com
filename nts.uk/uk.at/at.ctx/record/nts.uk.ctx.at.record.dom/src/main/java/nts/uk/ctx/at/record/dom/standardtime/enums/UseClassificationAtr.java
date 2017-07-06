package nts.uk.ctx.at.record.dom.standardtime.enums;

/**
 * 
 * @author nampt
 * 
 */
public enum UseClassificationAtr {
	
	/*
	 * 使用区分
	 */
	// 0: 使用しない
	NOT_USE(0),
	// 1: 使用する
	USE(1);

	public final int value;
	
	private UseClassificationAtr(int type) {
		this.value = type;
	}

}
