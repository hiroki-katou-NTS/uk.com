package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;

/**
 * 発生消化区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum OccurrenceDigClass {
	/**
	 * 発生
	 */
	OCCURRENCE(0),
	/**消化	 */
	DIGESTION(1);
	public final Integer value;
}
