package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeforLaborWkpDto {
	
	//職場別変形労働法定労働時間
	private DeforLaborTimeWkp deforLaborTimeWkp;
	
	//職場別変形労働集計設定
	private WkpDeforLaborMonthActCalSet setting;
	
}
