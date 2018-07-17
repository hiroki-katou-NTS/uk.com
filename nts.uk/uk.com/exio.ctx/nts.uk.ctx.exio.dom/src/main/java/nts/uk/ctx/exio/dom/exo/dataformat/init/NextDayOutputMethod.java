package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum NextDayOutputMethod {
	
	//２４時間表記で出力
	OUT_PUT_24HOUR(0, "Enum_NextDayOutputMethod_24HOUR"),
	//４８時間表記で出力
	OUT_PUT_48HOUR(1, "Enum_NextDayOutputMethod_48HOUR");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private NextDayOutputMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
