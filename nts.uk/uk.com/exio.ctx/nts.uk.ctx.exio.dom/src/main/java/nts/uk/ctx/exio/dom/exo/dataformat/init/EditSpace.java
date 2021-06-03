package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum EditSpace {
	//削除しない
	DO_NOT_DELETE(0, "Enum_EditSpace_DO_NOT_DELETE"),
	//前スペースを削除
	DELETE_SPACE_BEFORE(1, "Enum_EditSpace_DELETE_SPACE_BEFORE"),
	//後スペースを削除
	DELETE_SPACE_AFTER(2, "Enum_EditSpace_DELETE_SPACE_AFTER");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private EditSpace(int value, String nameId) {
		this.value = value;
	this.nameId = nameId;
	}
}
