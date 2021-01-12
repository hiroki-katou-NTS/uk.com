package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class BasicScheduleConfirmImport {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	// 予定確定区分
	private ConfirmedAtrImport confirmedAtr;

	/**
	 * 予定確定区分
	 */
	public static enum ConfirmedAtrImport {

		/** The unsettled. */
		// 未確定
		UNSETTLED(0, "Enum_ConfirmedAtrImport_unsettled", " 未確定"),
		// 確定済み
		CONFIRMED(1, "Enum_ConfirmedAtrImport_confirmed", "確定済み");

		/** The value. */
		public final int value;

		/** The name id. */
		public final String nameId;

		/** The description. */
		public final String description;

		/** The Constant values. */
		private final static ConfirmedAtrImport[] values = ConfirmedAtrImport.values();

		/**
		 * Instantiates a new re create atr.
		 *
		 * @param value       the value
		 * @param nameId      the name id
		 * @param description the description
		 */
		private ConfirmedAtrImport(int value, String nameId, String description) {
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
		public static ConfirmedAtrImport valueOf(Integer value) {
			// Invalid object.
			if (value == null) {
				return null;
			}

			// Find value.
			for (ConfirmedAtrImport val : ConfirmedAtrImport.values) {
				if (val.value == value) {
					return val;
				}
			}

			// Not found.
			return null;
		}
	}
}
