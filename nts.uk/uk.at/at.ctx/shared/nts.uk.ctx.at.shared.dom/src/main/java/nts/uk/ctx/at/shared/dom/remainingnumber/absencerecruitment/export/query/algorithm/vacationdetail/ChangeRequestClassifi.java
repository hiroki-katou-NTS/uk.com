package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *変更要求区分
 */
@AllArgsConstructor
public enum ChangeRequestClassifi {

	PERIOD(0, "期間"),
	
	DATE(1, "年月日");
	
	public final int value;
	
	public final String name;
	
}
