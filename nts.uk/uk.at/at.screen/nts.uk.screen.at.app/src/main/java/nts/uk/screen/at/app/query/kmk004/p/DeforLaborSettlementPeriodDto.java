package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborSettlementPeriod;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeforLaborSettlementPeriodDto {

	//開始月
	private Integer startMonth;

	//期間
	private Integer period;

	//繰り返し区分
	private boolean repeatAtr;
	
	public DeforLaborSettlementPeriod domain() {
		
		return new DeforLaborSettlementPeriod(
				new Month(startMonth == null ? 1 : startMonth), 
				new Month(period == null ? 1 : period), 
				repeatAtr);
	}

}
