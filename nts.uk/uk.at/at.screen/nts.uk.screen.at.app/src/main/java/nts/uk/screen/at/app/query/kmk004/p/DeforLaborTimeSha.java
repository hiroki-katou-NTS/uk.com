package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeforLaborTimeSha {
	
	//社員別変形労働法定労働時間
	private DeforLaborTimeSha deforLaborTimeSha;

	//社員別変形労働集計設定
	private ShaDeforLaborMonthActCalSet setting;
	
}
