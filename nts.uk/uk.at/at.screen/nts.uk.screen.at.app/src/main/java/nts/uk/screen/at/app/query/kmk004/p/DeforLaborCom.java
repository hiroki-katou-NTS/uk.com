package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeforLaborCom {

	// 会社別変形労働法定労働時間
	private DeforLaborTimeCom deforLaborTimeCom;

	// 会社別変形労働集計設定
	private ComDeforLaborMonthActCalSet setting;

}
