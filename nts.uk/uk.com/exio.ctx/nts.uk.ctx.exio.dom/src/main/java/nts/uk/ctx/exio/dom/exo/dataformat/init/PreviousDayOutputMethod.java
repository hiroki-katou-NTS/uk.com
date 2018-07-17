package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum PreviousDayOutputMethod {
	
	//２４時間表記で出力
	FORMAT24HOUR(0, "Enum_PreviousDayOutputMethod_24HOUR"),
	//0:00で出力
	FORMAT0H00(1, "Enum_PreviousDayOutputMethod_FORMAT0H00"),
	//マイナスで出力
	WITHMINUS(2, "Enum_PreviousDayOutputMethod_WITHMINUS");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private PreviousDayOutputMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
