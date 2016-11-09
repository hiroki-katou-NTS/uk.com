package nts.uk.ctx.pr.proto.dom.itemmaster;
/** 表示区分 */
public enum DisplayAtr {
	//0:表示しない
	NO_DISPLAY(0),
	//1:表示する
	DISPLAY(1);
	public final int value;

	/** 値 */
	public int value() {
		return value;
	}
	/**
	 * Constructor.
	 * 
	 * @param 賃金対象区分の値
	 */
	private DisplayAtr(int value) {
		this.value = value;
	}
	public DisplayAtr valueOf(int value){
		switch (value) {
		case 0:
			return NO_DISPLAY;
		case 1:
			return DISPLAY;
		default:
			throw new RuntimeException("Invalid value of DisplayAtr");
		}
	}
}
