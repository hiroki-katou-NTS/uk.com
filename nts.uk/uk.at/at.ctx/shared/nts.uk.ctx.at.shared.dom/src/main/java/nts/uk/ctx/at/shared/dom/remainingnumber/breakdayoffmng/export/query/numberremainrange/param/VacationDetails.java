package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         逐次発生の休暇明細一覧
 */
@AllArgsConstructor
@Getter
public class VacationDetails {

	// 休暇リスト
	private List<AccumulationAbsenceDetail> lstAcctAbsenDetail;
}
