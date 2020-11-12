package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class BasicScheduleConfirmExport {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	// 予定確定区分
	private ConfirmedAtrExport confirmedAtr;

	/**
	 * 予定確定区分
	 */
	public static enum ConfirmedAtrExport {

		/** The unsettled. */
		// 未確定
		UNSETTLED(0, "Enum_ConfirmedAtrExport_unsettled", " 未確定"),
		// 確定済み
		CONFIRMED(1, "Enum_ConfirmedAtrExport_confirmed", "確定済み");

		/** The value. */
		public final int value;

		/** The name id. */
		public final String nameId;

		/** The description. */
		public final String description;

		/** The Constant values. */
		private final static ConfirmedAtrExport[] values = ConfirmedAtrExport.values();

		/**
		 * Instantiates a new re create atr.
		 *
		 * @param value       the value
		 * @param nameId      the name id
		 * @param description the description
		 */
		private ConfirmedAtrExport(int value, String nameId, String description) {
			this.value = value;
			this.nameId = nameId;
			this.description = description;
		}

		/**
		 * Value of.
		 *
		 * @param value the value
		 * @return the re create atr
		 */
		public static ConfirmedAtrExport valueOf(Integer value) {
			// Invalid object.
			if (value == null) {
				return null;
			}

			// Find value.
			for (ConfirmedAtrExport val : ConfirmedAtrExport.values) {
				if (val.value == value) {
					return val;
				}
			}

			// Not found.
			return null;
		}
	}
}
