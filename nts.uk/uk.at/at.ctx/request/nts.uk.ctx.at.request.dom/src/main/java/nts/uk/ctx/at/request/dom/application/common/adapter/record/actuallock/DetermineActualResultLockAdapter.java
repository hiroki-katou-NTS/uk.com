package nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock;

import nts.arc.time.GeneralDate;

public interface DetermineActualResultLockAdapter {

	LockStatus lockStatus(String companyId, GeneralDate baseDate, Integer closureId, PerformanceType type);

	public static enum LockStatus {
		/** The lock. */
		LOCK(1, "LOCK"),

		/** The unlock. */
		UNLOCK(0, "UNLOCK");

		/** The value. */
		public final int value;

		/** The name id. */
		public final String nameId;

		/** The Constant values. */
		private final static LockStatus[] values = LockStatus.values();

		/**
		 * Instantiates a new lock status.
		 *
		 * @param value       the value
		 * @param nameId      the name id
		 * @param description the description
		 */
		private LockStatus(int value, String nameId) {
			this.value = value;
			this.nameId = nameId;
		}

		/**
		 * Value of.
		 *
		 * @param value the value
		 * @return the lock status
		 */
		public static LockStatus valueOf(Integer value) {
			if (value == null) {
				return null;
			}

			for (LockStatus val : LockStatus.values) {
				if (val.value == value) {
					return val;
				}
			}
			return null;
		}
	}

	public static enum PerformanceType {

		DAILY(0, "DAILY", "日別"),

		MONTHLY(1, "MONTHLY", "月別");

		/** The value. */
		public final int value;

		/** The name id. */
		public final String nameId;

		/** The description. */
		public final String description;

		private PerformanceType(int value, String nameId, String description) {
			this.value = value;
			this.nameId = nameId;
			this.description = description;
		}
	}

}
