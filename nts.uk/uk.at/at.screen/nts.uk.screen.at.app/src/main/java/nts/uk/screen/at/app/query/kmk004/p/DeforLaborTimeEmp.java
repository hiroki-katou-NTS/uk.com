package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeforLaborTimeEmp {

	//雇用別変形労働法定労働時間
	private DeforLaborTimeEmp deforLaborTimeEmp;
	
	//雇用別変形労働集計設定
	private EmpDeforLaborMonthActCalSet setting;
	
}
