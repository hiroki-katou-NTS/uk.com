package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BreakDayOffOutputHisData {
	/**
	 * 休出代休履歴対照情報
	 */
	List<BreakDayOffHistory> lstHistory;
	/**
	 * 代休残数集計情報
	 */
	AsbRemainTotalInfor totalInfor;
}
