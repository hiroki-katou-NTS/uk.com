package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BreakDayOffInterimMngData {
	/**
	 * 暫定休出管理データ
	 */
	List<InterimBreakMng> lstBreakMng;
	/**
	 * 暫定代休管理データ
	 */
	List<InterimDayOffMng> lstDayOffMng;
	/**
	 * 暫定休出代休紐付け管理
	 */
	List<InterimBreakDayOffMng> lstBreakDayOffMng;
}
