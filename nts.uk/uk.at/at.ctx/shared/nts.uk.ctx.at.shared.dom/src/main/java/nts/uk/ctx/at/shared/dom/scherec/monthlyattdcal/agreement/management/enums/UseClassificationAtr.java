package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums;

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
