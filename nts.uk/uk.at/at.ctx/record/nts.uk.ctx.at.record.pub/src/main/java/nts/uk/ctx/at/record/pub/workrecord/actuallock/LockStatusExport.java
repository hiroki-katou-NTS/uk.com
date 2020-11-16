package nts.uk.ctx.at.record.pub.workrecord.actuallock;

public enum LockStatusExport {

	/** The lock. */
	LOCK(1, "LOCK", "ロック"),
	
	/** The unlock. */
	UNLOCK(0, "UNLOCK", "アンロック");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;
	
	/** The Constant values. */
	private final static LockStatusExport[] values = LockStatusExport.values();

	/**
	 * Instantiates a new lock status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private LockStatusExport(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the lock status
	 */
	public static LockStatusExport valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		
		// Find Value.
		for(LockStatusExport val: LockStatusExport.values) {
			if (val.value == value) {
				return val;
			}
		}
		
		// Not Found.
		return null;
	}
}
