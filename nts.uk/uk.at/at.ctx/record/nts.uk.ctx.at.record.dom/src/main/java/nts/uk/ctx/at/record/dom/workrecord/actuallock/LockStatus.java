/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

/**
 * The Enum LockStatus. ロック状態
 */
public enum LockStatus {

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
	private final static LockStatus[] values = LockStatus.values();

	/**
	 * Instantiates a new lock status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private LockStatus(int value, String nameId, String description) {
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
	public static LockStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		
		// Find Value.
		for(LockStatus val: LockStatus.values) {
			if (val.value == value) {
				return val;
			}
		}
		
		// Not Found.
		return null;
	}
}
