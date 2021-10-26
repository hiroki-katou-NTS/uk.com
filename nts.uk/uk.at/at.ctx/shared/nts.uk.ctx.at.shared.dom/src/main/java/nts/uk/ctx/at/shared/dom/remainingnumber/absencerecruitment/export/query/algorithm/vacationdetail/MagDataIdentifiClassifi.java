package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         管理データ識別区分
 */
@AllArgsConstructor
public enum MagDataIdentifiClassifi {

	// 暫定発生
	TEMP_OCC(0, "暫定発生"),

	// 暫定消化
	TEMP_DIGEST(0, "暫定消化"),

	// 確定発生
	CONFIRM_OCC(0, "確定発生"),

	// 確定消化
	CONFIRM_DIGEST(0, "確定消化");

	public final int value;

	public final String name;

}
