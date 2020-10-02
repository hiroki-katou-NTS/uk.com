package nts.uk.ctx.sys.assist.dom.storage;

/**
 * 手動自動区分
 */
public enum StorageClassification {
	
	//手動
	MANUAL(0, "Enum_StorageClassification_MANUAL"),
	//手動
	AUTO(1, "Enum_StorageClassification_AUTO");
	
	public final int value;
	public final String nameId;
	
	private StorageClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
