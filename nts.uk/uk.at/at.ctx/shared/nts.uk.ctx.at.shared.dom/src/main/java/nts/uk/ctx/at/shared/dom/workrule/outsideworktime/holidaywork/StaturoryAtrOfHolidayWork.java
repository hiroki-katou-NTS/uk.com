package nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork;

import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * 休日出勤の法定区分
 * @author keisuke_hoshina
 *
 */
public enum StaturoryAtrOfHolidayWork {
	/**法定内休出	 */
	WithinPrescribedHolidayWork,
	/**法定外休出	 */
	ExcessOfStatutoryHolidayWork,
	/**祝日休出	 */
	PublicHolidayWork;
	
	public static StaturoryAtrOfHolidayWork deicisionAtrByHolidayAtr(HolidayAtr atr) {
		switch(atr) {
			case STATUTORY_HOLIDAYS:
				return WithinPrescribedHolidayWork;
			case NON_STATUTORY_HOLIDAYS:
				return ExcessOfStatutoryHolidayWork;
			case PUBLIC_HOLIDAY:
				return PublicHolidayWork;
			default:
				throw new RuntimeException("unknown holidayAtr:"+atr);
		}
	}
}
