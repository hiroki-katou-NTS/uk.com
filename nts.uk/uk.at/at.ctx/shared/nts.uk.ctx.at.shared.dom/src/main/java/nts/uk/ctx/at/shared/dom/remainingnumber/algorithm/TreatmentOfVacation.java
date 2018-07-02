package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;

/**
 * 休暇の扱い
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum TreatmentOfVacation {
	/**	振出として扱わない */
	NOTCASHIER(0),
	/**	午前振出として扱う */
	MORNINGPICKUP(1),
	/**	午後振出として扱う */
	AFTERNOONPICKUP(2),
	/**	１日振出として扱う */
	ONEDAYHANDOUT(3);
	public final Integer value;
}
